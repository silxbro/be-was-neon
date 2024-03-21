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
    @DisplayName("사용자 쿼리 파라미터 문자열이 필드명-필드값으로 정확히 파싱되었는지 확인")
    void parseUserParamsTest(String input, String expectedOutput) {
        String userInfo = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> userParams = ParameterUtils.parseParams(userInfo);

        assertThat(userParams.get(input)).isEqualTo(expectedOutput);
    }
}