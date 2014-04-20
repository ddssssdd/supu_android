package cc.android.supu.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GoodsBean;

/**
 * 获得收藏列表
 * 
 * @author zsx
 * 
 */
public class FavoritesListHandler extends DefaultJSONData {

	/** 当前页索引 */
	public int pageIndex;
	/** 每页显示条数 */
	public int pageSize;
	/** 总记录数 */
	public int recordCount;
	/** 收藏列表 */
	public List<GoodsBean> goodsList;
	/** 收藏时间列表 */
	public List<String> timeList;
	/** 商品bean */
	private GoodsBean goodsBean;
	/** 商品编号 */
	private String GoodsSN;
	/** 商品标语 */
	private int PriceSnapshot;
	/** 添加时间 */
	private String AddTime;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		pageIndex = data.optJSONObject("PageInfo").optInt("PageIndex");
		pageSize = data.optJSONObject("PageInfo").optInt("PageSize");
		recordCount = data.optJSONObject("PageInfo").optInt("RecordCount");
		JSONArray favoritesList = data.optJSONArray("FavoritesList");
		goodsList = new ArrayList<GoodsBean>();
		timeList = new ArrayList<String>();
		if (favoritesList != null) {
			for (int index = 0; index < favoritesList.length(); index++) {
				GoodsSN = favoritesList.optJSONObject(index).optString(
						"GoodsSN");
				PriceSnapshot = favoritesList.optJSONObject(index).optInt(
						"PriceSnapshot");
				AddTime = favoritesList.optJSONObject(index).optString(
						"AddTime");
				String time = getTime(AddTime);
				JSONObject data1 = favoritesList.optJSONObject(index)
						.optJSONObject("Goods");
				timeList.add(time);
				if (data1 != null) {
					goodsBean = new GoodsBean();
					goodsBean.goodsSN = data1.optString("GoodsSN");
					goodsBean.goodsName = data1.optString("GoodsName");
					goodsBean.goodsSlogan = data1.optString("GoodsSlogan");
					goodsBean.commentCount = data1.optString("CommentCount");
					goodsBean.categoryID = data1.optString("CategoryID");
					goodsBean.brandID = data1.optString("BrandID");
					goodsBean.goodsScore = data1.optString("GoodsScore");
					goodsBean.goodsSort = data1.optString("GoodsSort");
					goodsBean.integral = data1.optString("Integral");
					goodsBean.imgFile = data1.optString("ImgFile");
					goodsBean.shopPrice = data1.optString("ShopPrice");
					goodsBean.marketPrice = data1.optString("MarketPrice");
					goodsBean.isNoStock = data1.optBoolean("IsNoStock");
					goodsBean.addTime = data1.optString("AddTime");
					
					goodsList.add(goodsBean);
				}
			}
		}
	}
	/**
	 * 得到时间
	 */
	private String getTime(String scrTime) {
		String strTime = scrTime.trim();
		int length = strTime.length();
		if (length < 13) {
			for (int j = 0; j < 13 - length; j++) {
				strTime = strTime + "0";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Long time = new Long(strTime);
		return format.format(time);
	}
}
