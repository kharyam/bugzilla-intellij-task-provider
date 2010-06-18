package org.bugzilla.tasks;

import org.apache.http.cookie.Cookie;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 9:06:39 AM
 * To change this template use File | Settings | File Templates.
 */
class BugzillaCookieToken extends AuthToken {
    public BugzillaCookieToken () {

    }
    Cookie[] cookies;

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    @Override
    public org.apache.http.cookie.Cookie[] toCookies() {
        return this.cookies;
    }
}
