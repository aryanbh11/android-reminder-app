package comp5216.sydney.edu.au.reminderapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class is the model (entity) for a Task which represents a table named "Tasks"
 * within the database.
 */
@Entity(tableName = "Tasks")
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "taskID")
    private int taskID;

    @ColumnInfo(name = "taskName")
    private String taskName;

    @ColumnInfo(name = "dueDate")
    private String dueDate;

    @ColumnInfo(name = "dueTime")
    private String dueTime;


    /**
     * Getter for Task ID
     * @return
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     * Setter for Task ID
     * @param taskID
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Getter for Task name
     * @return
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Setter for Task Name
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Getter for due date
     * @return
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Setter for due date
     * @param dueDate
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Getter for Due time
     * @return
     */
    public String getDueTime() {
        return dueTime;
    }

    /**
     * Setter for due time
     * @param dueTime
     */
    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
