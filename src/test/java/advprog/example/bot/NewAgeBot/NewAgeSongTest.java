package advprog.example.bot.NewAgeBot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class NewAgeSongTest {
    NewAgeSong song;

    @BeforeEach
    public void setUp(){
        song = new NewAgeSong("judul","artis",1);
    }

    @org.junit.jupiter.api.Test
    public void titleTest(){
        Assertions.assertEquals("judul",song.getTitle());
        song.setTitle("ganti judul");
        Assertions.assertEquals("ganti judul",song.getTitle());
    }

    @org.junit.jupiter.api.Test
    public void artisTest(){
        Assertions.assertEquals("artis",song.getArtist());
        song.setArtist("ganti artis");
        Assertions.assertEquals("ganti artis", song.getArtist());
    }

    @org.junit.jupiter.api.Test
    public void rankTest(){
        Assertions.assertEquals(1,song.getRank());
        song.setRank(2);
        Assertions.assertEquals(2,song.getRank());
    }

    @org.junit.jupiter.api.Test
    public void songTest(){
        Assertions.assertEquals("artis\njudul\n1",song.song());
    }
}
