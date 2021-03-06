package cc.android.supu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.adapter.OrderInfoListAdapter;
import cc.android.supu.bean.CityBean;
import cc.android.supu.bean.OrderInfoBean;
import cc.android.supu.datebase.AllCityListDbHelper;
import cc.android.supu.datebase.CityDbhelper;
import cc.android.supu.handler.OrderInfoHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.PriceTools;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.view.OrderInfoListView;

/**
 * 订单的详细页面
 * 
 * @author zsx
 * 
 */
public class OrderInfoActivity extends BaseActivity {
	/** 订单详情 上面的用户信息 和 订单信息 */
	private TextView orderInfoSN, infoConsignee, infoTel, infoAddress,
			infoZipCode, infoShippingName, infoPayName, infoPayStatus,
			infoOrderStatus;

	private ScrollView scrollview;
	/** 订单金额(总计) */
	private TextView orderAmountTv;
	/** 现金账户金额 */
	private TextView cashPriceTv;
	/** 优惠金额 */
	private TextView discountTv;
	/** 运费 */
	private TextView shippingFeeTv;
	/** 商品小计 */
	private TextView totalTv;
	/** 商品明细listview */
	private OrderInfoListView listview;
	/** 键值对 */
	private TreeMap<String, String> params;
	/** 订单详细信息解析器 */
	private OrderInfoHandler orderInfoHandler;
	/** 订单详情listView的适配器 */
	private OrderInfoListAdapter adapter;
	/** 订单中的商品明细列表 */
	private ArrayList<OrderInfoBean> orderInfoList;
	/** 订单编号 */
	private String OrderSN = "";
	/** 订单总额 */
	private String orderAmount;
	/** 订单状态 */
	private String orderState = "";
	/** 支付方式名称 */
	private String payName = "";
	// /** 银联手机支付安全控件数据*/
	// private String UPPayData = "";
	/** 物流查询按钮、支付按钮、取消按钮 */
	private Button orderLogistics, payOrder, CancelOrder;
	/** 提示正在加载的view */
	private LinearLayout loadingLayout;
	/** 加载进度条 */
	private ProgressBar progressBar;
	/** 显示加载的信息 */
	private TextView loadingText;
	/** 选择支付方式的对话框 */
	private Dialog dialog;
	private ArrayList<String> cityState;
	private boolean isfirst = true;

	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "支付";
	}

	@Override
	protected void initPage() {
		isfirst = true;
		if (getIntent() != null) {
			OrderSN = this.getIntent().getExtras().getString("OrderSN");
		}

		findVB();
		// requestServer(requestOrderInfo);

	}

	/**
	 * 服务器的请求
	 */
	private PageRequest requestOrderInfo = new PageRequest() {
		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("OrderSN", OrderSN);

			orderInfoHandler = new OrderInfoHandler();
			Tools.requestToParse(OrderInfoActivity.this, ConstantUrl.GETORDER,
					"GetOrder", params, orderInfoHandler, false);
			if (Tools.responseValue == 1) {
				if (orderInfoHandler.result_code == 0) {
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
			setValue();
			loadingLayout.setVisibility(View.GONE);
			scrollview.fling(0);
			break;
		case STATE_FAILURE:
			progressBar.setVisibility(View.GONE);
			loadingText.setText("加载未成功，可以重新进入该页面试试。");
			Toast.makeText(OrderInfoActivity.this, "服务器异常！", Toast.LENGTH_SHORT)
					.show();
			break;
		case STATE_ERROR:
			progressBar.setVisibility(View.GONE);
			loadingText.setText("加载未成功，可以重新进入该页面试试。");
			Toast.makeText(OrderInfoActivity.this, "网络异常，请检查网络",
					Toast.LENGTH_SHORT).show();
			break;
		}
	};

	/**
	 * 设置控件中显示的数据
	 */
	private void setValue() {
		if (orderInfoHandler != null) {
			orderInfoSN.setText(orderInfoHandler.OrderSN + "");
			// 对收件人进行处理
			String Consignee = "";
			if (orderInfoHandler.Consignee != null
					&& orderInfoHandler.Consignee.length() > 17) {
				Consignee = orderInfoHandler.Consignee.substring(0, 17) + "..";
			} else {
				Consignee = orderInfoHandler.Consignee;
			}
			infoConsignee.setText(Consignee + "");
			// 对电话进行处理
			String tell = "";
			if (orderInfoHandler.Tel != null
					&& orderInfoHandler.Tel.trim().equals("")) {
				tell = orderInfoHandler.Mobile;
			} else if (orderInfoHandler.Mobile != null
					&& orderInfoHandler.Mobile.trim().equals("")) {
				tell = orderInfoHandler.Tel;
			} else {
				tell = orderInfoHandler.Mobile + "/" + orderInfoHandler.Tel;
			}

			infoTel.setText(tell);

			// 对地址进行处理
			StringBuffer addrBuff = new StringBuffer();
			addrBuff.append(getName(orderInfoHandler.ProvinceID))
					.append(getName(orderInfoHandler.CityID))
					.append(getName(orderInfoHandler.AreaID))
					.append(orderInfoHandler.Address);
			infoAddress.setText(addrBuff.toString());
			infoZipCode.setText(orderInfoHandler.ZipCode + "");
			infoShippingName.setText(orderInfoHandler.ShippingName + "");
			payName = orderInfoHandler.PayName;
			if ("货到付款".equals(payName)) {
				enterLabel.setText("查看订单结果");
				payOrder.setText("查看订单结果");
			} else {
				enterLabel.setText("支付");
				payOrder.setText("支付");
			}
			infoPayName.setText(payName + "");
			infoPayStatus.setText(orderInfoHandler.PayStatus + "");
			infoOrderStatus.setText(orderInfoHandler.OrderStatus + "");
			orderState = orderInfoHandler.OrderStatus;

			orderInfoList = orderInfoHandler.orderInfoList;
			if (orderInfoList != null && orderInfoList.size() > 0) {
				adapter = new OrderInfoListAdapter(this, orderInfoList);
				listview.setAdapter(adapter);
			}

			// UPPayData = orderInfoHandler.UPPayData;
			// System.out.println("UPPayData="+UPPayData);

			totalTv.setText("￥"
					+ PriceTools.formatStr(orderInfoHandler.GoodsSubtotal + ""));

			shippingFeeTv.setText("￥"
					+ PriceTools.formatStr(orderInfoHandler.ShippingFee + ""));
			discountTv.setText(getDiscount(orderInfoHandler.Discount,
					orderInfoHandler.TicketDiscount));
			orderAmount = sum(
					orderInfoHandler.OrderAmount,
					orderInfoHandler.ShippingFee,
					getDiscount(orderInfoHandler.Discount,
							orderInfoHandler.TicketDiscount));

			orderAmountTv.setText(orderAmount);
			cashPriceTv.setText("￥"
					+ PriceTools.formatStr(orderInfoHandler.CashPrice + ""));

			if ("完成".equals(orderInfoHandler.OrderStatus)) {
				payOrder.setVisibility(View.INVISIBLE);
				enterLabel.setVisibility(View.INVISIBLE);
			} else if ("未支付".equals(orderInfoHandler.PayStatus)
					&& "在线支付".equals(orderInfoHandler.PayName)
					&& !"取消".equals(orderInfoHandler.OrderStatus)) {
				payOrder.setVisibility(View.VISIBLE);
				enterLabel.setVisibility(View.VISIBLE);
				sum(orderInfoHandler.OrderAmount,
						orderInfoHandler.ShippingFee,
						getDiscount(orderInfoHandler.Discount,
								orderInfoHandler.TicketDiscount));
			} else if ("未支付".equals(orderInfoHandler.PayStatus)
					&& "货到付款".equals(orderInfoHandler.PayName)
					&& "待确认".equals(orderInfoHandler.OrderStatus)) {
				payOrder.setVisibility(View.VISIBLE);
				enterLabel.setVisibility(View.VISIBLE);
				sum(orderInfoHandler.OrderAmount,
						orderInfoHandler.ShippingFee,
						getDiscount(orderInfoHandler.Discount,
								orderInfoHandler.TicketDiscount));
			} else {
				payOrder.setVisibility(View.INVISIBLE);
				enterLabel.setVisibility(View.INVISIBLE);
			}

			loadingLayout.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 计算优惠金额， 优惠金额 = 优惠活动的优惠金额 + 优惠券的优惠金额；
	 * 
	 * @param discount
	 *            优惠活动的优惠金额
	 * @param ticketDiscount
	 *            优惠券的优惠金额
	 * @return sumDisStr 总优惠金额
	 */
	private String getDiscount(String discount, String ticketDiscount) {
		double dis = PriceTools.stringToInt(discount);
		double ticketdis = PriceTools.stringToInt(ticketDiscount);
		double sumDis = dis + ticketdis;

		return "￥" + PriceTools.formatStr(PriceTools.intToString(sumDis));
	}

	/**
	 * 求总计(订单金额+运费)
	 * 
	 * @param orderAmount
	 *            订单金额
	 * @param shippingFee
	 *            运费
	 * @return
	 */
	private String sum(String orderAmount, String shippingFee, String discount) {
		double orderA = PriceTools.stringToInt(orderAmount);
		double shipFee = PriceTools.stringToInt(shippingFee);
		double shopDiscount = PriceTools.stringToInt(discount);
		double sum = orderA + shipFee - shopDiscount;
		// DecimalFormat df = new DecimalFormat("0.00");
		// System.out.println("sum====  " + sum);
		if (sum == 0) {
			// System.out.println("????????????====  ");
			enterLabel.setVisibility(View.INVISIBLE);
			payOrder.setVisibility(View.INVISIBLE);
		}
		return "￥" + PriceTools.formatStr(PriceTools.intToString(sum));
	}

	/**
	 * 绑定控件
	 */
	private void findVB() {
		scrollview = (ScrollView) findViewById(R.id.orderinfo_scrollView);
		orderInfoSN = (TextView) findViewById(R.id.orderInfoSN);
		infoConsignee = (TextView) findViewById(R.id.infoConsignee);
		infoTel = (TextView) findViewById(R.id.infoTel);
		infoAddress = (TextView) findViewById(R.id.infoAddress);
		infoZipCode = (TextView) findViewById(R.id.infoZipCode);
		infoShippingName = (TextView) findViewById(R.id.infoShippingName);
		infoPayName = (TextView) findViewById(R.id.infoPayName);
		infoPayStatus = (TextView) findViewById(R.id.infoPayStatus);
		infoOrderStatus = (TextView) findViewById(R.id.infoOrderStatus);

		shippingFeeTv = (TextView) findViewById(R.id.infoShippingFee);
		discountTv = (TextView) findViewById(R.id.infoDiscount);
		orderAmountTv = (TextView) findViewById(R.id.infoTotal);
		cashPriceTv = (TextView) findViewById(R.id.infoRemainder);
		totalTv = (TextView) findViewById(R.id.infoPrice);

		orderLogistics = (Button) findViewById(R.id.orderLogistics);
		payOrder = (Button) findViewById(R.id.payOrder);
		CancelOrder = (Button) findViewById(R.id.CancelOrder);
		orderLogistics.setOnClickListener(this);
		payOrder.setOnClickListener(this);
		CancelOrder.setOnClickListener(this);

		listview = (OrderInfoListView) findViewById(R.id.orderinfo_listview);
		listview.setOnItemClickListener(mOnItemClickListener);

		loadingLayout = (LinearLayout) findViewById(R.id.orderinfo_loadLayout);
		progressBar = (ProgressBar) findViewById(R.id.orderinfo_progressBar);
		loadingText = (TextView) findViewById(R.id.orderinfo_loadtext);

		enterLabel.setText("支付");
		enterLabel.setOnClickListener(this);

	}

	@Override
	protected void onSubActivityClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.orderLogistics:
			intent = new Intent(this, OrderProcessActivity.class);
			intent.putExtra("orderSN", OrderSN);
			startActivity(intent);
			break;
		case R.id.payOrder:
			if ("货到付款".equals(payName)) {
				intent = new Intent(this, PayActivity.class);
				intent.putExtra("orderSN", OrderSN);
				intent.putExtra("orderAmount", orderAmount + "");
				intent.putExtra("payName", payName);
				startActivityForResult(intent, 434);
			} else {
				showPayWayDialog();
			}
			break;

		case R.id.CancelOrder:
			if (IsOrderState()) {
				// Toast.makeText(OrderInfoActivity.this, "",
				// Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(OrderInfoActivity.this, "订单已处于取消状态，不可以重复取消",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.alipay:
			cancelPayWayDialog();
			// payName = "支付宝";
			intent = new Intent(this, PayActivity.class);
			intent.putExtra("orderSN", OrderSN);
			intent.putExtra("orderAmount", orderAmount + "");
			intent.putExtra("payName", "支付宝");
			startActivityForResult(intent, 434);
			break;
		case R.id.UPPay:
			cancelPayWayDialog();
			// payName = "银联";
			intent = new Intent(this, PayActivity.class);
			intent.putExtra("orderSN", OrderSN);
			intent.putExtra("orderAmount", orderAmount + "");
			intent.putExtra("payName", "银联");
			startActivityForResult(intent, 434);
			break;
		}
	}

	private boolean IsOrderState() {
		if (orderState.trim().equals("取消")) {
			return false;
		}
		return true;
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.orderinfo;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "订单详情";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected void enterBtnOnClick() {
		if ("货到付款".equals(payName)) {
			Intent intent = new Intent(this, PayActivity.class);
			intent.putExtra("orderSN", OrderSN);
			intent.putExtra("orderAmount", orderAmount + "");
			intent.putExtra("payName", payName);
			startActivityForResult(intent, 434);
		} else {
			showPayWayDialog();
		}

	}

	//
	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		if (!isfirst) {
			Intent intent = new Intent(this, PayActivity.class);
			intent.putExtra("orderSN", OrderSN);
			intent.putExtra("orderAmount", orderAmount + "");
			intent.putExtra("payName", payName);
			startActivityForResult(intent, 434);
		} else {
			super.backBtnOnClick();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 434 && data != null) {
			isfirst = data.getBooleanExtra("isfirst", true);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		enterLabel.setVisibility(View.INVISIBLE);
		requestServer(requestOrderInfo);
		super.onResume();
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(OrderInfoActivity.this,
					GoodsDetailsActivity.class);
			intent.putExtra("goodsSN", orderInfoList.get(position).GoodsSN);
			intent.putExtra("imgFile", orderInfoList.get(position).ImgFile);
			startActivity(intent);
		}
	};

	/**
	 * 得到全部的省市区
	 */
	private List<CityBean> getCityBeanlist() {
		List<CityBean> cityBeanList = null;
		try {
//			cityState = UserInfoTools.getCity(OrderInfoActivity.this);
//			String version1 = "1";
//			if (cityState != null && cityState.size() > 0) {
//
//				version1 = cityState.get(1);
//				System.out.println("version = " + version1);
//			}
//			if (version1.equals("1")) {
				System.out.println("现有数据库");
				CityDbhelper cityDbhelper = new CityDbhelper(
						OrderInfoActivity.this);
				cityBeanList = cityDbhelper.queryAllCity();
//			} else {
//				AllCityListDbHelper allCityListDbHelper = new AllCityListDbHelper(
//						OrderInfoActivity.this);
//				cityBeanList = allCityListDbHelper.queryAllCity();
//			}
		} catch (Exception e) {
			System.out.println("地址数据库查询异常！");
			e.printStackTrace();
		}

		return cityBeanList;
	}

	/**
	 * 查找省 市 区
	 * 
	 */
	private String getName(String ID) {
		List<CityBean> cityBeanList = getCityBeanlist();
		if (cityBeanList == null)
			return "";

		for (int i = 0; i < cityBeanList.size(); i++) {
			String areaCode = cityBeanList.get(i).getAreaCode();
			if (areaCode.equals(ID)) {
				return cityBeanList.get(i).getAreaName();
			}
		}
		return "";
	}

	/**
	 * 显示支付方式的对话框
	 */
	private void showPayWayDialog() {

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.payway, null);
		TextView alipay = (TextView) view.findViewById(R.id.alipay);
		TextView uppay = (TextView) view.findViewById(R.id.UPPay);
		alipay.setOnClickListener(this);
		uppay.setOnClickListener(this);
		dialog = new AlertDialog.Builder(this).setView(view)
				.setNegativeButton("取消", null).create();
		dialog.show();
	}

	/**
	 * 取消对话框
	 */
	private void cancelPayWayDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

}
