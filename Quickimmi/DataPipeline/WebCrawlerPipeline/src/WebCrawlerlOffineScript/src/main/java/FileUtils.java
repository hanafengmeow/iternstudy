import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class FileUtils {

    public static String getTitle(File file) throws IOException {
        Document document = Jsoup.parse(file, "UTF-8");
        return document.title();
    }

    public static void saveToFile(Set<String> urls, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String url : urls) {
                writer.write(url);
                writer.newLine(); // Add a new line for each URL
            }

            System.out.println("URLs have been written to the file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
