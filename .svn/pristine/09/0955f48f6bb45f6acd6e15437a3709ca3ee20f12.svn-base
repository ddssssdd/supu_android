package cc.android.supu.ailpay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sss 生成订单帮助类
 */
public class CreateOrderInfo {
	 private static String subject = "THE Emporium 宽松慵懒蝙蝠袖露肩性感波点上衣(紫色波点)(1)";
	 private static String body = "THE Emporium 宽松慵懒蝙蝠袖露肩性感波点上衣(紫色波点)(1)";

	// private static String price = "0.01";

	public static String getOrderInfo(float price, String orderNo, String notify_url) {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + orderNo + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + subject + "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + body + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\"" + price + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\"" + notify_url + "\"";

		return strOrderInfo;
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 * @return
	 */
	public static String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param signType
	 *            签名方式
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
	public static String sign(String signType, String content) {
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}

	/**
	 * check some info.the partner,seller etc. 检测配置信息
	 * partnerid商户id，seller收款帐号不能为空
	 * 
	 * @return
	 */
	public static boolean checkInfo() {
		String partner = PartnerConfig.PARTNER;
		String seller = PartnerConfig.SELLER;
		if (partner == null || partner.length() <= 0 || seller == null || seller.length() <= 0)
			return false;

		return true;
	}

	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String strKey = format.format(date);

		java.util.Random r = new java.util.Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}
}
