package advprog.bot.feature.docssimilarity.helper;

import advprog.example.bot.service.DocumentsSimilarityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentsSimilarityApiHelperTest {
    private DocumentsSimilarityApiHelper documentsSimilarityHelper;

    @BeforeEach
    public void setUp() {
        documentsSimilarityHelper = new DocumentsSimilarityApiHelper();
    }

    @Test
    public void testGetSimilarity1() {
        String similarity = documentsSimilarityHelper.getSimilarity(
                "'my name is fahmi' 'your name is fahmi'"
        );
        assertEquals("100%", similarity);
    }

    @Test
    public void testGetSimilarity2() {
        String similarity = documentsSimilarityHelper.getSimilarity(
                "https://www.biography.com/people/adolf-hitler-9340144 "
                        + "https://en.wikipedia.org/wiki/Adolf_Hitler"
        );
        assertEquals("87%", similarity);
    }

    @Test
    public void testGetSimilarityFromTextSuccess() {
        String similarity = documentsSimilarityHelper.getSimilarityFromText(
                "my name is fahmi",
                "your name is fahmi"
        );
        assertEquals("100%", similarity);
    }

    @Test
    public void testGetSimilarityFromTextBadRequest() {
        String similarity = documentsSimilarityHelper.getSimilarityFromText(
                "hey ho",
                "ho hey"
        );
        assertEquals("Unmanaged language [so]", similarity);
    }

    @Test
    public void testGetSimilarityFromUrlSuccess() {
        String similarity = documentsSimilarityHelper.getSimilarityFromUrl(
                "https://www.biography.com/people/adolf-hitler-9340144",
                "https://en.wikipedia.org/wiki/Adolf_Hitler"
        );
        assertEquals("87%", similarity);
    }

//    @Test
//    void testGetSimilarityFromUrlBadRequest() {
//        String similarity = documentsSimilarityHelper.getSimilarityFromUrl(
//                "https://facebook.com",
//                "https://google.com"
//        );
//        assertEquals("Unmanaged language [da]", similarity);
//    }
}
