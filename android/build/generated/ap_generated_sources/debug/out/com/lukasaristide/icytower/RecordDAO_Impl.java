package com.lukasaristide.icytower;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RecordDAO_Impl implements RecordDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Record> __insertionAdapterOfRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RecordDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecord = new EntityInsertionAdapter<Record>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Record` (`id`,`score`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Record value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.score);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from record";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final Record... records) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecord.insert(records);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Record> getAll() {
    final String _sql = "select * from record order by score desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
      final List<Record> _result = new ArrayList<Record>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Record _item;
        _item = new Record();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.score = _cursor.getInt(_cursorIndexOfScore);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
