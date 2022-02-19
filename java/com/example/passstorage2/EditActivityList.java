package com.example.passstorage2;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EditActivityList extends AppCompatActivity {
    /**
     * 新規登録モードを表す定数フィールド。
     */
    static final int MODE_INSERT = 1;
    /**
     * 更新モードを表す定数フィールド。
     */
    static final int MODE_EDIT = 2;
    /**
     * リスト用ListView。
     */
    private ListView _lvList;
    /**
     * データベースヘルパーオブジェクト。
     */
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        _lvList = findViewById(R.id.lvList);
        _lvList.setOnItemClickListener(new ListItemClickListener());

        _helper = new DatabaseHelper(EditActivityList.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * リスト構造の作成
     */
    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = _helper.getWritableDatabase();
        Cursor cursor = DataAccess.findAll(db);
        String[] from = { "title" ,"genre"};
        int[] to = { android.R.id.text1 ,android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(EditActivityList.this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        _lvList.setAdapter(adapter);
    }

    /**
     * リストがクリックされた時のリスナクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor item = (Cursor) parent.getItemAtPosition(position);
            int idxId = item.getColumnIndex("_id");
            long idNo = item.getLong(idxId);

            Intent intent = new Intent(EditActivityList.this, AddEditActivity.class);
            intent.putExtra("mode", MODE_EDIT);
            intent.putExtra("idNo", idNo);
            // intent.putExtra("idNo", id);
            startActivity(intent);
        }
    }

    /**
     * アクションバーの表示
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_activity_main, menu);
        return true;
    }

    /**
     * アクションバーの遷移先
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean returnVal = true;
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.list_add:
                Intent intent = new Intent(EditActivityList.this, AddEditActivity.class);
                intent.putExtra("mode", MODE_INSERT);
                startActivity(intent);
                break;
            case android.R.id.home:
                intent = new Intent(EditActivityList.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
        }
        return returnVal;
    }

    /**
     * 検索機能
     */
    public void onSaveButtonClickSearch(View view){
        Spinner spInputGenre = findViewById(R.id.etGenre);
        String inputGenre = spInputGenre.getSelectedItem().toString();
        super.onResume();
        SQLiteDatabase db = _helper.getWritableDatabase();
        Cursor cursor = DataAccess.findSearch(db,inputGenre);
        String[] from = { "title" ,"genre"};
        int[] to = { android.R.id.text1 ,android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(EditActivityList.this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        _lvList.setAdapter(adapter);
    }
}