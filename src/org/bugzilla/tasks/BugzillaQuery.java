package org.bugzilla.tasks;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 8:41:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaQuery {

    private String assignedTo;
    private String status;
    private String targetMilestone;


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetMilestone() {
        return targetMilestone;
    }

    public void setTargetMilestone(String targetMilestone) {
        this.targetMilestone = targetMilestone;
    }
}
