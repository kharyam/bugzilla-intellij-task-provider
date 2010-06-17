package org.bugzilla.tasks;

import com.intellij.tasks.Comment;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 3, 2010
 * Time: 9:16:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaComment extends Comment {
    String text;
    String author;
    Date date;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
