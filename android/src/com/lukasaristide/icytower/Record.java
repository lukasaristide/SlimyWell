package com.lukasaristide.icytower;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "score")
    public int score;
}
