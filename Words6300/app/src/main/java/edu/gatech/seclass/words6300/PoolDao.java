package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PoolDao {
    @Query("Select * from pool")
    List<Pool> getAll();

    @Query("Select * from Pool where game_id = :id")
    List<Pool> getPoolForGameID( int id );

    @Query("Select * from pool where id = :id")
    Pool getPoolViaID( int id );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pool pool);

    @Delete
    void delete(Pool pool);

    @Update
    void update(Pool pool);
}


