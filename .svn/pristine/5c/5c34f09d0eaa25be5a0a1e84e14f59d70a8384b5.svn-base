package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GoodsActivityBean;

/** 
 * 可享受优惠活动的信息解析器
 * @author sheng
 *
 */
public class GoodsActivitiesHandler extends DefaultJSONData {
	
	public List<GoodsActivityBean> list;
	
	private GoodsActivityBean activityBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			JSONObject data = object.optJSONObject("Data");
			if(data != null){
				JSONArray activityList = data.optJSONArray("ActivityList");
				if(activityList != null){
					list = new ArrayList<GoodsActivityBean>();
					for(int i=0;i<activityList.length();i++){
						JSONObject activity = activityList.optJSONObject(i);
						if(activity != null){
							activityBean = new GoodsActivityBean();
							activityBean.id = activity.optString("Id");
							activityBean.name = activity.optString("Name");
							activityBean.description = activity.optString("Description");
							activityBean.activityImage = activity.optString("ActivityImage");
							//System.out.println("activityBean.activityImage="+activityBean.activityImage);
							list.add(activityBean);
						}
					}
				}
			}
		}
	}
}
