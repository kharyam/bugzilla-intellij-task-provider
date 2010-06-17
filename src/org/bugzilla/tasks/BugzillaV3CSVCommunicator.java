package org.bugzilla.tasks;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 17, 2010
 * Time: 11:02:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaV3CSVCommunicator extends BugzillaV3HTTPCommunicator {
    public BugzillaV3CSVCommunicator(URI uri) {
        super(uri);
    }

    @Override
    protected HttpParams queryToParams(BugzillaQuery query)     {
        HttpParams params = new BasicHttpParams();
        params.setParameter("bug_status", query.getStatus());
        params.setParameter("email1", query.getAssignedTo());
        params.setParameter("emailassigned_to1", 1);
        params.setParameter("emailtype1", "substring");
        params.setParameter("ctype", "csv");
        params.setParameter("query_format", "advanced");
        return params;
    }



    class Property {
        int line_position;
        String property_name;
        String bugzilla_name;
        DataType type;
    }
    enum DataType {
        STRING, DATE, BOOL;
    }

    @Override
    protected BugzillaTask[] process(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream), 32768);
        Property[] properties = getProperties(reader);
        LinkedList<BugzillaTask> tasks = new LinkedList<BugzillaTask>();
        String line = reader.readLine();
        while(line != null) {
            tasks.add(processBug(properties, line));
            line = reader.readLine();
        }
        return tasks.toArray(new BugzillaTask[0]);
    }

    private BugzillaTask processBug(Property[] properties, String line) {
        String[] tokens = line.split(",");
        BugzillaTask task = new BugzillaTask();
        for(Property property: properties) {
            // TODO: finish this
        }
        return task;
    }

    private Property[] getProperties(BufferedReader reader) {
         // SAMPLE :  bug_id,"bug_severity","priority","op_sys","assigned_to","bug_status","resolution","short_desc","opendate","changeddate","rep_platform","assigned_to_realname","reporter","reporter_realname","product","component","version","target_milestone","qa_contact","qa_contact_realname","keywords","estimated_time","remaining_time","actual_time","percentage_complete","deadline","short_short_desc"


        // TODO: finish this
        return new Property[0];  //To change body of created methods use File | Settings | File Templates.
    }
}
