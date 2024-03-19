package utils;

import java.io.File;

public class PathUtils {

    private static final String STATIC_RESOURCES_PATH = "src/main/resources/static";
    private static final String COMMON_FILE = "/index.html";
    private static final String EXTENSION_DELIMITER = ".";

    public static String getStaticResourcesPath(String absolutePath) {
        File file = new File(STATIC_RESOURCES_PATH + absolutePath);
        if (file.isDirectory()) {
            return file.getPath() + COMMON_FILE;
        }
        return file.getPath();
    }

    public static String getExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(EXTENSION_DELIMITER)+1);
    }
}