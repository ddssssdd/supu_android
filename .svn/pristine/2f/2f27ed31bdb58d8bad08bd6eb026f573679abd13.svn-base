package cc.android.supu;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.OrderListAdapter;
import cc.android.supu.bean.OrderBean;
import cc.android.supu.handler.OrderListHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 订单页面
 * 
 * @author zsx
 * 
 */
public class OrderActivity extends BaseActivity {
	/*** 0-全部，1-未完成，2-已取消 默认值 0 */
	private int State = 1;
	/** 当前页 */
	private int Page = 1, pagecount = 1;
	/** 每页显示条数 */
	private int PageSize = 10;
	/** 键值对 */
	private TreeMap<String, String> params;
	private OrderListHandler orderListHandler;
	/*** 未完成 全部 取消的按钮 */
	private TextView orderNoCompleted, orderCompleted, orderCancel;
	/** 适配器 */
	private OrderListAdapter orderListAdapter;
	/** 列表布局 */
	private ListView List;
	/** 列表 */
	private List<OrderBean> orderList;
	/** 是否加载 显示 */
	private boolean isLoading = false;
	/** 空布局 */
	private RelativeLayout order_empty;
	/** 加载布局 */
	private View bottomView;

	@Override
	protected void initPage() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		bottomView = LayoutInflater.from(this).inflate(R.layout.list_downing,
				null, false);
		bottomView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				(int) (45 * metrics.density)));

		orderList = new ArrayList<OrderBean>();
		List = (ListView) findViewById(R.id.OrderList);

		List.setOnScrollListener(scrollListener);
		List.addFooterView(bottomView);
		bottomView.setTag(true);

		order_empty = (RelativeLayout) findViewById(R.id.order_empty);
		orderNoCompleted = (TextView) findViewById(R.id.orderNoCompleted);
		orderCompleted = (TextView) findViewById(R.id.orderCompleted);
		orderCancel = (TextView) findViewById(R.id.orderCancel);
		orderCancel.setOnClickListener(this);
		orderNoCompleted.setOnClickListener(this);
		orderCompleted.setOnClickListener(this);
		orderListAdapter = new OrderListAdapter(OrderActivity.this, orderList);
		List.setAdapter(orderListAdapter);

		ItemClickListener();
		changeTitle();

	}

	/**
	 * list滚动到下面的效应事件
	 */
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
				requestServer(requestOrderList, false);
			}
		}
	};

	/**
	 * 响应事件
	 */
	private void ItemClickListener() {
		List.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(OrderActivity.this,
						OrderInfoActivity.class);
				intent.putExtra("OrderSN", orderList.get(position).OrderSN);
				startActivity(intent);
				// OrderActivity.this.finish();
			}
		});
	}

	/**
	 * 服务器的请求
	 */
	private PageRequest requestOrderList = new PageRequest() {
		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Page", Page + "");
			params.put("PageSize", PageSize + "");
			params.put("State", State + "");
			params.put("StartTime", "");
			params.put("EndTime", "");
			System.out.println("getStringDateShort()" + getStringDateShort());
			orderListHandler = new OrderListHandler();
			Tools.requestToParse(OrderActivity.this, ConstantUrl.GETORDERLIST,
					"GetOrderList", params, orderListHandler, false);
			isLoading = true;
			if (Tools.responseValue == 1) {
				if (orderListHandler.result_code == 0) {
					if (orderListHandler.orderList != null) {
						orderList.addAll(orderListHandler.orderList);
					}
					pagecount = (orderListHandler.recordCount + PageSize - 1)
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
				List.removeFooterView(bottomView);
				bottomView.setTag(false);
			}
			if (orderList != null && orderList.size() > 0) {
				order_empty.setVisibility(View.GONE);
				List.setVisibility(View.VISIBLE);
				orderListAdapter.notifyDataSetChanged();
			} else {
				List.setVisibility(View.GONE);
				order_empty.setVisibility(View.VISIBLE);
			}
			break;
		case STATE_FAILURE:
			Toast.makeText(OrderActivity.this, "服务器异常！", Toast.LENGTH_SHORT)
					.show();
			break;
		case STATE_ERROR:
			Toast.makeText(OrderActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	};

	@Override
	protected void onSubActivityClick(View view) {
		switch (view.getId()) {
		case R.id.orderNoCompleted:
			if (State == 1)
				return;
			State = 1;
			changeTitle();
			resetPage();
			requestServer(requestOrderList);
			break;
		case R.id.orderCompleted:
			if (State == 0)
				return;
			State = 0;
			changeTitle();
			resetPage();
			requestServer(requestOrderList);
			break;
		case R.id.orderCancel:
			if (State == 2)
				return;
			State = 2;
			changeTitle();
			resetPage();
			requestServer(requestOrderList);
			break;

		default:
			break;
		}
	}

	/**
	 * 选中变色
	 */
	private void changeTitle() {
		switch (State) {
		case 0:
			orderCompleted.setBackgroundResource(R.drawable.read_a);
			orderCompleted.setTextColor(Color.WHITE);
			orderNoCompleted.setBackgroundResource(R.drawable.read_b);
			orderNoCompleted.setTextColor(Color.BLACK);
			orderCancel.setBackgroundResource(R.drawable.read_b);
			orderCancel.setTextColor(Color.BLACK);
			break;
		case 1:
			orderNoCompleted.setBackgroundResource(R.drawable.read_a);
			orderNoCompleted.setTextColor(Color.WHITE);
			orderCompleted.setBackgroundResource(R.drawable.read_b);
			orderCompleted.setTextColor(Color.BLACK);
			orderCancel.setBackgroundResource(R.drawable.read_b);
			orderCancel.setTextColor(Color.BLACK);
			break;
		case 2:
			orderCancel.setBackgroundResource(R.drawable.read_a);
			orderCancel.setTextColor(Color.WHITE);
			orderCompleted.setBackgroundResource(R.drawable.read_b);
			orderCompleted.setTextColor(Color.BLACK);
			orderNoCompleted.setBackgroundResource(R.drawable.read_b);
			orderNoCompleted.setTextColor(Color.BLACK);
			break;

		default:
			break;
		}

	}

	/**
	 * 初始化页面数据
	 */
	private void resetPage() {
		orderList.clear();
		pagecount = 1;
		Page = 1;
		boolean isAdd = (Boolean) bottomView.getTag();
		if (!isAdd) {
			bottomView.setTag(true);
			List.addFooterView(bottomView);
			List.setAdapter(orderListAdapter);
		}
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "我的订单";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected void onResume() {
		super.onResume();
		resetPage();
		requestServer(requestOrderList);

	}

	// @Override
	// protected void backBtnOnClick() {
	// Intent intent = new Intent(this, VipActivity.class);
	// startActivity(intent);
	// this.finish();
	// }

	@Override
	int setLayout() {
		return R.layout.order;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // 按下返回键
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	// Intent intent = new Intent(this, VipActivity.class);
	// startActivity(intent);
	// this.finish();
	// }
	//
	// return false;
	// }
}
