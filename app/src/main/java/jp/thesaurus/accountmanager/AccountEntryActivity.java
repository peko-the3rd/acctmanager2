package jp.thesaurus.accountmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import jp.thesaurus.accountmanager.adapter.SpinnerAdapter;
import jp.thesaurus.accountmanager.entity.Account;
import jp.thesaurus.accountmanager.presenter.AccountEntryActivityPresenter;
import jp.thesaurus.accountmanager.utils.ViewUtil;

public class AccountEntryActivity extends AppCompatActivity {

    private AccountEntryActivityPresenter entryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_entry);

        Spinner spinner = findViewById(R.id.service_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this.getApplicationContext(),
                R.layout.spinner_list, ViewUtil.getServiceMapConstants(this.getResources())
        );
        spinner.setAdapter(adapter);

        findViewById(R.id.entry_button).setOnClickListener(entryClickListener);
        findViewById(R.id.entry_finish_button).setOnClickListener(finishClickListener);
    }

    /**
     * 登録ボタン
     */
    View.OnClickListener entryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Account account = new Account();
            TextView userIdView = findViewById(R.id.entry_user_id);
            TextView passwordView = findViewById(R.id.entry_password);
            TextView remarksView = findViewById(R.id.entry_remarks);
            TextView sindexView = findViewById(R.id.s_index);
            if(isEntryValidation(userIdView,passwordView)){
                return;
            }

            account.setUserId(userIdView.getText().toString());
            account.setPassword(passwordView.getText().toString());
            account.setSIndex(sindexView.getText().toString());
            account.setRemarks(remarksView.getText().toString());

            entryPresenter = new AccountEntryActivityPresenter(account);
            entryPresenter.insertEntryData(getApplication());
            finish();
        }
    };
    /**
     * 入力チェック関数
     * @param userIdView ユーザーID要素
     * @param passwordView パスワード要素
     *  */
    private boolean isEntryValidation(TextView userIdView,TextView passwordView) {
        boolean validError = false;
        if(userIdView.getText().toString().isEmpty()) {
            userIdView.setError("ユーザーIDを入力してください");
            validError = true;
        }
        if(passwordView.getText().toString().isEmpty()) {
            passwordView.setError("パスワードを入力してください");
            validError = true;
        }

        return validError;
    }
    /**
     * 終了ボタン
     */
    View.OnClickListener finishClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AccountEntryActivity.this.finish();
        }
    };
}
