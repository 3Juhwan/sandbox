package sandbox.http.connection;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import org.junit.jupiter.api.Test;

class HttpURLConnectionTest {

    @Test
    void connectionTest() throws Exception {
        URI uri = URI.create("http://localhost:8080/hello");
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setConnectTimeout(1000);
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                responseBody.append(inputLine);
            }
        }

        assertThat(responseCode).isEqualTo(HttpURLConnection.HTTP_OK);
        assertThat(responseBody.toString()).isEqualTo("Hello, World!");
    }
}
