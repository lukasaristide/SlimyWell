package com.lukasaristide.icytower;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DatabaseAndroid implements Database{
    ScoresDatabase scoresDatabase;
    RecordDAO recordDAO;
    DatabaseAndroid(Context context){
        scoresDatabase = Room.databaseBuilder(context, ScoresDatabase.class, "record").build();
        recordDAO = scoresDatabase.recordDao();
    }

    @Override
    public List<Integer> get() {
        List<Record> list = recordDAO.getAll();
        List<Integer> ans = new ArrayList<Integer>();
        for (Record r : list)
            ans.add(r.score);
        return ans;
    }

    @Override
    public void insert(List<Integer> list) {
        Record[] toInsert = new Record[list.size()];
        int id = 0;
        for(Integer i : list){
            Record r = new Record();
            r.id = id++;
            r.score = i;
            toInsert[id-1] = r;
        }
        recordDAO.insertAll(toInsert);
    }

    @Override
    public void clear() {
        recordDAO.deleteAll();
    }

    public void close(){
        scoresDatabase.close();
    }
}
