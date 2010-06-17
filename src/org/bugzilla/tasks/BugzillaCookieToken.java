package org.bugzilla.tasks;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 9:06:39 AM
 * To change this template use File | Settings | File Templates.
 */
class BugzillaCookieToken extends AuthToken {
    private String cookieContent;

    public BugzillaCookieToken (String cookieContent) {
        this.cookieContent = cookieContent;
    }

    public String getCookieContent() {
        return cookieContent;
    }
}
