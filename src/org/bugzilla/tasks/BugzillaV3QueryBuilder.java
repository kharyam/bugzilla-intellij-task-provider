package org.bugzilla.tasks;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 9:22:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaV3QueryBuilder implements BugzillaQueryBuilder {
    @Override
    public BugzillaQuery buildQuery(String intellijQuery, String username) {

        BugzillaQuery query = new BugzillaQuery();
        // just add the login as assignee
        query.setAssignedTo(username);
        String[] statuses = new String[] { "ASSIGNED", "NEW" , "REOPENED" };
        query.setStatus(statuses);
        if(intellijQuery != null) {
            query.setSummary(intellijQuery);
        }
        return query;
    }

    @Override
    public BugzillaQuery buildQueryForTaskId(String id) {
        BugzillaQuery query = new BugzillaQuery();
        query.setBugId(id);
        return query;
    }
}
