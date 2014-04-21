package cc.android.supu.tools;

import java.util.List;
import java.util.Locale;

import cc.android.supu.bean.CityBean;
import cc.android.supu.service.CityDataService;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * @author sss 提供一些参数
 */
public final class PublicParams {

	private static String IMEI = null;

	private static String IMSI = null;

	private static String LANGUAGE = null;
	private static String OPERATOR = null;
	private static String SCREENSIZE = null;

	private static int ISFINISH = 0;

	private static String MEMBERID = "";// "HF2igw0j0sw5313BsiKTWA==";

	public static int getISFINISH() {
		return ISFINISH;
	}

	public static void setISFINISH(int iSFINISH) {
		ISFINISH = iSFINISH;
	}

	public static void setMEMBERID(String mEMBERID) {
		//
		MEMBERID = mEMBERID;
	}

	/**
	 * @return 用户ID
	 */
	public static String getMemberId() {
		// "pg+z00vxiKsV1N3RVgIMpg==";
		if (MEMBERID == null)
			return "";
		return MEMBERID;
	}

	/**
	 * @return 系统平台类型 i:Iphone; A:Android
	 */
	public static String getPlatform() {
		return "android";
	}

	/**
	 * @return 设备号，symbian和android有可能返回的是imei
	 */
	public static String getUdid(Context context) {
		return getImei(context);
	}

	/**
	 * @return 手机型号
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * @return 手机Imsi标示
	 */
	public static String getImsi(Context context) {
		if (IMSI == null) {
			try {
				TelephonyManager telephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				IMSI = telephonyManager.getSubscriberId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (IMSI == null) {
			IMSI = "";
		}
		return IMSI;
	}

	/**
	 * @return 客戶端设备标识,Android手机的IMEI
	 */
	public static String getImei(Context context) {
		if (IMEI == null) {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			IMEI = telephonyManager.getDeviceId();
		}
		return IMEI;
	}

	/**
	 * @return 渠道ID
	 */
	public static String getSource() {
		return "渠道ID";
	}

	/**
	 * @return 语言
	 */
	public static String getLanguage() {
		if (LANGUAGE == null)
			LANGUAGE = Locale.getDefault().getLanguage();
		return LANGUAGE;
	}

	/**
	 * @return 运营商信息
	 */
	public static String getOperator(Context context) {
		if (OPERATOR == null) {
			String imsi = getImsi(context);
			if (null == imsi) {
				OPERATOR = " ";
				return OPERATOR;
			}
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				OPERATOR = "中国移动";
			} else if (imsi.startsWith("46001")) {
				OPERATOR = "中国联通";
			} else if (imsi.startsWith("46003")) {
				OPERATOR = "中国电信";
			} else {
				OPERATOR = "未知";
			}
		}
		return OPERATOR;
	}

	/**
	 * @return 短信中心号码
	 */
	public static String getSmsNumber() {
		return "";
	}

	/**
	 * @return 屏幕分辨率
	 */
	public static String getScreenSize(Context context) {

		if (context instanceof Activity) {
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			SCREENSIZE = metrics.widthPixels + "x" + metrics.heightPixels;
		} else {
			SCREENSIZE = "480 * 800";
		}
		return SCREENSIZE;
	}

	/**
	 * @return 接口版本号
	 */
	public static String getAppVersion() {
		return "1.2";
	}

	/**
	 * @return 客户端版本号
	 */
	public static String getClientVersion() {
		return "1.1";
	}

}
