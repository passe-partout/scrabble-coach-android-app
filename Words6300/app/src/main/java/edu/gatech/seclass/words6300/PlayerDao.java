package edu.gatech.seclass.words6300;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {

    @Query("Select * from player")
    List<Player> getAll();

    @Query("Select * from player order by playerid asc limit 1")
    Player getLatestPlayer();

    @Query("Select * from player where playerId = :id")
    Player getPlayerViaId( int id );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Player player);

    @Delete
    void delete(Player player);

    @Update
    void update(Player player);
}
