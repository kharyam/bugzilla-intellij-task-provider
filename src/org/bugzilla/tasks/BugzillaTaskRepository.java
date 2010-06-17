package org.bugzilla.tasks;

import com.intellij.tasks.Task;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.impl.BaseRepository;
import org.apache.xmlrpc.XmlRpcClient;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
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
    
    XmlRpcClient client = null;

    private synchronized void buildClient() throws MalformedURLException {
        if(client != null) {
            return;
        }
        client = new XmlRpcClient(getUrl());
    }

    @Override
    public void testConnection() throws Exception {
        buildClient();
        Vector params = new Vector();
        params.add(getUsername());
        params.add(getPassword());
        client.execute("login", params);
    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {
        buildClient();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("assigned_to", getUsername());
        Vector v = new Vector();
        v.add(map);
        Vector result = (Vector) client.execute("get", v);
        Task[] tasks =  toTasks((Vector) result.get(0));
        if(tasks != null && tasks.length > 0) {
            return tasks;
        }
        return null;
    }

    @Override
    public Task findTask(String id) throws Exception {
        buildClient();
        Vector v = new Vector();
        v.add(id);
        Vector result = (Vector) client.execute("get", v);
        Task[] tasks =  toTasks((Vector) result.get(0));
        if(tasks != null && tasks.length > 0) {
            return tasks[0];
        }
        return null;
    }

    private Task[] toTasks(Vector o) {
        if(o == null) {
            return null;
        }
        Task [] result_list = new Task[o.size()];
        for(int i = 0; i < o.size(); i++) {
            BugzillaTask bt = new BugzillaTask();
            HashMap map = (HashMap) o.get(i);
            bt.setId(map.get("id").toString());
            bt.setSummary((String) map.get("summary"));
            bt.setCreated((Date)map.get("creation_time"));
            bt.setIssue(true);
            bt.setUpdated((Date) map.get("last_change_time"));
            result_list[i] = bt;
        }
        return result_list;
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
        return taskName.split(":")[0];
    }
}
