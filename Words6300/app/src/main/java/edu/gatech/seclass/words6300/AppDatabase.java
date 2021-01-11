package edu.gatech.seclass.words6300;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Game.class, Letter.class, Pool.class, Word.class, WordBank.class, WordBankStatistics.class, LetterStatistics.class, Player.class},
        version = 20, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

  private static AppDatabase appDatabase;

  public abstract GameDao gameDao();
  public abstract LetterDao letterDao();
  public abstract PoolDao poolDao();
  public abstract WordDao wordDao();
  public abstract WordBankDao wordBankDao();
  public abstract WordBankStatisticsDao wordBankStatisticsDao();
  public abstract LetterStatisticsDao letterStatisticsDao();
  public abstract PlayerDao playerDao();

  public static AppDatabase getAppDatabase(Context context) {

    System.out.println("getAppDatabase DATABASE_NAME: " + "words6300.db");

    if (appDatabase == null) {
      synchronized (AppDatabase.class) {
        if (appDatabase == null) {
          appDatabase =
                  Room.databaseBuilder(context.getApplicationContext(),
                          AppDatabase.class, "Words6300")
                          .allowMainThreadQueries()
                          .fallbackToDestructiveMigration()
                          .build();
        }
      }
    }
    return appDatabase;
  }

  @Override
  protected void finalize() throws Throwable {
    this.close();
    super.finalize();
  }
}
