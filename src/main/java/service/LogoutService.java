package service;

import db.SessionManager;
import java.util.List;
import webserver.handler.CookieHandler;
import webserver.http.Cookie;
import webserver.http.HttpStatus;
import webserver.http.RequestResult;

public class LogoutService implements Service {

    @Override
    public RequestResult execute(String sessionId) {
        try {
            SessionManager.removeSession(sessionId);
            Cookie sessionCookie = new CookieHandler().getLogoutCookie(sessionId);
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGOUT.getSuccessRedirectionPath(), List.of(sessionCookie));
        } catch (Exception e) {
            return new RequestResult(HttpStatus.FOUND, ServiceType.LOGOUT.getFailRedirectionPath());
        }
    }
}