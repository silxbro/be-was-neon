
package service;

import db.Database;
import java.util.List;
import java.util.Map;
import model.User;
import utils.StringParser;
import webserver.handler.CookieHandler;
import webserver.http.RequestResult;
import db.SessionManager;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

public class LoginService implements Service {

    @Override
    public RequestResult execute(String userData) {
        try {
            User user = findUser(userData);
            String sessionId = SessionManager.addSession(user);
            Cookie sessionCookie = new CookieHandler().getLoginCookie(sessionId);
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGIN.getSuccessRedirectionPath(), List.of(sessionCookie));
        } catch (Exception e) {
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGIN.getFailRedirectionPath());
        }
    }

    private User findUser(String userData) throws IllegalArgumentException {
        Map<String, String> userParams = StringParser.parseUserData(userData);
        User user = Database.findUserById(userParams.get("userId"));
        authenticate(user, userParams.get("password"));
        return user;
    }

    private void authenticate(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException();
        }
    }
}