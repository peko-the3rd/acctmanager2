package jp.thesaurus.accountmanager.presenter;

import android.content.Context;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.thesaurus.accountmanager.entity.Account;
import jp.thesaurus.accountmanager.utils.DBHelper;

public class AccountListActivityPresenter {
    private Account account;
    //private AccountEntryActivity thisView;

    public AccountListActivityPresenter() {

        //this.account = account;
    }

/*    public void updateSTextView(String fullName){
        account.setSTextView(fullName);
        view.updateUserInfoTextView(account.toString());

    }

    public void updatesetEntryId(String email){
        account.setEntryId(email);
        view.updateUserInfoTextView(account.toString());

    }*/
    public List<Map<String, String>> getEntryData(Context ct){
        DBHelper dh = new DBHelper(ct);
        Date date = new Date();
        return dh.getAllAccountList(date);
    }

/*    public interface View{

        void updateUserInfoTextView(String info);
        void showProgressBar();
        void hideProgressBar();

    }*/
}
