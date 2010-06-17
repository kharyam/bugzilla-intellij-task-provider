package org.bugzilla.tasks;

import com.intellij.openapi.project.Project;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.TaskRepositoryType;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.tasks.config.TaskRepositoryEditor;
import com.intellij.tasks.impl.BaseRepositoryType;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;

import javax.swing.ImageIcon;
import javax.swing.Icon;

/**
 * Created by IntelliJ IDEA.
 * User: nickhristov
 * Date: Jun 3, 2010
 * Time: 9:06:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class BugzillaRepositoryType extends BaseRepositoryType {

    @NotNull
    @Override
    public String getName() {
        return "Bugzilla";
    }

    @Override
    public Icon getIcon() {
        Icon icon = new ImageIcon(this.getClass().getClassLoader().getResource("org/bugzilla/bugzilla.png"), "BugzillaIcon");  // todo:resolve icon filepath
        return icon;
    }

    @NotNull
    @Override
    public TaskRepositoryEditor createEditor(TaskRepository repository, Project project, final Consumer changeListener) {

        Consumer<BugzillaTaskRepository> myconsumer = new Consumer<BugzillaTaskRepository>() {

            public void consume(BugzillaTaskRepository bugzillaTaskRepository) {
                changeListener.consume(bugzillaTaskRepository);
            }
        };
        return new BaseRepositoryEditor<BugzillaTaskRepository>(project, (BugzillaTaskRepository)repository, myconsumer);
    }

    @NotNull
    @Override
    public TaskRepository createRepository() {
        return new BugzillaTaskRepository(this);
    }

    @Override
    public Class getRepositoryClass() {
        return BugzillaTaskRepository.class;
    }
}
