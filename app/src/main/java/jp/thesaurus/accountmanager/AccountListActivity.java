package jp.thesaurus.accountmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.thesaurus.accountmanager.adapter.ListAdapter;
import jp.thesaurus.accountmanager.presenter.AccountListActivityPresenter;
import jp.thesaurus.accountmanager.utils.DBHelper;

public class AccountListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static SQLiteDatabase db;
    private List<Map<String, String>> listData = new ArrayList<>();

    static ArrayAdapter<String> adapter;
    private AccountListActivityPresenter listPresenter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);


        DBHelper helper = new DBHelper(getApplicationContext());
        db = helper.getWritableDatabase();
        listPresenter = new AccountListActivityPresenter();
        listData = listPresenter.getEntryData(this);

        listView = (ListView)findViewById(R.id.listview);

        BaseAdapter adapter = new ListAdapter(this.getApplicationContext(),
                R.layout.row, getResources(),listData);

        // ListViewにadapterをセット
        listView.setAdapter(adapter);
        // クリックリスナーをセット
        listView.setOnItemClickListener(listViewClickListener);
        //リスト項目が選択された時のイベントを追加

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        findViewById(R.id.floating_action_button).setOnClickListener(fabClickListener);

    }
    @Override
    public void onRestart(){
        //super.onRestart();
        Log.v("LifeCycle", "onRestart");
        reload();
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.d("","--------------------a---------------------");
        //Intent intent = new Intent(
        //        this.getApplicationContext(), AccountDetailActivity.class);

        // clickされたpositionのtextとphotoのID
        //String selectedText = names.get(position);
        //Bitmap selectedPhoto = icons.get(position);
        // インテントにセット
        //intent.putExtra("Text", selectedText);
        //intent.putExtra("Photo", selectedPhoto);

        // SubActivityへ遷移
        //startActivity(intent);

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){ // もし押されたのがバックキーなら
            this.moveTaskToBack(true); // 終了
            finish();
        }
        return false;
    }*/
    /**
     * 登録fabボタン
     */
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), AccountEntryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // clickされたpositionのtextとphotoのID
            // SubActivityへ遷移
            startActivity(intent);
        }
    };
    /**
     * 一覧選択ボタン
     */
    AdapterView.OnItemClickListener listViewClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(
                    view.getContext(), AccountEditActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // 選択されたビューを取得
            ConstraintLayout linear = (ConstraintLayout)view;
            // LinearLayoutの中からidで目的のウィジェットを取得する
            TextView uidView = (TextView)linear.findViewById(R.id.row_uid_hidden);

            String msg = "ItemClick : "+uidView.getText().toString();

            // インテントにセット
            intent.putExtra("uid", uidView.getText().toString());
            //intent.putExtra("Photo", selectedPhoto);
            final ListView lv = findViewById(R.id.listview);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    lv.setEnabled(false);
                }
            }, 10);

            // SubActivityへ遷移
            startActivity(intent);

            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("LifeCycle", "onDestroy");

        listData = null;
        adapter = null;
        listPresenter = null;
        listView = null;
        db = null;
    }
}
