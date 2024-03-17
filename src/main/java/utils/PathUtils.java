package utils;

import java.io.File;

public class PathUtils {

    private static final String STATIC_RESOURCES_PATH = "src/main/resources/static";
    private static final String COMMON_FILE = "/index.html";
    private static final String EXTENSION_DELIMITER = ".";

    public static String getStaticPath(String path) {
        File file = new File(STATIC_RESOURCES_PATH + path);
        if (file.isDirectory()) {
            return file.getPath() + COMMON_FILE;
        }
        return file.getPath();
    }

    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(EXTENSION_DELIMITER)+1);
    }
}