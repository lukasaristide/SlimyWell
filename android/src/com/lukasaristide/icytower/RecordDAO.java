package com.lukasaristide.icytower;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;

import java.util.List;

@Dao
public interface RecordDAO {
   @Query("select * from record order by score desc")
    public abstract List<Record> getAll();

   @Insert
   public abstract void insertAll(Record... records);

   @Query("delete from record")
   public abstract void deleteAll();
}
