package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PathUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "/index.html, src/main/resources/static/index.html",
        "/registration, src/main/resources/static/registration/index.html",
        "/img/signiture.svg, src/main/resources/static/img/signiture.svg",
        "/main.css, src/main/resources/static/main.css",
    })
    @DisplayName("클라이언트 요청 헤더에서 추출한 경로의 전체 경로 반환 기능 검증")
    void getStaticPathTest(String input, String expectedOutput) {
        String relativePath = PathUtils.getStaticPath(input);
        assertThat(relativePath).isEqualTo(expectedOutput);
    }

    @ParameterizedTest
    @CsvSource({
        "src/main/resources/static/index.html, html",
        "src/main/resources/static/img/bookMark.svg, svg",
        "src/main/resources/static/img/signiture.png, png",
        "src/main/resources/static/main.min.css, css",
        "src/main/resources/static/favicon.ico, ico",
        "src/main/resources/static/main.css, css"
    })
    @DisplayName("파일 경로의 전체 경로 반환 기능 검증")
    void getExtensionTest(String input, String expectedOutput) {
        String extension = PathUtils.getExtension(input);
        assertThat(extension).isEqualTo(expectedOutput);
    }
}