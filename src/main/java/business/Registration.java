package business;

import db.Database;
import java.util.Map;
import model.User;
import utils.ParameterUtils;

public class Registration {

    public static void execute(String userInfo) {
        Database.addUser(createUser(userInfo));
    }

    private static User createUser(String userInfo) {
        Map<String, String> userParams = ParameterUtils.parseParams(userInfo);
        return new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
    }
}
