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

import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
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
        return true;
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
                            new TextMessage("")
                    );
                } else if (command.equals("jadwal")) {
                    return Collections.singletonList(
                            new TextMessage("")
                    );
                } else {
                    return Collections.singletonList(
                            new TextMessage("")
                    );
                }
            } else if (source instanceof UserSource) {
                String userId = source.getSenderId();

                if (userRequestGroup.containsKey(userId)) {

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

                    List<Action> hoursOpts = new ArrayList<>();
                    for (String hour : hours) {
                        hoursOpts.add(new PostbackAction(hour, hour, hour));
                    }

                    CarouselTemplate carouselTemplate = new CarouselTemplate(
                            Arrays.asList(
                                    new CarouselColumn(null, "Jadwal", "Jadwal", hoursOpts)
                            )
                    );

                    TemplateMessage templateMessage =
                            new TemplateMessage(
                                    "carousel alt text", carouselTemplate);

                    return Collections.singletonList(
                            templateMessage
                    );
                } else if (userRequestDate.containsKey(userId)) {

                    String time = event.getMessage().getText();
                    userRequestTime.put(userId, time);

                    return Collections.singletonList(
                            new TextMessage("Masukan deskripsi.")
                    );
                } else if (userRequestTime.containsKey(userId)) {
                    String groupId = ((GroupSource) source).getGroupId();
                    String date = userRequestDate.get(userId);
                    String requestedHour = userRequestTime.get(userId);
                    String description = event.getMessage().getText();
                    groupUsedSchedule.get(groupId).get(date)
                            .add(new Schedule(requestedHour, description));

                    String userDisplayName = lineMessagingClient
                            .getProfile(userId).get().getDisplayName();
                    String response = userDisplayName + " telah menambahkan jadwal baru"
                            + " pada tanggal " + date + " pukul " + requestedHour;
                    lineMessagingClient.pushMessage(new PushMessage(groupId,
                            Collections.singletonList(
                                    new TextMessage(response)
                            )
                    ));

                    TreeSet<String> hours = groupFreeSchedule.get(groupId).get(date);

                    hours.remove(requestedHour);
                    userRequestGroup.remove(userId);
                    userRequestDate.remove(userId);
                    userRequestTime.remove(userId);

                    return Collections.singletonList(
                            new TextMessage("Jadwal baru telah ditambahkan!")
                    );
                } else {
                    return Collections.singletonList(
                            new TextMessage("!!!")
                    );
                }
            } else {
                return Collections.singletonList(
                        new TextMessage("!!!")
                );
            }
        } catch (Exception e) {
            return Collections.singletonList(
                    new TextMessage("")
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

        for (String hour : HOURS) {
            set.add(hour);
        }
    }

    private boolean is_date_message(String message) {
        String[] msg_split = message.split("-");

        if (msg_split.length == 3) {
            return msg_split[0].length() == 4 && msg_split[1].length() == 2
                    && msg_split[2].length() == 2 && is_digit(msg_split[0])
                    && is_digit(msg_split[1]) && is_digit(msg_split[2]);
        } else {
            return false;
        }
    }

    private boolean is_time_message(String message) {
        String[] msg_split = message.split("-");
        String[] left_split = msg_split[0].split(":");
        String[] right_split = msg_split[1].split(":");

        return left_split[0].length() == 2 && left_split[1].length() == 2
                && is_digit(left_split[0]) && is_digit(left_split[1])
                && right_split[0].length() == 2 && right_split[1].length() == 2
                && is_digit(right_split[0]) && is_digit(right_split[1]);
    }

    private boolean is_digit(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
