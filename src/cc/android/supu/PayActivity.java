package cc.android.supu;

import java.util.TreeMap;

import org.json.JSONObject;

import cc.android.supu.ailpay.AlixId;
import cc.android.supu.ailpay.BaseHelper;
import cc.android.supu.ailpay.CreateOrderInfo;
import cc.android.supu.ailpay.MobileSecurePayHelper;
import cc.android.supu.ailpay.MobileSecurePayer;
import cc.android.supu.handler.OrderSuccessHandler;
import cc.android.supu.handler.UPPayDataHandler;
import cc.android.supu.tools.BankInitTask;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车支付页面
 * 
 * @author sheng
 * 
 */
public class PayActivity extends BaseActivity {

	/** 订单号 */
	private TextView orderSNTv;
	/** 支付总金额 */
	private TextView orderAmountTv;
	/** 确认信息 */
	private TextView promptTv;

	private RelativeLayout payLayout;
	/** 订单号 */
	private String orderSN;
	/** 支付总金额 */
	private String orderAmount;
	/** 服务器返回的数据 */
	private String data = "";
	/** 支付方式名称 */
	private String payName = "";
	// /**　银联手机支付安全控件数据*/
	// private String UPPayData = "";
	/** 支付结果 */
	private String payResult = "";
	/** 订单结果信息解析器 */
	private OrderSuccessHandler orderSuccessHandler;
	/** 银联订单信息解析器 */
	private UPPayDataHandler mUPPayDataHandler;

	public static final String R_FAIL = "fail";
	public static final String R_SUCCESS = "success";
	public static final String R_CANCEL = "cancel";

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return payName + "  速普";
	}

	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "下一步";
	}

	@Override
	protected void enterBtnOnClick() {
		// TODO Auto-generated method stub
		if ("支付宝".equals(payName)) {
			if (!"".equals(mUPPayDataHandler.AliPayData)) {
				alipay(mUPPayDataHandler.AliPayData);
			} else {
				Toast.makeText(this, "签名获取失败，无法支付", Toast.LENGTH_SHORT).show();
			}
		} else {
			if (!"".equals(mUPPayDataHandler.UPPayData)) {
				new BankInitTask(this).execute(mUPPayDataHandler.UPPayData, mUPPayDataHandler.ServerMode);
			} else {
				Toast.makeText(this, "签名获取失败，无法支付", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	protected void onSubActivityClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.pay_payLayout) {
			Intent intent = getIntent();
			intent.putExtra("isfirst", false);
			setResult(444, intent);
			finish();
			// onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		intent.putExtra("isfirst", true);
		setResult(444, intent);
		finish();
	}

	@Override
	protected void initPage() {
		if (getIntent() != null) {
			// UPPayData = getIntent().getStringExtra("UPPayData");
			orderSN = getIntent().getStringExtra("orderSN");
			orderAmount = getIntent().getStringExtra("orderAmount") + "";
			payName = getIntent().getStringExtra("payName");
		}
		requestServer(defaultPageRequest);

		// TODO Auto-generated method stub
		orderSNTv = (TextView) findViewById(R.id.pay_orderSN);
		orderAmountTv = (TextView) findViewById(R.id.pay_OrderAmount);
		promptTv = (TextView) findViewById(R.id.pay_prompt);
		payLayout = (RelativeLayout) findViewById(R.id.pay_payLayout);
		orderSNTv.setText(orderSN);
		orderAmountTv.setText(orderAmount + "元");

		payLayout.setOnClickListener(this);

	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.pay;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("uppay", "onActivityResult() +++");
		if (data == null)
			return;

		String str = data.getExtras().getString("pay_result");
		payResult = str;
		if (str.equalsIgnoreCase(R_SUCCESS)) {
			showResultDialog(" 支付成功！ ");
		} else if (str.equalsIgnoreCase(R_FAIL)) {
			showResultDialog(" 支付失败！ ");
		} else if (str.equalsIgnoreCase(R_CANCEL)) {
			showResultDialog(" 你已取消了本次订单的支付！ ");
		}

		Log.e("uppay", "onActivityResult() ---");
	}

	private void alipay(String orderInfo) {
		// check to see if the MobileSecurePay is already installed.
		// 检测安全支付服务是否被安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		// System.out.println("isMobile_spExist="+isMobile_spExist);
		if (!mspHelper.isMobile_spExist()) {
			// System.out.println("isMobile_spExist="+isMobile_spExist);
			payResult = R_FAIL;
			return;
		}

		// check some info.
		// 检测配置信息
		if (!CreateOrderInfo.checkInfo()) {
			BaseHelper.showDialog(this, "提示", "缺少partner或者seller，请在src/PartnerConfig.java中增加。", R.drawable.infoicon);
			return;
		}

		try {
			// 调用pay方法进行支付
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(orderInfo, handler, AlixId.RQF_PAY, this);

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				// 显示“正在支付”进度条
				closeProgress();
				// System.out.println("正在支付");
				mProgress = BaseHelper.showProgress(this, null, "正在支付", false, true);
			}
		} catch (Exception ex) {
			closeProgress();
			Toast.makeText(this, R.string.remote_call_failed, Toast.LENGTH_SHORT).show();
		}
	}

	private ProgressDialog mProgress = null;

	//
	// close the progress bar
	// 关闭进度框
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showResultDialog(String msg) {
		// TODO Auto-generated method stub
		Dialog resultDialog = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("确定", null).show();
		if(" 支付成功！ ".equals(msg)){
			resultDialog.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
				}
			});
		}
	}

	@Override
	protected void defaultRequest() {
		// TODO Auto-generated method stub
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("ordersn", orderSN);

		orderSuccessHandler = new OrderSuccessHandler();
		Tools.requestToParse(this, ConstantUrl.GETORDERSUCCESS, "GetOrderSuccess", params, orderSuccessHandler, false);
		if (Tools.responseValue == 1) {
			if (orderSuccessHandler.result_code == 0) {
				handler.sendEmptyMessage(STATE_SUCCESS);
			} else {
				handler.sendEmptyMessage(STATE_FAILURE);
			}
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}
	}

	/**
	 * 请求银联的订单信息
	 */
	private PageRequest requestUPPayData = new PageRequest() {

		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			String url = "";
			String method = "";
			if ("银联".equals(payName)) {
				url = ConstantUrl.GETUPPAYDATA;
				method = "GetUPPayData";
			} else if ("支付宝".equals(payName)) {
				url = ConstantUrl.GETALIPAYDATA;
				method = "GetAliPayData";
			}
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("OrderSN", orderSN);

			mUPPayDataHandler = new UPPayDataHandler();
			System.out.println("银联支付....");
			Tools.requestToParse(PayActivity.this, url, method, params, mUPPayDataHandler, false);
			// System.out.println("UPPayData="+mUPPayDataHandler.UPPayData);
			// System.out.println("AliPayData="+mUPPayDataHandler.AliPayData);
		}
	};

	@Override
	protected void dealwithMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case STATE_SUCCESS:
			if (orderSuccessHandler != null) {
				if ("支付宝".equals(payName) || "银联".equals(payName)) {
					enterLabel.setVisibility(View.VISIBLE);
				}else if("货到付款".equals(payName)){
					enterLabel.setVisibility(View.GONE);
				}
				data = orderSuccessHandler.data;
				// System.out.println("data--1="+data);
				if (data != null) {
					if (data.indexOf("{") != -1 && data.indexOf("}") != -1) {
						data = data.replace("{PayType}", payName);
					}
					data = data.replace("<span style='color:red'>", "<font color=\"#D70610\">");
					data = data.replace("</span>", "</font>");
					// System.out.println("data---2="+data);
					promptTv.setText(Html.fromHtml(data));
				}
			}

			break;
		case STATE_FAILURE:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求异常！", Toast.LENGTH_SHORT).show();
			break;
		case AlixId.RQF_PAY:
			String alixPayResult = (String) msg.obj;
			String[] resultArr = null;
			if (alixPayResult != null) {
				resultArr = alixPayResult.split(";");
				if (resultArr != null) {
					String[] first = resultArr[0].split("=");
					if ("resultStatus".equals(first[0])) {
						if (first[1] != null) {
							String resultStatus = first[1].substring(1, first[1].length() - 1);
							if ("9000".equals(resultStatus)) {
								payResult = R_SUCCESS;
							}
						}
					}
				}
			}
			if (payResult != R_SUCCESS) {
				requestServer(requestUPPayData, false);
				enterLabel.setVisibility(View.VISIBLE);
			}
			closeProgress();
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// enterLabel.setVisibility(View.INVISIBLE);
		if (!"".equals(payResult) && !R_SUCCESS.equalsIgnoreCase(payResult)) {
			enterLabel.setVisibility(View.VISIBLE);
		}else if("货到付款".equals(payName)){
			enterLabel.setVisibility(View.GONE);
		}
		requestServer(requestUPPayData, false);
		super.onResume();
	}
}
