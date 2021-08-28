package comp5216.sydney.edu.au.reminderapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Define Variables
    private ListView listView;
    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;

    // Register a request to start an activity for result and register the result callback
    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    String taskName = result.getData().getExtras().getString("taskName");
                    String dueDate = result.getData().getExtras().getString("dueDate");
                    String dueTime = result.getData().getExtras().getString("dueTime");
                    int position = result.getData().getIntExtra("position", -1);

                    if (position == -1) {
                        // Add Item
                        Task newTask = new Task(taskName, dueDate, dueTime);
                        adapter.add(newTask);
                    } else {
                        // Update Item
                        tasks.get(position).setTaskName(taskName);
                        tasks.get(position).setDueDateString(dueDate);
                        tasks.get(position).setDueTimeString(dueTime);
                        // Make a standard toast that just contains text
                        Toast.makeText(getApplicationContext(), "Updated Task "
                                        + Integer.toString(position), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the "listView" variable to the id "lstView" in the layout
        listView = (ListView) findViewById(R.id.lstView);

        tasks = new ArrayList<Task>();
        tasks.add(new Task("Laundry", "27 /08/2021", "21:30"));

        adapter = new TaskArrayAdapter(this, 0, tasks);

        listView.setAdapter(adapter);
        setupListViewListener();
    }


    private void setupListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                                           long rowId) {
                Log.i("MainActivity", "Long Clicked item " + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Task")
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tasks.remove(position); // Remove item from the ArrayList
                                // Notify listView adapter to update the list:
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });
                builder.create().show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task updateTask = adapter.getItem(position);
                Log.i("MainActivity", "Clicked item " + position);
                Intent intent = new Intent(MainActivity.this,
                        EditAddTaskActivity.class);
                if (intent != null) {
                    // put "extras" into the bundle for access in the edit activity
                    intent.putExtra("taskName", updateTask.getTaskName());
                    intent.putExtra("dueDate", updateTask.getDueDate());
                    intent.putExtra("dueTime", updateTask.getDueTime());
                    intent.putExtra("position", position);

                    // brings up the second activity
                    mLauncher.launch(intent);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    public void onAddButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditAddTaskActivity.class);
        if (intent != null) {
            intent.putExtra("position", -1);
            mLauncher.launch(intent);
            adapter.notifyDataSetChanged();
        }
    }
}