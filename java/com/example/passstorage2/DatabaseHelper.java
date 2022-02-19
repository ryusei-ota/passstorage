package com.example.passstorage2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
    /**
     * データベースファイル名の定数フィールド。
     */
    private static final String DATABASE_NAME_USER = "last.db";

    /**
     * バージョン情報の定数フィールド。
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * コンストラクタ。
     *
     * @param context コンテキスト。
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME_USER, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE user (");
        sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sb.append("uniqueName TEXT NOT NULL , ");
        sb.append("random TEXT NOT NULL");
        sb.append(");");
        String sql = sb.toString();
        db.execSQL(sql);

        //listテーブル作成
        StringBuffer ad = new StringBuffer();
        ad.append("CREATE TABLE list (");
        ad.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        ad.append("userId INTEGER NOT NULL,");
        ad.append("title TEXT NOT NULL,");
        ad.append("name TEXT DEFAULT NULL,");
        ad.append("genre TEXT DEFAULT NULL,");
        ad.append("passUnique TEXT DEFAULT NULL,");
        ad.append("randomPass TEXT DEFAULT NULL,");
        ad.append("link TEXT DEFAULT NULL,");
        ad.append("memo TEXT DEFAULT NULL");
        ad.append(");");
        sql = ad.toString();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}