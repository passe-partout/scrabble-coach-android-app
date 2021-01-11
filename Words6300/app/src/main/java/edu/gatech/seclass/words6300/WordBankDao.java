package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordBankDao {
    @Query("Select * from wordbank")
    List<WordBank> getAll();

    @Query("Select * from wordbank where id = :id")
    WordBank getWordBankViaID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WordBank wordbank);

    @Delete
    void delete(WordBank wordbank);

    @Update
    void update(WordBank wordbank);
}
