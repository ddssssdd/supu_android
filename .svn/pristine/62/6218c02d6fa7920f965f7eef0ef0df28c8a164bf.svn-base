package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.BannerBean;

public class GetBannerHandler extends DefaultJSONData {

	public ArrayList<BannerBean> list;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray bannerList = data.optJSONArray("BannerList");
			if (bannerList != null) {
				list = new ArrayList<BannerBean>();
				for (int i = 0; i < bannerList.length(); i++) {
					BannerBean bannerBean = new BannerBean();
					JSONObject banner = bannerList.optJSONObject(i);
					bannerBean.picUrl = banner.optString("PicUrl");
					bannerBean.beginTime = banner.optString("BeginTime");
					bannerBean.endTime = banner.optString("EndTime");
					long begin = getTime(bannerBean.beginTime);
					long end = getTime(bannerBean.endTime);
					if (timeBool(begin, end))
						list.add(bannerBean);
				}
			}
		}

	}

	/**
	 * 
	 */
	private boolean timeBool(long begin, long end) {
		boolean isAdd = false;
		long currentTime = System.currentTimeMillis();
//		System.out.println("currentTime"+currentTime);
//		System.out.println("begin"+begin);
//		System.out.println("end"+end);
		if (currentTime >= begin && currentTime <= end) {
			isAdd = true;
		}
		return isAdd;
	}

	/**
	 * 得到时间
	 */
	private long getTime(String scrTime) {
		String strTime = scrTime.trim();
		int length = strTime.length();
		if (length < 13) {
			for (int j = 0; j < 13 - length; j++) {
				strTime = strTime + "0";
			}
		}
		long time = Long.parseLong(strTime);
		
		return time;
	}
}
