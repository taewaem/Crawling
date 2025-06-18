package toy.first.study.project.example;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class News{
    String title;
    String link;
    String image;

    public String[] toArray() {
        return new String[] { title, link, image };
    }
}

public class NewsCrawlerUI extends JFrame {
    private JTextField urlField;
    private JButton crawlBtn, saveCsvBtn, saveJsonBtn;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<News> newsList = new ArrayList<>();

//  GUI기반 뉴스 크롤링
    public NewsCrawlerUI() {
        setTitle("뉴스 크롤러");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        urlField = new JTextField("https://news.naver.com/", 40);
        crawlBtn = new JButton("크롤링 시작");
        saveCsvBtn = new JButton("CSV 저장");
        saveJsonBtn = new JButton("JSON 저장");

        JPanel topPanel = new JPanel();
        topPanel.add(urlField);
        topPanel.add(crawlBtn);

        tableModel = new DefaultTableModel(new String[]{"제목", "링크", "이미지 URL"}, 0);
        resultTable = new JTable(tableModel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveCsvBtn);
        bottomPanel.add(saveJsonBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        crawlBtn.addActionListener(this::startCrawling);
        saveCsvBtn.addActionListener(e -> saveToCSV("news.csv"));
        saveJsonBtn.addActionListener(e -> saveToJSON("news.json"));
    }

    //크롤링 시작 메서드
    private void startCrawling(ActionEvent e) {

        try {
            String url = urlField.getText();
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Elements articles = doc.select("a[href^=https://n.news.naver.com/article/]");
            Elements imgs = doc.select("a[href^=https://n.news.naver.com/article/] img");

            newsList.clear();
            tableModel.setRowCount(0);

            int count = Math.min(articles.size(), imgs.size());

                for (int i = 0; i < count; i++) {
                    String title = articles.get(i).text();
                    String link = articles.get(i).attr("href");
                    String image = imgs.get(i).attr("src");
                    News item = new News(title, link, image);
                    newsList.add(item);
                    tableModel.addRow(item.toArray());
                }
                JOptionPane.showMessageDialog(this, "총 " + count + "건의 뉴스를 크롤링했습니다.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "크롤링 중 오류 발생: " + ex.getMessage());
        }
    }

    private void saveToJSON(String filename) {
//        try (FileWriter fw = new FileWriter(filename)) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            gson.toJson(newsList, fw);
//            JOptionPane.showMessageDialog(this, filename + " 저장 완료!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "JSON 저장 중 오류: " + e.getMessage());
//        }
    }

    private void saveToCSV(String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("title,link,image\n");
            for (News item : newsList) {
                fw.write(String.format("\"%s\",\"%s\",\"%s\"\n",
                        item.title.replace("\"", ""),
                        item.link,
                        item.image));
            }
            JOptionPane.showMessageDialog(this, filename + " 저장 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "CSV 저장 중 오류: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewsCrawlerUI().setVisible(true));
    }

}

