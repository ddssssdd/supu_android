package cc.android.supu.handler;

import org.json.JSONObject;

import android.webkit.WebView;

/**
 * 商品描述信息解析类
 * @author sheng
 *
 */
public class GoodsDesciptionHandler extends DefaultJSONData {
	
	public String goodsSN;
	/** 商品描述*/
	public String note;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			JSONObject data = object.optJSONObject("Data");
			if(data != null){
				goodsSN = data.optString("GoodsSN");
				note = data.optString("Note");
			}
		}
	}
}
