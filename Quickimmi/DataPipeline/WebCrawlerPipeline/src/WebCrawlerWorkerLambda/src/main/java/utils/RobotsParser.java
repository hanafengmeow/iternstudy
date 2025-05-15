package utils;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import crawlercommons.robots.SimpleRobotRules;
import crawlercommons.robots.SimpleRobotRulesParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class RobotsParser {
    private Set<String> excludedExtensions = Sets.newHashSet(".css", ".css?", ".css$", ".js$", ".js?", ".svg",
            ".png", ".jpeg", ".jpg", ".gif", ".js", ".xml", ".avi", ".mov", ".mp3");

    //download the robotText from the website and save it in S3 in Init Lambda process.
    public static String uscisRobotText = "#\n" +
            "# robots.txt\n" +
            "#\n" +
            "# This file is to prevent the crawling and indexing of certain parts\n" +
            "# of your site by web crawlers and spiders run by sites like Yahoo!\n" +
            "# and Google. By telling these \"robots\" where not to go on your site,\n" +
            "# you save bandwidth and server resources.\n" +
            "#\n" +
            "# This file will be ignored unless it is at the root of your host:\n" +
            "# Used:    http://example.com/robots.txt\n" +
            "# Ignored: http://example.com/site/robots.txt\n" +
            "#\n" +
            "# For more information about the robots.txt standard, see:\n" +
            "# http://www.robotstxt.org/robotstxt.html\n" +
            "\n" +
            "User-agent: *\n" +
            "Crawl-delay: 1\n" +
            "# Custom\n" +
            "Disallow: /sites/default/files/archive/\n" +
            "Disallow: /tools/reports-and-studies/h-1b-employer-data-hub\n" +
            "Disallow: /tools/reports-and-studies/h-1b-employer-data-hub/export\n" +
            "Disallow: /tools/reports-and-studies/h-1b-employer-data-hub/export-csv\n" +
            "Disallow: /es/herramientas/informes-y-estudios/centro-de-datos-de-empleadores-h-1b\n" +
            "Disallow: /es/tools/reports-and-studies/h-1b-employer-data-hub/export\n" +
            "Disallow: /es/tools/reports-and-studies/h-1b-employer-data-hub/export-csv\n" +
            "Disallow: /tools/reports-and-studies/h-2b-employer-data-hub\n" +
            "Disallow: /tools/reports-and-studies/h-2b-employer-data-hub/export\n" +
            "Disallow: /tools/reports-and-studies/h-2b-employer-data-hub/export-csv\n" +
            "Disallow: /es/herramientas/informes-y-estudios/centro-de-datos-de-empleadores-h-2b\n" +
            "Disallow: /es/tools/reports-and-studies/h-2b-employer-data-hub/export\n" +
            "Disallow: /es/tools/reports-and-studies/h-2b-employer-data-hub/export-csv\n" +
            "Disallow: /save/save-agency-search-tool\n" +
            "Disallow: /save/save-agency-search-tool/export\n" +
            "Disallow: /tools/civil-surgeons-by-region\n" +
            "Disallow: /tools/find-a-doctor/list/export\n" +
            "# CSS, JS, Images\n" +
            "Allow: /core/*.css$\n" +
            "Allow: /core/*.css?\n" +
            "Allow: /core/*.js$\n" +
            "Allow: /core/*.js?\n" +
            "Allow: /core/*.gif\n" +
            "Allow: /core/*.jpg\n" +
            "Allow: /core/*.jpeg\n" +
            "Allow: /core/*.png\n" +
            "Allow: /core/*.svg\n" +
            "Allow: /profiles/*.css$\n" +
            "Allow: /profiles/*.css?\n" +
            "Allow: /profiles/*.js$\n" +
            "Allow: /profiles/*.js?\n" +
            "Allow: /profiles/*.gif\n" +
            "Allow: /profiles/*.jpg\n" +
            "Allow: /profiles/*.jpeg\n" +
            "Allow: /profiles/*.png\n" +
            "Allow: /profiles/*.svg\n" +
            "# Directories\n" +
            "Disallow: /core/\n" +
            "Disallow: /profiles/\n" +
            "# Files\n" +
            "Disallow: /README.txt\n" +
            "Disallow: /web.config\n" +
            "# Paths (clean URLs)\n" +
            "Disallow: /admin/\n" +
            "Disallow: /comment/reply/\n" +
            "Disallow: /filter/tips\n" +
            "Disallow: /node/add/\n" +
            "Disallow: /search/\n" +
            "Disallow: /user/register\n" +
            "Disallow: /user/password\n" +
            "Disallow: /user/login\n" +
            "Disallow: /user/logout\n" +
            "Disallow: /media/oembed\n" +
            "Disallow: /*/media/oembed\n" +
            "# Paths (no clean URLs)\n" +
            "Disallow: /index.php/admin/\n" +
            "Disallow: /index.php/comment/reply/\n" +
            "Disallow: /index.php/filter/tips\n" +
            "Disallow: /index.php/node/add/\n" +
            "Disallow: /index.php/search/\n" +
            "Disallow: /index.php/user/password\n" +
            "Disallow: /index.php/user/register\n" +
            "Disallow: /index.php/user/login\n" +
            "Disallow: /index.php/user/logout\n" +
            "Disallow: /index.php/media/oembed\n" +
            "Disallow: /index.php/*/media/oembed\n";

    private String baseUrl = "https://www.uscis.gov/robots.txt";

    private LambdaLogger logger;

    public RobotsParser(LambdaLogger logger) {
        this.logger = logger;
    }

    public List<String> filter(Set<String> urls) {
        logger.log("enter filter method with input: %s " + urls);
        SimpleRobotRulesParser robotParser = new SimpleRobotRulesParser();
        SimpleRobotRules simpleRobotRules = robotParser.parseContent(
                baseUrl,
                uscisRobotText.getBytes(),
                "text/plain", "crawler-commons");

        List<String> result = Lists.newArrayList();
        for (String url : urls) {
            boolean allowed = simpleRobotRules.isAllowed(url);
            boolean isExcluded = excludedExtensions.stream().anyMatch(ext -> url.endsWith(ext));
            if (allowed && !isExcluded) {
                result.add(url);
            }
        }
        return result;
    }
}
