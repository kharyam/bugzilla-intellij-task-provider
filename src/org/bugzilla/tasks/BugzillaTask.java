package org.bugzilla.tasks;

import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 3, 2010
 * Time: 9:04:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaTask extends Task {

    String id;
    String summary;
    String description;
    Comment[] comments = new Comment[0];
    Icon icon;
    TaskType type = TaskType.BUG;
    Date updated;
    Date created;
    boolean isClosed;
    boolean isIssue;
    String issueURL;
    String reporterName;
    String reporterEmail;
    String assignedTo;
    String status;
    String targetMilestone;
    String version ;
    String severity;
    String priority;

    @NotNull
    @Override
    public String getId() {
        return id;
    }

    @NotNull
    @Override
    public String getSummary() {
        return this.id + "@" + this.targetMilestone +"@" + this.priority + "@"+ this.severity + " " + this.description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @NotNull
    @Override
    public Comment[] getComments() {
        return comments;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @NotNull
    @Override
    public TaskType getType() {
        return type;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public boolean isIssue() {
        return isIssue;
    }

    @Override
    public String getIssueUrl() {
        return issueURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void setIssue(boolean issue) {
        isIssue = issue;
    }

    public void setIssueURL(String issueURL) {
        this.issueURL = issueURL;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
