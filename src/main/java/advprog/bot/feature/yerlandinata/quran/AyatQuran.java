package advprog.bot.feature.yerlandinata.quran;

public final class AyatQuran {

    private final String surahName;
    private final int ayatNum;
    private final String ayatIndonesia;
    private final String ayatArab;
    private final String audioUri;
    private final int audioDuration;

    public AyatQuran(String surahName, int ayatNum, String ayatIndonesia,
                     String ayatArab, String audioUri, int audioDuration) {
        this.surahName = surahName;
        this.ayatNum = ayatNum;
        this.ayatIndonesia = ayatIndonesia;
        this.ayatArab = ayatArab;
        this.audioUri = audioUri;
        this.audioDuration = audioDuration;
    }

    public String getAyatIndonesia() {
        return ayatIndonesia;
    }

    public String getAyatArab() {
        return ayatArab;
    }

    public String getAudioUri() {
        return audioUri;
    }

    public int getAudioDuration() {
        return audioDuration;
    }

    public String getSurahName() {
        return surahName;
    }

    public int getAyatNum() {
        return ayatNum;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AyatQuran) {
            AyatQuran other = (AyatQuran)o;
            return     surahName.equals(other.surahName)
                    && ayatNum == other.ayatNum
                    && ayatIndonesia.equals(other.ayatIndonesia)
                    && ayatArab.equals(other.ayatArab)
                    && audioUri.equals(other.audioUri)
                    && audioDuration == other.audioDuration;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += 31 * hash + surahName.hashCode();
        hash += 31 * hash + ayatNum;
        hash += 31 * hash + ayatIndonesia.hashCode();
        hash += 31 * hash + ayatArab.hashCode();
        hash += 31 * hash + audioUri.hashCode();
        hash += 31 * hash + audioDuration;
        return hash;
    }

    @Override
    public String toString() {
        return String.format(
                "QS %s : %d [%s; %s; %s]", surahName, ayatNum, ayatArab, ayatIndonesia, audioUri
        );
    }
}
