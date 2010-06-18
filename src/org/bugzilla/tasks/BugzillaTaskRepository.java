package org.bugzilla.tasks;

import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.impl.BaseRepository;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 3, 2010
 * Time: 9:04:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaTaskRepository extends BaseRepository {

    public BugzillaTaskRepository() {
        super();
    }

    public BugzillaTaskRepository(BugzillaTaskRepository other) {
        super(other);
    }
    public BugzillaTaskRepository(TaskRepositoryType type) {
        super(type);

    }
    BugzillaCommunicator communicator;
    BugzillaQueryBuilder queryBuilder;
    AuthToken token ;
    URI uri;

    private synchronized void init() throws URISyntaxException {
        URI uri = new URI(getUrl());
        if(! uri.equals(this.uri)) {
            // new uri. reset state. not sure if this is supposed to happen
            communicator = null;
            queryBuilder = null;
            token = null;
        }

        if(communicator == null) {
            communicator = BugzillaImplFactory.getCommunicator(uri);
        }
        if(queryBuilder == null) {
            queryBuilder = BugzillaImplFactory.getQueryBuilder(uri);
        }
    }

    @Override
    public void testConnection() throws Exception {
        init();
        token = communicator.login(getUsername(), getPassword());
        if(token == null) {
            throw new Exception("Login failed for the specified username/password");
        }
    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {
        init();
        if(this.token == null) {
            token = communicator.login(getUsername(), getPassword());
        }
        return communicator.getIssues(this.token, this.queryBuilder.buildQuery(query,getUsername()));
    }

    @Override
    public Task findTask(String id) throws Exception {
        init();
        if(this.token == null) {
            token = communicator.login(getUsername(), getPassword());
        }
        Task[] tasks =  communicator.getIssues(this.token, this.queryBuilder.buildQueryForTaskId(id));
        if(tasks.length != 1) {
            return null;
        }
        return tasks[0];
    }

    @Override
    public BaseRepository clone() {
        BugzillaTaskRepository cloned = new BugzillaTaskRepository(this);
        cloned.setPassword(this.getPassword());
        cloned.setUrl(this.getUrl());
        cloned.setUsername(this.getUsername());
        cloned.setRepositoryType(this.getRepositoryType());
        return cloned;
    }

    @Override
    public String extractId(String taskName) {
        return taskName.split("@")[0];
    }
}
