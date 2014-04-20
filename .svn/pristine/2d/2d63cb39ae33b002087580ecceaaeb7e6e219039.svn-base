package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GoodsBean;
import cc.android.supu.bean.GoodsImageBean;

/**
 * 商品详情信息解析类
 * 
 * @author sheng
 * 
 */
public class GoodsDetailsHandler extends DefaultJSONData {

	// public ArrayList<GoodsImageBean> goodsImagList;
	public GoodsBean goodsDetail;
	// private GoodsImageBean goodsImage;
	public ArrayList<String> bigImagUrls;
	public ArrayList<String> smallImagUrls;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if (object == null) {
			return;
		}

		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONObject goods = data.optJSONObject("Goods");
			if (goods != null) {
				goodsDetail = new GoodsBean();
				goodsDetail.goodsSN = goods.optString("GoodsSN");
				goodsDetail.goodsName = goods.optString("GoodsName");
				goodsDetail.marketPrice = goods.optString("MarketPrice");
				goodsDetail.shopPrice = goods.optString("ShopPrice");
				goodsDetail.goodsSlogan = goods.optString("GoodsSlogan");
				goodsDetail.goodsScore = goods.optString("GoodsScore");
				goodsDetail.categoryID = goods.optString("CategoryID");
				goodsDetail.brandID = goods.optString("BrandID");
				goodsDetail.commentCount = goods.optString("CommentCount");
				goodsDetail.consultCount = goods.optString("ConsultCount");
				goodsDetail.shareText = goods.optString("ShareText");
				goodsDetail.isNoStock = goods.optBoolean("IsNoStock");

				JSONArray goodsImages = goods.optJSONArray("GoodsImages");
				if (goodsImages != null && goodsImages.length() > 0) {
					bigImagUrls = new ArrayList<String>();
					smallImagUrls = new ArrayList<String>();
					for (int i = 0; i < goodsImages.length(); i++) {
						if (goodsImages.optJSONObject(i) != null) {
							String bigImgUrl = goodsImages.optJSONObject(i).optString("ImgFile");
							String samllImgUrl = goodsImages.optJSONObject(i).optString("SmallImgFile");
							bigImagUrls.add(bigImgUrl);
							smallImagUrls.add(samllImgUrl);
						}
					}

				}
			}
		}
	}
}
