package jp.thesaurus.accountmanager.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ViewUtilTest {

    private ViewUtil viewUti;

    @Before
    public void setUp() throws Exception {
        viewUti = new ViewUtil();
    }

    @Test
    public void isMap() {
        Map<String, String> listA = new HashMap<String, String>();
        listA.put("user_id","apple");
        listA.put("password","2");
        listA.put("remarks","3");
        // MAP-B
        Map<String, String> listB = new HashMap<String, String>();
        listB.put("user_id","apple");
        listB.put("password","2");
        listB.put("remarks","3");
        assertEquals(viewUti.isMap(listA, listB),true);

        Map<String, String> listC = new HashMap<String, String>();
    }
    @Test
    public void isMapFalse() {
        Map<String, String> listA = new HashMap<String, String>();
        listA.put("user_id","apple");
        listA.put("password","2");
        listA.put("remarks","3");

        Map<String, String> listC = new HashMap<String, String>();
        listC.put("user_id","apple");
        listC.put("password","2");
        listC.put("remarks","4");
        assertEquals(viewUti.isMap(listA, listC),false);
    }

    @Test
    public void changeAbleTrueToFalse() {
        assertEquals(viewUti.changeAble(true),false);
    }

    @Test
    public void changeAbleFalseToTrue() {
        assertEquals(viewUti.changeAble(false),true);
    }

    @Test
    public void isFocusable0ToFalse() {
        assertEquals(viewUti.changeFocusable(0),true);
    }

    @Test
    public void isFocusable1ToTrue() {
        assertEquals(viewUti.changeFocusable(1),false);
    }
}