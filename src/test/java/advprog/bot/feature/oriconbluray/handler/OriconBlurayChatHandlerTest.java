package advprog.bot.feature.oriconbluray.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {OriconBlurayChatHandler.class})
public class OriconBlurayChatHandlerTest {

    OriconBlurayChatHandler handler =
            new OriconBlurayChatHandler(new BaseChatHandler());

    private String sampleDaily = "Haiiii~ Here's your top 10 bluray as you requested~\n"
            + "\n(1) 劇場版「Fate/stay night[Heaven’s Feel]�T.presage flower」"
            + "(完全生産限定版) - アニメーション - 2018-05-09\n"
            + "(2) ナラタージュ Blu-ray 豪華版 - 松本潤 - 2018-05-09\n"
            + "(3) 仮面ライダー平成ジェネレーションズFINAL ビルド&エグゼイドwith"
            + "レジェンドライダー コレクターズパック - 犬飼貴丈 - 2018-05-09\n"
            + "(4) CNBLUE 2017 ARENA LIVE TOUR 〜Starting Over〜 @YOKOHAMA ARENA"
            + " - CNBLUE - 2018-05-09\n"
            + "(5) Little Glee Monster Arena Tour 2018 -juice !!!!!- "
            + "at YOKOHAMA ARENA - Little Glee Monster - 2018-05-09\n"
            + "(6) 劇場版「Fate/stay night[Heaven’s Feel]�T.presage flower」"
            + "(通常版) - アニメーション - 2018-05-09\n"
            + "(7) カードキャプターさくら クリアカード編 Vol.1＜初回仕様版＞ "
            + "- アニメーション - 2018-05-09\n"
            + "(8) スター・ウォーズ/最後のジェダイ MovieNEX(初回版) - "
            + "マーク・ハミル - 2018-04-25\n"
            + "(9) 超英雄祭 KAMEN RIDER×SUPER SENTAI LIVE&SHOW 2018 "
            + "- - - 2018-05-09\n"
            + "(10) ワイルド・スピード ICE BREAK - ヴィン・ディーゼル - 2018-05-09";

    private String sampleWeekly = "Haiiii~ Here's your top 10 bluray as you requested~\n"
            + "\n(1) スター・ウォーズ/最後のジェダイ MovieNEX(初回版) - マーク・ハミル - 2018-04-25\n"
            + "(2) ラブライブ!サンシャイン!! 2nd Season 5【特装限定版】 - アニメーション - 2018-04-24\n"
            + "(3) SHOGO HAMADA ON THE ROAD 2015-2016“Journey of a Songwriter” - "
            + "浜田省吾 - 2018-04-25\n"
            + "(4) ラブライブ!サンシャイン!! Aqours 2nd LoveLive! HAPPY PARTY TRAIN TOUR "
            + "Blu-ray Memorial BOX - Aqours - 2018-04-25\n"
            + "(5) THE IDOLM@STER SideM GREETING TOUR 2017 〜BEYOND THE DREAM〜 "
            + "LIVE Blu-ray - アイドルマスターSideM - 2018-04-25\n"
            + "(6) スター・ウォーズ/最後のジェダイ 4K UHD MovieNEX - マーク・ハミル - 2018-04-25\n"
            + "(7) A3! FIRST Blooming FESTIVAL【Blu-ray】 - - - 2018-04-25\n"
            + "(8) アイドリッシュセブン Blu-ray 3【特装限定版】 - アニメーション - 2018-04-24\n"
            + "(9) アウトレイジ 最終章 - ビートたけし - 2018-04-24\n"
            + "(10) アイドルマスター SideM 5(完全生産限定版) - アニメーション - 2018-04-25";

    private String noChartDailyMsg = "It seems there's no rank charts on that date";

    private String noChartWeeklyMsg = "It seems there's no rank charts on that date"
            + ". Make sure the date you assign is on monday";

    private String invalidFormatDateMsg = "Hmmmmm, forget about the date format?"
            + " No worries~ I can help you~ Here's the format\n\n"
            + "YYYY-MM-DD\n\nRemember it well, kay?";

    private String absurdDateMsg = "N-nani?! Is such date even exist?! You would"
            + " like to check the calendar... and enter an existing "
            + "date...";

    @Test
    public void testDailyReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(sampleDaily));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "fff", "/oricon bluray weekly 2018-05-09");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testWeeklyReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(sampleWeekly));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "eeee", "/oricon bluray weekly 2018-05-07");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testNoDailyChartReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(noChartDailyMsg));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "bbb", "/oricon bluray daily 2019-05-09");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testNoWeeklyChartReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(noChartWeeklyMsg));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "aaa", "/oricon bluray weekly 2018-05-09");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testInvalidDateFormatReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(invalidFormatDateMsg));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "cccc", "/oricon bluray daily 20190507");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testAbsurdDateReply() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(absurdDateMsg));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "aaa","/oricon bluray weekly 2018-05-32");

        assertEquals(expectedMessages,
                handler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(handler.canHandleAudioMessage(null));
        assertFalse(handler.canHandleImageMessage(null));
        assertFalse(handler.canHandleStickerMessage(null));
        assertFalse(handler.canHandleLocationMessage(null));
    }

}