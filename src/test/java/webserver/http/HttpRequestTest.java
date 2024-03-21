package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.method.Method;

class HttpRequestTest {

    HttpRequest firstRequest;
    HttpRequest userCreateByGetRequest;
    HttpRequest userCreateByPutRequest;

    @BeforeEach
    void setUp() {
        List<String> firstRequestHeaders = List.of(
            "GET /index.html HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
        );

        List<String> userCreateByGetRequestHeaders = List.of(
            "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
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
        userCreateByGetRequest = new HttpRequest(userCreateByGetRequestHeaders, "");
        userCreateByPutRequest = new HttpRequest(userCreateByPutRequestHeaders, "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

    }

    @Test
    @DisplayName("HTTP 요청 메시지의 메서드가 정확히 반환되는지 확인")
    void getMethodTest() {
        assertThat(firstRequest.getMethod()).isEqualTo(Method.GET);
        assertThat(userCreateByGetRequest.getMethod()).isEqualTo(Method.GET);
        assertThat(userCreateByPutRequest.getMethod()).isEqualTo(Method.POST);

    }

    @Test
    @DisplayName("")
    void shouldDirectTest() {
        assertThat(firstRequest.shouldRedirect()).isEqualTo(false);
        assertThat(userCreateByGetRequest.shouldRedirect()).isEqualTo(false);
        assertThat(userCreateByPutRequest.shouldRedirect()).isEqualTo(true);
    }

    @Test
    @DisplayName("HTTP 요청 메시지의 절대 경로가 정확히 반환되는지 확인")
    void getAbsolutePathTest() {
        assertThat(firstRequest.getAbsolutePath()).isEqualTo("/index.html");
        assertThat(userCreateByGetRequest.getAbsolutePath()).isEqualTo("/user/create");
        assertThat(userCreateByPutRequest.getAbsolutePath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("HTTP 요청 메시지의 쿼리 파라미터가 정확히 반환되는지 확인")
    void getQueryTest() {
        assertThat(firstRequest.getQuery()).isEqualTo("");
        assertThat(userCreateByGetRequest.getQuery()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertThat(userCreateByPutRequest.getQuery()).isEqualTo("");
    }

    @Test
    @DisplayName("HTTP 요청 메시지의 body가 정확히 반환되는지 확인")
    void getBodyTest() {
        assertThat(firstRequest.getBody()).isEqualTo("");
        assertThat(userCreateByGetRequest.getBody()).isEqualTo("");
        assertThat(userCreateByPutRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }

    @Test
    @DisplayName("HTTP 요청 메시지의 Accept Type들이 정확히 반환되는지 확인")
    void getAcceptTypesTest() {
        assertThat(firstRequest.getAcceptTypes()).isEqualTo(List.of(
            "text/html", "application/xhtml+xml", "application/xml;q=0.9", "image/avif", "image/webp", "image/apng", "*/*;q=0.8", "application/signed-exchange;v=b3;q=0.7"));
        assertThat(userCreateByGetRequest.getAcceptTypes()).isEqualTo(List.of(
            "text/html", "application/xhtml+xml", "application/xml;q=0.9", "image/avif", "image/webp", "*/*;q=0.8", "application/signed-exchange;v=b3;q=0.7"));
        assertThat(userCreateByPutRequest.getAcceptTypes()).isEqualTo(List.of(
            "*/*"));
    }
}