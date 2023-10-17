package com.example.eventengine2.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {
    private static EventDatabase INSTANCE;
    public abstract EventDao eventDao();
    public abstract CategoryDao categoryDao();

    public static synchronized EventDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class, "event_database")
                    .fallbackToDestructiveMigration() // For simplicity, you can handle migrations differently
                    .build();
        }
        return INSTANCE;
    }

   /* private static volatile EventDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //If database doesn't exist
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventDatabase.class, "event_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/
}
