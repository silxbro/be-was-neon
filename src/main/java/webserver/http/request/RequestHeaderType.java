package webserver.http.request;

public enum RequestHeaderType {

    REQUEST_LINE("Request Line", " "),
    ACCEPT("Accept", ","),
    COOKIE("Cookie", ";"),
    ;

    private final String name;
    private final String valueDelimiter;

    RequestHeaderType(String name, String valueDelimiter) {
        this.name = name;
        this.valueDelimiter = valueDelimiter;
    }

    public String getName() {
        return name;
    }

    public String getValueDelimiter() {
        return valueDelimiter;
    }
}
