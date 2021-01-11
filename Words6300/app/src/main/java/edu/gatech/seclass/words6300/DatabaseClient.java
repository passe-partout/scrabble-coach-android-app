package edu.gatech.seclass.words6300;

import android.content.Context;

import androidx.room.Room;

/**
 * Database Client for Room.  Modified only slightly from simplifiedcoding.net/android-room-database-example/
 * Pretty standard database client code.
 */
public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

//        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "Words6300")
//                .fallbackToDestructiveMigration().build();

        appDatabase = //Room.databaseBuilder(mCtx.getApplicationContext(),AppDatabase.class,"Words6300b").build();
            Room.databaseBuilder(mCtx.getApplicationContext(),
                AppDatabase.class, "Words6300")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized DatabaseClient getInstance( Context mCtx ) {
        if( mInstance == null ){
            mInstance = new DatabaseClient( mCtx );
        }

        return mInstance;
    }

    public  AppDatabase getAppDatabase(){
        return appDatabase;
    }


}
