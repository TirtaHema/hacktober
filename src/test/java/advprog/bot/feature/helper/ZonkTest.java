package advprog.bot.feature.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import advprog.bot.feature.zonk.helper.Zonk;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ZonkTest {
    private Zonk zonk;
    public static ArrayList<String> e = new ArrayList<>();

    @Before
    public void setUp() {
        zonk = new Zonk("haha");

        e.add("haha");
        e.add("bobo");
        e.add("aaa");
        e.add("bbb");
        e.add("ccc");
    }

    @Test
    public void testMethodJawab() {
        assertEquals("benar",zonk.jawab(1,"mc donald phone number ?"));
    }

    @Test
    public void testMethodJawabSalah() {
        assertEquals("salah",zonk.jawab(0,"mc donald phone number ?"));
    }

    @Test
    public void testMethodBuatSoal() {
        String[] question = {"what is the dog name?","helo","hela","heli","helu","heli"};
        zonk.buatSoal(question);
        String jawaban = "1 helo\n2 hela\n3 heli\n4 helu\n";
        assertEquals(jawaban,zonk.option("what is the dog name?"));
    }



}