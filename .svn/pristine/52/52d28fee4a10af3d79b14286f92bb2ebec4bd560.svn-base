package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GreedBean;

/**
 * @author sss 孕育宝典二级分类Handler
 */
public class GreedSubHandler extends DefaultJSONData {

	public int pageIndex;
	public int pageSize;
	public int recordCount;
	public ArrayList<GreedBean> greedBeans;

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONObject pageInfo = data.optJSONObject("PageInfo");
			if (pageInfo != null) {
				pageIndex = pageInfo.optInt("PageIndex", 1);
				pageSize = pageInfo.optInt("PageSize", 10);
				recordCount = pageInfo.optInt("RecordCount", 0);
			}

			JSONArray list = data.optJSONArray("ArticleList");
			if (list != null) {
				greedBeans = new ArrayList<GreedBean>();
				int len = list.length();
				for (int i = 0; i < len; i++) {
					JSONObject greed = list.optJSONObject(i);
					GreedBean bean = new GreedBean();
					bean.ID = greed.optString("ID");
					bean.categoryName = greed.optString("ArticleTitle");
					greedBeans.add(bean);
				}
			}
		}
	}
}
