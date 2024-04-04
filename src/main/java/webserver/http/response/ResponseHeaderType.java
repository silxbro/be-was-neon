package webserver.http.response;

public enum ResponseHeaderType {

    STATUS_LINE("Status Line"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),

    ;

    private final String name;

    ResponseHeaderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
