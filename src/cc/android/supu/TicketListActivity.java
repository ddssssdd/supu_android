package cc.android.supu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.TicketListAdapter;
import cc.android.supu.bean.TicketBean;
import cc.android.supu.handler.CreateAddressHandler;
import cc.android.supu.handler.DefaultJSONData;
import cc.android.supu.handler.TicketHandler;
import cc.android.supu.handler.TicketInfoHandler;
import cc.android.supu.handler.TicketListHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;

/**
 * 优惠券列表页面
 * 
 * @author sheng
 * 
 */
public class TicketListActivity extends BaseActivity {

	/** 请求列表的标志 */
	private static final int LIST = 1;

	/** 请求添加优惠券的标志 */
	private static final int ADD = 2;

	/** 请求优惠券信息 */
	private static final int INFO = 4;

	/** 请求标识码 */
	private int whichResponse;

	/** 编辑框 */
	private EditText searchEt;

	/** 添加按钮 */
	private TextView addTicketBtn;

	/** 已使用安钮 */
	private TextView usedBtn;

	/** 未使用按钮 */
	private TextView unUsedBtn;

	/** 列表listview */
	private ListView listview;

	/** 当列表的内容为空时显示的text文本 */
	private TextView textTv;

	/** listview加载时显示的view */
	private LinearLayout loadview;

	/** 优惠券列表解析器 */
	private TicketListHandler jsonData;

	/** 优惠券信息解析器 */
	private TicketHandler ticketHandler;

	/** 绑定优惠券解析器 */
	private DefaultJSONData bindTicketHandler;

	/** 获取优惠券可用信息解析器 */
	private TicketInfoHandler ticketInfoHandler;

	/** 添加优惠券的信息解析器 */
	private CreateAddressHandler CreateAddressHandler;

	/** 列表适配器 */
	private TicketListAdapter adapter;

	/** 已使用的列表 */
	private ArrayList<TicketBean> usedList;

	/** 未使用的列表 */
	private ArrayList<TicketBean> unUsedList;

	/** 优惠券bean */
	private TicketBean ticketBean;

	/** 当前页 */
	private int page = 1;

	/** 每页显示数 */
	private int pageSize = 10;

	/** 是否已使用，0-未使用，1-已使用 */
	private int isUsed = 0;

	/** 优惠券编号 */
	private String ticketNo = "";

	/** 记录滚动状态 */
	private int mScrollState;

	/** 标识自哪个页面跳转而来 */
	private String where = "";
	/** 是否正在加载更多 */
	private boolean isLoadingMore;

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "我的优惠券";
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		if (getIntent() != null) {
			where = getIntent().getStringExtra("shopCart");
		}
		searchEt = (EditText) findViewById(R.id.ticketlist_searchEt);

		addTicketBtn = (TextView) findViewById(R.id.ticketlist_addticketBtn);
		addTicketBtn.setOnClickListener(this);

		usedBtn = (TextView) findViewById(R.id.ticketlist_usedBtn);
		usedBtn.setOnClickListener(this);

		unUsedBtn = (TextView) findViewById(R.id.ticketlist_unusedBtn);
		unUsedBtn.setOnClickListener(this);

		listview = (ListView) findViewById(R.id.ticketlist_listview);
		textTv = (TextView) findViewById(R.id.ticketlist_textview);

		loadview = (LinearLayout) findViewById(R.id.ticketlist_bottomLayout);
		if ("shopCart".equals(where)) {
			addTicketBtn.setText("使用优惠券");
		} else {
			addTicketBtn.setText("添加优惠券");
		}

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				TicketBean ticketBean = (isUsed == 1 ? usedList.get(position) : unUsedList.get(position));
				if (!"".equals(where) && isUsed == 0) {// 若是自结算中心跳到当前页面，则执行以下操作
					ticketNo = ticketBean.ticketNo;
					requestServer(requestTicketInfo, true);// 查看优惠券是否可用
				} else {// 跳转到优惠券详情
					Intent intent = new Intent(TicketListActivity.this, TicketInfoActivity.class);
					intent.putExtra("ticketNo", ticketBean.ticketNo);
					startActivity(intent);
				}
			}
		});

		listview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mScrollState = scrollState;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// 滚到底部时加载更多
				if (firstVisibleItem + visibleItemCount == totalItemCount && mScrollState != SCROLL_STATE_IDLE) {
					loadMore();
				}
			}
		});
	}

	@Override
	protected void onSubActivityClick(View view) {
		// TODO Auto-generated method stub
		super.onSubActivityClick(view);
		switch (view.getId()) {
		// 添加优惠券
		case R.id.ticketlist_addticketBtn:
			addTicket();
			break;
		// 已使用
		case R.id.ticketlist_usedBtn:
			changeContent(usedBtn);
			break;
		// 未使用
		case R.id.ticketlist_unusedBtn:
			changeContent(unUsedBtn);
			break;
		}
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.ticketlist;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	/**
	 * 请求优惠券列表
	 */
	private PageRequest requestTicketList = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("Page", page + "");
			params.put("pageSize", pageSize + "");
			params.put("IsUsed", isUsed + "");

			jsonData = new TicketListHandler();
			Tools.requestToParse(TicketListActivity.this, ConstantUrl.GETTICKETLIST, "GetTicketList", params, jsonData,
					false);
			System.out.println("Tools.responseValue=" + Tools.responseValue);
			if (Tools.responseValue == 1) {
				if (jsonData.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS, LIST));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE, LIST));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR, LIST));
			}
		}
	};

	/**
	 * 请求添加优惠券
	 */
	private PageRequest requestAddTicket = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("TicketNo", ticketNo);

			CreateAddressHandler = new CreateAddressHandler();
			Tools.requestToParse(TicketListActivity.this, ConstantUrl.BINDTICKET, "BindTicket", params,
					CreateAddressHandler, false);
			if (Tools.responseValue == 1) {
				if (CreateAddressHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS, ADD));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE, ADD));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR, ADD));
			}
		}
	};

	/**
	 * 请求优惠券信息,查看优惠券是否可用
	 */
	private PageRequest requestTicketInfo = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("TicketNo", ticketNo);

			ticketInfoHandler = new TicketInfoHandler();
			Tools.requestToParse(TicketListActivity.this, ConstantUrl.GETTICKETINFO, "GetTicketInfo", params,
					ticketInfoHandler, false);
			if (Tools.responseValue == 1) {
				if (ticketInfoHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS, INFO));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE, INFO));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR, INFO));
			}
		}
	};

	protected void dealwithMessage(android.os.Message msg) {
		whichResponse = (Integer) msg.obj;
		switch (msg.what) {
		case STATE_SUCCESS:
			loadMoreSucceed();
			if (whichResponse == LIST) {// 优惠券列表
				if (jsonData != null && jsonData.ticketsList != null) {
					changeList(jsonData.ticketsList);
					showContent();
				}
			} else if (whichResponse == ADD) {// 添加优惠券
				searchEt.setText("");
				Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
				isUsed = 1;
				changeContent(unUsedBtn);
			} else if (whichResponse == INFO) {// 检查优惠券是否可用
				if (ticketInfoHandler.ticketBean != null) {
					if ("可使用此优惠券".equals(ticketInfoHandler.validateResult)) {// 优惠券可用就返回结算中心
						Intent intent = new Intent();
						intent.putExtra("ticketBean", ticketInfoHandler.ticketBean);
						setResult(40, intent);
						finish();
					} else {// 不可用就提示不可用的信息
						Toast.makeText(this, ticketInfoHandler.validateResult, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case STATE_FAILURE:
			loadMoreFailed();
			if (whichResponse == LIST) {
				if (jsonData.result_code == 1000) {// 需要登录
					showDialog();
				} else {
					Toast.makeText(this, "服务器异常", Toast.LENGTH_SHORT).show();
				}
			} else if (whichResponse == ADD) {
				if (CreateAddressHandler.result_code == 1000) {
					showDialog();
				} else if (CreateAddressHandler.result_code == 1) {
					Toast.makeText(this, "优惠券编号不存在！", Toast.LENGTH_SHORT).show();
				} else if (CreateAddressHandler.result_code == 2) {
					Toast.makeText(this, "优惠券编号错误！", Toast.LENGTH_SHORT).show();
				} else if (CreateAddressHandler.result_code == 3) {
					Toast.makeText(this, "优惠券不能重复添加！", Toast.LENGTH_SHORT).show();
				} else if(CreateAddressHandler.result_code == 5){
					Toast.makeText(this, "统一码优惠券不能绑定！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "服务器异常", Toast.LENGTH_SHORT).show();
				}
			} else if (whichResponse == INFO) {
				if (ticketInfoHandler.result_code == 1000) {
					showDialog();
				} else if (ticketInfoHandler.result_code == 1) {
					Toast.makeText(this, "优惠券编号为空！", Toast.LENGTH_SHORT).show();
				} else if (ticketInfoHandler.result_code == 2) {
					Toast.makeText(this, "优惠券编号错误！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case STATE_ERROR:
			loadMoreFailed();
			Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show();
			break;
		}
		loadview.setVisibility(View.GONE);

	}

	/**
	 * 根据当前已加载的页码和isUsed改变优惠券列表
	 * 
	 * @param list
	 */
	private void changeList(ArrayList<TicketBean> list) {
		if (isUsed == 0) {
			if (page == 1) {
				unUsedList = list;
			} else {
				unUsedList.addAll(list);
			}
		} else {
			if (page == 1) {
				usedList = list;
			} else {
				usedList.addAll(list);
			}
		}
	}

	/**
	 * 显示内容
	 */
	private void showContent() {
		if (isUsed == 1) {
			changeVisible(usedList);
			updateListViewData(usedList);
			textTv.setText(R.string.ticketlist_textview);
		} else if (isUsed == 0) {
			changeVisible(unUsedList);
			updateListViewData(unUsedList);
			textTv.setText(R.string.ticketlist_textview_unused);
		}
	}

	/**
	 * 根据给定的List<TicketBean>是否是null或大小为0来改变view的可见性
	 * 
	 * @param list
	 */
	private void changeVisible(ArrayList<TicketBean> list) {
		if (list != null && list.size() > 0) {
			listview.setVisibility(View.VISIBLE);
			textTv.setVisibility(View.GONE);
		} else {
			listview.setVisibility(View.GONE);
			textTv.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 更新listview中的数据
	 */
	private void updateListViewData(ArrayList<TicketBean> list) {
		if (adapter == null) {
			adapter = new TicketListAdapter(this, list);
			listview.setAdapter(adapter);
		} else {
			adapter.setData(list);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 提示登录对话框
	 */
	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示对话框").setMessage("您未登录，请先登录！").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// 跳到登录页面
				Intent intent = new Intent(TicketListActivity.this, LoginActivity.class);
				intent.putExtra("shopcart", "shopcart");
				startActivity(intent);
			}
		}).setNegativeButton("取消", null);
		dialog.create().show();
	}

	/**
	 * 点击按钮时改变显示内容
	 * 
	 * @param useBtn
	 *            被点击的按钮
	 */
	private void changeContent(TextView useBtn) {
		if (useBtn.getId() == usedBtn.getId() && isUsed == 0) {// 当前显示的是未使用的
			// 转到已使用界面
			usedBtn.setBackgroundResource(R.drawable.partion_btn_normal);
			usedBtn.setTextColor(Color.WHITE);
			unUsedBtn.setBackgroundResource(R.drawable.partion_btn_toching);
			unUsedBtn.setTextColor(Color.BLACK);
			isUsed = 1;
			page = 1;
			listview.setVisibility(View.INVISIBLE);
			requestServer(requestTicketList);
		} else if (useBtn.getId() == unUsedBtn.getId() && isUsed == 1) {// 当前显示的时已使用的
			// 转到未使用界面
			usedBtn.setBackgroundResource(R.drawable.partion_btn_toching);
			usedBtn.setTextColor(Color.BLACK);
			unUsedBtn.setBackgroundResource(R.drawable.partion_btn_normal);
			unUsedBtn.setTextColor(Color.WHITE);
			isUsed = 0;
			page = 1;
			listview.setVisibility(View.INVISIBLE);
			requestServer(requestTicketList);
		}

	}

	/**
	 * 添加优惠券
	 */
	private void addTicket() {
		ticketNo = searchEt.getText().toString().trim();
		if (ticketNo.equals("")) {
			Toast.makeText(this, "请先输入有效的优惠券编号！", Toast.LENGTH_SHORT).show();
			searchEt.requestFocus();
			return;
		}
		if("shopCart".equals(where)){
			requestServer(requestTicketInfo);
		}else{
			requestServer(requestAddTicket);
		}
	}

	/**
	 * 加载更多
	 */
	private void loadMore() {
		List<TicketBean> list = (isUsed == 1) ? usedList : unUsedList;
		if (list.size() < jsonData.recordCount) {
			int maxPage = jsonData.recordCount / pageSize;
			maxPage = (jsonData.recordCount % pageSize) > 0 ? maxPage + 1 : maxPage;
			if (page < maxPage) {
				isLoadingMore = true;
				page++;
				loadview.setVisibility(View.VISIBLE);
				requestServer(requestTicketList, false);
			}
		}
	}

	/**
	 * 分页加载失败
	 */
	private void loadMoreFailed() {
		if (isLoadingMore) {// 分页加载失败将page减1
			isLoadingMore = !isLoadingMore;
			page--;
		}
	}

	/**
	 * 分页加载成功
	 */
	private void loadMoreSucceed() {
		isLoadingMore = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (!UserInfoTools.isLogin(this)) {
			showDialog();
		} else {
			requestServer(requestTicketList);
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (this.getCurrentFocus() != null) {// 隐藏软键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this
					.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		super.onPause();
	}
}
