package advprog.bot.feature.yerlandinata.quran.groupchat.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.json.JSONException;

public class GuessSurahServiceImpl implements GuessSurahService {

    private final AyatQuranFetcher ayatQuranFetcher;
    private Map<String, String> groupMapper;

    public GuessSurahServiceImpl(AyatQuranFetcher ayatQuranFetcher) {
        this.ayatQuranFetcher = ayatQuranFetcher;
    }

    @Override
    public AyatQuran startGuessing(String groupId) throws IOException, JSONException {
        Random random = new Random();
        int surah = random.nextInt() % 114 + 1;
        AyatQuran ayatQuran = ayatQuranFetcher.fetchAyatQuran(surah, 2);
        groupMapper.put(groupId, ayatQuran.getSurahName());
        return ayatQuran;
    }

    @Override
    public boolean guess(String groupId, String guess) {
        boolean result = groupMapper.get(groupId).equals(guess);
        if (result) {
            groupMapper.remove(groupId);
        }
        return groupMapper.get(groupId).equals(guess);
    }

    @Override
    public boolean isGuessing(String groupId) {
        return groupMapper.containsKey(groupId)
                && !Objects.equals(groupMapper.get(groupId), "null");
    }
}
