package advprog.bot.feature.acronym;

import static org.junit.Assert.assertNotNull;

import advprog.bot.feature.acronym.helper.State;
import org.junit.Test;

public class StateTest {

    @Test
    public void testStateHelper_Exist() {
        assertNotNull(new State());
    }
}
