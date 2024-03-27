package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.User;

public class SessionManager {

    private static final Map<String, User> sessionMap = new HashMap<>();

    public static String addSession(User user) {
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, user);
        return sessionId;
    }

    public static User findUserBySessionId(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
