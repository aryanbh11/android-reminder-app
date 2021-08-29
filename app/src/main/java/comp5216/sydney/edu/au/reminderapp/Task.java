package comp5216.sydney.edu.au.reminderapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * An instance of this class represents 1 task and stores all the info related to that task.
 */
public class Task {

    private String taskName;
    private String dueDateString;
    private String dueTimeString;

    /**
     * Constructor for creating an instance of Task
     * @param taskName Name of the Task
     * @param dueDateString Due Date of the Task as a String of the format dd/mm/yyyy
     * @param dueTimeString Due Time of the Task as a String of the fotmat HH:mm (24hr)
     */
    public Task(String taskName, String dueDateString, String dueTimeString) {
        this.taskName = taskName;
        this.dueDateString = dueDateString;
        this.dueTimeString = dueTimeString;
    }

    /**
     * Getter for Task Name
     * @return Task name as String
     */
    public String getTaskName() { return taskName; }

    /**
     * Getter for Due Date
     * @return Due date of Task as String
     */
    public String getDueDate() { return dueDateString; }

    /**
     * Getter for Due Time
     * @return Due Time of Task as String
     */
    public String getDueTime() { return dueTimeString; }

    /**
     * Returns "OVERDUE" if task is overdue and "Invalid Input" if the date and time strings are
     * invalid.
     * @return Value of Remaining time as String
     */
    public String getRemainingTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        try {
            Date dueDate = dateFormat.parse(dueDateString + " " + dueTimeString);
            Date currentDate = new Date();

            long remainingTime = dueDate.getTime() - currentDate.getTime();

            if (remainingTime <= 0) {
                return "OVERDUE";
            }

            long diffInDays = TimeUnit.MILLISECONDS.toDays(remainingTime);
            long diffInHours = TimeUnit.MILLISECONDS.toHours(remainingTime) - (24 * diffInDays);
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
                    - (1440 * diffInDays) - (60 * diffInHours);

            String remainingTimeString = Long.toString(diffInDays) + " d " +
                    Long.toString(diffInHours) + " hr " + Long.toString(diffInMinutes) + " min ";

            return remainingTimeString;

        } catch (ParseException e) {
            return "Invalid Input";
        }

    }

    /**
     * @return Value of remaining time as a long
     */
    public long getRemainingTimeValue() {
        // Used for Sorting the Tasks
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        try {
            Date dueDate = dateFormat.parse(dueDateString + " " + dueTimeString);
            Date currentDate = new Date();

            long remainingTime = dueDate.getTime() - currentDate.getTime();

            return remainingTime;

        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * Setter for task name
     * @param taskName Task name to set to
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Setter for Due Date
     * @param dueDateString The string to set due date to
     */
    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    /**
     * Setter for Due Time
     * @param dueTimeString The string to set due time to
     */
    public void setDueTimeString(String dueTimeString) {
        this.dueTimeString = dueTimeString;
    }
}
