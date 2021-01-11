package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordBankStatisticsDao {
    @Query("Select * from wordbankstatistics")
    List<WordBankStatistics> getAll();

    @Insert
    void insert(WordBankStatistics wordbankstatistics);

    @Delete
    void delete(WordBankStatistics wordbankstatistics);

    @Update
    void update(WordBankStatistics wordbankstatistics);
}
