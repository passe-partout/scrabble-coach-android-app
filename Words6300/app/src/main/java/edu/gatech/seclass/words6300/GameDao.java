package edu.gatech.seclass.words6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GameDao {

    @Query("Select * from game")
    List<Game> getAll();

    @Query("Select * from game where id = :id")
    Game getGameViaID( int id );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Game game);

    @Delete
    void delete(Game game);

    @Update
    int update(Game game);
}
