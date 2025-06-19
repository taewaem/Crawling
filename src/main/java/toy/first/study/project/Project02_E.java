package toy.first.study.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import toy.first.study.soldesk.DownloadBroker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Project02_E {
    public static void main(String[] args) {
        try {

            String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("[입력->년(yyyy)-월(mm)-일(dd)]:");
            String bible = br.readLine();
            url = url + "&Base_de=" + bible + "&bibleType=1";
            System.out.println("============================================");
            Document doc=Jsoup.connect(url).post();
            //System.out.println(doc);
            Element bible_text= doc.select(".bible_text").first();
            System.out.println(bible_text.text());

            Elements liList = doc.select(".body_list > li");
            for (Element li : liList) {
                System.out.print(li.select(".num").first().text() + ":");
                System.out.println(li.select(".info").first().text());
            }

            System.out.println("------------------------------------------");

            // sound
//            Element tag = doc.select("soucre").first();
//            String dPath = tag.attr("src").trim();
            // image
            Element tag = doc.select(".img > img").first();
            String dPath = "https://sum/su.or.kr.8888" + tag.attr("src").trim();

            System.out.println(dPath);

            String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);
            System.out.println(fileName);

            Runnable r = new DownloadBroker(dPath, fileName);
            Thread dLoad = new Thread(r);
            dLoad.start();
            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(" " + (i + 1));
            }
            System.out.println();
            System.out.println("================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}