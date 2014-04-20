package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GoodsBean;

/**
 * 修改购物车信息解析类
 * @author sheng
 *
 */
public class ModifyShopCartHandler extends DefaultJSONData {
	
	public ArrayList<GoodsBean> list;
	private GoodsBean goods;
	public String discountAmount;
	public String sumAmount;
	
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object == null){
			return;
		}
		
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			JSONArray result = data.optJSONArray("Result");
			if(result != null){
				list = new ArrayList<GoodsBean>();
				for(int i=0;i<result.length();i++){
					if(result.optJSONObject(i) != null){
						goods = new GoodsBean();
						goods.goodsSN = result.optJSONObject(i).optString("GoodsSN");
						goods.goodsName = result.optJSONObject(i).optString("GoodsName");
						goods.count = result.optJSONObject(i).optInt("Count");
						goods.isNoStock = result.optJSONObject(i).optBoolean("IsNoStock");
						goods.stockCount = -1;
						list.add(goods);
					}
				}
			}
			discountAmount = fomatString(data.optString("DiscountAmount"));
			sumAmount = data.optString("SumAmount");
		}
	}
	
	
	public String fomatString(String str) {
		int pointIndex = str.indexOf('.');
		if (pointIndex == -1) {
			return str + ".00";
		} else if (pointIndex == str.length() - 2) {
			return str + "0";
		} else if (pointIndex == str.length() - 3) {
			return str;
		}
		return null;
	}
}
