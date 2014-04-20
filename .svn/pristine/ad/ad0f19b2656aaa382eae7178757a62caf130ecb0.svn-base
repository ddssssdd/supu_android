package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import cc.android.supu.bean.ActivityBean;
import cc.android.supu.bean.ActivityItemBean;

/**
 * @author sss 活动列表解析类
 */
public class ActivityHandler extends DefaultJSONData {

	/** 活动页面需要 */
	public ActivityBean activityBean;
	/** 分类需要的参数 */
	/** 当前页 */
	public int pageIndex;
	/** 每页显示条数 */
	public int pageSize;
	/** 总记录数 */
	public int recordCount;

	public int pageCount;

	public int len;
	/** 所有商品列表界面共用 */
	public ArrayList<ActivityItemBean> activityItemBeans;

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONObject activity = data.optJSONObject("Activity");
			if (activity != null) {
				activityBean = new ActivityBean();
				activityBean.ID = activity.optString("Id");
				activityBean.name = activity.optString("Name");
				activityBean.description = activity.optString("Description");
				activityBean.activityImage = activity.optString("ActivityImage");
			}
			JSONObject pageInfo = data.optJSONObject("PageInfo");
			if (pageInfo != null) {
				pageIndex = pageInfo.optInt("PageIndex", 1);
				pageSize = pageInfo.optInt("PageSize", 20);
				recordCount = pageInfo.optInt("RecordCount", 0);
				pageCount = (recordCount - 1 + pageSize) / pageSize;
			}

			JSONArray goodsList = data.optJSONArray("GoodsList");
			if (goodsList != null) {
				len = goodsList.length();
				activityItemBeans = new ArrayList<ActivityItemBean>();
				for (int i = 0; i < len; i++) {
					ActivityItemBean bean = new ActivityItemBean();
					JSONObject item = goodsList.optJSONObject(i);
					bean.goodsSN = item.optString("GoodsSN");
					bean.goodsName = item.optString("GoodsName");
					bean.goodsSlogan = item.optString("GoodsSlogan");
					bean.commentCount = item.optInt("CommentCount", 0);
					bean.categoryId = item.optString("CategoryID");
					bean.brandId = item.optString("BrandID");
					bean.goodsScore = (float) item.optDouble("GoodsScore", 0);
					bean.goodsSort = item.optInt("GoodsSort", 0);
					bean.integral = item.optInt("Integral", 0);
					bean.imgFile = item.optString("ImgFile");
					bean.marketPrice = item.optString("MarketPrice");
					bean.shopPrice = item.optString("ShopPrice");
					bean.isNoStock = item.optBoolean("IsNoStock", false);
					// if (isNoStock != null) {
					// bean.isNoStock =
					// item.optString("IsNoStock").equals("true");
					// } else
					// bean.isNoStock = false;
					bean.addTime = item.optString("AddTime");
					activityItemBeans.add(bean);
				}
			}
		}
	}
}
