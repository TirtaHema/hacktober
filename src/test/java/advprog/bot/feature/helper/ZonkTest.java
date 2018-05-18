package advprog.bot.feature.helper;

import advprog.bot.feature.zonk.helper.Zonk;


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
    public void testMethodJawabSalah() {
        assertEquals("salah",zonk.jawab(0,"mc donald phone number ?"));
    }
    @Test
    public void testMethodBuatSoal() {
        String[] question = {"what is the dog name?","helo","hela","heli","helu","heli"};
        zonk.buatSoal(question);
        String jawaban = "helohelaheliheluheli";
        assertEquals(jawaban,zonk.option("what is the dog name?"));
    }
    @Test
    public void testMethodOption() {
        String jawaban = "bandungjogjasolobalibali";
        assertEquals(jawaban,zonk.option("capital city of indonesia?"));
    }
    @Test
    public void testMethodGantiSoal() {
        String option = "bandungjogjasolobalibali";
        zonk.gantiSoal("capital city of indonesia?","bali");
        assertEquals(option,zonk.option("capital city of indonesia?"));
    }

}
