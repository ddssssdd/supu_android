package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.IndexPicBean;

/**
 * @author sss 首页顶部滑动图片解析器
 */
public class IndexGalleryHandler extends DefaultJSONData {
	private ArrayList<IndexPicBean> picBeans;

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray picList = data.optJSONArray("PicList");
			if (picList != null) {
				picBeans = new ArrayList<IndexPicBean>();
				int len = picList.length();
				for (int i = 0; i < len; i++) {
					IndexPicBean bean = new IndexPicBean();
					JSONObject pic = picList.optJSONObject(i);
					bean.picUrl = pic.optString("PicUrl");
					bean.picSort = pic.optString("PicSort");
					bean.linkType = pic.optString("LinkType");
					bean.linkData = pic.optString("LinkData");
					System.out.println("bean.linkData:" + bean.linkData);
					picBeans.add(bean);
				}
			}
		}
	}

	public ArrayList<IndexPicBean> getPicBeans() {
		return picBeans;
	}
}
