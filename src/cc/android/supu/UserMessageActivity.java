package cc.android.supu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.MessageListAdapter;
import cc.android.supu.bean.UserMessageBean;
import cc.android.supu.handler.UserMessageHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 站内消息
 * 
 * @author zsx
 * 
 */
public class UserMessageActivity extends BaseActivity {
	private Context context = UserMessageActivity.this;

	/** 未读 已读按钮 */
	private TextView alreadyRead, noRead;
	/** 消息列表 */
	private ListView messageList;

	/** 键值对 */
	private TreeMap<String, String> params;
	/** 处理得到消息的请求 */
	private UserMessageHandler userMessageHandler;
	/** 当前页 */
	private int Page = 1, pagecount = 1;
	/** 是否加载 显示 */
	private boolean isLoading = false;
	/** 当前页显示的条数 */
	private int PageSize = 10;
	/**适配器*/	
	private MessageListAdapter messageListAdapter;
	/** 是否已读 0-未读、1-已读 */
	private int State = 0;
	/** 未读列表0 */
	private List<UserMessageBean> UserMessageList;
	/**空布局*/	
	private RelativeLayout message_empty;
	/**加载布局*/	
	private View bottomView;

	@Override
	protected void initPage() {

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		bottomView = LayoutInflater.from(this).inflate(R.layout.list_downing,
				null, false);
		bottomView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				(int) (45 * metrics.density)));

		
		UserMessageList = new ArrayList<UserMessageBean>();
		params = new TreeMap<String, String>();
		alreadyRead = (TextView) findViewById(R.id.alreadyRead);
		noRead = (TextView) findViewById(R.id.noRead);
		message_empty = (RelativeLayout) findViewById(R.id.message_empty);

		messageList = (ListView) findViewById(R.id.messageList);
		messageList.setOnScrollListener(scrollListener);
		messageList.addFooterView(bottomView);
		bottomView.setTag(true);

		messageListAdapter = new MessageListAdapter(context, UserMessageList);
		messageList.setAdapter(messageListAdapter);
		ItemClickListener();
		State = 0;
		if (State == 0) {
			changeTextB();
		} else {
			changeTextA();
		}
		alreadyRead.setOnClickListener(onclickListener);
		noRead.setOnClickListener(onclickListener);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		resetPage();
		requestServer(requestMessageList,true);
	}
	/**List滚动到底的效应事件*/	
	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem == 0)
				return;
			if (!isLoading)
				return;
			if (Page > pagecount)
				return;
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				isLoading = false;
				Page++;
				requestServer(requestMessageList);
			}
		}
	};

	/**
	 * 响应事件
	 */
	private void ItemClickListener() {
		messageList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(context, MessageInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("MessageInfo",
						UserMessageList.get(position).messageContent);
				bundle.putString("MID", UserMessageList.get(position).MID);
				bundle.putString("RID", UserMessageList.get(position).RID);
				bundle.putInt("State", State);

				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	/**
	 * 
	 * 按钮的响应事件
	 */
	private Button.OnClickListener onclickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.alreadyRead:
				changeTextA();
				if (State == 1)
					return;
				State = 1;
				resetPage();
				requestServer(requestMessageList,true);
				break;
			case R.id.noRead:
				changeTextB();
				if (State == 0)
					return;
				State = 0;
				resetPage();
				requestServer(requestMessageList,true);
				break;

			default:
				break;
			}
		}

	};
	/**初始化页面数据*/	
	private void resetPage() {
		UserMessageList.clear();
		pagecount = 1;
		Page = 1;
		boolean isAdd = (Boolean) bottomView.getTag();
		if (!isAdd) {
			bottomView.setTag(true);
			messageList.addFooterView(bottomView);
			messageList.setAdapter(messageListAdapter);
		}
	}

	/**
	 * 按钮的请求
	 */
	private PageRequest requestMessageList = new PageRequest() {
		@Override
		public void requestServer() {
			params.put("Page", Page + "");
			params.put("PageSize", PageSize + "");
			params.put("State", State + "");

			userMessageHandler = new UserMessageHandler();
			Tools.requestToParse(UserMessageActivity.this,
					ConstantUrl.GETUSERMESSAGE, "GetUserMessage", params,
					userMessageHandler, false);
			isLoading = true;
			if (Tools.responseValue == 1) {
				if (userMessageHandler.result_code == 0) {
					if (userMessageHandler.messageList != null) {
						UserMessageList.addAll(userMessageHandler.messageList);
					}
					pagecount = (userMessageHandler.recordCount + PageSize - 1)
							/ PageSize;

					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
		
			if (pagecount <= Page) {
				messageList.removeFooterView(bottomView);
				bottomView.setTag(false);
			}
			UnHandlerSuccess();

			break;
		case STATE_FAILURE:
			Toast.makeText(UserMessageActivity.this, "服务器异常！",
					Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(UserMessageActivity.this, "网络异常，请检查网络",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	};

	/**
	 * 读消息功能
	 */
	private void UnHandlerSuccess() {

		if (UserMessageList != null && UserMessageList.size() > 0) {
			messageList.setVisibility(View.VISIBLE);
			message_empty.setVisibility(View.GONE);
			messageListAdapter.notifyDataSetChanged();
		} else {
			messageList.setVisibility(View.GONE);
			message_empty.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 改变按钮的背景 和字体颜色
	 */
	private void changeTextA() {
		alreadyRead.setBackgroundResource(R.drawable.read_a);
		alreadyRead.setTextColor(Color.WHITE);
		noRead.setBackgroundResource(R.drawable.read_b);
		noRead.setTextColor(Color.BLACK);
	}

	/**
	 * 改变按钮的背景 和字体颜色
	 */
	private void changeTextB() {
		noRead.setBackgroundResource(R.drawable.read_a);
		noRead.setTextColor(Color.WHITE);
		alreadyRead.setBackgroundResource(R.drawable.read_b);
		alreadyRead.setTextColor(Color.BLACK);
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "站内消息";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.user_message;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}
}
