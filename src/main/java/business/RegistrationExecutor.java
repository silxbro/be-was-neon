package business;

import db.Database;
import java.util.Map;
import model.User;
import utils.ParameterUtils;

public class RegistrationExecutor implements BusinessExecutor {

    @Override
    public boolean execute(String userInfo) {
        try {
            User user = createUser(userInfo);
            Database.addUser(user);
            return true;
        } catch (IllegalArgumentException|ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private User createUser(String userInfo) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        Map<String, String> userParams = ParameterUtils.parseParams(userInfo);
        User user = new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
        validate(user);

        return user;
    }

    private void validate(User user) {
        if (user.getUserId().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
