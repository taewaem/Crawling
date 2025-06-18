package toy.first.study.project.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaverMovie {

    public static void main(String[] args) throws Exception {
        // 크롬 드라이버 경로 설정
        System.setProperty("webdriver.chrome.driver", "C:/Dev/tool/chromedriver/chromedriver.exe");

        WebDriver driver = (WebDriver) new ChromeDriver();

        // 최근 영화 제목 수동 예시 (자동으로 Jsoup으로 받을 수도 있음)
        String[] titles = {
                "엘리오", "혹성탈출", "범죄도시4", "쿵푸팬더4", "가필드 더 무비"
        };

        List<Map<String, String>> results = new ArrayList<>();

        for (String title : titles) {
            try {
                String query = URLEncoder.encode(title, "UTF-8");
                driver.get("https://search.naver.com/search.naver?query=" + query);
                Thread.sleep(1500);

                String rating = getText(driver, ".star_score em");
                String openDate = getText(driver, ".info_group dd");
                String audience = getText(driver, "//*[contains(text(),'관람객')]/..");
                String summary = getText(driver, ".story_area .desc_group");

                Map<String, String> movie = new HashMap<>();
                movie.put("제목", title);
                movie.put("개봉일", openDate);
                movie.put("평점", rating);
                movie.put("관객수", audience);
                movie.put("줄거리", summary);

                results.add(movie);

            } catch (Exception e) {
                System.out.println("[오류] " + title + " : " + e.getMessage());
            }
        }

        driver.quit();

        // CSV 저장
        try (FileWriter csv = new FileWriter("recent_movies.csv")) {
            csv.write("제목,개봉일,평점,관객수,줄거리\n");
            for (Map<String, String> movie : results) {
                csv.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        movie.get("제목"),
                        movie.get("개봉일"),
                        movie.get("평점"),
                        movie.get("관객수"),
                        movie.get("줄거리").replace("\"", "'")));
            }
        }

        // JSON 저장
        try (FileWriter json = new FileWriter("recent_movies.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(results, json);
        }

        System.out.println("#영화 정보 5개 저장 완료!#");
    }

    // CSS 또는 XPath 요소 가져오기 (없을 경우 "없음")
    static String getText(WebDriver driver, String selector) {
        try {
            WebElement el;
            if (selector.startsWith("//")) {
                el = driver.findElement(By.xpath(selector));
            } else {
                el = driver.findElement(By.cssSelector(selector));
            }
            return el.getText().trim();
        } catch (Exception e) {
            return "없음";
        }
    }
}

