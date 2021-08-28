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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    // Define Variables
    private ListView listView;
    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;
    private TaskEntityDB db;
    private TaskEntityDao taskEntityDao;

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
                        // Sorting list based on remaining time:
                        Collections.sort(tasks, new TaskComparator());
                        saveItemsToDatabase();
                    } else {
                        // Update Item
                        tasks.get(position).setTaskName(taskName);
                        tasks.get(position).setDueDateString(dueDate);
                        tasks.get(position).setDueTimeString(dueTime);
                        // Make a standard toast that just contains text
                        Toast.makeText(getApplicationContext(), "Updated Task "
                                        + Integer.toString(position), Toast.LENGTH_SHORT).show();
                        // Sorting list based on remaining time:
                        Collections.sort(tasks, new TaskComparator());
                        adapter.notifyDataSetChanged();
                        saveItemsToDatabase();
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

        // Setup Room Database Entities
        db = TaskEntityDB.getDatabase(this.getApplication().getApplicationContext());
        taskEntityDao = db.taskEntityDao();
        readItemsFromDatabase();

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
                                // Sorting list based on remaining time:
                                Collections.sort(tasks, new TaskComparator());
                                // Notify listView adapter to update the list:
                                adapter.notifyDataSetChanged();
                                saveItemsToDatabase();
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
                    // Sorting list based on remaining time:
                    Collections.sort(tasks, new TaskComparator());
                    adapter.notifyDataSetChanged();
                    saveItemsToDatabase();
                }
            }
        });
    }

    private void readItemsFromDatabase() {
        //Use asynchronous task to run query on the background and wait for result
        try {
            // Run a task specified by a Runnable Object asynchronously.
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    //read items from database
                    List<TaskEntity> itemsFromDB = taskEntityDao.listAll();
                    tasks = new ArrayList<Task>();
                    if (itemsFromDB != null & itemsFromDB.size() > 0) {
                        for (TaskEntity item : itemsFromDB) {
                            Task task = new Task(item.getTaskName(), item.getDueDate(),
                                    item.getDueTime());
                            tasks.add(task);
                            Log.i("SQLite read item", "ID: " + item.getTaskID() +
                                    " Name: " + item.getTaskName());
                        }
                    }
                    System.out.println("I'll run in a separate thread than the main thread.");
                }
            });

            // Block and wait for the future to complete
            future.get();
        } catch(Exception ex) {
            Log.e("readItemsFromDatabase", ex.getStackTrace().toString());
        }
    }

    private void saveItemsToDatabase() {
        //Use asynchronous task to run query on the background to avoid locking UI
        try {
            // Run a task specified by a Runnable Object asynchronously.
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    //delete all items and re-insert
                    taskEntityDao.deleteAll();
                    for (Task todo : tasks) {
                        TaskEntity task = new TaskEntity();
                        task.setTaskName(todo.getTaskName());
                        task.setDueDate(todo.getDueDate());
                        task.setDueTime(todo.getDueTime());
                        taskEntityDao.insert(task);
                        Log.i("SQLite saved item", todo.getTaskName());
                    }
                    System.out.println("I'll run in a separate thread than the main thread.");
                }
            });

            // Block and wait for the future to complete
            future.get();
        } catch(Exception ex) {
            Log.e("saveItemsToDatabase", ex.getStackTrace().toString());
        }
    }


    public void onAddButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, EditAddTaskActivity.class);
        if (intent != null) {
            intent.putExtra("position", -1);
            mLauncher.launch(intent);
            Collections.sort(tasks, new TaskComparator()); // Sorting list based on remaining time
            adapter.notifyDataSetChanged();
            saveItemsToDatabase();
        }
    }
}