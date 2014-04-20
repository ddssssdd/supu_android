package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import cc.android.supu.bean.IndexGoodBean;

/**
 * @author sss 首页商品解析器
 */
public class IndexGoodsHandler extends DefaultJSONData {
	private LinkedHashMap<String, ArrayList<IndexGoodBean>> categoryGoods;

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray goodsList = data.optJSONArray("TopGoodsList");
			if (goodsList != null) {
				categoryGoods = new LinkedHashMap<String, ArrayList<IndexGoodBean>>();
				int len = goodsList.length();
				for (int i = 0; i < len; i++) {
					JSONObject categorys = goodsList.optJSONObject(i);
					String goodsName = categorys.optString("Name");
					if (goodsName == null) {
						goodsName = "";
					}
					ArrayList<IndexGoodBean> indexGoods = new ArrayList<IndexGoodBean>();
					JSONArray goods = categorys.optJSONArray("Goods");
					if (goods != null && goods.length() > 0) {
						categoryGoods.put(goodsName, indexGoods);
						int size = goods.length();
						for (int j = 0; j < size; j++) {
							IndexGoodBean goodBean = new IndexGoodBean();
							JSONObject jsonGood = goods.optJSONObject(j);
							goodBean.goodsSN = jsonGood.optString("GoodsSN");
							goodBean.imageFile = jsonGood.optString("ImgFile");
//							goodBean.price = (float) jsonGood.optDouble("Price", 0);
							goodBean.price = jsonGood.optString("Price");
//							System.out.println("goodBean.price"+goodBean.price);
							indexGoods.add(goodBean);
						}
					}
				}
			}
		}
	}

	public LinkedHashMap<String, ArrayList<IndexGoodBean>> getCategoryGoods() {
		return categoryGoods;
	}
}
