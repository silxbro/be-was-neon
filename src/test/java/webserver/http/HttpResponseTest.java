package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.path.PathHandler;

class HttpResponseTest {

    HttpRequest firstRequest;
    HttpRequest userCreateByPutRequest;
    OutputStream outputStream;
    HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        httpResponse = new HttpResponse(outputStream);

        initRequests();
    }

    void initRequests() {
        List<String> firstRequestHeaders = List.of(
            "GET /index.html HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
        );
        List<String> userCreateByPutRequestHeaders = List.of(
            "POST /user/create HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Content-Length: 59",
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*"
        );
        firstRequest = new HttpRequest(firstRequestHeaders, "");
        userCreateByPutRequest = new HttpRequest(userCreateByPutRequestHeaders,
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    @DisplayName("응답할 파일이 있는 HTTP 요청의 경우, 성공(200 OK)을 나타내는 HTTP 응답 메시지를 정확히 보내는지 확인")
    void sendSuccessfulTest() throws IOException {
        httpResponse.send(firstRequest);
        String[] responseLines = outputStream.toString().split("\r\n");;

        assertThat(responseLines[0]).isEqualTo("HTTP/1.1 200 OK");
        assertThat(responseLines[1]).isEqualTo("Content-Type: text/html");
        assertThat(responseLines[2].substring(0, 15)).isEqualTo("Content-Length:");
    }

    @Test
    @DisplayName("응답할 파일이 없는 HTTP 요청의 경우, 리다이렉션(302 Found)을 나타내는 HTTP 응답 메시지를 정확히 보내는지 확인")
    void sendRedirectTest() throws IOException {
        httpResponse.send(userCreateByPutRequest);
        String[] responseLines = outputStream.toString().split("\r\n");;

        assertThat(responseLines[0]).isEqualTo("HTTP/1.1 302 Found");
        assertThat(responseLines[1]).isEqualTo("Location: " + PathHandler.getStaticDefaultPath());
    }
}