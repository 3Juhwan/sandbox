package sandbox.http.connection;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class HttpClientTest {

    @Test
    void syncTest() throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(1000))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/hello"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        assertThat(response.body()).isEqualTo("Hello, World!");
    }

    @Test
    void asyncTest() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(1000))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/hello"))
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(request,
                BodyHandlers.ofString());

        HttpResponse<String> response = futureResponse.join();
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        assertThat(response.body()).isEqualTo("Hello, World!");
    }
}
