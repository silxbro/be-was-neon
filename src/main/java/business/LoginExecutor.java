package business;

import db.Database;
import java.util.Map;
import model.User;
import utils.ParameterUtils;

public class LoginExecutor implements BusinessExecutor {

    @Override
    public boolean execute(String userInfo) {
        try {
            User user = findUser(userInfo);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private User findUser(String userInfo) throws IllegalArgumentException {
        Map<String, String> userParams = ParameterUtils.parseParams(userInfo);
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
