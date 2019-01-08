package jp.thesaurus.accountmanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private Map<Date, String> activities = new LinkedHashMap<>();
    static final String DB = "acct_manager.db";
    static final int DB_VERSION = 7;
    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS acct (" +
            "  uid text not null PRIMARY KEY" +
            " ,user_id text not null" +
            " ,password text not null" +
            " ,service_index text" +
            " ,sub_service_name text" +
            " ,remarks text"+
            " ,regist_date datetime" +
            " ,updata_date datetime" +
            " );";
    static final String DROP_TABLE = "drop table acct;";

    public DBHelper(Context c) {
        super(c, DB, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    /****
     * 一覧用全アカウント情報取得
     * @param target キャッシュ用
     * @return lmap
     */
    public List<Map<String, String>> getAllAccountList(Date target) {
        List<Map<String, String>> lmap = new ArrayList<Map<String, String>>();
        if (!activities.containsKey(target)) {
            activities.put(target, target.toString());
            //SQL作成
            String query = "SELECT uid,user_id,password,service_index,sub_service_name,remarks FROM acct ORDER BY service_index,user_id;";
            //rawQueryメソッドでデータを取得
            SQLiteDatabase db = getReadableDatabase();
            try {
                Cursor cursor = db.rawQuery(query, null);
                boolean isEof = cursor.moveToFirst();
                //String result = cursor.getString(0);
                //Log.d("result", result);
                if(cursor.moveToFirst()) {
                    do {
                        Map<String, String> map = new LinkedHashMap<String, String>();
                        map.put("uid", cursor.getString(cursor.getColumnIndex("uid")));
                        map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                        map.put("password", cursor.getString(cursor.getColumnIndex("password")));
                        map.put("service_index", cursor.getString(cursor.getColumnIndex("service_index")));
                        map.put("sub_service_name", cursor.getString(cursor.getColumnIndex("sub_service_name")));
                        map.put("remarks", cursor.getString(cursor.getColumnIndex("remarks")));
                        lmap.add(map);
                    } while (cursor.moveToNext());
                }
            } finally {
                db.close();
            }
        }
        return lmap;
    }

    /****
     * 詳細画面用アカウント情報取得
     * @param target キャッシュ用
     * @param uid 検索用ユニークid
     * @return map
     */
    public Map<String, String> getAccountData(Date target,String uid) {
        Map<String, String> map = new LinkedHashMap<String, String>(){
            {
                put("uid", "");
                put("user_id", "");
                put("password", "");
                put("service_index", "");
                put("sub_service_name", "");
                put("remarks", "");
                put("regist_date", "");
                put("updata_date", "");
            }
        };
        if (!activities.containsKey(target)) {
            activities.put(target, target.toString());
            //SQL作成
            String query = "SELECT * FROM acct Where uid = ?;";
            //rawQueryメソッドでデータを取得
            SQLiteDatabase db = getReadableDatabase();
            try {
                Cursor cursor = db.rawQuery(query, new String[]{uid});
                if(( cursor != null ) && cursor.getCount() > 0 ) {
                    cursor.moveToFirst();
                    map.put("uid", cursor.getString(cursor.getColumnIndex("uid")));
                    map.put("user_id", cursor.getString(cursor.getColumnIndex("user_id")));
                    map.put("password", cursor.getString(cursor.getColumnIndex("password")));
                    map.put("service_index", cursor.getString(cursor.getColumnIndex("service_index")));
                    map.put("sub_service_name", cursor.getString(cursor.getColumnIndex("sub_service_name")));
                    map.put("remarks", cursor.getString(cursor.getColumnIndex("remarks")));
                    map.put("regist_date", cursor.getString(cursor.getColumnIndex("regist_date")));
                    map.put("updata_date", cursor.getString(cursor.getColumnIndex("updata_date")));
                }
            } finally {
                db.close();
            }
        }
        return map;
    }

    /**
     * @param user_id 更新用アカウントid
     * @param serviceIndex スピナー用index
     * @param password 更新用パスワード
     * @param subServiceName サービスサブネーム
     * @param remarks 更新用備考
     * */
    public void insertData(String user_id,String password,String serviceIndex,String subServiceName,String remarks) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        RandGeneratUtils rgu = new RandGeneratUtils();
        Long nowTime = System.currentTimeMillis();
        values.put("uid", rgu.get());
        values.put("user_id", user_id);
        values.put("password", password);
        values.put("service_index", serviceIndex);
        values.put("sub_service_name", subServiceName);
        values.put("remarks", remarks);
        values.put("regist_date", convertLongToYyyymmddhhmm(nowTime));
        values.put("updata_date", convertLongToYyyymmddhhmm(nowTime));
        try {
            db.insert("acct", null,values);
        } finally {
            db.close();
        }
    }

    /**
     * @param uid 一位キー
     * @param columnListMap 更新カラム
     * */
    public long updateData( String uid,List<Map<String,String>> columnListMap) {
        long returnCode = -1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(Map<String,String> columnData :columnListMap){
            for (Map.Entry<String, String> entry : columnData.entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }
            //values.put(columnData.entrySet(), columnData.values());
        }
        Long nowTime = System.currentTimeMillis();
        values.put("updata_date", convertLongToYyyymmddhhmm(nowTime));
        String whereClause = "uid = ?";
        String whereArgs[] = new String[]{uid};
        //whereArgs[0] = "text";
        try {
            returnCode = db.update("acct", values, whereClause, whereArgs);
        } finally {
            db.close();
        }
        return returnCode;
    }

    /**
     * @param uid 一位キー
     * */
    public long deleteData(String uid) {
        SQLiteDatabase db = getWritableDatabase();
        long returnCode = -1;
        String whereClause = "uid = ?";
        String whereArgs[] = new String[]{uid};

        db.beginTransaction();
        try {
            returnCode = db.delete("acct", whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return returnCode;
    }
    /**
     * Long の数字を日付フォーマットに変換します。
     * @param date Long の数字
     * @return "yyyy/MM/dd HH:mm" フォーマットの文字列
     */
    private static String convertLongToYyyymmddhhmm(Long date) {
        DateFormat yyyymmddhhmm = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return yyyymmddhhmm.format(new Date(date));
    }
}
