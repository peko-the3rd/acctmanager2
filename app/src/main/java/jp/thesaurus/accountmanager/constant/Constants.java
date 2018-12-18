package jp.thesaurus.accountmanager.constant;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    private Constants() {
    }
    public static final Map<String, Map<String, String>> SERVICE_MAP;
    static {
        Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
        map.put("1", new LinkedHashMap<String, String>() {
            {
                put("Amazon", "amazon");
            }
        });
        map.put("2", new LinkedHashMap<String, String>() {
            {
                put("Git", "git");
            }
        });
        map.put("3", new LinkedHashMap<String, String>() {
            {
                put("Google", "google");
            }
        });
        map.put("4", new LinkedHashMap<String, String>() {
            {
                put("はてな", "hatena");
            }
        });
        map.put("5", new LinkedHashMap<String, String>() {
            {
                put("みずほ", "mizuho");
            }
        });
        map.put("6", new LinkedHashMap<String, String>() {
            {
                put("SBI", "sbi");
            }
        });
        map.put("7", new LinkedHashMap<String, String>() {
            {
                put("Yahoo", "yahoo");
            }
        });
        map.put("8", new LinkedHashMap<String, String>() {
            {
                put("X−flag", "wakuwaku");
            }
        });
        map.put("9",  new LinkedHashMap<String, String>() {
            {
                put("開発関連", "warai");
            }
        });
        map.put("10", new LinkedHashMap<String, String>() {
            {
                put("その他", "sonota");
            }
        });
        SERVICE_MAP = Collections.unmodifiableMap(map);
    }
    public static class Character {
        public static final String SLASH = "/";
        public static final String BLANK = " ";
        public static final String CRLF = "¥r¥n";
        public static final String LF = "¥n";
    }
}