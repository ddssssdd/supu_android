package cc.android.supu.handler;

import org.json.JSONObject;

/**
 * 关于我们信息解析器
 * @author sheng
 *
 */
public class AboutUsHandler extends DefaultJSONData {
	
	public String data;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			data = object.optString("Data");
		}
	}
	
}
