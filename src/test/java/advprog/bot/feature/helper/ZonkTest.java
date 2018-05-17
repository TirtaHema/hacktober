package advprog.bot.feature.helper;

import advprog.bot.feature.zonk.helper.Zonk;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ZonkTest {
    private Zonk zonk;

    @Before
    public void setUp() {
        zonk = new Zonk("haha");
    }
    @Test
    public void testMethodJawab() {
        assertEquals("benar",zonk.jawab(1,"mc donald phone number ?"));
    }
    @Test
    public void testMethodBuatSoal() {
        String soal = zonk.soal("what is the dog name");
        assertEquals(soal,zonk.buatSoal({"what is the dog name","heli","helo","hi","hoo","heli"}));
    }
    @Test
    public void testMethodSoal() {
        String soal = zonk.soal("mc donald phone number ?");
        assertEquals(soal,zonk.soal("mc donald phone number ?"));
    }
    @Test
    public void testMethodGantiSoal() {
        assertEquals("indomie\napple\nnutrisari\ntoyota\napple",zonk.Gantisoal("mc donald phone number ?","apple"));
    }

}
