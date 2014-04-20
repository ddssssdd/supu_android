package cc.android.supu.handler;

import org.json.JSONObject;

import cc.android.supu.bean.OrderBean;

/**
 * 提交订单信息解析
 * @author sheng
 *
 */
public class SubmitOrderhandler extends DefaultJSONData {
	
	public OrderBean order;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			JSONObject data = object.optJSONObject("Data");
			if(data != null){
				JSONObject orderJson = data.optJSONObject("Order");
				if(orderJson != null){
					order = new OrderBean();
					order.OrderSN =	orderJson.optString("OrderSN");
					order.Consignee = orderJson.optString("Consignee");
					order.ProvinceID = orderJson.optString("ProvinceID");
					order.CityID = orderJson.optString("CityID");
					order.AreaID = orderJson.optString("AreaID");
					order.ZipCode = orderJson.optString("ZipCode");
					order.Address = orderJson.optString("Address");
					order.Tel = orderJson.optString("Tel");
					order.Mobile = orderJson.optString("Mobile");
					order.Discount = orderJson.optString("Discount");
					order.OrderAmount = orderJson.optString("OrderAmount");
					order.OrderStatus = orderJson.optString("OrderStatus");
					order.Email = orderJson.optString("Email");
					order.PayName = orderJson.optString("PayName");
					order.PayStatus = orderJson.optString("PayStatus");
					order.ShippingName = orderJson.optString("ShippingName");
					order.ShippingFee = orderJson.optString("ShippingFee");
					order.Account = orderJson.optString("Account");
					order.MemberId = orderJson.optString("MemberId");
					order.AddTime = orderJson.optString("AddTime");
				}
			}
		}
	}
}
