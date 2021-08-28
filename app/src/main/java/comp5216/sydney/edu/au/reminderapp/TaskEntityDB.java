package comp5216.sydney.edu.au.reminderapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
public abstract class TaskEntityDB extends RoomDatabase {
    private static final String DATABASE_NAME = "tasks_db";
    private static TaskEntityDB DBINSTANCE;

    public abstract TaskEntityDao taskEntityDao();

    public static TaskEntityDB getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (TaskEntityDB.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TaskEntityDB.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
