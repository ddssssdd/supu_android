package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.ProcessBean;

/**
 * 物流详情解析器
 * @author sheng
 *
 */
public class OrderProcessHandler extends DefaultJSONData {
	
	private ProcessBean processBean; 
	public ArrayList<ProcessBean> list;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			JSONObject data = object.optJSONObject("Data");
			JSONArray processList = data.optJSONArray("ProcessList");
			if(processList != null){
				list = new ArrayList<ProcessBean>();
				for(int i=0;i<processList.length();i++){
					if(processList.optJSONObject(i) != null){
						processBean = new ProcessBean();
						processBean.note = processList.optJSONObject(i).optString("Note");
						processBean.time = processList.optJSONObject(i).optString("Time");
						
						list.add(processBean);
						
					}
				}
			}
		}
	}
}
