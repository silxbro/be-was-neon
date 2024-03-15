package http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    @DisplayName("Request Line에서 path 분리 기능 검증")
    void getHeaderPath() {
        List<String> headers = List.of(
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
        HttpRequest request = new HttpRequest(headers);
        assertThat(request.getRequestPath()).isEqualTo("/reset.css");
    }
}