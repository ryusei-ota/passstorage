package com.example.passstorage2;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AddEditActivity extends AppCompatActivity {
    /**
     * 新規登録モードか更新モードかを表すフィールド。
     */
    private int _mode = MainActivity.MODE_INSERT;
    /**
     * 更新モードの際、現在表示しているメモ情報のデータベース上の主キー値。
     */
    private long _idNo = 0;
    /**
     * データベースヘルパーオブジェクト。
     */
    private DatabaseHelper _helper;

    int index = 0;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        _helper = new DatabaseHelper(AddEditActivity.this);

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode", MainActivity.MODE_INSERT);

        if (_mode == MainActivity.MODE_INSERT) {
            TextView tvTitleEdit = findViewById(R.id.tvUsrInsert);
            tvTitleEdit.setText(R.string.add_insert);
        } else {
            _idNo = intent.getLongExtra("idNo", 0);
            SQLiteDatabase db = _helper.getWritableDatabase();
            ManageList ListData = DataAccess.findByPK(db, _idNo);

            TextView tvTitleEdit = findViewById(R.id.tvUsrInsert);
            tvTitleEdit.setText(R.string.add_edit);

            EditText etInputTitle = findViewById(R.id.etInputTitle);
            etInputTitle.setText(ListData.getTitle());

            EditText etInputName = findViewById(R.id.etInputName);
            etInputName.setText(ListData.getListName());

            EditText etInputPass = findViewById(R.id.etInputPass);
            etInputPass.setText(ListData.getPassUnique());

            EditText etLink = findViewById(R.id.etLink);
            etLink.setText(ListData.getLink());

            EditText etInputMemo = findViewById(R.id.etInputMemo);
            etInputMemo.setText(ListData.getMemo());

            String num = ListData.getGenre();

            switch (num) {
                case "選択なし":
                    index = 0;
                    break;
                case "SNS":
                    index = 1;
                    break;
                case "銀行口座":
                    index = 2;
                    break;
                case "メールアドレス":
                    index = 3;
                    break;
                case "ゲームID":
                    index = 4;
                    break;
                case "Webアカウント":
                    index = 5;
                    break;
                case "秘密":
                    index = 6;
                    break;
                case "その他":
                    index = 7;
                    break;
            }
            spinner = (Spinner) findViewById(R.id.etGenre);
            spinner.setSelection(index);
        }
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }

    public void onClickAdd() {
        EditText etInputTitle = findViewById(R.id.etInputTitle);
        String inputTitle = etInputTitle.getText().toString();
        if(inputTitle.equals("")) {
            Toast.makeText(AddEditActivity.this, R.string.msg_input_msg, Toast.LENGTH_SHORT).show();
        }
        else {
            SQLiteDatabase db = _helper.getWritableDatabase();
            EditText etInputName = findViewById(R.id.etInputName);
            String inputName = etInputName.getText().toString();
            EditText etInputPass = findViewById(R.id.etInputPass);
            String inputPassUnique = etInputPass.getText().toString();
            Spinner spInputGenre = findViewById(R.id.etGenre);
            String inputGenre = spInputGenre.getSelectedItem().toString();
            EditText etInputLink = findViewById(R.id.etLink);
            String inputLink= etInputLink.getText().toString();
            EditText etInputMemo = findViewById(R.id.etInputMemo);
            String inputMemo = etInputMemo.getText().toString();
            if(_mode == MainActivity.MODE_INSERT) {
                if (!inputTitle.equals("")) {
                    Bundle extras = new Bundle();
                    extras.putString("title", inputTitle);
                    extras.putString("name", inputName);
                    extras.putString("genre", inputGenre);
                    extras.putString("passUnique", inputPassUnique);
                    extras.putString("link", inputLink);
                    extras.putString("memo", inputMemo);
                    AddDialogFragment dialog = new AddDialogFragment();
                    dialog.setArguments(extras);
                    FragmentManager manager = getSupportFragmentManager();
                    dialog.show(manager, "AddDialogFragment");
                } else {
                    Toast.makeText(AddEditActivity.this, R.string.msg_input_msg, Toast.LENGTH_SHORT).show();
                }
                DataAccess.insert(db, inputTitle, inputName, inputGenre, inputPassUnique, inputLink, inputMemo);
            }
            else {
                Bundle extras = new Bundle();
                extras.putString("title", inputTitle);
                extras.putString("name", inputName);
                extras.putString("genre", inputGenre);
                extras.putString("passUnique", inputPassUnique);
                extras.putString("link", inputLink);
                extras.putString("memo", inputMemo);
                AddDialogFragment dialog = new AddDialogFragment();
                dialog.setArguments(extras);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "AddDialogFragment");
                DataAccess.update(db, _idNo, inputTitle, inputName, inputGenre, inputPassUnique, inputLink, inputMemo);
            }
//            finish();
        }
    }

    public void onClickSamplePass(View view) {
        EditText etInputPass = findViewById(R.id.etKeta);
        String StrKeta = etInputPass.getText().toString();
        try {
            int intNum = Integer.parseInt(StrKeta);

            //数値判定
            boolean isNumeric = true;
            for (int i = 0; i < StrKeta.length(); i++) {
                if (!Character.isDigit(StrKeta.charAt(i))) {
                    isNumeric = false;
                }
            }
            if (isNumeric) {
                int intKeta = Integer.parseInt(StrKeta);
                if (intKeta <= 25) {
                    EditText et = (EditText) findViewById(R.id.etInputPass);
                    int length = intNum;
//                    String symbol= "-/^&amp*!%=+";
                    String cap_letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    String small_letter = "abcdefghijklmnopqrstuvwxyz";
                    String numbers = "0123456789";
                    String finalString = cap_letter + small_letter +
                            numbers;
                    Random random = new Random();
                    char[] password = new char[length];
                    for (int i = 0; i < length; i++) {
                        password[i] = finalString.charAt(random.nextInt(finalString.length()));
                    }
                    String pass = (String.valueOf(password));
                    et.setText(pass);
                } else {
                    Toast.makeText(AddEditActivity.this, R.string.msg_num_err, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddEditActivity.this, R.string.msg_input_pass_err_flg, Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_activity_sub, menu);

        if (_mode == MainActivity.MODE_INSERT) {
            MenuItem item = menu.findItem(R.id.textDel);
            item.setVisible(false);
        } else {
            MenuItem btnAdd = menu.findItem(R.id.textInsert);
            btnAdd.setTitle(R.string.add_edit);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.textInsert:
                onClickAdd();
                break;
            case R.id.textDel:
                SQLiteDatabase db = _helper.getWritableDatabase();
                DataAccess.delete(db, _idNo);
                finish();
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}