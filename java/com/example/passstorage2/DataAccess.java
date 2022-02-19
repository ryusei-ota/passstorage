package com.example.passstorage2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataAccess {
    /**
     * 全データ検索メソッド。
     *
     * @param db SQLiteDatabaseオブジェクト。
     * @return 検索結果のCursorオブジェクト。
     */
    public static Cursor findAll(SQLiteDatabase db) {
        String sql = "SELECT * FROM list";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public static Cursor findSearch(SQLiteDatabase db,String Genre) {
        String sql = "";
        if(Genre.equals("選択なし")){
            sql = "SELECT * FROM list";
        }else{
            sql = "SELECT * FROM list WHERE genre =\"" + Genre + "\"";
        }
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    /**
     * パス判定
     * @param db
     * @param pass
     * @return
     */
    public static Boolean searchUser(SQLiteDatabase db, String pass) {
        String sql = "select * from user WHERE _id = 4;";
        Cursor cursor = db.rawQuery(sql, null);
        ManageList result = null;
        Boolean flg = false;
        if(cursor.moveToFirst()) {
            int idxUniqueName = cursor.getColumnIndex("uniqueName");
            int idxRandom = cursor.getColumnIndex("random");
            String uniqueName = cursor.getString(idxUniqueName);
            String random = cursor.getString(idxRandom);
            String hash = AddNewUserActivity.md5(pass + random);
            if(hash.equals(uniqueName)){
                result = new ManageList();
                result.setRandom(hash);
                flg = true;
            }else{
                flg = false;
            }
        }
        return flg;
    }

    /**
     * 新規ユーザーの登録
     * @param db
     * @param
     * @param
     * @return
     */
    public static long insertUser(SQLiteDatabase db, String uniqueName, String random) {
        String sql = "INSERT INTO user (uniqueName, random) VALUES (?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, uniqueName);
        stmt.bindString(2, random);
        long id = stmt.executeInsert();
        return id;
    }

    /**
     * リスト検索
     * @param db
     * @param id
     * @return
     */
    public static ManageList findByPK(SQLiteDatabase db, long id) {
        String sql = "SELECT * FROM list WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        ManageList result = null;
        if(cursor.moveToFirst()) {
            int idxUserId = cursor.getColumnIndex("userId");
            int idxTitle = cursor.getColumnIndex("title");
            int idxName = cursor.getColumnIndex("name");
            int idxGenre = cursor.getColumnIndex("genre");
            int idxPassUnique = cursor.getColumnIndex("passUnique");
            int idxLink = cursor.getColumnIndex("link");
            int idxMemo = cursor.getColumnIndex("memo");

            int userId = cursor.getInt(idxUserId);
            String title = cursor.getString(idxTitle);
            String name = cursor.getString(idxName);
            String genre = cursor.getString(idxGenre);
            String passUnique = cursor.getString(idxPassUnique);
            String link = cursor.getString(idxLink);
            String memo = cursor.getString(idxMemo);

            result = new ManageList();
            result.setUserId(userId);
            result.setTitle(title);
            result.setListName(name);
            result.setGenre(genre);
            result.setPassUnique(passUnique);
            result.setLink(link);
            result.setMemo(memo);
        }
        return result;
    }

    /**
     * 新規リスト登録
     * @param db
     * @param name
     * @param pass1
     * @param genre
     * @param memo
     * @return
     */
    public static long insert(SQLiteDatabase db, String title, String name, String genre, String passUnique,String link,String memo) {
        String sql = "INSERT INTO list (userId, title, name, genre, passUnique, randomPass, link, memo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, "");
        stmt.bindString(2, title);
        stmt.bindString(3, name);
        stmt.bindString(4, genre);
        stmt.bindString(5, passUnique);
        stmt.bindString(6, "");
        stmt.bindString(7, link);
        stmt.bindString(8, memo);
        long id = stmt.executeInsert();
        return id;
    }

    /**
     * リストの更新
     * @param db
     * @param id
     * @param name
     * @param pass1
     * @param genre
     * @param memo
     * @return
     */
    public static int update(SQLiteDatabase db, long id, String title, String name, String genre, String passUnique,String link,String memo) {
        String sql = "UPDATE list SET title = ?, name = ?, genre = ?, passUnique = ?, link = ?, memo = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);

        stmt.bindString(1, title);
        stmt.bindString(2, name);
        stmt.bindString(3, genre);
        stmt.bindString(4, passUnique);
        stmt.bindString(5, link);
        stmt.bindString(6, memo);
        stmt.bindLong(7, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }

    /**
     * リストの削除
     * @param db
     * @param id
     * @return
     */
    public static int delete(SQLiteDatabase db, long id) {
        String sql = "DELETE FROM list WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }
}