package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LetterDao {
    @Query("Select * from letter")
    List<Letter> getAll();

    @Insert
    void insert(Letter letter);

    @Delete
    void delete(Letter letter);

    @Update
    void update(Letter letter);
}
