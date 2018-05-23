package advprog.bot.feature.anisong.handler;

import advprog.bot.feature.anisong.util.service.Database;
import advprog.bot.feature.anisong.util.service.SongGetter;
import advprog.bot.feature.anisong.util.service.SongRecognizer;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AnisongChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger
            .getLogger(EchoChatHandler.class.getName());

    private UserHandler userHandler = new UserHandler();

    public AnisongChatHandler(LineChatHandler decoratorHandler) {
        this.decoratedLineChatHandler = decoratorHandler;
        LOGGER.info("Anisong chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            String text = event.getMessage().getText();
            if (text.equalsIgnoreCase("/listen_song")) {
                if (!event.getSource().toString().contains("groupId")) {
                    String id = event.getSource().getSenderId();
                    userHandler.userSession.put(id, "listen");
                    List<CarouselColumn> cc = new ArrayList<>();
                    List<String> songs = Database.getSong(id);
                    SongRecognizer songRecognizer = new SongRecognizer();
                    if (songs.size() > 10) {
                        for (int i = 0; i < 10; i++) {
                            List<Action> act = new ArrayList<>();
                            act.add(new MessageAction("Choose song", songs.get(i)));
                            cc.add(new CarouselColumn(songRecognizer.getImage(songs.get(i)), null,
                                    songRecognizer.getSongName(songs.get(i)), "iTunes id : " + songRecognizer.songValidate(songs.get(i)), act));
                        }
                    }
                    else {
                        for (int i = 0; i < songs.size(); i++) {
                            List<Action> act = new ArrayList<>();
                            act.add(new MessageAction("Choose song", songs.get(i)));
                            cc.add(new CarouselColumn(songRecognizer.getImage(songs.get(i)), null,
                                    songRecognizer.getSongName(songs.get(i)), "iTunes id : " + songRecognizer.songValidate(songs.get(i)), act));
                        }
                    }
                    return Collections.singletonList(new TemplateMessage("Carousel Template", new CarouselTemplate(cc)));
                } else {
                    return Collections.singletonList(
                            new TextMessage("You must private chat me..."));
                }
            }
            else if (text.equalsIgnoreCase("/add_song")) {
                if (!event.getSource().toString().contains("groupId")) {
                    String id = event.getSource().getSenderId();
                    userHandler.userSession.put(id, "add");
                    return Collections.singletonList(new TextMessage("What song?"));
                } else {
                    return Collections.singletonList(
                            new TextMessage("You must private chat me..."));
                }
            }
            else if (text.equalsIgnoreCase("/remove_song")) {
                if (!event.getSource().toString().contains("groupId")) {
                    String id = event.getSource().getSenderId();
                    userHandler.userSession.put(id, "remove");
                    List<CarouselColumn> cc = new ArrayList<>();
                    List<String> songs = Database.getSong(id);
                    SongRecognizer songRecognizer = new SongRecognizer();
                    if (songs.size() > 10) {
                        for (int i = 0; i < 10; i++) {
                            List<Action> act = new ArrayList<>();
                            act.add(new MessageAction("Choose song", songs.get(i)));
                            cc.add(new CarouselColumn(songRecognizer.getImage(songs.get(i)), null,
                                    songRecognizer.getSongName(songs.get(i)), "iTunes id : " + songRecognizer.songValidate(songs.get(i)), act));
                        }
                    }
                    else {
                        for (int i = 0; i < 10; i++) {
                            List<Action> act = new ArrayList<>();
                            act.add(new MessageAction("Choose song", songs.get(i)));
                            cc.add(new CarouselColumn(songRecognizer.getImage(songs.get(i)), null,
                                    songRecognizer.getSongName(songs.get(i)), "iTunes id : " + songRecognizer.songValidate(songs.get(i)), act));
                        }
                    }
                    return Collections.singletonList(new TemplateMessage("Carousel Template", new CarouselTemplate(cc)));
                } else {
                    return Collections.singletonList(
                            new TextMessage("You must private chat me..."));
                }
            }
            else if (text.equalsIgnoreCase("/help")) {
                return Collections.singletonList(new TextMessage("format:\n/add_song untuk nambahin lagu ke database\n" +
                        "/listen_song untuk mendengarkan lagu(preview) yang tersimpan di database\n" +
                        "/remove_song untuk menghapus lagu dari database"));
            }
            else {
                if (!event.getSource().toString().contains("groupId")) {
                    String id = event.getSource().getSenderId();
                    if (userHandler.userSession.get(id).equalsIgnoreCase("listen")) {
                        userHandler.userSession.put(id, "");
                        List<String> songs = Database.getSong(id);
                        SongGetter songGetter = new SongGetter();
                        List<Message> list = new ArrayList<Message>();
                        list.add(
                                new AudioMessage(songGetter.getSong(text), 29000));
                        list.add(
                                new ImageMessage(
                                        "https://i2.wp.com/www.xeniumhr.com/wp-content/uploads/2015/03/" +
                                                "Get_it_on_iTunes_Badge_US_1114.png?fit=1196%2C435",
                                        "https://i2.wp.com/www.xeniumhr.com/wp-content/uploads" +
                                        "/2015/03/Get_it_on_iTunes_Badge_US_1114.png?fit=1196%2C435"));
                        return list;
                    }
                    else if (userHandler.userSession.get(id).equalsIgnoreCase("add")) {
                        userHandler.userSession.put(id, "");
                        SongGetter songGetter = new SongGetter();
                        if (songGetter.getSong(text) != null) {
                            if (Database.addSong(id, text) == 1) {
                                return Collections.singletonList(
                                        new TextMessage("Your song has been added to database"));
                            }
                            else {
                                return Collections.singletonList(
                                        new TextMessage("Your song already added to database"));
                            }

                        }
                    }
                    else if (userHandler.userSession.get(id).equalsIgnoreCase("remove")) {
                        userHandler.userSession.put(id,"");
                        Database.deleteSong(id, text);
                        return Collections.singletonList(
                                new TextMessage("Your song has been deleted from database"));

                    }
                } else {
                    return Collections.singletonList(
                            new TextMessage("You must private chat me..."));
                }
            }
        }
        catch (NullPointerException e){
            return Collections.singletonList(new TextMessage("You must add the format first"));
        }
        catch (IOException e) {
            return Collections.singletonList(new TextMessage("Your Song is not available in Itunes or Not Love Live song"));
        } catch (SQLException e) {
            return Collections.singletonList(new TextMessage("Your Song is not in database"));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(new TextMessage("Your Input is not valid"));
        }
        return Collections.singletonList(new TextMessage("Your Input is not valid"));
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
}
