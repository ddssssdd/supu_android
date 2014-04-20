package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.OrderBean;

/**
 * 获取订单列表
 * 
 * @author zsx
 * 
 */
public class OrderListHandler extends DefaultJSONData {

	/** 当前页索引 */
	public int pageIndex;
	/** 每页显示条数 */
	public int pageSize;
	/** 总记录数 */
	public int recordCount;
	/** 收藏列表 */
	public List<OrderBean> orderList;
	/** 订单bean */
	private OrderBean orderBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		pageIndex = data.optJSONObject("PageInfo").optInt("PageIndex");
		pageSize = data.optJSONObject("PageInfo").optInt("PageSize");
		recordCount = data.optJSONObject("PageInfo").optInt("RecordCount");
		JSONArray OrderList = data.optJSONArray("OrderList");
		orderList = new ArrayList<OrderBean>();
		if (OrderList != null) {
			for (int index = 0; index < OrderList.length(); index++) {
				orderBean = new OrderBean();
				orderBean.OrderSN = OrderList.optJSONObject(index).optString("OrderSN");
				orderBean.RunningNumber = OrderList.optJSONObject(index).optString("RunningNumber");
				orderBean.Consignee = OrderList.optJSONObject(index).optString("Consignee");
				orderBean.ProvinceID = OrderList.optJSONObject(index).optString("ProvinceID");
				orderBean.CityID = OrderList.optJSONObject(index).optString("CityID");
				orderBean.AreaID = OrderList.optJSONObject(index).optString("AreaID");
				orderBean.ZipCode = OrderList.optJSONObject(index).optString("ZipCode");
				orderBean.Address = OrderList.optJSONObject(index).optString("Address");
				orderBean.Tel = OrderList.optJSONObject(index).optString("Tel");
				orderBean.Mobile = OrderList.optJSONObject(index).optString("Mobile");
				orderBean.Discount = OrderList.optJSONObject(index).optString("Discount");
				orderBean.OrderAmount = OrderList.optJSONObject(index).optString("OrderAmount");
//				System.out.println("orderBean.OrderAmount"+orderBean.OrderAmount);
				orderBean.OrderStatus = OrderList.optJSONObject(index).optString("OrderStatus");
				orderBean.Email = OrderList.optJSONObject(index).optString("Email");
				orderBean.PayName = OrderList.optJSONObject(index).optString("PayName");
				orderBean.PayStatus = OrderList.optJSONObject(index).optString("PayStatus");
				orderBean.ShippingName = OrderList.optJSONObject(index).optString("ShippingName");
				orderBean.ShippingFee = OrderList.optJSONObject(index).optString("ShippingFee");
				orderBean.Account = OrderList.optJSONObject(index).optString("Account");
				orderBean.MemberId = OrderList.optJSONObject(index).optString("MemberId");
				orderBean.AddTime = OrderList.optJSONObject(index).optString("AddTime");
			
					
				orderList.add(orderBean);
				}
			
		}
	}

}
