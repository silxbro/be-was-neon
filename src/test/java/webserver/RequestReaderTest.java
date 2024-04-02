package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.MethodType;
import webserver.http.RequestReader;

class RequestReaderTest {

    @Test
    @DisplayName("주어진 HTTP 요청 메시지를 읽어 해당 HTTP 요청 객체를 정확히 생성하는지 확인")
    void getRequestReaderTest() throws IOException {
        String input = "POST /user/create HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 59\r\n" +
            "Content-Type: application/x-www-form-urlencoded\r\n" +
            "Accept: */*\r\n" +
            "\r\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestReader requestReader = new RequestReader();
        HttpRequest httpRequest = requestReader.getRequest(new ByteArrayInputStream(input.getBytes()));

        assertThat(httpRequest.getMethod()).isEqualTo(MethodType.POST);
        assertThat(httpRequest.getAbsolutePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getQuery()).isEqualTo("");
        assertThat(httpRequest.needService()).isEqualTo(true);
        assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}