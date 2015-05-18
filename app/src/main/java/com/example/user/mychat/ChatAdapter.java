package com.example.user.mychat;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter{

    private static final int RESOURCE = R.layout.message_item;

    private List<String> mListMess;
    private Activity mActivity;

    public ChatAdapter(Activity activity, List<String> listMess){
        mListMess = listMess;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mListMess.size();
    }

    @Override
    public Object getItem(int position) {
        return mListMess.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(RESOURCE, null, false);
            viewHolder = new ViewHolder();
            viewHolder.mMessageText = (TextView) convertView.findViewById(ViewHolder.MESSAGE_TEXT_ID);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String text = mListMess.get(position);
        viewHolder.mMessageText.setText(text);

        return convertView;
    }

    public class ViewHolder{
        public static final int MESSAGE_TEXT_ID = R.id.messages_item_text;
        public TextView mMessageText;
    }
}
