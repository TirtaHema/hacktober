package advprog.bot.feature.top5poster;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutionException;
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
        HashMap<String, Integer> dataGroupMember =  data.getOrDefault(groupId, null);
        if (dataGroupMember == null) {
            dataGroupMember = new HashMap<>();
            data.put(groupId, dataGroupMember);
        }
        dataGroupMember.put(userId, dataGroupMember.getOrDefault(userId, 0) + 1);
    }

    public List<Poster> getTop5(String groupId) throws ExecutionException, InterruptedException {
        HashMap<String, Integer> dataGroupMember = data.get(groupId);
        int totalChatGroup = 0;
        for (String userId : dataGroupMember.keySet()) {
            totalChatGroup += dataGroupMember.get(userId);
        }

        List<Poster> posters = new ArrayList<>();

        for (String userId: dataGroupMember.keySet()) {
            posters.add(new Poster(client.getGroupMemberProfile(groupId, userId).get().getDisplayName(), ((double) dataGroupMember.get(userId) / (double) totalChatGroup) * 100));
        }

        Collections.sort(posters);

        return posters.subList(0, posters.size() > 5 ? 5 : posters.size());
    }
}
