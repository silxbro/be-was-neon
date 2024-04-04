package db;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.SecuredUser;
import model.User;

public class SessionManager {

    private static final Map<String, SecuredUser> sessionMap = new HashMap<>();

    public static String addSession(User user) {
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, new SecuredUser(user));
        return sessionId;
    }

    public static boolean isSessionIdExist(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }

    public static String findUserIdBySession(String sessionId) {
        return sessionMap.get(sessionId).getUserId();
    }

    public static String findUserNameBySession(String sessionId) {
        return sessionMap.get(sessionId).getName();
    }

    public static void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}