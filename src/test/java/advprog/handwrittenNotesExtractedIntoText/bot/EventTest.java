package advprog.handwrittenNotesExtractedIntoText.bot;

import advprog.handwrittenNotesExtractedIntoText.bot.APIUtil;
import advprog.handwrittenNotesExtractedIntoText.bot.BotApplication;
import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {

    @Before
    public void setUp() {
        BotApplication botApp = new BotApplication();
        APIUtil apiUtil = new APIUtil();
    }

    //Work In Progress : Test to check if the request is image, the return will be a text
    @Test
    public void inputImageReturnTextTest() {
        assertEquals(1,2);
    }
}