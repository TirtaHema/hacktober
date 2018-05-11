package yerlandinata.youtubeinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = YoutubeInfoChatResponderTest.YoutubeInfoChatResponderTestConfiguration.class
)
public class YoutubeInfoChatResponderTest {

    @Configuration
    static class YoutubeInfoChatResponderTestConfiguration {

        @Bean
        YoutubeInfoChatResponder youtubeInfoChatResponder(YoutubeInfoFetcher youtubeInfoFetcher) {
            return new YoutubeInfoChatResponder(youtubeInfoFetcher);
        }

        @Bean
        YoutubeInfoFetcher youtubeInfoFetcher() {
            return mock(YoutubeInfoFetcher.class);
        }
    }

    @Autowired
    YoutubeInfoChatResponder youtubeInfoChatResponder;

    @Autowired
    YoutubeInfoFetcher mockYoutubeInfoFetcher;

    @Test
    public void testConstruct() {
        assertNotNull(youtubeInfoChatResponder);
    }

}
