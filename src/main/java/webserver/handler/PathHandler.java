package webserver.handler;

import java.io.File;

public class PathHandler {

    private static final String STATIC_RESOURCES_DIRECTORY = "src/main/resources/static";
    private static final String STATIC_DEFAULT_PATH = "/index.html";
    private static final String LOGIN_FAILED_PATH = "/login/login_failed.html";
    private static final String REGISTRATION_FAILED_PATH = "/registration/registration_failed.html";
    private static final String EXTENSION_DELIMITER = ".";

    public static String getStaticDefaultPath() {
        return STATIC_DEFAULT_PATH;
    }

    public static String getLoginFailedPath() {
        return LOGIN_FAILED_PATH;
    }

    public static String getRegistrationFailedPath() {
        return REGISTRATION_FAILED_PATH;
    }

    public static String getStaticResourcePath(String absolutePath) {
        File file = new File(STATIC_RESOURCES_DIRECTORY + absolutePath);
        if (file.isDirectory()) {
            return file.getPath() + STATIC_DEFAULT_PATH;
        }
        return file.getPath();
    }

    public static String getExtension(String absolutePath) {
        String staticResourcePath = getStaticResourcePath(absolutePath);
        return staticResourcePath.substring(staticResourcePath.lastIndexOf(EXTENSION_DELIMITER)+1);
    }
}