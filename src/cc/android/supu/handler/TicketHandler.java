package cc.android.supu.handler;

import org.json.JSONObject;

import cc.android.supu.bean.TicketBean;

/**
 * 优惠券信息解析类
 * @author sheng
 *
 */
public class TicketHandler extends DefaultJSONData {
	
	public TicketBean ticketBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		
		if(object == null){
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			JSONObject ticket = data.optJSONObject("Ticket");
			if(ticket != null){
				ticketBean = new TicketBean();
				ticketBean.ticketNo = ticket.optString("TicketNo");
				ticketBean.bindingTime = ticket.optString("BindingTime");
				ticketBean.ticketId = ticket.optString("TicketId");
				ticketBean.ticketName = ticket.optString("TicketName");
				ticketBean.ticketDescribe = ticket.optString("TicketDescribe");
				ticketBean.beginTime = ticket.optString("BeginTime");
				ticketBean.endTime = ticket.optString("EndTime");
				ticketBean.discount = ticket.optString("Discount");
				ticketBean.discountAmount = ticket.optString("DiscountAmount");
				ticketBean.discountCumulative = ticket.optBoolean("DiscountCumulative");
				ticketBean.status = ticket.optInt("Status");
				ticketBean.isUsed = ticket.optBoolean("IsUsed");
				ticketBean.grantAmount = (float) ticket.optDouble("GrantAmount");
			}
		}
	}
}
