package service;

import db.Database;
import java.util.Map;
import model.User;
import utils.StringParser;
import webserver.http.RequestResult;
import webserver.http.HttpStatus;

public class RegistrationService implements Service {

    @Override
    public RequestResult execute(String userData) {
        try {
            User user = createUser(userData);
            Database.addUser(user);
            return new RequestResult(HttpStatus.FOUND, ServiceType.REGISTRATION.getSuccessRedirectionPath());
        } catch (IllegalArgumentException e) {
            return new RequestResult(HttpStatus.FOUND, ServiceType.REGISTRATION.getFailRedirectionPath());
        }
    }

    private User createUser(String userData) throws IllegalArgumentException {
        Map<String, String> userParams = getUserParams(userData);
        return new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
    }

    private Map<String, String> getUserParams(String userParameterData) {
        try {
            return StringParser.parseUserData(userParameterData);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }
}
