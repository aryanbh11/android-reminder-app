package comp5216.sydney.edu.au.reminderapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;

    public TaskArrayAdapter(Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);

        this.context = context;
        this.tasks = tasks;
    }

    private boolean isOverdue(String dueDate, String dueTime) {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //get the task we are displaying
        Task task = tasks.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.task_layout, null);

        TextView taskName = (TextView) view.findViewById(R.id.taskTxt);
        TextView dueInfo = (TextView) view.findViewById(R.id.dueTxt);
        TextView remainingTime = (TextView) view.findViewById(R.id.remainingTxt);

        String dueInfoString = "Due On: " + task.getDueDate() + " at " + task.getDueTime();
        String remainingTimeString = "Remaining Time: " + task.getRemainingTime();

        taskName.setText(task.getTaskName());
        dueInfo.setText(dueInfoString);
        remainingTime.setText(remainingTimeString);

        return view;
    }
}
