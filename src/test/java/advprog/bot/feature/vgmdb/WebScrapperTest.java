package advprog.bot.feature.vgmdb;

import static advprog.bot.feature.vgmdb.WebScrapper.getData;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class WebScrapperTest {

    @Test
    public void getDataTest() throws IOException {
        List<String> data = getData();
        assertTrue(data.size()>0);
    }

}
