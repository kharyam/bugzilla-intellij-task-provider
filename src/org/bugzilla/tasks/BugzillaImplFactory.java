package org.bugzilla.tasks;

import java.net.URI;
import java.net.URL;

/**
 * A communicator factory. Instantiates communicators based on remote bugzilla version.
 */
public class BugzillaImplFactory {

    /**
     * Sniff the site at the remote URL to obtain the bugzilla version. 
     * @return
     */
    public static final BugzillaCommunicator getCommunicator(URI uri) {
        return new BugzillaV3CSVCommunicator(uri);
    }

    public static final BugzillaQueryBuilder getQueryBuilder(URI uri) {
        return new BugzillaV3QueryBuilder();
    }
}
