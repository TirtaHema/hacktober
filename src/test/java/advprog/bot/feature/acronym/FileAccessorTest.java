package advprog.bot.feature.acronym;

import advprog.bot.feature.acronym.helper.FileAccessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileAccessorTest {
    @Test
    public void testFileNotFound() {
        assertEquals("", new FileAccessor().loadFile("qwertyuioop"));
    }
}
