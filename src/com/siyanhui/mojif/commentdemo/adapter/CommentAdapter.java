package com.siyanhui.mojif.commentdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.melink.baseframe.utils.StringUtils;
import com.siyanhui.mojif.commentdemo.R;
import com.siyanhui.mojif.commentdemo.bean.CommentMessage;
import com.melink.bqmmsdk.widget.BQMMMessageText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
	private Context mContext;
	private List<CommentMessage> datas = null;

	public CommentAdapter(Context cxt, List<CommentMessage> datas) {
		this.mContext = cxt;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		final ViewHolder holder;
		final CommentMessage itemMsg = datas.get(position);
		if (v == null) {
			holder = new ViewHolder();
			v = View.inflate(mContext, R.layout.bqmm_comment_item, null);
			holder.userName = (TextView) v.findViewById(R.id.item_username);
			holder.date = (TextView) v.findViewById(R.id.item_date);
			holder.time = (TextView) v.findViewById(R.id.item_time);
			holder.content = (BQMMMessageText) v.findViewById(R.id.item_content);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.date.setText(StringUtils
				.getDateTime(itemMsg.getTime(),"yyyy-MM-dd " + "HH:mm:ss"));
		holder.userName.setText(itemMsg.getUserName());
		JSONArray mixedJSON = null;
		try {
			mixedJSON = new JSONArray(itemMsg.getMixedCommentMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		holder.content.setEmojiSize(60);
		holder.content.setStickerSize(140);
		/**
		 * BQMMMessageText.EMOJITYPE 混排模式
		 */
		if(itemMsg.isMixedMessage()){
			holder.content.showMessage(itemMsg.getMixedCommentMessage(), BQMMMessageText.EMOJITYPE, mixedJSON);
		}else{
			holder.content.setText(itemMsg.getMessageString());
		}
		
		return v;
	}
	
	class ViewHolder {
		TextView userName;
		TextView date;
		TextView time;
		BQMMMessageText content;
	}
}
