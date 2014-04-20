package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.TicketBean;
import cc.android.supu.bean.TicketsGiftBean;

/**
 * 获取优惠券可用信息
 * 
 * @author sheng
 * 
 */
public class TicketInfoHandler extends DefaultJSONData {

	/** 优惠券bean */
	public TicketBean ticketBean;
	/** 验证结果 */
	public String validateResult;
	/** 优惠券赠品列表 */
	private ArrayList<TicketsGiftBean> ticketsGiftsList;
	private TicketsGiftBean ticketsGiftBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			validateResult = data.optString("ValidateResult");
			ticketBean = new TicketBean();
			ticketBean.ticketNo = data.optString("TicketNo");
			JSONObject ticket = data.optJSONObject("Ticket");
			if (ticket != null) {
				ticketBean.ticketName = ticket.optString("TicketName");
				ticketBean.ticketDescribe = ticket.optString("TicketDescribe");
				ticketBean.discountAmount = ticket.optString("DiscountAmount");
				ticketBean.discountCumulative = ticket
						.optBoolean("DiscountCumulative");
				ticketBean.discount = ticket.optString("Discount");

				JSONArray ticketsGifts = ticket.optJSONArray("TicketsGifts");
				if (ticketsGifts != null) {
					ticketsGiftsList = new ArrayList<TicketsGiftBean>();
					for (int i = 0; i < ticketsGifts.length(); i++) {
						if (ticketsGifts.optJSONObject(i) != null) {
							ticketsGiftBean = new TicketsGiftBean();
							ticketsGiftBean.presentSN = ticketsGifts
									.optJSONObject(i).optString("PresentSN");
							ticketsGiftBean.presentName = ticketsGifts
									.optJSONObject(i).optString("PresentName");
							ticketsGiftBean.giftCount = ticketsGifts
									.optJSONObject(i).optInt("GiftCount");
							ticketsGiftsList.add(ticketsGiftBean);
						}
					}
				}
			}
		}
	}

}
