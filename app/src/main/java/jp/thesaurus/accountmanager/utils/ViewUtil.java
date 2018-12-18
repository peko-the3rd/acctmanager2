package jp.thesaurus.accountmanager.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.thesaurus.accountmanager.R;

public class ViewUtil {

    public static final Map<String, Map<String, Bitmap>> getServiceMapConstants(final Resources res, String ... serviceId) {

        Map<String, Map<String, Bitmap>> map = new LinkedHashMap<String, Map<String, Bitmap>>();
        map.put("1", new LinkedHashMap<String, Bitmap>() {
            {
                put("Google", getServiceBitmap("1",res));
            }
        });
        map.put("2", new LinkedHashMap<String, Bitmap>() {
            {
                put("Amazon", getServiceBitmap("2",res));
            }
        });
        map.put("3", new LinkedHashMap<String, Bitmap>() {
            {
                put("Git", getServiceBitmap("3",res));
            }
        });
        map.put("4", new LinkedHashMap<String, Bitmap>() {
            {
                put("はてな", getServiceBitmap("4",res));
            }
        });
        map.put("5", new LinkedHashMap<String, Bitmap>() {
            {
                put("みずほ", getServiceBitmap("5",res));
            }
        });
        map.put("6", new LinkedHashMap<String, Bitmap>() {
            {
                put("SBI", getServiceBitmap("6",res));
            }
        });
        map.put("7", new LinkedHashMap<String, Bitmap>() {
            {
                put("Yahoo", getServiceBitmap("7",res));
            }
        });
        map.put("8", new LinkedHashMap<String, Bitmap>() {
            {
                put("X−flag", getServiceBitmap("8",res));
            }
        });
        map.put("9",  new LinkedHashMap<String, Bitmap>() {
            {
                put("開発関連", getServiceBitmap("9",res));
            }
        });
        map.put("10", new LinkedHashMap<String, Bitmap>() {
            {
                put("その他", getServiceBitmap("10",res));
            }
        });
        return map;//Collections.unmodifiableMap(map);
    }

    public static Bitmap getServiceBitmap(String serviceId,Resources r) {

        Bitmap bmp = null;
        switch (serviceId) {
            case "1":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_google);
                break;
            case "2":
                bmp= BitmapFactory.decodeResource(r, R.mipmap.s_amazon);
                break;
            case "3":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_git);
                break;
            case "4":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_hatena);
                break;
            case "5":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_mizuho);
                break;
            case "6":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_sbi);
                break;
            case "7":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_yahoo);
                break;
            case "8":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_wakuwaku);
                break;
            case "9":
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_warai);
                break;
            default:
                bmp = BitmapFactory.decodeResource(r, R.mipmap.s_sonota);
        }
        return bmp;
    }

    /**
     * mapの一致判定
     * @param beforeMap 変更前
     * @param afterMap 変更後
     * @return boolean
     */
    public static final boolean isMap(Map<String, String> beforeMap ,Map<String, String> afterMap){
        List<String> list = new ArrayList<>();
        for(Map.Entry<String,String> entry : beforeMap.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            if (afterMap.get(key)!= null && !afterMap.get(key).equals(val)){
                list.add(key);
                return false;
            }
        }
        return true;
    }
    /**
     * mapの差分判定
     * @param beforeMap 変更前
     * @param afterMap 変更後
     * @return List<Map<String,String>>
     */
    public static final List<Map<String,String>> diffMap(Map<String, String> beforeMap ,Map<String, String> afterMap){
        List<Map<String,String>> list = new ArrayList<>();
        for(Map.Entry<String,String> entry : beforeMap.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            if (afterMap.get(key)!= null && !afterMap.get(key).equals(val)){
                Map<String, String> map = new LinkedHashMap<String, String>();
                map.put(key,afterMap.get(key));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * booleanの切り替え
     * @param checkItem 検証
     * @return boolean
     */
    public final boolean changeAble(boolean checkItem){
        if(checkItem){
            return false;
        }
        return true;
    }

    /**
     * 入力制御の切り替えを行う
     * NOT_FOCUSABL = 0
     * FOCUSABLE = 1
     * FOCUSABLE_AUTO = 16
     * https://developer.android.com/reference/android/view/View
     * @param checkFocusable 検証
     * @return boolean
     */
    public final boolean changeFocusable(int checkFocusable){
        if(checkFocusable==0){
            return true;
        }
        if(checkFocusable==1){
            return false;
        }
        return false;
    }
}
