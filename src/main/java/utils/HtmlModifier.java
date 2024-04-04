package utils;

import java.nio.charset.StandardCharsets;

public class HtmlModifier {
    private static final String LOGIN_BUTTON = """
                  <li class="header__menu__item">
                    <a class="btn btn_contained btn_size_s" href="/login">로그인</a>
                  </li>\
        """;
    private static final String LOGOUT_BUTTON = """
                  <form method="post" action="/user/logout">
                    <button type="submit" class="btn btn_contained btn_size_s">로그아웃</button>
                  </form>\
        """;
    private static final String REGISTRATION_BUTTON = """
                  <li class="header__menu__item">
                    <a class="btn btn_ghost btn_size_s" href="/registration">회원 가입</a>
                  </li>\
        """;
    private static final String ACCOUNT_BUTTON = """
                  <li class="post__account">
                    <img class="post__account__img" />
                    <p class="post__account__nickname">%s님</p>
                  </li>
        """;

    public static byte[] setLogin(byte[] originalContent, String userName) {
        String html = new String(originalContent, StandardCharsets.UTF_8);
        return html.replace(LOGIN_BUTTON, String.format(ACCOUNT_BUTTON, userName))
            .replace(REGISTRATION_BUTTON, LOGOUT_BUTTON)
            .getBytes(StandardCharsets.UTF_8);
    }
}
