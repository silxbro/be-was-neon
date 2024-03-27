package service;

import webserver.http.Cookie;

public class CookieHandler {

    public Cookie createCookie(String name, String value, String path) {
        Cookie userSessionCookie = new Cookie(name, value);
        userSessionCookie.setPath(path);
        return userSessionCookie;
    }
}
