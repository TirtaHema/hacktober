package advprog.bot.feature.fakejson.helper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FakeJsonApiHelperTest {
    private FakeJsonApiHelper fakeJsonApiHelper;

    @BeforeEach
    public void setUp() {
        fakeJsonApiHelper = new FakeJsonApiHelper();
    }

    @Test
    public void testGetUrl() {
        String url = fakeJsonApiHelper.getUrl();
        assertTrue(url.matches(
                "https:\\/\\/jsonplaceholder\\.typicode\\.com\\/.*\\/([1-9]|10)*"
        ));
    }

    @Test
    public void testGetFakeJsonObject() {
        String fakeJsonObject = fakeJsonApiHelper.getFakeJsonObject();
        Assertions.assertThatCode(() -> (new JSONObject(fakeJsonObject)).get("id"))
                .doesNotThrowAnyException();
    }
}
