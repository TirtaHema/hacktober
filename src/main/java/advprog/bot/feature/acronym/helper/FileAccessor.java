package advprog.bot.feature.acronym.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class FileAccessor {
    private static final String PATH = "";

    public String loadFile(String fileName) {
        String content;
        try {
            Path file = Paths.get(PATH + fileName);
            content = String.join("\n", Files.readAllLines(file));
        } catch (Exception e) {
            content = "";
        }
        return content;
    }

    public void saveFile(String fileName, Object data) throws IOException {
        Path file = Paths.get(PATH + fileName);
        Files.write(file, Collections.singletonList(data.toString()), Charset.forName("UTF-8"));
    }
}
