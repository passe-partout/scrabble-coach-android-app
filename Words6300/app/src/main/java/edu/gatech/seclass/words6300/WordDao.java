package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Query("Select * from word")
    List<Word> getAll();

    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Update
    void update(Word word);
}
