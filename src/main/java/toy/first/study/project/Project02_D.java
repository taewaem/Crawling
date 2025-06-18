package toy.first.study.project;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_D {
    public static void main(String[] args) {

        try {

//            실제 url
//            String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EB%82%A0%EC%94%A8&ackey=hvzvexpc";
//            String query = "서울 날씨";
//            String url = "https://m.search.naver.com/search.naver?where=m&query=" +
//                    java.net.URLEncoder.encode(query, "UTF-8");

            String url = "https://www.mois.go.kr/frt/bbs/type010/commonSelectBoardList.do?bbsId=BBSMSTR_000000000008";

            Document doc = Jsoup.connect(url)
                    .get();

            Elements rows = doc.select("#print_area > div.table_wrap.type_01 > form > table > tbody > tr");
            Elements titles = doc.select("#print_area");

//            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0")
//                    .get();

//            Elements tempElem = doc.select(".temperature_text")
            Elements tempElem = doc.select(".temperature_text");
            Elements descElem = doc.select(".weather_main");

            for (Element row : rows) {
                String value = row.select("td.l").text();
                System.out.println(value);
            }
//            if (!tempElem.isEmpty() && !descElem.isEmpty()) {
//                String temp = tempElem.text();
//                String desc = descElem.first().text();
//                System.out.println("현재 날씨 : " + desc);
//                System.out.println("현재 기온 : " + temp);
//
//            } else {
//                System.out.println("failed to rode weather");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}