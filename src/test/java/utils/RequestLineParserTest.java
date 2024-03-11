package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.RequestLineParser.INVALID_REQUEST_LINE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineParserTest {

    @Test
    @DisplayName("Request Line에서 path 분리 기능 검증")
    void extractPath() {
        String requestLine = "GET /index.html HTTP/1.1";
        String expectedPath = "/index.html";
        String extractedPath = RequestLineParser.extractPath(requestLine);
        assertThat(extractedPath).isEqualTo(expectedPath);
    }

    @Test
    @DisplayName("분리한 path가 유효하지 않은 경우(= 유효하지 않은 Request Line) 예외 발생 확인")
    void invalidRequestLine() {
        String requestLine = "GET";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            RequestLineParser.extractPath(requestLine);
        });

        assertThat(exception).hasMessageStartingWith(INVALID_REQUEST_LINE);
    }
}