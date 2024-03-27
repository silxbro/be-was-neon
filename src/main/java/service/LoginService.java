package service;

import db.Database;
import java.util.Map;
import model.User;
import utils.ParameterUtils;
import webserver.http.HttpStatus;
import webserver.http.RequestResult;

public class LoginService implements Service {

    @Override
    public RequestResult execute(String userParameterData) {
        try {
            User user = findUser(userParameterData);
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGIN.getSuccessRedirectionPath());
        } catch (Exception e) {
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGIN.getFailRedirectionPath());
        }
    }

    private User findUser(String userParameterData) throws IllegalArgumentException {
        Map<String, String> userParams = ParameterUtils.parseParams(userParameterData);
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
