package business;

import static org.assertj.core.api.Assertions.assertThat;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationExecutorTest {

    @Test
    @DisplayName("사용자 쿼리 파라미터 매개변수를 이용하여 사용자 등록 후 정확히 저장되었는지 확인")
    void executeTest() {
        String userInfo = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        new RegistrationExecutor().execute(userInfo);
        User user = Database.findUserById("javajigi");

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("박재성");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }
}