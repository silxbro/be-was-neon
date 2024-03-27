package webserver.handler;

import webserver.http.Cookie;

public class CookieHandler {

    private static final String SESSION_ID = "sid";
    private static final String ALL_PATH = "/";

    public Cookie createUserSessionCookie(String value) {
        Cookie userSessionCookie = new Cookie(SESSION_ID, value);
        userSessionCookie.setPath(ALL_PATH);
        return userSessionCookie;
    }
}
