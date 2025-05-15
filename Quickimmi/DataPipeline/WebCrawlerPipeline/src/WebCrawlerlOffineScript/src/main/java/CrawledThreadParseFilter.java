import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CrawledThreadParseFilter {

    public static void main(String[] args) throws IOException {
        parserAndFilter(Constant.NIW_TAG);
    }

    private static void parserAndFilter(String tag) throws IOException {
        Set<String> urlsNeedReview = new HashSet<>();
        File folder = new File(Constant.getThreadPagePath(tag));
        File[] files = folder.listFiles();
        for (File file : files) {
            // Check if the current item is a file
            if (file.isFile()) {
                // Read the first line of the file
                String title = getTitle(file);
                // Print file name and its first line
                if (containsKeyword(title)) {
                    System.out.println("File: " + file.getName() + ", Title: " + title);
                    urlsNeedReview.add(Constant.BASE_URL + "/" + file.getName());
                }
            }
        }
        String filePath = Constant.getNeedReviewURLPath(tag);
        saveToFile(urlsNeedReview, filePath);
        System.out.println(urlsNeedReview.size() + " need to review");
    }

    private static boolean containsKeyword(String title) {
        for (String keyword : Constant.KEY_WORDS) {
            // Check if the input string contains the current keyword
            if (title.toLowerCase().contains(keyword.toLowerCase())) {
                return true; // Return true if a keyword is found
            }
        }
        return false; // Return false if no keyword is found
    }

    private static String getTitle(File file) throws IOException {
        Document document = Jsoup.parse(file, "UTF-8");
        return document.title();
    }


    private static void saveToFile(Set<String> urls, String filePath) {
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
