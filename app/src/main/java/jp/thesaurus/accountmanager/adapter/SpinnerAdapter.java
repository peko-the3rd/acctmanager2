package jp.thesaurus.accountmanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.thesaurus.accountmanager.R;

public class SpinnerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutID;
    String[] names;
    Bitmap[] imageIDs;
    String[] index;

    static class ViewHolder {
        ImageView imageView;
        TextView userIdView;
        TextView indexView;
    }

    public SpinnerAdapter(Context context,
                int itemLayoutId,
                Map<String, Map<String, Bitmap>> spinnerMap){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        int i = 0;
        names = new String[spinnerMap.size()];
        imageIDs = new Bitmap[spinnerMap.size()];
        index = new String[spinnerMap.size()];
        //Resources res = context.getResources();
        for (Map.Entry<String, Map<String, Bitmap>> entry : spinnerMap.entrySet()) {
            index[i] = entry.getKey();
            Map<String, Bitmap> map = new LinkedHashMap<>();
            map = entry.getValue();

            for (String key : map.keySet()) {
                names[i] = key;
            }
            for (Bitmap val : map.values()) {
                imageIDs[i] = val;//res.getIdentifier(val,
                        //"drawable", context.getPackageName());
            }
            i++;
        }
    }
    @Override
    public View getDropDownView( int position, View convertView, ViewGroup parent ) {
        return createView(position,convertView,parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position,convertView,parent);
    }

    /**
     * spinner作成関数まとめ
     * */
    private View createView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.s_image_view);
            holder.userIdView = convertView.findViewById(R.id.s_text_view);
            holder.indexView = convertView.findViewById(R.id.s_index);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageBitmap(imageIDs[position]);
        holder.userIdView.setText(names[position]);
        holder.userIdView.setTextSize(18);

        holder.indexView.setText(index[position]);

        return convertView;
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
