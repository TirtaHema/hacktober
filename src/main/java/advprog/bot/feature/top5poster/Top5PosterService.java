package advprog.bot.feature.top5poster;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Top5PosterService {

    private LineMessagingClient client;
    private HashMap<String, HashMap<String, Integer>> data;

    @Autowired
    public Top5PosterService(LineMessagingClient client) {
        this.client = client;
        this.data = new HashMap<>();
    }

    public void recordChat(String groupId, String userId) {
        HashMap<String, Integer> dataGroupMember =  data.get(groupId);
        dataGroupMember.put(userId, dataGroupMember.getOrDefault(userId, 0) + 1);
    }
}
