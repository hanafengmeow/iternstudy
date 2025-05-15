package utils;

import crawlercommons.robots.SimpleRobotRules;
import crawlercommons.robots.SimpleRobotRulesParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RobotsParserTest {

    @Test
    void testSimpleRobotRule() {
        String baseUrl = "https://www.uscis.gov/robots.txt";
        SimpleRobotRulesParser robotParser = new SimpleRobotRulesParser();
        SimpleRobotRules simpleRobotRules = robotParser.parseContent(
                baseUrl,
                RobotsParser.uscisRobotText.getBytes(),
                "text/plain", "crawler-commons");
        Assertions.assertTrue(simpleRobotRules.isAllowed("https://www.uscis.gov"));
        Assertions.assertFalse(simpleRobotRules.isAllowed("https://www.uscis.gov/tools/reports-and-studies/h-1b-employer-data-hub"));
        Assertions.assertTrue(simpleRobotRules.isAllowed("https://www.uscis.gov/topics"));
    }
}