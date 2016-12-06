package com.siyanhui.mojif.commentdemo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.siyanhui.mojif.commentdemo.adapter.CommentAdapter;
import com.siyanhui.mojif.commentdemo.bean.CommentMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentListActivity extends Activity {

	private LinearLayout mCommentButton;
	private TextView mMainButton;
	private ListView mCommentList;
	private List<CommentMessage> mData = new ArrayList<CommentMessage>();
	private CommentAdapter mAdapter;
	private int msgId = 0;
	public final static int REQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.siyanhui.mojif.commentdemo.R.layout.bqmm_comment_list);
		initView();
	}
	
	@SuppressLint("InflateParams")
	private void initView() {
		mCommentButton = (LinearLayout) findViewById(com.siyanhui.mojif.commentdemo.R.id.listlayout_comment);
		mMainButton = (TextView) findViewById(com.siyanhui.mojif.commentdemo.R.id.titleLayout_main);
		mCommentList = (ListView) findViewById(com.siyanhui.mojif.commentdemo.R.id.listlayout_list);
		View emptyView = findViewById(com.siyanhui.mojif.commentdemo.R.id.listlayout_empty);
		mCommentList.setEmptyView(emptyView);
		init_Msgdata();
		mAdapter = new CommentAdapter(CommentListActivity.this, mData);
		mCommentList.setAdapter(mAdapter);
		//跳转至编辑页面
		mCommentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CommentListActivity.this,CommentEditActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}
	
	
	/**
	 * 
	 * @Title: init_data 
	 * @Description: TODO
	 * @return: void
	 */
	private void init_Msgdata() {
		String[] msgDateArr = new String[]{
			"[[\"jcbgcy\",\"1\"]]",
			"[[\"xdcrrczj\",\"2\"],[\"xdcrrcpf\",\"2\"],[\"xdcrrcbkx\",\"2\"],[\"xdcrrclql\",\"2\"],[\"abc\",\"0\"]]",
			"[[\"jcbgcy\",\"1\"],[\"jcbgsq\",\"1\"],[\"jcbgjy\",\"1\"],[\"jcbgts\",\"1\"],[\"jcbgc\",\"1\"],[\"hjzhj\",\"2\"],[\"hjzmmd\",\"2\"]]",
			"[[\"你好，微笑\",\"0\"]]",
			"[[\"jcmbdk\",\"1\"],[\"jcmbgg\",\"1\"],[\"jcmbfn\",\"1\"]]",
			"[[\"qjqrbhn\",\"2\"],[\"qjqran\",\"2\"]]"
		};
		for (int i = 0; i < 1; i++) {
			CommentMessage commentMsg = new CommentMessage(String.valueOf(msgId++),"表情mm",true,"",msgDateArr[i],new Date());
			mData.add(commentMsg);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == REQUEST_CODE ){
			String mixedCommentMessage = data.getStringExtra("mixedCommentMessageData");
			String messageString = data.getStringExtra("mixedCommentMessageString");
			boolean isMixedMessage = data.getBooleanExtra("isMixedMessage", false);
			CommentMessage commentMsg = new CommentMessage(String.valueOf(msgId++),"表情mm",isMixedMessage,messageString,mixedCommentMessage,new Date());
			mData.add(commentMsg);
			refreshListView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 刷新页面
	 */
	private void refreshListView() {
		if(mData == null || mData.size() <= 0){
			return;
		}
		if(mAdapter == null){
			mAdapter = new CommentAdapter(CommentListActivity.this, mData);
			mCommentList.setAdapter(mAdapter);
		}else{
			mAdapter.notifyDataSetChanged();
		}
		mCommentList.setSelection(mCommentList.getAdapter().getCount() -1);
	}
}
