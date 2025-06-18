package toy.first.study.project;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_A {
    public static void main(String[] args) {
//        String url = "https://news.naver.com/";
        try {
            String url = "https://news.naver.com/main/list.naver?mode=LSD&mid=sec&sid1=105"; // IT/과학

            //크롤링 봇으로 차단되지 않도록 브라우징으로 위장
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();     //html을 가져옴

            //주요 뉴스 제목 선택자 (2025년 기준, 구조 변경 가능)
            Elements newsItems = doc.select("ul.type06_headline li dt:not(.photo) a"); // 텍스트 뉴스 제목만            System.out.println(headlines);

//            System.out.println("▶ 주요 뉴스 헤드라인:");
            for (Element el : newsItems) {
                System.out.println(el.text());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
