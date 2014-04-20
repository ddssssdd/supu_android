package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.OrderInfoBean;

/**
 * 获取订单详细
 * 
 * @author zsx
 * 
 */
public class OrderInfoHandler extends DefaultJSONData {

	/** 订单编号 */
	public int OrderSN;
	/** 订单流水号 */
	public String RunningNumber;
	/** 收货人 */
	public String Consignee;
	/** 省编号 */
	public String ProvinceID;
	/** 市编号 */
	public String CityID;
	/** 县区编号*/
	public String AreaID;
	/** 邮政编码 */
	public String ZipCode;
	/** 收货地址*/
	public String Address;
	/** 电话 */
	public String Tel;
	/** 手机 */
	public String Mobile;
	/** 优惠金额*/
	public String Discount;
	/** 订单金额*/
	public String OrderAmount;
	/** 订单状态 */
	public String OrderStatus;
	/** 邮箱 */
	public String Email;
	/**支付方式 */
	public String PayName;
	/** 支付状态 */
	public String PayStatus;
	/** 配送方式*/
	public String ShippingName;
	/** 配送费*/
	public String ShippingFee;
	/** 订单用户账号*/
	public String Account;
	/** 订单用户编号*/
	public int MemberId;
	/** 订单提交时间 */
	public String AddTime;
	/** 现金账户金额*/
	public String CashPrice;
	/** 银联手机支付安全控件数据*/
	public String UPPayData;
	/** 商品小计*/
	public String GoodsSubtotal;
	/** 优惠券优惠金额*/
	public String TicketDiscount;
	
	/** 订单详情中的商品*/
	public ArrayList<OrderInfoBean> orderInfoList;
	private OrderInfoBean orderInfoBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			UPPayData = data.optString("UPPayData");
//			System.out.println("1---UPPayData="+UPPayData);
			if(data.optJSONObject("Order") != null){
				OrderSN = data.optJSONObject("Order").optInt("OrderSN");
				RunningNumber = data.optJSONObject("Order").optString("RunningNumber");
				Consignee = data.optJSONObject("Order").optString("Consignee");
				ProvinceID = data.optJSONObject("Order").optString("ProvinceID");
				CityID = data.optJSONObject("Order").optString("CityID");
				AreaID = data.optJSONObject("Order").optString("AreaID");
				ZipCode = data.optJSONObject("Order").optString("ZipCode");
				Address = data.optJSONObject("Order").optString("Address");
				Tel = data.optJSONObject("Order").optString("Tel");
				Mobile = data.optJSONObject("Order").optString("Mobile");
				Discount = data.optJSONObject("Order").optString("Discount");
				OrderAmount = data.optJSONObject("Order").optString("OrderAmount");
				GoodsSubtotal = data.optJSONObject("Order").optString("GoodsSubtotal");
				OrderStatus = data.optJSONObject("Order").optString("OrderStatus");
				Email = data.optJSONObject("Order").optString("Email");
				PayName = data.optJSONObject("Order").optString("PayName");
				PayStatus = data.optJSONObject("Order").optString("PayStatus");
				ShippingName = data.optJSONObject("Order").optString("ShippingName");
				ShippingFee = data.optJSONObject("Order").optString("ShippingFee");
				Account = data.optJSONObject("Order").optString("Account");
				MemberId = data.optJSONObject("Order").optInt("MemberId");
				AddTime = data.optJSONObject("Order").optString("AddTime");
				CashPrice = data.optJSONObject("Order").optString("CashPrice");
				TicketDiscount = data.optJSONObject("Order").optString("TicketDiscount");
				
				JSONArray OrderDetail = data.optJSONObject("Order").optJSONArray("OrderDetail");
				orderInfoList = new ArrayList<OrderInfoBean>();
				if (OrderDetail != null) {
					for (int index = 0; index < OrderDetail.length(); index++) {
						orderInfoBean = new OrderInfoBean();
						orderInfoBean.Id = OrderDetail.optJSONObject(index).optInt("Id");
						orderInfoBean.OrderSN = OrderDetail.optJSONObject(index).optInt("OrderSN");
						orderInfoBean.GoodsSN = OrderDetail.optJSONObject(index).optString("GoodsSN");
						orderInfoBean.GoodsName = OrderDetail.optJSONObject(index).optString("GoodsName");
						orderInfoBean.ImgFile = OrderDetail.optJSONObject(index).optString("ImgFile");
						orderInfoBean.Price = OrderDetail.optJSONObject(index).optString("Price");
						orderInfoBean.Discount = OrderDetail.optJSONObject(index).optString("Discount");
						orderInfoBean.Count = OrderDetail.optJSONObject(index).optInt("Count");
						orderInfoBean.isGift = OrderDetail.optJSONObject(index).optInt("IsGift");
						orderInfoBean.Integral = OrderDetail.optJSONObject(index).optString("Integral");

						orderInfoList.add(orderInfoBean);
						System.out.println("-------//////////////------");
						}
				}
			}
		}
		
	}
	

}
