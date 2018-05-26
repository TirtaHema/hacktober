package advprog.bot.feature.acronym;

import advprog.bot.feature.acronym.helper.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StateTest {

    @Test
    public void testStateHelper_Exist() {
        assertNotNull(new State());
    }
}
