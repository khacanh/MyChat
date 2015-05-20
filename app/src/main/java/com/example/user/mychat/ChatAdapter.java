package com.example.user.mychat;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.user.mychat.database.DBHelper;
import com.example.user.mychat.model.Message;

public class ChatAdapter extends SimpleCursorAdapter{

    private static final int[] RESOURCE= {R.layout.message_item_left, R.layout.message_item_right};

    private Activity mActivity;
    private String mOwnerId;
    private Cursor mCursor;

    public ChatAdapter(Activity activity, int layout, Cursor c, String[] from, int[] to, String ownerId){
        super(activity, layout,c, from, to );
        mActivity = activity;
        mOwnerId = ownerId;
        mCursor = c;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private int getLayoutResource(String authorId){
        if(mOwnerId.equals(authorId)){
            //right
            return 1;
        }else{
            //left
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String authorId = "";
        if (mCursor.getCount() > 0) {
            if (mCursor.getPosition() == -1) {
                mCursor.moveToFirst();
            }
            authorId = mCursor.getString(mCursor.getColumnIndex(DBHelper.CHAT_COLUMN_AUTHOR));
        }
        return getLayoutResource(authorId);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Message message = new Message().update(mCursor);
        int resource = getLayoutResource(message.getAuthor());

        return generateData(resource, viewGroup, message);
    }

    private View generateData(int index, ViewGroup viewGroup, Message message){
        View view = mActivity.getLayoutInflater().inflate(RESOURCE[index], viewGroup, false);


        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mMessageText = (TextView) view.findViewById(ViewHolder.MESSAGE_TEXT_ID);
        viewHolder.mAuthor = (TextView) view.findViewById(ViewHolder.AUTHOR_TEXT_ID);

        viewHolder.mMessageText.setText(mCursor.getString(mCursor.getColumnIndex(DBHelper.CHAT_COLUMN_MESSAGE)));
        viewHolder.mAuthor.setText(message.getAuthor());
        viewHolder.mResourceId = index;

        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(viewHolder != null) {
            Message message = new Message().update(cursor);
                //TODO: need to fix getting wrong resource because of cursor adapter.
//            int index = getLayoutResource(viewHolder.mAuthor.getText().toString());
//            if(index != viewHolder.mResourceId){
//                generateData(index, null,message);
//            }else{
                viewHolder.mMessageText.setText(message.getMessage());
                viewHolder.mAuthor.setText(message.getAuthor());
//            }
        }
    }

    public class ViewHolder{
        public static final int MESSAGE_TEXT_ID = R.id.messages_item_text;
        public static final int AUTHOR_TEXT_ID = R.id.messages_item_author;

        public TextView mMessageText;
        public TextView mAuthor;
        public int mResourceId;
    }
}
