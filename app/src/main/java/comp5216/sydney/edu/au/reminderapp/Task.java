package comp5216.sydney.edu.au.reminderapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task {

    private String taskName;
    private String dueDateString;
    private String dueTimeString;

    public Task(String taskName, String dueDateString, String dueTimeString) {
        this.taskName = taskName;
        this.dueDateString = dueDateString;
        this.dueTimeString = dueTimeString;
    }

    public String getTaskName() { return taskName; }
    public String getDueDate() { return dueDateString; }
    public String getDueTime() { return dueTimeString; }

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

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    public void setDueTimeString(String dueTimeString) {
        this.dueTimeString = dueTimeString;
    }
}
