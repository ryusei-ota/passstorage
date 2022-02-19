package com.example.passstorage2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.Random;
public class AddNewUserActivity extends AppCompatActivity {
    /**
     * 新規登録モードか更新モードかを表すフィールド。
     */
    private int _mode = MainActivity.MODE_INSERT;
    /**
     * データベースヘルパーオブジェクト。
     */
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        _helper = new DatabaseHelper(AddNewUserActivity.this);

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode", MainActivity.MODE_INSERT);

    }

    /**
     * アクションバーの遷移先
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 英数字を作成
     */
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    /**
     * ALLOWED_CHARACTERSで作成したモノを取得数字からランダムを生成
     * @param sizeOfRandomString
     * @return
     */
    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    /**
     * ハッシュmd5
     * @param
     */
    public static String md5(String inputPass) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(inputPass.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            String hash = String.valueOf(sb);
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 新規登録
     * @param view
     */
    public void onSaveButtonClickUser(View view) {
        //パスワード取得
        EditText etInputPass = findViewById(R.id.etUserPass);
        String inputPass = etInputPass.getText().toString();

        //パスワードハッシュ化
        String random = getRandomString(10);
        String unique = md5(inputPass + random);

        if(inputPass.equals("")) {
            Toast.makeText(AddNewUserActivity.this, R.string.msg_pass_title, Toast.LENGTH_SHORT).show();
        }
        else {
            SQLiteDatabase db = _helper.getWritableDatabase();
            if(_mode == MainActivity.MODE_INSERT) {
                DataAccess.insertUser(db, unique, random);
            }
            finish();
        }
    }

}