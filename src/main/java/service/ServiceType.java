package service;

import java.util.Arrays;
import webserver.handler.PathHandler;

public enum ServiceType {
    REGISTRATION("/user/create", PathHandler.getStaticDefaultPath(), PathHandler.getRegistrationFailedPath()),
    LOGIN("/user/login", PathHandler.getStaticDefaultPath(), PathHandler.getLoginFailedPath()),
    LOGOUT("/user/logout", PathHandler.getStaticDefaultPath(), PathHandler.getStaticDefaultPath()),
    ;

    private final String absolutePath;
    private final String successRedirectionPath;
    private final String failRedirectionPath;

    ServiceType(String absolutePath, String successRedirectionPath, String failRedirectionPath) {
        this.absolutePath = absolutePath;
        this.successRedirectionPath = successRedirectionPath;
        this.failRedirectionPath = failRedirectionPath;
    }

    public String getSuccessRedirectionPath() {
        return successRedirectionPath;
    }

    public String getFailRedirectionPath() {
        return failRedirectionPath;
    }

    public static boolean contains(String absolutePath) {
        for (ServiceType path : ServiceType.values()) {
            if (path.absolutePath.equals(absolutePath)) {
                return true;
            }
        }
        return false;
    }

    public static ServiceType of(String absolutePath) {
        return Arrays.stream(ServiceType.values())
            .filter(path -> path.absolutePath.equals(absolutePath))
            .findAny().orElseThrow();
    }
}