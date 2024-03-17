package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    HttpRequest resetRequest;
    HttpRequest userCreateRequest;

    @BeforeEach
    void setUp() {
        List<String> resetRequestHeaders = List.of(
            "GET /reset.css HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "sec-ch-ua: \"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"",
            "sec-ch-ua-mobile: ?0",
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
            "sec-ch-ua-platform: \"macOS\"",
            "Accept: text/css,*/*;q=0.1",
            "Sec-Fetch-Site: same-origin",
            "Sec-Fetch-Mode: no-cors",
            "Sec-Fetch-Dest: style",
            "Referer: http://localhost:8080/index.html",
            "Accept-Encoding: gzip, deflate, br, zstd",
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            "Cookie: Idea-efef64bb=8e37f30a-e0d5-49bb-811d-9347a1cd1b40"
        );

        List<String> userCreateRequestHeaders = List.of(
            "GET /user/create?userId=ehpark&password=ehpark&name=ehpark&email=ehpark%40gmail.com HTTP/1.1",
            "Host: localhost:8080",
            "Connection: keep-alive",
            "Cache-Control: max-age=0",
            "sec-ch-ua: \"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"",
            "sec-ch-ua-mobile: ?0",
            "sec-ch-ua-platform: \"macOS\"",
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Sec-Fetch-Site: same-origin",
            "Sec-Fetch-Mode: navigate",
            "Sec-Fetch-Dest: document",
            "Referer: http://localhost:8080/registration",
            "Accept-Encoding: gzip, deflate, br, zstd",
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            "Cookie: Idea-efef64bb=8e37f30a-e0d5-49bb-811d-9347a1cd1b40"
        );
        resetRequest = new HttpRequest(resetRequestHeaders);
        userCreateRequest = new HttpRequest(userCreateRequestHeaders);
    }

    @Test
    @DisplayName("요청 메소드 반환 기능 검증")
    void getMethodTest() {
        assertThat(resetRequest.getMethod()).isEqualTo("GET");
        assertThat(userCreateRequest.getMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 경로 문자열 반환 기능 검증")
    void getRequestPathTest() {
        assertThat(resetRequest.getRequestPath()).isEqualTo("/reset.css");
        assertThat(userCreateRequest.getRequestPath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("요청 데이터 Parameter 문자열 반환 기능 검증")
    void getParameterDataTest() {
        assertThat(resetRequest.getParameterData()).isEqualTo("");
        assertThat(userCreateRequest.getParameterData()).isEqualTo("userId=ehpark&password=ehpark&name=ehpark&email=ehpark%40gmail.com");
    }

    @Test
    @DisplayName("요청 Accept Content Type 반환 기능 검증")
    void getRequestedTypesTest() {
        List<String> resetRequestAcceptTypes = List.of(
            "text/css",
            "*/*;q=0.1"
        );
        List<String> userCreateRequestAcceptTypes = List.of(
            "text/html",
            "application/xhtml+xml",
            "application/xml;q=0.9",
            "image/avif",
            "image/webp",
            "image/apng",
            "*/*;q=0.8",
            "application/signed-exchange;v=b3;q=0.7"
        );
        assertThat(resetRequest.getRequestedTypes()).isEqualTo(resetRequestAcceptTypes);
        assertThat(userCreateRequest.getRequestedTypes()).isEqualTo(userCreateRequestAcceptTypes);
    }

    @Test
    @DisplayName("요청 헤더 로그 출력 기능 검증")
    public void printHeadersTest() {
    }
}