
package webserver.executor;

import db.Database;
import db.SessionManager;
import model.User;
import webserver.http.cookie.CookieHandler;
import webserver.path.PathHandler;
import webserver.http.cookie.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class LoginExecutor extends AbstractExecutor {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setMethodError();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        try {
            User user = findUser(request);
            String sessionId = SessionManager.addSession(user);
            Cookie loginCookie = new CookieHandler().getLoginCookie(sessionId);

            response.setCookie(loginCookie);
            response.setRedirect(PathHandler.getStaticDefaultPath());
        } catch (Exception e) {
            response.setRedirect(PathHandler.getLoginFailedPath());
        }
    }

    private User findUser(HttpRequest request) throws IllegalArgumentException {
        User user = Database.findUserById(request.getParameter("userId"));
        authenticate(user, request.getParameter("password"));
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