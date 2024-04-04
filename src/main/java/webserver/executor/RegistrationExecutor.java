package webserver.executor;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.path.PathHandler;

public class RegistrationExecutor extends PostExecutor {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        try {
            User user = createUser(request);
            Database.addUser(user);
            response.setRedirect(PathHandler.getStaticDefaultPath());
        } catch (IllegalArgumentException e) {
            response.setRedirect(PathHandler.getRegistrationFailedPath());
        }
    }

    private User createUser(HttpRequest request) throws IllegalArgumentException {
        String userId = validateUserId(request.getParameter("userId"));
        String password = validatePassword(request.getParameter("password"));
        String name = validateName(request.getParameter("name"));
        String email = validateEmail(request.getParameter("email"));
        return new User(userId, password, name, email);
    }

    private String validateUserId(String userId) {
        // 아이디 입력 누락 또는 공백 포함
        if (userId == null || userId.isEmpty() || userId.contains(" ")) {
            throw new IllegalArgumentException();
        }
        // 이미 사용중인 아이디
        if (Database.findUserById(userId) != null) {
            throw new IllegalArgumentException();
        }
        return userId;
    }

    private String validatePassword(String password) {
        // 비밀번호 입력 누락 또는 공백 포함
        if (password == null || password.isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException();
        }
        return password;
    }

    private String validateName(String name) {
        // 이름 입력 누락 또는 공백 포함
        if (name == null || name.isEmpty() || name.contains(" ")) {
            throw new IllegalArgumentException();
        }
        return name;
    }

    private String validateEmail(String email) {
        // 이메일 입력 누락 또는 공백 포함
        if (email == null || email.isEmpty() || email.contains(" ")) {
            throw new IllegalArgumentException();
        }
        return email;
    }
}