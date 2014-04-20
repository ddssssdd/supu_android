package cc.android.supu.handler;

import org.json.JSONObject;

/**
 * 获取银联订单信息解析器
 * @author sheng
 *
 */
public class UPPayDataHandler extends DefaultJSONData {
	
	/** 银联订单信息*/
	public String UPPayData = "";
	/** 银联交易模式：00正式环境交易；01测试环境交易*/
	public String ServerMode = "";
	/** 支付宝手机支付安全控件数据*/
	public String AliPayData = "";
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			UPPayData = data.optString("TradeNumber", "");
			ServerMode = data.optString("ServerMode", "01");
			AliPayData = data.optString("AliPayData", "");
		}
	}
}
