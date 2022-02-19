package com.example.passstorage2;

public class ManageList {
    /**
     * 承認ID
     */
    private int _user;

    /**
     * 主キーのID値。
     */
    private int _id;
    /**
     * 本人確認ユニーク
     */
    private String _uniqueName;
    /**
     * 本人確認ランダム
     */
    private String _random;
    /**
     * リストのID値
     */
    private int _listId;
    /**
     * 保存用の名前
     */
    private int _userId;
    /**
     * 保存用のタイトル
     */
    private String _title;
    /**
     * 保存用の名前
     */
    private String _name;
    /**
     * ジャンル
     */
    private String _genre;
    /**
     * 保存したいpassUnique
     */
    private String _passUnique;
    /**
     * 保存したいrandomPass
     */
    private String _randomPass;
    /**
     * メモ
     */
    private String _link;
    /**
     * メモ
     */
    private String _memo;

    //以下アクセサメソッド。
    public int getUser() {
        return _user;
    }
    public void setUser(int user) {
        _user = user;
    }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }

    public String getUniqueName() {
        return _uniqueName;
    }
    public void setUniqueName(String uniqueName) {
        _uniqueName = uniqueName;
    }

    public String getRandom() {
        return _random;
    }
    public void setRandom(String random) {
        _random = random;
    }

    public int getListId() {
        return _listId;
    }
    public void setListId(int listId) {
        _listId = listId;
    }

    public int getUserId() { return _userId; }
    public void setUserId(int userId) {
        _userId = userId;
    }

    public String getTitle() {
        return _title;
    }
    public void setTitle(String title) {
        _title = title;
    }

    public String getListName() {
        return _name;
    }
    public void setListName(String listName) {
        _name = listName;
    }

    public String getGenre() {
        return _genre;
    }
    public void setGenre(String genre) {
        _genre = genre;
    }

    public String getPassUnique() { return _passUnique;}
    public void setPassUnique(String passUnique) {
        _passUnique = passUnique;
    }

    public String getRandomPass() { return _randomPass;}
    public void setRandomPass(String randomPass) {
        _randomPass = randomPass;
    }

    public String getLink() { return _link;}
    public void setLink(String link) {
        _link = link;
    }

    public String getMemo() {
        return _memo;
    }
    public void setMemo(String memo) {
        _memo = memo;
    }
}