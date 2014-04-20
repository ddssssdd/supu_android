package cc.android.supu.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import com.unionpay.UPPayAssistEx;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.AsyncTask;

/**
 * @author sss 银联支付类
 */
public class BankInitTask extends AsyncTask<String, Void, Boolean> {

	public static final String PAY_DATA = "PayData";
	public static final String SP_ID = "SpId";
	public static final String SECURITY_CHIP_TYPE = "SecurityChipType";
	public static final String SYS_PROVIDE = "SysProvide";
	public static final String USE_TEST_MODE = "UseTestMode";

	private String orderInfo = null;
	/** 银联交易模式：00正式环境交易；01测试环境交易*/
	public String ServerMode = "00";
	public Activity activity;
	private AlertDialog dialog;

	// 外网paa test环境地址
	// private static String PAA_SERVER_URL =
	// "http://222.66.233.198:8080/getPaa1?id=";//
	// "http://222.66.233.198:8080/getPaa?id=";

	// 外网paa dev环境地址
	private static String PAA_SERVER_URL = "http://218.80.192.213:1725/getPaa?id=";

	// 内网paa地址
	// private static String PAA_SERVER_URL =
	// "http://172.17.233.180:3001/getPaa?id=";

	public BankInitTask(Activity activity) {
		// TODO Auto-generated constructor stub
		super();
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		if (needSetProxy(activity)) {
			Properties prop = System.getProperties();
			prop.put("http.proxyHost", Proxy.getDefaultHost());
			prop.put("http.proxyPort", "" + Proxy.getDefaultPort());
		}
		dialog = new AlertDialog(activity) {
		};
		dialog.setTitle("提示信息");
		dialog.setMessage("请稍候正在获取订单交易信息!");
		dialog.setCancelable(false);
		dialog.show();

	}

	@Override
	protected Boolean doInBackground(String... params) {
		orderInfo = params[0];
		ServerMode = params[1];
//		System.out.println("....ServerMode="+ServerMode);
		getTestUrlData(2);
		// getUrlData(params[0]);
		if (orderInfo != null)
			return false;
		else
			return true;
	}

	private boolean needSetProxy(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
		if (mobNetInfo == null || "WIFI".equals(mobNetInfo.getTypeName())) {
			return false;
		}
		if (mobNetInfo.getSubtypeName().toLowerCase().contains("cdma")) {
			// 电信
			if (android.net.Proxy.getDefaultHost() != null && android.net.Proxy.getDefaultPort() != -1)
				return true;
		} else if (mobNetInfo.getExtraInfo().contains("wap")) {
			// 移动网络
			return true;
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result) {
			dialog.dismiss();
			dialog = new AlertDialog(activity) {
			};
			dialog.setTitle("异常提示");
			dialog.setMessage("网络异常，获取订单失败！");
			dialog.setButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog.show();
			dialog.setCancelable(false);
		} else {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			String spId = "0281";
			String sysprovider = "11564520";
			int ret = UPPayAssistEx.startPay(activity, spId, sysprovider, orderInfo, ServerMode);

			if (ret == UPPayAssistEx.PLUGIN_NOT_FOUND) {
				DialogInterface.OnClickListener clickedOk = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (dialog instanceof AlertDialog) {
							UPPayAssistEx.installUPPayPlugin(activity);
						}
					}
				};

				DialogInterface.OnClickListener clickedCancel = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (dialog instanceof AlertDialog) {
							activity.finish();
						}
					}
				};

				String msg = "银联安全支付控件未被正确安装，检查并重新安装？";
				UPPayUtils.showAlertDlg(activity, null, msg, "确定", clickedOk, "取消", clickedCancel);
			}
			// }
		}
	}

	public void getTestUrlData(int position) {
		InputStream is;
		try {
			// 定义获取文件内容的URL
			position++;
			String url = PAA_SERVER_URL + position;
			// System.out.println("url:" + url);
			URL myURL = new URL(url);
			if (needSetProxy(activity)) {
				Properties prop = System.getProperties();
				prop.put("http.proxyHost", Proxy.getDefaultHost());
				prop.put("http.proxyPort", "" + Proxy.getDefaultPort());
			}
			// 打开URL链接
			URLConnection ucon = myURL.openConnection();
			// 设定连接超时2分钟
			ucon.setConnectTimeout(120000);
			// 使用InputStream，从URLConnection读取数据
			is = ucon.getInputStream();

			int i = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			// orderInfo = baos.toString();
//			System.out.println("test orderInfo:" + baos.toString());
			is.close();
			baos.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
