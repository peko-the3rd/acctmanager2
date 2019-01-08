package jp.thesaurus.accountmanager.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.thesaurus.accountmanager.R;
import jp.thesaurus.accountmanager.utils.ViewUtil;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private List displayList;

    private List<String> displayUidList = new ArrayList<>();
    private List<String> displayNameList = new ArrayList<>();
    private List<Bitmap> displayImageList = new ArrayList<>();
    private List<String> displaySubServiceNameList = new ArrayList<>();

    static class ViewHolder {
        TextView uid;
        TextView name;
        ImageView image;
        TextView subServiceName;
    }

    public ListAdapter(Context context, int itemLayoutId, Resources res, List<Map<String, String>> listData ){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        displayList = listData;

        for (Map<String, String> map : listData){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getKey().equals("uid")){
                    displayUidList.add(entry.getValue());
                }
                if(entry.getKey().equals("user_id")){
                    displayNameList.add(entry.getValue());
                }
                if(entry.getKey().equals("service_index")){
                    displayImageList.add(ViewUtil.getServiceBitmap(entry.getValue(),res));
                }
                if(entry.getKey().equals("sub_service_name")){
                    if(entry.getValue().isEmpty() || entry.getValue() == null){
                        displaySubServiceNameList.add("");
                    }else{
                        displaySubServiceNameList.add(entry.getValue());
                    }
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.uid = convertView.findViewById(R.id.row_uid_hidden);
            holder.name = convertView.findViewById(R.id.row_mail_text);
            holder.image = convertView.findViewById(R.id.row_img_item);
            holder.subServiceName = convertView.findViewById(R.id.row_sub_service_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String uid = displayUidList.get(position).toString();
        holder.uid.setText(uid);
        String name = displayNameList.get(position).toString();
        holder.name.setText(name);
        holder.image.setImageBitmap(displayImageList.get(position));

        String subServiceName = displaySubServiceNameList.get(position).toString();
        holder.subServiceName.setText(subServiceName);


        return convertView;
    }

    @Override
    public int getCount() {
        return displayList.size();
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
