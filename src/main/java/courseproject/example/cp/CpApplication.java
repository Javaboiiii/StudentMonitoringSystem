package courseproject.example.cp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.SwingUtilities;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import courseproject.example.cp.loginandsignup.LoginAndSignUp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootApplication
public class CpApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(CpApplication.class, args);
        CpApplication app = new CpApplication();
        app.someMethod();
    }

    @EventListener(ApplicationReadyEvent.class)
public void startSwingApp(ApplicationReadyEvent event) {
    SwingUtilities.invokeLater(() -> {
        System.out.println("About to create LoginAndSignUp");
        LoginAndSignUp.main(new String[0]);
        System.out.println("Created LoginAndSignUp");
    });
}

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    public String sendPostRequest(OkHttpClient client, String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json.getBytes(StandardCharsets.UTF_8));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void someMethod() {
        String json = "{ \"query\": \"query Query { users { email password role user_id username } }\" }";
        try {
            String response = sendPostRequest(client(), "http://localhost:4000", json);
            System.out.println(response);
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
