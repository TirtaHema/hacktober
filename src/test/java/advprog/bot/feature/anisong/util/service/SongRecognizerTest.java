package advprog.bot.feature.anisong.util.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SongRecognizerConfig.class})
public class SongRecognizerTest {

    @Autowired
    private SongRecognizer songRecognizer;

    private String sampleSong = "torikoriko please";
    private String id = "1113388224";

    @Test
    public void testSongValidate() throws IOException {
        assertEquals(id,songRecognizer.songValidate(sampleSong));
    }
}

