package webserver.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PathHandlerTest {

    @ParameterizedTest
    @CsvSource({
        "/index.html, src/main/resources/static/index.html",
        "/registration, src/main/resources/static/registration/index.html",
        "/img/signiture.svg, src/main/resources/static/img/signiture.svg",
        "/main.css, src/main/resources/static/main.css",
    })
    @DisplayName("HTTP 요청 메시지의 절대 경로를 정적 리소스 파일의 상대 경로로 정확히 바꾸어 반환하는지 확인")
<<<<<<< HEAD:src/test/java/webserver/path/PathHandlerTest.java
    void getStaticResourcePathTest(String input, String expectedOutput) {
        String relativePath = PathHandler.getStaticResourcePath(input);
=======
    void getStaticPathTest(String input, String expectedOutput) {
        String relativePath = PathUtils.getStaticResourcePath(input);
>>>>>>> upstream/silxbro:src/test/java/utils/PathUtilsTest.java
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
    @DisplayName("파일 경로의 확장자를 정확히 반환하는지 확인")
    void getExtensionTest(String input, String expectedOutput) {
        String extension = PathHandler.getExtension(input);
        assertThat(extension).isEqualTo(expectedOutput);
    }
}