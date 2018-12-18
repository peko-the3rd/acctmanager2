package jp.thesaurus.accountmanager.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.thesaurus.accountmanager.entity.Account;
import jp.thesaurus.accountmanager.utils.DBHelper;
import jp.thesaurus.accountmanager.utils.ViewUtil;

public class AccountEditActivityPresenter {
    private Account account;
    //private AccountEntryActivity thisView;

    public AccountEditActivityPresenter() {

    }
    public AccountEditActivityPresenter(Account account) {
        this.account = account;
    }

    /**
     * 個別データ取得
     * @param ct コンテキスト
     * @param uid キー
     * @return 対象データ
     */
    public Map<String, String> getEditData(Context ct, String uid){
        DBHelper dh = new DBHelper(ct);
        SQLiteDatabase db = dh.getWritableDatabase();
        Date date = new Date();
        return dh.getAccountData(date,uid);
    }

    /**
     * mapの変更内容差分確認
     * @param ct コンテキスト
     * @param list 更新対象データ群
     * @return 変更されたカラムと値
     */
    public long updataEditData(Context ct,List<Map<String,String>> list){
        DBHelper dh = new DBHelper(ct);
        return dh.updateData(account.getUid(),list);
    }

    /**
     * mapの変更内容差分確認
     * @param ct コンテキスト
     * @param uid キー
     * @return 変更されたカラムと値
     */
    public long deleteEditData(Context ct,String uid){
        DBHelper dh = new DBHelper(ct);
        return dh.deleteData(uid);
    }

    /**
     * mapの変更確認判定
     * @param beforeMap 変更前
     * @return boolean 変更が無い場合:true 変更がある場合:false
     */
    public boolean isEditData(Map<String, String> beforeMap){
        Map<String, String> afterData =new LinkedHashMap<>();
        afterData.put("user_id",account.getUserId());
        afterData.put("password",account.getPassword());
        afterData.put("service_index",account.getSIndex());
        afterData.put("remarks",account.getRemarks());
        return ViewUtil.isMap(beforeMap,afterData);
    }

    /**
     * mapの変更内容差分確認
     * @param beforeMap 変更前
     * @return 変更されたカラムと値
     */
    public List<Map<String,String>> diffEditData(Map<String, String> beforeMap){
        Map<String, String> afterData =new LinkedHashMap<>();
        afterData.put("user_id",account.getUserId());
        afterData.put("password",account.getPassword());
        afterData.put("service_index",account.getSIndex());
        afterData.put("remarks",account.getRemarks());
        return ViewUtil.diffMap(beforeMap,afterData);
    }

/*    public interface View{

        void updateUserInfoTextView(String info);
        void showProgressBar();
        void hideProgressBar();

    }*/
}
