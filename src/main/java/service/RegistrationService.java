package service;

import db.Database;
import java.util.Map;
import model.User;
import utils.ParameterUtils;
import webserver.http.RequestResult;
import webserver.http.HttpStatus;

public class RegistrationService implements Service {

    @Override
    public RequestResult execute(String userParameterData) {
        try {
            User user = createUser(userParameterData);
            Database.addUser(user);
            return new RequestResult(HttpStatus.FOUND, ServiceType.REGISTRATION.getSuccessRedirectionPath());
        } catch (IllegalArgumentException e) {
            return new RequestResult(HttpStatus.FOUND, ServiceType.REGISTRATION.getFailRedirectionPath());
        }
    }

    private User createUser(String userParameterData) throws IllegalArgumentException {
        Map<String, String> userParams = getUserParams(userParameterData);

        return new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
    }

    private Map<String, String> getUserParams(String userParameterData) {
        try {
            return ParameterUtils.parseParams(userParameterData);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }
}
