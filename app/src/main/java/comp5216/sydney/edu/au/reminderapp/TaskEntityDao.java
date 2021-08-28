package comp5216.sydney.edu.au.reminderapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskEntityDao {
    @Query("SELECT * FROM Tasks")
    List<TaskEntity> listAll();

    @Insert
    void insert(TaskEntity taskEntity);

    @Insert
    void insertAll(TaskEntity... taskEntities);

    @Query("DELETE FROM Tasks")
    void deleteAll();
}
