package org.bugzilla.tasks;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 8:40:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BugzillaCommunicator {
    /**
     * Perform login with the specified username, password and URL. If the URL is incomplete (just domain name)
     * the communicator is expected to mend it.
     *
     * @param username
     * @param password
     * @return auth token on successful login, null otherwise
     * @throws IOException if an exception occurred while communicating with server
     * @throws java.net.URISyntaxException if the supplied URL is invalid
     */
    public AuthToken login(String username, String password) throws IOException, URISyntaxException;

    public BugzillaTask[] getIssues(AuthToken token, BugzillaQuery query) throws IOException;
}
