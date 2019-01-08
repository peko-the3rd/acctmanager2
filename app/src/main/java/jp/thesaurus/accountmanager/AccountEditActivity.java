package jp.thesaurus.accountmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import jp.thesaurus.accountmanager.adapter.SpinnerAdapter;
import jp.thesaurus.accountmanager.entity.Account;
import jp.thesaurus.accountmanager.presenter.AccountEditActivityPresenter;
import jp.thesaurus.accountmanager.utils.ViewUtil;

public class AccountEditActivity extends AppCompatActivity {

    private AccountEditActivityPresenter editPresenter;
    private Map<String, String> editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        Spinner spinner = findViewById(R.id.edit_service_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this.getApplicationContext(),
                R.layout.spinner_list, ViewUtil.getServiceMapConstants(this.getResources())
        );
        spinner.setAdapter(adapter);

        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        editPresenter = new AccountEditActivityPresenter();
        editData = editPresenter.getEditData(this,uid);
        initEditView(editData);
        findViewById(R.id.change_button).setOnClickListener(changeClickListener);
        findViewById(R.id.update_button).setOnClickListener(updateClickListener);
        findViewById(R.id.delete_button).setOnClickListener(deleteClickListener);
        findViewById(R.id.finish_button).setOnClickListener(finishClickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    /**
     * 初期表示時の画面描画
     * @param editData 取得データ
     */
    protected void initEditView(Map<String, String> editData){

        TextView editUidView = findViewById(R.id.edit_uid_hidden);
        editUidView.setText(editData.get("uid"));

        TextView editUserIdView = findViewById(R.id.edit_user_id);
        editUserIdView.setText(editData.get("user_id"));
        editUserIdView.setEnabled(false);

        TextView passwordView = findViewById(R.id.edit_password);
        passwordView.setText(editData.get("password"));
        passwordView.setEnabled(false);

        TextView subServiceNameView = findViewById(R.id.edit_sub_service_name);
        subServiceNameView.setText(editData.get("sub_service_name"));
        subServiceNameView.setEnabled(false);

        TextView remarksView = findViewById(R.id.edit_remarks);
        remarksView.setText(editData.get("remarks"));
        remarksView.setEnabled(false);

        TextView registDateView = findViewById(R.id.regist_date);
        registDateView.setText(editData.get("regist_date"));
        TextView updataDateView = findViewById(R.id.updata_date);
        updataDateView.setText(editData.get("updata_date"));

        TextView updateButtonView = findViewById(R.id.update_button);
        TextView deleteButtonView = findViewById(R.id.delete_button);
        updateButtonView.setEnabled(false);
        deleteButtonView.setEnabled(false);

        Spinner spinner = findViewById(R.id.edit_service_spinner);
        spinner.setSelection(Integer.parseInt(editData.get("service_index"))-1);
        spinner.setEnabled(false);

    }

    /**
     * 更新後の画面再描画
     * 最後にeditDataを編集後のデータに更新する
     * @param refreshData 再取得時データ
     */
    protected void refreshEditView(Map<String, String> refreshData){

        TextView editUidView = findViewById(R.id.edit_uid_hidden);
        editUidView.setText(refreshData.get("uid"));

        TextView editUserIdView = findViewById(R.id.edit_user_id);
        editUserIdView.setText(refreshData.get("user_id"));


        TextView passwordView = findViewById(R.id.edit_password);
        passwordView.setText(refreshData.get("password"));


        TextView subServiceNameView = findViewById(R.id.edit_sub_service_name);
        subServiceNameView.setText(refreshData.get("sub_service_name"));

        TextView remarksView = findViewById(R.id.edit_remarks);
        remarksView.setText(refreshData.get("remarks"));


        TextView registDateView = findViewById(R.id.regist_date);
        registDateView.setText(refreshData.get("regist_date"));
        TextView updataDateView = findViewById(R.id.updata_date);
        updataDateView.setText(refreshData.get("updata_date"));


        Spinner spinner = findViewById(R.id.edit_service_spinner);
        spinner.setSelection(Integer.parseInt(refreshData.get("service_index"))-1);

        editData = refreshData;
    }

    /**
     * 活性/非活性変更ボタン
     */
    View.OnClickListener changeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ViewUtil vu = new ViewUtil();
            TextView editUserIdView = findViewById(R.id.edit_user_id);
            TextView passwordView = findViewById(R.id.edit_password);
            TextView subServiceNameView = findViewById(R.id.edit_sub_service_name);
            TextView remarksView = findViewById(R.id.edit_remarks);
            TextView updateButtonView = findViewById(R.id.update_button);
            TextView deleteButtonView = findViewById(R.id.delete_button);
            Spinner spinner = findViewById(R.id.edit_service_spinner);

            editUserIdView.setEnabled(vu.changeAble(editUserIdView.isEnabled()));
            passwordView.setEnabled(vu.changeAble(passwordView.isEnabled()));
            subServiceNameView.setEnabled(vu.changeAble(subServiceNameView.isEnabled()));
            remarksView.setEnabled(vu.changeAble(remarksView.isEnabled()));
            //passwordView.setFocusableInTouchMode(true);
            //remarksView.setFocusableInTouchMode(true);
            updateButtonView.setEnabled(vu.changeAble(updateButtonView.isEnabled()));
            deleteButtonView.setEnabled(vu.changeAble(deleteButtonView.isEnabled()));
            spinner.setEnabled(vu.changeAble(spinner.isEnabled()));
        }
    };

    /**
     * 削除ボタン
     */
    View.OnClickListener deleteClickListener = new View.OnClickListener() {
        TextView editUserIdView = null;
        @Override
        public void onClick(View v) {
            editUserIdView = findViewById(R.id.edit_user_id);

            String message = "ユーザーID「"+editUserIdView.getText().toString()+"」を削除しますがよろしいですか?";

            new AlertDialog.Builder(v.getContext())
                    .setTitle("削除確認")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // OK button pressed
                            TextView editUidView = findViewById(R.id.edit_uid_hidden);
                            editPresenter = new AccountEditActivityPresenter();

                            if(0 < editPresenter.deleteEditData(getApplicationContext(),editUidView.getText().toString())) {
                                Toast.makeText(AccountEditActivity.this, editUserIdView.getText().toString()+"を削除しました", Toast.LENGTH_SHORT).show();
                                AccountEditActivity.this.finish();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    };
    /**
     * 更新ボタン
     */
    View.OnClickListener updateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView uidView        = findViewById(R.id.edit_uid_hidden);
            TextView editUserIdView = findViewById(R.id.edit_user_id);
            TextView passwordView   = findViewById(R.id.edit_password);
            TextView sindexView     = findViewById(R.id.s_index);
            TextView subServiceNameView = findViewById(R.id.edit_sub_service_name);
            TextView remarksView    = findViewById(R.id.edit_remarks);

            Account account = new Account();
            account.setUid(uidView.getText().toString());
            account.setUserId(editUserIdView.getText().toString());
            account.setPassword(passwordView.getText().toString());
            account.setSIndex(sindexView.getText().toString());
            account.setSubServiceName(subServiceNameView.getText().toString());
            account.setRemarks(remarksView.getText().toString());

            editPresenter = new AccountEditActivityPresenter(account);
            if(editPresenter.diffEditData(editData).size() == 0){
                return;
            }
            if(0 < editPresenter.updataEditData(getApplicationContext(),editPresenter.diffEditData(editData))) {
                refreshEditView(editPresenter.getEditData(getApplicationContext(),uidView.getText().toString()));
                Toast.makeText(AccountEditActivity.this, editUserIdView.getText().toString()+"を更新しました", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 終了ボタン
     */
    View.OnClickListener finishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AccountEditActivity.this, AccountListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //AccountEditActivity.this.finish();
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("LifeCycle", "onDestroy");

        editPresenter = null;
        editData = null;
    }
}
