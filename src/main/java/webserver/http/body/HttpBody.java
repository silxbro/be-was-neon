package webserver.http.body;

import java.nio.charset.StandardCharsets;

public class HttpBody {
    private byte[] content;

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] toByteArray() {
        if (content == null) {
            return "".getBytes(StandardCharsets.UTF_8);
        }
        return content;
    }

    public String toString() {
        if (content == null) {
            return "";
        }
        return new String(content, StandardCharsets.UTF_8);
    }
}
