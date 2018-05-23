package advprog.bot.feature.xkcd.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import advprog.bot.feature.xkcd.helper.Xkcd;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class XkcdTest {

    @Before
    public void setUp(){

    }

    @Test
    public void testGambarBerhasil() {
        assertEquals("https://imgs.xkcd.com/comics/ferret.jpg",Xkcd.gambar("/xkcd 20"));
    }

    @Test
    public void testGambarGagalAngka() {
        assertEquals("harus angka !",Xkcd.gambar("/xkcd hahaha"));
    }

    @Test
    public void testGambarGagalFormat() {
        assertEquals("Formatnya salah kakak /xkcd [nomor id] !",Xkcd.gambar("/xkcd"));
    }

    @Test
    public void testGambarGagalNomor() {
        assertEquals("Image tidak ada !",Xkcd.gambar("/xkcd 2000000"));
    }

}
