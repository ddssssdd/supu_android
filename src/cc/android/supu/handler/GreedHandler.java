package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GreedBean;

/**
 * @author sss 孕育宝典Handler
 */
public class GreedHandler extends DefaultJSONData {

	public ArrayList<GreedBean> greedBeans;

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray list = data.optJSONArray("ArticleCategoryList");
			if (list != null) {
				greedBeans = new ArrayList<GreedBean>();
				int len = list.length();
				for (int i = 0; i < len; i++) {
					JSONObject greed = list.optJSONObject(i);
					GreedBean bean = new GreedBean();
					bean.ID = greed.optString("ID");
					bean.categoryName = greed.optString("CategoryName");
					bean.sort = greed.optInt("Sort", 0);
					bean.picUrl = greed.optString("PicUrl");
					greedBeans.add(bean);
				}
			}
		}
	}
}
