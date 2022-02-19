package com.example.passstorage2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

public class AddDialogFragment  extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parent = getActivity();
        Bundle extras = getArguments();
        String title = extras.getString("title");
        String name = extras.getString("name");
        if(name.equals(name)){
            name = "登録されていません";
        }
        String genre = extras.getString("genre");
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setTitle(R.string.dlg_msg);
        builder.setMessage("※パスワードは表示されません\n\nタイトル：" + title + "\n登録名：" + name +"\nジャンル：" + genre);
        builder.setPositiveButton(R.string.dlg_ok, new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * ダイアログのボタンが押されたときの処理が記述されたメンバクラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent = new Intent(getContext(), EditActivityList.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}