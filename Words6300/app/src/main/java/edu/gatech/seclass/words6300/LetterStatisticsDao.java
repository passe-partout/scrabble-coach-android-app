package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LetterStatisticsDao {
    @Query("Select * from letterstatistics")
    List<LetterStatistics> getAll();

    @Query("Select * from letterstatistics where lsId = :id")
    LetterStatistics getLetterStatsViaID(int id);

    @Insert
    void insert(LetterStatistics letterStatistics);

    @Delete
    void delete(LetterStatistics letterStatistics);

    @Update
    void update(LetterStatistics letterStatistics);
}
