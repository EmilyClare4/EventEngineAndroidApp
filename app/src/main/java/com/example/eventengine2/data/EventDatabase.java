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
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Insert initial categories into the Category table
                CategoryDao categoryDao = INSTANCE.categoryDao();

                // Category data
                long c1 = categoryDao.insert(new Category("Sport"));
                long c2 = categoryDao.insert(new Category("Dance"));
                long c3 = categoryDao.insert(new Category("Food and Drink"));
                long c4 = categoryDao.insert(new Category("Creative"));

                // Initial event data
                EventDao eventDao = INSTANCE.eventDao();
                eventDao.insert(new Event("Cosy Cafe", "Enjoy a nice relaxing coffee and catch up", 5.0, 8, c3));
                eventDao.insert(new Event("Ice Skating", "Let's get frozen together!", 15.0, 20, c1));
                eventDao.insert(new Event("Hike", "Join us for some fresh air", 0.0, 30, c1));
                eventDao.insert(new Event("Paint 'n' Sip", "Get creative while enjoying a glass of wine", 25.0, 10, c4));
                eventDao.insert(new Event("Bouldering", "Challenge yourself mentally and physically", 15.0, 10, c1));
                eventDao.insert(new Event("Ceramic Painting", "Decorate your own piece of pottery", 30.0, 6, c4));
                eventDao.insert(new Event("Delicious Dinner", "Share some tasty dishes from around the world", 40.0, 12, c3));
                eventDao.insert(new Event("Burlesque", "Gain some confidence with a burlesque dance class", 10.0, 10, c2));
                eventDao.insert(new Event("Yoga in the Park", "Bring your own mat and join us for some stretching", 5.0, 15, c1));
                eventDao.insert(new Event("Salsa Dancing", "Learn some moves at salsa dancing", 25.0, 10, c2));
                eventDao.insert(new Event("Game Night", "Get competitive with some new board games", 8.0, 18, c4));
                eventDao.insert(new Event("Padel", "Come and learn how to play Padel", 8.0, 18, c1));
                eventDao.insert(new Event("SUP", "Test your balance on a paddle board", 20.0, 18, c1));
                eventDao.insert(new Event("Kickboxing", "Learn how to throw some serious punches", 8.0, 16, c1));
                eventDao.insert(new Event("Run club", "Be part of our couch-to-5km", 0.0, 40, c1));
                eventDao.insert(new Event("Football fun", "Kick a ball around and get some exercise", 0.0, 10, c1));
            });
        }
    };

    public static void runOnDatabaseExecutor(Runnable r) {
        databaseWriteExecutor.execute(r);
    }

}
