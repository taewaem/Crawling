//package toy.first.study.project;
//
//import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
//import toy.first.study.soldesk.AddressVO;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class NaverMap implements ActionListener {
//
//    private static final String GEOCODE_API_URL = "https://maps.apigw.ntruss.com/map-geocode/v2/geocode";
//    private static final String STATICMAP_API_URL = "https://maps.apigw.ntruss.com/map-static/v2/raster";
//    private static final String CLIENT_ID = "0lgw78jhst";
//    private static final String CLIENT_SECRET = "lmME4abm9iAUYRycbLi5ckVaSebS18HfwlQcn7dO";
//
//
//    Project01_GUI naverMap;
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        AddressVO vo = null;
//
//        try {
//
//            String address = naverMap.address.getText();
//            String encodedAddress = URLEncoder.encode(address, "UTF-8");
//
//            String apiURL = GEOCODE_API_URL + "?query=" + encodedAddress;
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
//            con.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);
//            con.setRequestProperty("Accept", "application/json");
//
//
//            int responseCode = con.getResponseCode();
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (responseCode == 200) ? con.getInputStream() : con.getErrorStream(), "UTF-8"));
//
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            br.close();
//            con.disconnect();
//
//            if (responseCode == 200) {
//                JSONObject json = new JSONObject(sb.toString());
//                JSONArray arr = json.getJSONArray("addresses");
//                if (arr.length() > 0) {
//                    JSONObject temp = arr.getJSONObject(0);
//                    vo = new AddressVO();
//                    vo.setRoadAddress(temp.getString("roadAddress"));
//                    vo.setJibunAddress(temp.getString("jibunAddress"));
//                    vo.setX(temp.getString("x"));
//                    vo.setY(temp.getString("y"));
//                } else {
//                    System.out.println("❗ 검색 결과 없음");
//                }
//            } else {
//                System.out.println("❗ Geocode API 오류: " + sb.toString());
//            }
//
//            if (vo != null) {
//                map_service(vo);
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    public void map_service(AddressVO vo) {
//        try {
////정보 출력
//            naverMap.resAddress.setText("도로명 주소: " + vo.getRoadAddress());
//            naverMap.jibunAddress.setText("지번 주소: " + vo.getJibunAddress());
//            naverMap.resX.setText("경도: " + vo.getX());
//            naverMap.resY.setText("위도: " + vo.getY());
//
//            //이미지 출력
//            String center = vo.getX() + "," + vo.getY();
//            String requestUrl = STATICMAP_API_URL
//                    + "?center=" + center
//                    + "&level=16&w=700&h=500";
//
//            URL url = new URL(requestUrl);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
//            con.setRequestProperty("X-NCP-APIGW-API-KEY", CLIENT_SECRET);
//
//            int responseCode = con.getResponseCode();
//            if (responseCode == 200) {
//                InputStream inputStream = con.getInputStream();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                byte[] buffer = new byte[1024];
//                int len;
//                while ((len = inputStream.read(buffer)) != -1) {
//                    baos.write(buffer, 0, len);
//                }
//
//                // 이미지 출력
//                ImageIcon imageIcon = new ImageIcon(baos.toByteArray());
//                naverMap.imageLabel.setIcon(imageIcon);
//            } else {
//                System.out.println("❌ Static Map API 오류 코드: " + responseCode);
//                BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//                String line;
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }
//                br.close();
//                naverMap.imageLabel.setText("❌ 이미지 불러오기 실패");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            naverMap.imageLabel.setText("❌ 예외 발생");
//        }
//    }
//
//    public static void main(String[] args) {
//        new Project01_GUI().initGUI();
//    }
//}
