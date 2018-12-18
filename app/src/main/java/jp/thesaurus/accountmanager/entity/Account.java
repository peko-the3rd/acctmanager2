package jp.thesaurus.accountmanager.entity;


import android.support.annotation.NonNull;


public class Account {

    @NonNull
    private String uid;
    @NonNull
    private String sIndex;
    @NonNull
    private String userId;
    @NonNull
    private String password;
    @NonNull
    private String remarks;

    public Account() {
    }
    public Account(String sIndex, String userId, String password, String remarks) {
        this.sIndex = sIndex;
        this.userId = userId;
        this.password = password;
        this.remarks = remarks;
    }
    public Account(String uid,String sIndex, String userId, String password, String remarks) {
        this.uid = uid;
        this.sIndex = sIndex;
        this.userId = userId;
        this.password = password;
        this.remarks = remarks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSIndex() {
        return sIndex;
    }

    public void setSIndex(String sIndex) {
        this.sIndex = sIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
