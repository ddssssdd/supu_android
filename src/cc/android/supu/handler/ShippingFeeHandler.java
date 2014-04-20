package cc.android.supu.handler;

import org.json.JSONObject;

/**
 * 获得运费信息解析器
 * @author sheng
 *
 */
public class ShippingFeeHandler extends DefaultJSONData {
	
	public String shippingFee;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		JSONObject data = object.optJSONObject("Data");
		shippingFee = data.optString("ShippingFee");
//		shippingFee = "5.0";
	}
}
