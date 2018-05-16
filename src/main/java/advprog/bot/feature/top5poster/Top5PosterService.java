package advprog.bot.feature.top5poster;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Top5PosterService {

    private LineMessagingClient client;
    private HashMap<String, TreeMap<String, Integer>> data;

    @Autowired
    public Top5PosterService(LineMessagingClient client) {
        this.client = client;
        this.data = new HashMap<>();
    }

    public void recordChat(String groupId, String userId) {
        TreeMap<String, Integer> dataGroupMember =  data.getOrDefault(groupId, new TreeMap<>());
        dataGroupMember.put(userId, dataGroupMember.getOrDefault(userId, 0) + 1);
    }

    public List<Poster> getTop5(String groupId) {

    }
}
