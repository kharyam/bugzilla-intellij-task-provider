package org.bugzilla.tasks;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 9:21:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BugzillaQueryBuilder {
    public BugzillaQuery buildQuery(String intellijQuery);
    public BugzillaQuery buildQueryForTaskId(String id);
}
