import  java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class LMSIntegration {

    private static final String LMS_API_URL = "https://example-lms.com/api/v1/studentData";
    private static final String API_KEY = "your_api_key_here";

    public static void main(String[] args) {
        try {
            URL url = new URL(LMS_API_URL + "?apiKey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject studentData = new JSONObject(response.toString());
                System.out.println("Student Data: " + studentData);
                // Process the student data as needed
            } else {
                System.out.println("GET request failed: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

