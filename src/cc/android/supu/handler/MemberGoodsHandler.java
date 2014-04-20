package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import cc.android.supu.bean.IndexGoodBean;

/**
 * @author zsx 会员中心货物
 */
public class MemberGoodsHandler extends DefaultJSONData {
	/**会员中心下面商品的Map*/
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
					categoryGoods.put(goodsName, indexGoods);
					JSONArray goods = categorys.optJSONArray("Goods");
					if (goods != null) {
						int size = goods.length();
						for (int j = 0; j < size; j++) {
							IndexGoodBean goodBean = new IndexGoodBean();
							JSONObject jsonGood = goods.optJSONObject(j);
							goodBean.goodsSN = jsonGood.optString("GoodsSN");
							goodBean.imageFile = jsonGood.optString("ImgFile");
//							goodBean.price = (float) jsonGood.optDouble("Price", 0);
							goodBean.price = jsonGood.optString("Price");
							indexGoods.add(goodBean);
						}
					}
				}
			}
		}
	}
	/**
	 * 得到Map
	 * @return
	 */
	public LinkedHashMap<String, ArrayList<IndexGoodBean>> getCategoryGoods() {
		return categoryGoods;
	}
}
