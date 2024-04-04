package webserver.http.response.statusline;

public class StatusLine {

    private static final String HTTP_1_1_VERSION = "HTTP/1.1 ";

    private HttpStatus status;

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String toString() {
        return HTTP_1_1_VERSION + status.toString();
    }
}