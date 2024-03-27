package webserver.handler;

import webserver.http.Cookie;

public class CookieHandler {

    private static final String SESSION_ID = "sid";
    private static final String ALL_PATH = "/";
    private static final int DEFAULT_MAX_AGE = 86400;

    public Cookie getLoginCookie(String value) {
        Cookie loginCookie = new Cookie(SESSION_ID, value);
        loginCookie.setPath(ALL_PATH);
        loginCookie.setMaxAge(DEFAULT_MAX_AGE); // 쿠키의 기본 수명 : 하루 (=24*12*60)
        return loginCookie;
    }

    public Cookie getLogoutCookie(String value) {
        Cookie logoutCookie = new Cookie(SESSION_ID, value);
        logoutCookie.setPath(ALL_PATH);
        logoutCookie.setMaxAge(0);
        return logoutCookie;
    }
}
