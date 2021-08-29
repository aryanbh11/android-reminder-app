package comp5216.sydney.edu.au.reminderapp;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * The Edit/Add Task page.
 */
public class EditAddTaskActivity extends Activity {
    // Define Variables
    EditText taskName;
    EditText dueDate;
    EditText dueTime;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add);

        taskName = (EditText) findViewById(R.id.editTextName);
        dueDate = (EditText) findViewById(R.id.editTextDate);
        dueTime = (EditText) findViewById(R.id.editTextTime);

        position = getIntent().getIntExtra("position",-1);

        if (position == -1) {
            // Add item case
            taskName.setText("");
            dueDate.setText("");
            dueTime.setText("");
        } else {
            // Edit item case
            String taskNameRec = getIntent().getStringExtra("taskName");
            String dueDateRec = getIntent().getStringExtra("dueDate");
            String dueTimeRec = getIntent().getStringExtra("dueTime");

            // pre fill the fields with current values for the task
            taskName.setText(taskNameRec);
            dueDate.setText(dueDateRec);
            dueTime.setText(dueTimeRec);
        }
    }

    /**
     * Handler for the "Save" Button. Saves the task to the ListView and database and sends user
     * back to homepage.
     * @param v
     */
    public void onSubmit(View v) {

        // Prepare data for sending back
        Intent data = new Intent();

        data.putExtra("taskName", taskName.getText().toString());
        data.putExtra("dueDate", dueDate.getText().toString());
        data.putExtra("dueTime", dueTime.getText().toString());
        data.putExtra("position", position);

        // Activity finishes OK, return the data
        setResult(RESULT_OK, data); // Set result code and bundle data for response
        finish(); // Close the activity, pass data to parent
    }

    /**
     * Handler for the "Cancel" Button. Fires a up a dialogue box.
     * @param v
     */
    public void onCancel(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAddTaskActivity.this);
        builder.setTitle("Are you sure you want to cancel the edit?")
                .setMessage("Your unsaved edit will be discarded if you click YES")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(RESULT_CANCELED); // Set result code and bundle data for response
                        finish(); // Close the activity, pass data to parent
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User cancelled the dialog
                        // Nothing happens
                    }
                });
        builder.create().show();
    }
}
