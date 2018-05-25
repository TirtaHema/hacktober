package advprog.bot.feature.acronym.helper;

import com.linecorp.bot.client.LineMessagingClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class AcronymService {
    private static final FileAccessor FILE_ACCESSOR = new FileAccessor();
    private static final String ACRONYM_DATA_NAME = "acronyms";
    private static final String ACCESS_TOKEN = "pVBXJUHNrekJlf62vNMWsGiSGKUI+1WspucGp/Z1fK72BNgkET/NQBhHHMF84myOVytSHqAWndKKLU46pD7Ig/OiGSQ6eArvXIDoGIxvgzJgu/xP1md+V4lP90px+vmrJCRVrlAVS2/I29imTMkvjQdB04t89/1O/w1cDnyilFU=";
    private static final LineMessagingClient client = LineMessagingClient.builder(ACCESS_TOKEN).build();

    public boolean isRecievingUserInput(String userId, String input) {
        String state = getState(userId);
        if (state.equals(State.NOTHING)) {
            return input.equals("/add_acronym")
                    || input.equals("/update_acronym")
                    || input.equals("/delete_acronym");
        }
        return true;
    }

    public boolean isRecievingGroupInput(String groupId, String input) {
        String content = FILE_ACCESSOR.loadFile(groupId);
        if (content.equals("")) {
            return input.contains("start acronym");
        }

        return true;
    }

    public String processUserInput(String userId, String input) throws Exception {
        switch (getState(userId)) {
            case State.NOTHING:
                switch (input) {
                    case "/add_acronym":
                        return add(userId);
                    case "/update_acronym":
                        return update(userId);
                    case "/delete_acronym":
                        return delete(userId);
                }
                break;
            case State.ADD_WAITING_ACRONYM:
                return addRecieveAcronym(userId, input);
            case State.WAITING_EXTENSION:
                return recieveExtension(userId, input);
            case State.UPDATE_WAITING_ACRONYM:
                return updateRecieveAcronym(userId, input);
            case State.DELETE_WAITING_ACRONYM:
                return deleteRecieveAcronym(userId, input);
        }
        //STATE.DELETE_WAITING_CONFIRMATION outside for compilation and coverage purpose
        return deleteRecieveConfirmation(userId, input);
    }

    public String processGroupInput(String groupId, String userId, String input) throws Exception {
        String content = FILE_ACCESSOR.loadFile(groupId);
        if (content.equals("")) {
            return restartAcronyms(groupId);
        }
        if (input.contains("stop acronym")) {
            return stopAcronym(groupId);
        }
        JSONArray array = new JSONArray(content);
        String acronym = array.getString(1);
        JSONObject participants = array.getJSONObject(2);
        if (!participants.has(userId)) {
            participants.put(userId, new JSONObject("{\"score\":0, \"fault\":0}"));
        }
        JSONObject currentUser = participants.getJSONObject(userId);
        if (input.equals(getAcronyms().getString(acronym)) && (!currentUser.has("fault") || currentUser.getInt("fault") < 3)) {
            currentUser.increment("score");
            FILE_ACCESSOR.saveFile(groupId, array);
            return getUserName(userId) + " has answered correctly\n" + nextAcronym(groupId);
        } else {
            int fault = (currentUser.has("fault") ? currentUser.getInt("fault") : 0);
            if (fault == 3) {
                return getUserName(userId) + " cannot answer anymore";
            }
            currentUser.increment("fault");
            FILE_ACCESSOR.saveFile(groupId, array);
            return getUserName(userId) + "'s answer is not correct";
        }
    }

    private String restartAcronyms(String groupId) throws IOException {
        JSONObject acronyms = getAcronyms();
        if (acronyms.keySet().isEmpty()) {
            return "No acronyms yet";
        }
        JSONArray array = new JSONArray();
        array.put(acronyms);
        String acronym = acronyms.keys().next();
        array.put(acronym);
        array.put(new JSONObject("{}"));
        FILE_ACCESSOR.saveFile(groupId, array);
        return "What is the extension of " + acronym + "?";
    }

    private String stopAcronym(String groupId) throws Exception {
        String content = FILE_ACCESSOR.loadFile(groupId);
        JSONArray array = new JSONArray(content);
        JSONObject participants = array.getJSONObject(2);
        Iterator<?> users = participants.keys();
        List<Pair<Integer, String>> leaderBoard = new ArrayList<>();
        while (users.hasNext()) {
            String user = (String)users.next();
            leaderBoard.add(new Pair<>(participants.getJSONObject(user).getInt("score"), user));
        }
        FILE_ACCESSOR.saveFile(groupId, "");
        leaderBoard.sort(new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });
        StringBuilder ret = new StringBuilder();
        for (Pair<Integer, String> user : leaderBoard) {
            ret.append(getUserName(user.getValue()));
            ret.append(" ");
            ret.append(user.getKey());
            ret.append("\n");
        }
        return ret.toString();
    }

    private String nextAcronym(String groupId) throws IOException {
        JSONArray array = new JSONArray(FILE_ACCESSOR.loadFile(groupId));
        JSONObject acronyms = array.getJSONObject(0);
        acronyms.remove(array.getString(1));
        if (acronyms.keySet().isEmpty()) {
            acronyms = getAcronyms();
        }
        String acronym = acronyms.keys().next();
        array.put(1, acronym);
        JSONObject participants = array.getJSONObject(2);
        Iterator<?> users = participants.keys();
        while (users.hasNext()) {
            String user = (String)users.next();
            participants.getJSONObject(user).put("fault", 0);
        }
        FILE_ACCESSOR.saveFile(groupId, array);
        return "What is the extension of " + acronym + "?";
    }

    private String add(String userId) throws IOException {
        FILE_ACCESSOR.saveFile(userId, State.ADD_WAITING_ACRONYM);
        return "Please enter your acronym";
    }

    private String update(String userId) throws IOException {
        if (getAcronyms().keySet().isEmpty()) {
            return "No acronyms yet";
        }
        FILE_ACCESSOR.saveFile(userId, State.UPDATE_WAITING_ACRONYM);
        return "Which acronym to update?";
    }

    private String delete(String userId) throws IOException {
        if (getAcronyms().keySet().isEmpty()) {
            return "No acronyms yet";
        }
        FILE_ACCESSOR.saveFile(userId, State.DELETE_WAITING_ACRONYM);
        return "Which acronym to delete?";
    }

    private String addRecieveAcronym(String userId, String acronym) throws IOException  {
        FILE_ACCESSOR.saveFile(userId, State.WAITING_EXTENSION + " " + acronym);
        return "Please specify the extension";
    }

    private String recieveExtension(String userId, String extension) throws IOException {
        String acronym = getUserInputAcronym(userId);
        JSONObject acronyms = getAcronyms();
        acronyms.put(acronym, extension);
        FILE_ACCESSOR.saveFile(ACRONYM_DATA_NAME, acronyms);
        FILE_ACCESSOR.saveFile(userId, State.NOTHING);
        return "Acronym successfully added/updated";
    }

    private String updateRecieveAcronym(String userId, String acronym) throws IOException {
        JSONObject acronyms = getAcronyms();
        if (!acronyms.has(acronym)) {
            return "Acronym not found";
        }
        FILE_ACCESSOR.saveFile(userId, State.WAITING_EXTENSION + " " + acronym);
        return "Please specify the new extension";
    }

    private String deleteRecieveAcronym(String userId, String acronym) throws IOException {
        JSONObject acronyms = getAcronyms();
        if (!acronyms.has(acronym)) {
            return "Acronym not found";
        }
        FILE_ACCESSOR.saveFile(userId, State.DELETE_WAITING_CONFIRMATION + " " + acronym);
        return "Are you sure?";
    }

    private String deleteRecieveConfirmation(String userId, String confirmation) throws IOException {
        if (confirmation.toLowerCase().equals("yes")) {
            JSONObject acronyms = getAcronyms();
            acronyms.remove(getUserInputAcronym(userId));
            FILE_ACCESSOR.saveFile(ACRONYM_DATA_NAME, acronyms);
            FILE_ACCESSOR.saveFile(userId, State.NOTHING);
            return "Acronym successfully deleted";
        }
        FILE_ACCESSOR.saveFile(userId, State.NOTHING);
        return "Delete cancelled";
    }

    private String getState(String userId) {
        return FILE_ACCESSOR.loadFile(userId).split(" ")[0];
    }

    private String getUserInputAcronym(String userId) {
        return FILE_ACCESSOR.loadFile(userId).split(" ")[1];
    }

    private JSONObject getAcronyms() {
        String content = FILE_ACCESSOR.loadFile(ACRONYM_DATA_NAME);
        if (content.equals("")) content = "{}";
        JSONObject acronyms = new JSONObject(content);
        Iterator<?> acronym = acronyms.keys();
        List<Pair<String, String>> arr = new ArrayList<>();
        while (acronym.hasNext()) {
            String key = (String)acronym.next();
            arr.add(new Pair<>(key, acronyms.getString(key)));
        }
        Collections.shuffle(arr);
        JSONObject ret = new JSONObject("{}");
        for (Pair<String,String> acr : arr) {
            ret.put(acr.getKey(), acr.getValue());
        }
        return ret;
    }

    private String getUserName(String userId) throws Exception {
        return client.getProfile(userId).get().getDisplayName();
    }

    public List<String> getAcronymsList() {
        List<String> ret = new ArrayList<>();
        JSONObject acronyms = getAcronyms();
        Iterator<?> acronym = acronyms.keys();
        while (acronym.hasNext()) {
            String key = (String)acronym.next();
            ret.add(key + " -- " + acronyms.getString(key));
        }
        return ret;
    }

    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
