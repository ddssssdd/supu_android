package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.CityBean;

/**
 * 获得全部省市县信息
 * 
 * @author zsx
 * 
 */
public class GetAllDistrictHandler extends DefaultJSONData {

	/** 版本号 */
	public int version;
	/** 更新时间 */
	public String updated;

	/** 省市区列表 */
	public List<CityBean> cityBeanList;
	/** 省市区bean */
	private CityBean cityBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		version = data.optInt("Version");
		updated = data.optString("Updated");
		
		cityBeanList = new ArrayList<CityBean>();
		JSONArray districtList = data.optJSONArray("DistrictList");
		if (districtList != null) {
			for (int index = 0; index < districtList.length(); index++) {
				if (districtList.optJSONObject(index) != null) {
					cityBean = new CityBean();
					cityBean.setAreaCode(districtList.optJSONObject(index)
							.optString("AreaCode"));
					String name = districtList.optJSONObject(index).optString(
							"AreaName");
					cityBean.setAreaName(name);
					cityBean.setParentID(districtList.optJSONObject(index)
							.optString("ParentID"));
					
					cityBeanList.add(cityBean);
				}
			}
		}
	}
	/** 得到列表 */
	public List<CityBean> getCityBeanList() {
		return cityBeanList;
	}

}
