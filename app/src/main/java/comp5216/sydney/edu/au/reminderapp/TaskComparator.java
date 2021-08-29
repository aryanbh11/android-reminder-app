package comp5216.sydney.edu.au.reminderapp;

import java.util.Comparator;

/**
 * Custom Comparator to sort list of Tasks
 */
public class TaskComparator implements Comparator<Task> {
    /**
     * Compares task based on remaining time
     * @param o1 Task 1 (instance of Task class)
     * @param o2 Task 2 (instance of Task class)
     * @return diff in remaining time of 2 tasks
     */
    @Override
    public int compare(Task o1, Task o2) {
        return (int) (o1.getRemainingTimeValue() - o2.getRemainingTimeValue());
    }
}
