package advprog.bot.feature.anisong.util.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SongGetterConfig.class})
public class SongGetterTest {

    @Autowired
    private SongGetter songGetter;

    private String song = "torikoriko please";
    private String url = "https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001"
            + "/AudioPreview20/v4/cc/0a/90/cc0a907f-46b8-32b2-1bb7-d35786490f57/mzaf_63"
            + "82086061014043981.plus.aac.p.m4a";

    @Test
    public void testUrlSongValidate() throws IOException {
        assertEquals(url,songGetter.getSong(song));
    }
}

