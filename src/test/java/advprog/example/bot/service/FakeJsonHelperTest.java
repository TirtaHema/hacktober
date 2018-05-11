package advprog.example.bot.service;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeJsonHelperTest {

    private FakeJsonHelper fakeJsonHelper;

    @BeforeEach
    public void setUp() {
        fakeJsonHelper = new FakeJsonHelper();
    }

    @Test
    public void testGetUrl() {
        String url = fakeJsonHelper.getUrl();
        assertTrue(url.matches(
                "https:\\/\\/jsonplaceholder\\.typicode\\.com\\/.+\\/([1-10]|10)")
        );
    }

    @Test
    public void testGetFakeJsonObject() {
        String fakeJsonObject = fakeJsonHelper.getFakeJsonObject();
        Assertions.assertThatCode(() -> (new JSONObject(fakeJsonObject)).get("id"))
                .doesNotThrowAnyException();
    }
}
