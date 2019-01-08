package jp.thesaurus.accountmanager.presenter;

import android.content.Context;

import jp.thesaurus.accountmanager.entity.Account;
import jp.thesaurus.accountmanager.utils.DBHelper;

public class AccountEntryActivityPresenter {
    private Account account;
    //private AccountEntryActivity thisView;

    public AccountEntryActivityPresenter(Account account) {
        this.account = account;
    }

/*    public void updateSTextView(String fullName){
        account.setSTextView(fullName);
        view.updateUserInfoTextView(account.toString());

    }

    public void updatesetEntryId(String email){
        account.setEntryId(email);
        view.updateUserInfoTextView(account.toString());

    }*/
    public void insertEntryData(Context ct){
        DBHelper dh = new DBHelper(ct);
        dh.insertData(account.getUserId(),account.getPassword(),account.getSIndex(),account.getSubServiceName(),account.getRemarks());
    }

/*    public interface View{

        void updateUserInfoTextView(String info);
        void showProgressBar();
        void hideProgressBar();

    }*/
}
