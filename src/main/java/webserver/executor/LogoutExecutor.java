package webserver.executor;

import db.SessionManager;
import webserver.http.cookie.CookieHandler;
import webserver.path.PathHandler;
import webserver.http.cookie.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LogoutExecutor extends AbstractExecutor {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setMethodError();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        try {
            String sessionId = request.getSessionId();
            SessionManager.removeSession(sessionId);
            Cookie logoutCookie = new CookieHandler().getLogoutCookie(sessionId);

            response.setCookie(logoutCookie);
            response.setRedirect(PathHandler.getStaticDefaultPath());
        } catch (Exception e) {
            response.setRedirect(PathHandler.getStaticDefaultPath());
        }
    }
}