package cc.android.supu.handler;

import org.json.JSONObject;
/**
 * 获得订单提交成功提示信息
 * @author sheng
 *
 */
public class OrderSuccessHandler extends DefaultJSONData {
		
	public String data = "";
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			data = object.optString("Data");
		}
	}
	
}
