package advprog.example.bot.service;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentsSimilarityHelperTest {

    private DocumentsSimilarityHelper documentsSimilarityHelper;

    @BeforeEach
    public void setUp() {
        documentsSimilarityHelper = new DocumentsSimilarityHelper();
    }

    @Test
    public void testGetSimilarity1() {
        double similarity = documentsSimilarityHelper.getSimilarity(
                "'my name is fahmi' 'your name is fahmi'"
        );
        assertEquals(1, similarity, 1e-2);
    }

    @Test
    public void testGetSimilarity2() {
        double similarity = documentsSimilarityHelper.getSimilarity(
                "https://www.biography.com/people/adolf-hitler-9340144 "
                + "https://en.wikipedia.org/wiki/Adolf_Hitler"
        );
        assertEquals(0.87, similarity, 1e-2);
    }

    @Test
    public void testGetSimilarityFromText() {
        double similarity = documentsSimilarityHelper.getSimilarityFromText(
                "my name is fahmi",
                "your name is fahmi"
        );
        assertEquals(1, similarity, 1e-2);
    }

    @Test
    public void testGetSimilarityFromUrl() {
        double similarity = documentsSimilarityHelper.getSimilarityFromUrl(
                "https://www.biography.com/people/adolf-hitler-9340144",
                "https://en.wikipedia.org/wiki/Adolf_Hitler"
        );
        assertEquals(0.87, similarity, 1e-2);
    }
}
