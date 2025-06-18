package toy.first.study.project;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_C {
    public static void main(String[] args) {

        try {
            String url = "https://finance.naver.com/marketindex/exchangeList.naver";

            //크롤링 봇으로 차단되지 않도록 브라우징으로 위장
            Document doc = Jsoup.connect(url).get();     //html을 가져옴

            //주요 뉴스 제목 선택자 (2025년 기준, 구조 변경 가능)
            Elements rows = doc.select("table.tbl_exchange tbody tr"); // 텍스트 뉴스 제목만            System.out.println(headlines);

//            System.out.println("▶ 주요 뉴스 헤드라인:");
            for (Element row : rows) {
                String nation = row.select("td.tit").text();
                String rates = row.select("td.sale").text();
                System.out.println(nation);
                System.out.println(rates);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        try{
//            String url = "https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111";
//
//            //
//            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0")
//                    .get();
//
////            Elements
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
