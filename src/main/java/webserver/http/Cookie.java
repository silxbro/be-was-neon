package webserver.http;

public class Cookie {

    private static final int DEFAULT_MAX_AGE = 86400; // 하루를 초 단위로 변환
    private final String name;
    private final String value;
    private int maxAge;
    private String path;
    private String domain;
    private boolean secure;
    private boolean httpOnly;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.maxAge = DEFAULT_MAX_AGE;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public String toString() {
        StringBuilder header = new StringBuilder();
        header.append(name).append("=").append(value);

        if (maxAge >= 0) {
            header.append("; Max-Age=").append(maxAge);
        }
        if (path != null) {
            header.append("; Path=").append(path);
        }
        if (domain != null) {
            header.append("; Domain=").append(domain);
        }
        if (secure) {
            header.append("; Secure");
        }
        if (httpOnly) {
            header.append("; HttpOnly");
        }

        return header.toString();
    }
}
