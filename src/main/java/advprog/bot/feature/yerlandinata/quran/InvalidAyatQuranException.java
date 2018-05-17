package advprog.bot.feature.yerlandinata.quran;

@SuppressWarnings("serial")
public class InvalidAyatQuranException extends IllegalArgumentException {

    public static final String INVALID_SURAH = "Alquran terdiri dari 114 surah.";

    public InvalidAyatQuranException(String message) {
        super(message);
    }

}
