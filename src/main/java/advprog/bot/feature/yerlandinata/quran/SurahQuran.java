package advprog.bot.feature.yerlandinata.quran;

public final class SurahQuran {

    private final int surahNumber;
    private final int numberOfAyat;
    private final String arabName;
    private final String englishName;

    public SurahQuran(int surahNumber, int numberOfAyat, String arabName, String englishName) {
        this.surahNumber = surahNumber;
        this.numberOfAyat = numberOfAyat;
        this.arabName = arabName;
        this.englishName = englishName;
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public int getNumberOfAyat() {
        return numberOfAyat;
    }

    public String getArabName() {
        return arabName;
    }

    public String getEnglishName() {
        return englishName;
    }

    @Override
    public String toString() {
        return String.format(
                "%s/%s [%d]: %d ayat", arabName, englishName, surahNumber, numberOfAyat
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SurahQuran) {
            SurahQuran other = (SurahQuran) o;
            return     arabName.equals(other.arabName)
                    && englishName.equals(other.englishName)
                    && numberOfAyat == other.numberOfAyat
                    && surahNumber == other.surahNumber;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + surahNumber;
        hash = 31 * hash + numberOfAyat;
        hash = 31 * hash + arabName.hashCode();
        hash = 31 * hash + englishName.hashCode();
        return hash;
    }
}
