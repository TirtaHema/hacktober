package advprog.bot.feature.top5poster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.profile.UserProfileResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class Top5PosterServiceTest {

    @Mock
    LineMessagingClient client;

    @Mock
    CompletableFuture<UserProfileResponse> response1;

    @Mock
    CompletableFuture<UserProfileResponse> response2;

    @Mock
    CompletableFuture<UserProfileResponse> response3;

    Top5PosterService top5PosterService;

    @Before
    public void setUp() {
        top5PosterService = new Top5PosterService(client);
    }

    @Test
    public void testGetTop5() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 50; i++) {
            top5PosterService.recordChat("1", "1");
        }

        for (int i = 0; i < 30; i++) {
            top5PosterService.recordChat("1", "2");
        }

        for (int i = 0; i < 20; i++) {
            top5PosterService.recordChat("1", "3");
        }

        List<Poster> expectedPoster = new ArrayList<Poster>();
        expectedPoster.add(new Poster("user1", 50.00));
        expectedPoster.add(new Poster("user2", 30.00));
        expectedPoster.add(new Poster("user3", 20.00));

        when(client.getGroupMemberProfile(eq("1"), eq("1"))).thenReturn(response1);
        when(response1.get()).thenReturn(new UserProfileResponse("user1", "1", "", ""));

        when(client.getGroupMemberProfile(eq("1"), eq("2"))).thenReturn(response2);
        when(response2.get()).thenReturn(new UserProfileResponse("user2", "2", "", ""));

        when(client.getGroupMemberProfile(eq("1"), eq("3"))).thenReturn(response3);
        when(response3.get()).thenReturn(new UserProfileResponse("user3", "3", "", ""));

        assertEquals(expectedPoster, top5PosterService.getTop5("1"));
    }
}
