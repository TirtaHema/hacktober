package advprog.bot.feature.scheduler;

import advprog.bot.feature.scheduler.resources.Schedule;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

public class SchedulerChatHandler extends AbstractLineChatHandlerDecorator {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    private static final String[] HOURS = new String[]{"00:00-01:00", "01:00-02:00", "02:00-03:00",
                                                       "03:00-04:00", "04:00-05:00", "05:00-06:00",
                                                       "06:00-07:00", "07:00-08:00", "08:00-09:00",
                                                       "09:00-10:00", "10:00-11:00", "11:00-12:00",
                                                       "12:00-13:00", "13:00-14:00", "14:00-15:00",
                                                       "15:00-16:00", "16:00-17:00", "17:00-18:00",
                                                       "18:00-19:00", "19:00-20:00", "20:00-21:00",
                                                       "21:00-22:00", "22:00-23:00", "23:00-00:00"};

    private static final Logger LOGGER = Logger.getLogger(SchedulerChatHandler.class.getName());

    private HashMap<String, HashMap<String, TreeSet<String>>> groupFreeSchedule
            = new HashMap<>();
    private HashMap<String, TreeMap<String, TreeSet<Schedule>>> groupUsedSchedule
            = new HashMap<>();
    private HashMap<String, String> userRequestGroup
            = new HashMap<>();
    private HashMap<String, String> userRequestDate
            = new HashMap<>();
    private HashMap<String, String> userRequestTime
            = new HashMap<>();

    public SchedulerChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Scheduler chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        Source source = event.getSource();
        if (source instanceof GroupSource) {
            String command = event.getMessage().getText().split(" ")[0];
            return command.equals("/create_schedule") || command.equals("jadwal");
        } else if (source instanceof UserSource) {
            String userId = event.getSource().getSenderId();
            return userRequestGroup.containsKey(userId);
        } else {
            return false;
        }
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String command = event.getMessage().getText().split(" ")[0];
        Source source = event.getSource();

        try {
            if (source instanceof GroupSource) {
                if (command.equals("/create_schedule")) {
                    String userId = source.getUserId();

                    lineMessagingClient.pushMessage(new PushMessage(userId,
                            Collections.singletonList(
                                    new TextMessage("Masukan tanggal dalam format yyyy-mm-dd\n"
                                            + "e.g. 2017-05-15")
                            )
                    ));

                    String groupId = ((GroupSource) source).getGroupId();
                    userRequestGroup.put(userId, groupId);

                    return Collections.singletonList(
                            new TextMessage("Pilih waktu yang diinginkan pada private chat")
                    );
                } else {
                    String groupId = ((GroupSource) source).getGroupId();
                    String date = event.getTimestamp().toString().substring(0, 10);

                    if (isJadwalExists(groupId, date)) {
                        TreeSet<Schedule> schedules = groupUsedSchedule.get(groupId).get(date);

                        StringBuilder response = new StringBuilder();
                        int lineCounter = 1;
                        for (Schedule schedule : schedules) {
                            response.append(Integer.toString(lineCounter))
                                    .append(". ").append(schedule.getDescription()).append("\n");
                            lineCounter++;
                        }

                        return Collections.singletonList(
                                new TextMessage(response.toString())
                        );
                    }
                    return Collections.singletonList(
                            new TextMessage("Tidak terdapat jadwal apapun untuk hari ini")
                    );
                }
            } else {
                String userId = source.getSenderId();

                if (userRequestGroup.containsKey(userId)
                        && isDateMessage(event.getMessage().getText())) {
                    String groupId = userRequestGroup.get(userId);

                    if (!groupFreeSchedule.containsKey(groupId)) {
                        groupFreeSchedule.put(groupId, new HashMap<>());
                    }

                    String date = event.getMessage().getText();

                    if (!groupFreeSchedule.get(groupId).containsKey(date)) {
                        groupFreeSchedule.get(groupId).put(date, new TreeSet<>());
                        initDayFreeHours(groupId, date);
                    }

                    userRequestDate.put(userId, date);
                    TreeSet<String> hours = groupFreeSchedule.get(groupId).get(date);

                    int itemCounter = 0;
                    int carouselIndex = 0;
                    List<List<Action>> hoursOpts = new ArrayList<>();
                    hoursOpts.add(new ArrayList<>());
                    for (String hour : hours) {
                        if (itemCounter == 3) {
                            itemCounter = 0;
                            carouselIndex++;
                            hoursOpts.add(new ArrayList<>());
                        }
                        if (hour.contains("Used")) {
                            hoursOpts.get(carouselIndex).add(new PostbackAction(
                                    hour, hour.substring(0, 11), hour.substring(0, 11)));
                        } else {
                            hoursOpts.get(carouselIndex).add(new PostbackAction(hour, hour, hour));
                        }

                        itemCounter++;
                    }

                    List<CarouselColumn> carouselCols = new ArrayList<>();
                    for (List<Action> row : hoursOpts) {
                        carouselCols.add(new CarouselColumn(
                                null, "Set Waktu Kegiatan",
                                "Pilih Waktu yang Diinginkan", row));
                    }

                    CarouselTemplate carouselTemplate = new CarouselTemplate(
                            carouselCols
                    );

                    TemplateMessage templateMessage =
                            new TemplateMessage(
                                    "carousel alt text", carouselTemplate);

                    return Collections.singletonList(
                            templateMessage
                    );
                } else if (userRequestDate.containsKey(userId)
                        && isTimeMessage(event.getMessage().getText())) {

                    String groupId = userRequestGroup.get(userId);
                    String date = userRequestDate.get(userId);
                    String time = event.getMessage().getText();

                    if (!groupFreeSchedule.get(groupId).get(date).contains(time)) {
                        return Collections.singletonList(
                                new TextMessage("Waktu telah dipakai untuk kegiatan lain.\n"
                                        +  "Silahkan pilih waktu yang belum terpakai.")
                        );
                    }

                    userRequestTime.put(userId, time);

                    return Collections.singletonList(
                            new TextMessage("Masukan Deskripsi")
                    );
                } else if (userRequestTime.containsKey(userId)) {
                    String groupId = userRequestGroup.get(userId);
                    String date = userRequestDate.get(userId);
                    String requestedHour = userRequestTime.get(userId);
                    String description = event.getMessage().getText();

                    if (!groupUsedSchedule.containsKey(groupId)) {
                        groupUsedSchedule.put(groupId, new TreeMap<>());
                    }
                    if (!groupUsedSchedule.get(groupId).containsKey(date)) {
                        groupUsedSchedule.get(groupId).put(date, new TreeSet<>());
                    }

                    groupUsedSchedule.get(groupId).get(date)
                            .add(new Schedule(requestedHour, description));

                    TreeSet<String> hours = groupFreeSchedule.get(groupId).get(date);

                    hours.remove(requestedHour);
                    hours.add(requestedHour + " (Used)");
                    userRequestGroup.remove(userId);
                    userRequestDate.remove(userId);
                    userRequestTime.remove(userId);

                    String userDisplayName = lineMessagingClient
                            .getProfile(userId).get().getDisplayName();
                    String response = userDisplayName + " telah menambahkan jadwal baru"
                            + " pada tanggal " + date + " pukul " + requestedHour + "\n\n"
                            + "Deskripsi :\n" + description;

                    lineMessagingClient.pushMessage(new PushMessage(groupId,
                            Collections.singletonList(
                                    new TextMessage(response)
                            )
                    ));

                    return Collections.singletonList(
                            new TextMessage("Jadwal baru telah ditambahkan!")
                    );
                } else {
                    return Collections.singletonList(
                            new TextMessage("Pastikan input Anda sesuai dengan perintah")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(
                    new TextMessage("Terjadi kesalahan/error!")
            );
        }
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }

    private void initDayFreeHours(String groupId, String date) {
        TreeSet<String> set = groupFreeSchedule.get(groupId).get(date);

        set.addAll(Arrays.asList(HOURS));
    }

    private boolean isDateMessage(String message) {
        String[] msgSplit = message.split("-");

        if (msgSplit.length == 3) {
            return msgSplit[0].length() == 4 && msgSplit[1].length() == 2
                    && msgSplit[2].length() == 2 && isDigit(msgSplit[0])
                    && isDigit(msgSplit[1]) && isDigit(msgSplit[2]);
        } else {
            return false;
        }
    }

    private boolean isTimeMessage(String message) {
        String[] msgSplit = message.split("-");

        if (msgSplit.length < 2) {
            return false;
        }

        String[] leftSplit = msgSplit[0].split(":");
        String[] rightSplit = msgSplit[1].split(":");

        return leftSplit[0].length() == 2 && leftSplit[1].length() == 2
                && isDigit(leftSplit[0]) && isDigit(leftSplit[1])
                && rightSplit[0].length() == 2 && rightSplit[1].length() == 2
                && isDigit(rightSplit[0]) && isDigit(rightSplit[1]);
    }

    private boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isJadwalExists(String groupId, String date) {
        if (!groupUsedSchedule.containsKey(groupId)) {
            return false;
        }

        TreeMap<String, TreeSet<Schedule>> groupSchedule = groupUsedSchedule.get(groupId);

        if (!groupSchedule.containsKey(date)) {
            return false;
        }

        TreeSet<Schedule> groupScheduleWithDate = groupSchedule.get(date);

        return groupScheduleWithDate.size() > 0;
    }
}
