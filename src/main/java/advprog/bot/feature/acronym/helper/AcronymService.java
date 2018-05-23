package advprog.bot.feature.acronym.helper;

import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class AcronymService {
    private static final FileAccessor FILE_ACCESSOR = new FileAccessor();
    private static final String ACRONYM_DATA_NAME = "acronyms";

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
        JSONObject currentUser = participants.getJSONObject(userId);
        if (input.equals(acronym) && (!currentUser.has("fault") || currentUser.getInt("fault") < 3)) {
            currentUser.put("score", currentUser.getInt("score") + 1);
            FILE_ACCESSOR.saveFile(groupId, array);
            return userId + " has answered correctly\n" + nextAcronym(groupId);
        } else {
            int fault = (currentUser.has("fault") ? currentUser.getInt("fault") : 0);
            currentUser.put("fault", fault + 1);
            FILE_ACCESSOR.saveFile(groupId, array);
            return userId + "'s answer is not correct";
        }
    }

    private String restartAcronyms(String groupId) throws IOException {
        JSONObject acronyms = getAcronyms();
        JSONArray array = new JSONArray();
        array.put(acronyms);
        String acronym = acronyms.keys().next();
        array.put(acronym);
        FILE_ACCESSOR.saveFile(groupId, array);
        return "What is the extension of " + acronym;
    }

    private String stopAcronym(String groupId) throws IOException {
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
            ret.append(user.getValue());
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
            return restartAcronyms(groupId);
        }
        String acronym = acronyms.keys().next();
        array.put(1, acronym);
        JSONObject participants = array.getJSONObject(3);
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
        if (FILE_ACCESSOR.loadFile(ACRONYM_DATA_NAME).equals("")) {
            return "No acronyms yet";
        }
        FILE_ACCESSOR.saveFile(userId, State.UPDATE_WAITING_ACRONYM);
        return "Which acronym to update?";
    }

    private String delete(String userId) throws IOException {
        if (FILE_ACCESSOR.loadFile(ACRONYM_DATA_NAME).equals("")) {
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
        return new JSONObject(FILE_ACCESSOR.loadFile(ACRONYM_DATA_NAME));
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
}
