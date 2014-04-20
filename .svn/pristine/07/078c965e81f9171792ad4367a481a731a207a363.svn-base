package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.TicketBean;

/**
 * 优惠券列表信息解析器
 * @author sheng
 *
 */
public class TicketListHandler extends DefaultJSONData {
	
	/** 当前页索引*/
	public int pageIndex;
	/** 每页显示条数*/
	public int pageSize;
	/** 总记录数*/
	public int recordCount;
	/** 优惠券列表*/
	public ArrayList<TicketBean> ticketsList;
	/** 优惠券bean*/
	private TicketBean ticketBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		
		if(object == null){
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		pageIndex = data.optJSONObject("PageInfo").optInt("PageIndex");
		pageSize = data.optJSONObject("PageInfo").optInt("PageSize");
		recordCount = data.optJSONObject("PageInfo").optInt("RecordCount");
		
		ticketsList = new ArrayList<TicketBean>();
		JSONArray ticketList = data.optJSONArray("TicketList");
		if(ticketList != null){
			for(int index=0;index<ticketList.length();index++){
				if(ticketList.optJSONObject(index) != null){
					ticketBean = new TicketBean();
					ticketBean.ticketNo = ticketList.optJSONObject(index).optString("TicketNo");
					ticketBean.ticketId = ticketList.optJSONObject(index).optString("TicketId");
					ticketBean.ticketName = ticketList.optJSONObject(index).optString("TicketName");
					ticketBean.beginTime = ticketList.optJSONObject(index).optString("BeginTime");
					ticketBean.endTime = ticketList.optJSONObject(index).optString("EndTime");
					ticketBean.status = ticketList.optJSONObject(index).optInt("Status");
					ticketBean.isUsed = ticketList.optJSONObject(index).optBoolean("IsUsed");
					
					ticketsList.add(ticketBean);
				}
			}
		}
	}
}
