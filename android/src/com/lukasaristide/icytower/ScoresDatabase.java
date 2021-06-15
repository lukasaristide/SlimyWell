package com.lukasaristide.icytower;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class ScoresDatabase extends RoomDatabase {
    public abstract RecordDAO recordDao();
}
