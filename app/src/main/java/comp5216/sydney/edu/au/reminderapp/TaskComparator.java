package comp5216.sydney.edu.au.reminderapp;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return (int) (o1.getRemainingTimeValue() - o2.getRemainingTimeValue());
    }
}
