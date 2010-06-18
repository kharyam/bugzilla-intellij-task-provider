package org.bugzilla.tasks;

import org.apache.http.cookie.Cookie;

import java.io.Serializable;

/**
 * Something that persists the authentication. Probably a cookie.
 */
public abstract class AuthToken implements Serializable {
    public abstract Cookie[] toCookies();
}
