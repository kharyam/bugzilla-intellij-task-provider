package org.bugzilla.tasks;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    protected List<NameValuePair> queryToParams(BugzillaQuery query)     {
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        if(query.getStatus() != null && query.getStatus().length > 0) {
            for(String qstatus: query.getStatus()) {
                NameValuePair status = new BasicNameValuePair("bug_status", qstatus);
                qparams.add(status);
            }
        }
        if(query.getAssignedTo() != null) {
            NameValuePair assigned_to_email = new BasicNameValuePair("email1", query.getAssignedTo());
            NameValuePair email_assigned_to = new BasicNameValuePair("emailassigned_to1", "1");
            NameValuePair email_type = new BasicNameValuePair("emailtype1", "substring");
            qparams.add(assigned_to_email);
            qparams.add(email_assigned_to);
            qparams.add(email_type);
        }
        if(query.getBugId() != null) {
            NameValuePair bug_id = new BasicNameValuePair("bug_id", query.getBugId());
            qparams.add(bug_id);
        }

        if(query.getSummary() != null) {
            NameValuePair short_desc = new BasicNameValuePair("short_desc", query.getSummary());
            NameValuePair short_desc_type = new BasicNameValuePair("short_desc_type", "allwordssubstr");
            qparams.add(short_desc);
            qparams.add(short_desc_type);
        }
        
        NameValuePair ctype = new BasicNameValuePair("ctype", "csv");
        NameValuePair query_format = new BasicNameValuePair("query_format", "advanced");

        qparams.add(ctype);
        qparams.add(query_format);

        if(query.getTargetMilestone() != null) {
            NameValuePair target_milestone = new BasicNameValuePair("target_milestone", query.getTargetMilestone());
            qparams.add(target_milestone);
        }

        if(query.getVersion() != null) {
            NameValuePair version = new BasicNameValuePair("version", query.getTargetMilestone());
            qparams.add(version);
        }
        return qparams;
    }



    class Property {
        int line_position;
        String setter_name;
        String bugzilla_name;
        Class type;
        Property(String setter_name, String bugzilla_name, Class type) {
            this.type = type;
            this.setter_name = setter_name;
            this.bugzilla_name = bugzilla_name;
        }

        Property(Property p) {
            this.line_position = p.line_position;
            this.setter_name = p.setter_name;
            this.type = p.type;
            this.bugzilla_name = p.bugzilla_name;
        }
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
        try {
            String[] tokens = line.split(",");
            BugzillaTask task = new BugzillaTask();
            for(Property property: properties) {
                Method setter = task.getClass().getMethod(property.setter_name, property.type);
                String value = tokens[property.line_position];
                value = stripQuotes(value);

                setter.invoke(task, convert(value, property.type));
            }
            return task;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Exception while setting property", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception while setting property", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Exception while setting property", e);
        }
    }

    static String stripQuotes(String value) {
        if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"') {
            value = value.substring(1, value.length()-1);
        }
        return value;
    }

    private Object convert(String value, Class type) {
        if(type.equals(java.util.Date.class)) {

        }
        if(type.equals(java.lang.String.class)) {
            return value;
        }
        throw new RuntimeException ("Do not know how to parse type:" + type);
    }


    // SAMPLE :  bug_id,"bug_severity","priority","op_sys","assigned_to","bug_status","resolution","short_desc","opendate","changeddate","rep_platform","assigned_to_realname","reporter","reporter_realname","product","component","version","target_milestone","qa_contact","qa_contact_realname","keywords","estimated_time","remaining_time","actual_time","percentage_complete","deadline","short_short_desc"
    private Property[] known_properties = new Property[] {
            new Property("setStatus", "bug_status", java.lang.String.class),
            new Property("setDescription", "short_desc", java.lang.String.class),
            new Property("setUpdated", "changeddate", java.util.Date.class),
            new Property("setAssignedTo", "assigned_to", java.lang.String.class),
            new Property("setReporterEmail", "reporter", java.lang.String.class),
            new Property("setTargetMilestone", "target_milestone", java.lang.String.class),
            new Property("setVersion", "version", java.lang.String.class),
            new Property("setReporterName", "reporter_realname", java.lang.String.class),
            new Property("setSeverity", "bug_severity", java.lang.String.class),
            new Property("setId", "bug_id", java.lang.String.class),
            new Property("setPriority", "priority", java.lang.String.class)
    };

    private Property[] getProperties(BufferedReader reader) throws IOException {
        String first_line = reader.readLine();
        if(first_line == null) {
            throw new IOException("Obtained no data from server");
        }
        LinkedList<Property> properties = new LinkedList<Property>();
        String[] tokens = first_line.split(",");
        int i = 0;
        for(String token : tokens) {
            token = stripQuotes(token);
            for(Property p : known_properties) {
                if(p.bugzilla_name.equals(token)) {
                    Property cloned = new Property(p);
                    cloned.line_position = i;
                    properties.add(cloned);
                    break;
                }
            }
            i++;
        }
        return properties.toArray(new Property[properties.size()]);
    }
}
