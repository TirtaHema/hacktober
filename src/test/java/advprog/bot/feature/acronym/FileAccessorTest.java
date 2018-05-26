package advprog.bot.feature.acronym;

import static org.junit.Assert.assertEquals;

import advprog.bot.feature.acronym.helper.FileAccessor;
import org.junit.Test;

public class FileAccessorTest {
    @Test
    public void testFileNotFound() {
        assertEquals("", new FileAccessor().loadFile("qwertyuioop"));
    }
}
