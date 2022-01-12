package thons_of_requests;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import org.json.*;



public class RequestMaker {
    static Random random = new Random();
    static Integer requestCount = 500;
    static Integer threadCount = 5;
    static Integer complete = 0;

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < requestCount; j++) {
                    try {
                        doGetRequest("https://www.digi-api.com/api/v1/digimon/" + randomDigimon());
                    } catch (IOException e) {
                    }
                }
                complete++;
                if (complete == threadCount) {
                    System.out.printf("LatencyMedium: %.3f", Latency.totalLatencyMedium());
                }
            });
            thread.start();
        }
    }

    private static Integer randomDigimon() {
        Integer firstDigimon = 1;
        Integer lastDigimon = 1422;
        return random.nextInt(lastDigimon) + firstDigimon;
    }

    private static void doGetRequest(String url) throws IOException {
        Latency latency = new Latency();
        latency.start();
        URL requestUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();

        long stopTime = System.currentTimeMillis();
        System.out.printf("Latency: %.3f\n", latency.stop());

        JSONObject jsonObject = new JSONObject(new Scanner(inputStream).nextLine());
        System.out.println("Digimon: " + jsonObject.get("name"));

    }
}
