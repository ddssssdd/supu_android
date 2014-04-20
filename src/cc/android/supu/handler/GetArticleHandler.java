package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GreedBean;
import cc.android.supu.bean.GreedTitleBean;

/**
 * @author  zsx 孕育宝典标题Handler
 */
public class GetArticleHandler extends DefaultJSONData {

	public ArrayList<GreedTitleBean> greedTitleList;
	/** 当前页索引 */
	public int pageIndex;
	/** 每页显示条数 */
	public int pageSize;
	/** 总记录数 */
	public int recordCount;
	
	
	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			pageIndex = data.optJSONObject("PageInfo").optInt("PageIndex");
			pageSize = data.optJSONObject("PageInfo").optInt("PageSize");
			recordCount = data.optJSONObject("PageInfo").optInt("RecordCount");
			JSONArray list = data.optJSONArray("ArticleList");
			if (list != null) {
				greedTitleList = new ArrayList<GreedTitleBean>();
				int len = list.length();
				for (int i = 0; i < len; i++) {
					JSONObject greed = list.optJSONObject(i);
					GreedTitleBean bean = new GreedTitleBean();
					bean.ID = greed.optString("ID");
					bean.ArticleTitle = greed.optString("ArticleTitle");
					greedTitleList.add(bean);
				}
			}
		}
	}
}
