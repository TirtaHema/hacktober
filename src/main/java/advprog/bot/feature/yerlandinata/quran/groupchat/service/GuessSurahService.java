package advprog.bot.feature.yerlandinata.quran.groupchat.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

import java.io.IOException;

import org.json.JSONException;

public interface GuessSurahService {

    AyatQuran startGuessing(String groupId) throws IOException, JSONException;

    boolean guess(String groupId, String guess);

    boolean isGuessing(String groupId);

}
