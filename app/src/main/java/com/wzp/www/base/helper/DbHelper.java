package com.wzp.www.base.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wzp.www.base.bean.constant.Global;

/**
 * Created by wengzhipeng on 17/3/31.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    private static final String DB_NAME = Global.APP_PACKAGE_NAME + ".db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_LOG = "LOG";
    private static final String CREATE_LOG = "create table " + TABLE_LOG + " (id " +
            "integer primary key autoincrement, dt TIMESTAMP default (datetime('now', " +
            "'localtime')), tag text, source text, msg text)";
    private static DbHelper sInstance;
    private static SQLiteDatabase sDatabase;
    private static int sCount = 0;

    public static DbHelper getInstance() {
        if (sInstance == null) {
            synchronized (DbHelper.class) {
                if (sInstance == null) {
                    sInstance = new DbHelper();
                }
            }
        }
        return sInstance;
    }

    private DbHelper() {
        super(Global.APP_CONTEXT, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_LOG);
        } catch (SQLException e) {
            L.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized void open() {
        if (sCount == 0) {
            sDatabase = getWritableDatabase();
        }
        sCount++;
    }

    public synchronized void close() {
        sCount--;
        if (sCount == 0) {
            sDatabase.close();
        }
    }

    private void insert(final String table, final ContentValues values) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                open();
                try {
                    sDatabase.insert(table, null, values);
                } catch (Exception e) {
                    L.e(TAG, e.getMessage());
                } finally {
                    close();
                }
            }
        }).start();
    }

    /**
     * 添加日志
     */
    public void addLog(String tag, String source, String msg) {
        ContentValues values = new ContentValues();
        values.put("tag", tag);
        values.put("source", source);
        values.put("msg", msg);
        insert(TABLE_LOG, values);
    }

    /**
     * 获取日志
     */
    public String getLog() {
        StringBuffer buffer = new StringBuffer();
        open();
        try {
            String query = "select * from " + TABLE_LOG + " order by id desc limit 0, 49";
            Cursor cursor = sDatabase.rawQuery(query, null);
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    do {
                        buffer.append(cursor.getString(cursor.getColumnIndex("dt")))
                                .append("|");
                        buffer.append(cursor.getString(cursor.getColumnIndex("tag")))
                                .append("|");
                        buffer.append(cursor.getString(cursor.getColumnIndex("source"))
                        ).append("|");
                        buffer.append(cursor.getString(cursor.getColumnIndex("msg")))
                                .append(Global.LINE_SEPARATOR);
                    } while (cursor.moveToPrevious());
                }
                cursor.close();
            }
        } catch (Exception e) {
            L.e(TAG, e.getMessage());
        } finally {
            close();
        }
        return buffer.toString();
    }

}
