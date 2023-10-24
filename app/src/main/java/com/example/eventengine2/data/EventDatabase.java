package com.example.eventengine2.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    private static volatile EventDatabase INSTANCE;
    public abstract EventDao eventDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //If database doesn't exist
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventDatabase.class, "event_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

   /* public static synchronized EventDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EventDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)  // Add a callback to insert initial data
                    .build();
        }
        return INSTANCE;
    }*/

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Insert initial categories into the Category table
                CategoryDao categoryDao = INSTANCE.categoryDao();

                // Example category data
                long c1 = categoryDao.insert(new Category("Sport"));
                long c2 = categoryDao.insert(new Category("Dance"));
                long c3 = categoryDao.insert(new Category("Food and Drink"));
                long c4 = categoryDao.insert(new Category("Creative"));

                // Example event data with categoryIds matching the Category table
                EventDao eventDao = INSTANCE.eventDao();
                long event1 = eventDao.insert(new Event("Event 1", "Description for Event 1", 10.0, 5, c1));
                long event2 = eventDao.insert(new Event("Event 2", "Description for Event 2", 11.0, 5, c2));
                long event3 = eventDao.insert(new Event("Event 3", "Description for Event 3", 9.0, 20, c2));
                long event4 = eventDao.insert(new Event("Event 4", "Description for Event 4", 12.0, 10, c4));
            });
        }
    };

    public static void runOnDatabaseExecutor(Runnable r) {
        databaseWriteExecutor.execute(r);
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
