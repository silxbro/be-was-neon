package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ParameterUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "userId, javajigi",
        "password, password",
        "name, 박재성",
        "email, javajigi@slipp.net"
    })
    @DisplayName("사용자 정보를 담은 문자열 파싱 후 parameter 추출 기능 검증")
    void parseUserParamsTest(String input, String expectedOutput) {
        String userString = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> userParams = ParameterUtils.parseParams(userString);

        assertThat(userParams.get(input)).isEqualTo(expectedOutput);
    }
}