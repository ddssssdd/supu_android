package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.PaymentBean;
import cc.android.supu.bean.ShippingBean;

/**
 * 支付方式及送货方式的信息解析
 * @author sheng
 *
 */
public class PaymentShippingHandler extends DefaultJSONData {
	
	/** 支付方式列表*/
	public ArrayList<PaymentBean> paymentList;
	/** 配送方式列表*/
	public ArrayList<ShippingBean> shippingList;
	
	private PaymentBean paymentBean;
	
	private ShippingBean shippingBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		JSONObject data = object.optJSONObject("Data");
		if(data != null){
			
			JSONArray paymentList = data.optJSONArray("PaymentList");
			if(paymentList != null){
				this.paymentList = new ArrayList<PaymentBean>();
				for(int i=0;i<paymentList.length();i++){
					paymentBean = new PaymentBean();
					paymentBean.paymentID = paymentList.optJSONObject(i).optString("PaymentID");
					paymentBean.paymentCode = paymentList.optJSONObject(i).optString("PaymentCode");
					paymentBean.paymentName = paymentList.optJSONObject(i).optString("PaymentName");
					paymentBean.paymentDesc = paymentList.optJSONObject(i).optString("PaymentDesc");
					paymentBean.ordering = paymentList.optJSONObject(i).optString("Ordering");
					paymentBean.isOnline = paymentList.optJSONObject(i).optString("IsOnline");
					
					this.paymentList.add(paymentBean);
				}
			}
			
			JSONArray shippingList = data.optJSONArray("ShippingList");
			if(shippingList != null){
				this.shippingList = new ArrayList<ShippingBean>();
				for(int j=0;j<shippingList.length();j++){
					shippingBean = new ShippingBean();
					shippingBean.shippingID = shippingList.optJSONObject(j).optString("ShippingID");
					shippingBean.shippingCode = shippingList.optJSONObject(j).optString("ShippingCode");
					shippingBean.shippingName = shippingList.optJSONObject(j).optString("ShippingName");
					shippingBean.shippingDesc = shippingList.optJSONObject(j).optString("ShippingDesc");
					shippingBean.ordering = shippingList.optJSONObject(j).optString("Ordering");
					shippingBean.basicFee = shippingList.optJSONObject(j).optString("BasicFee");
					shippingBean.stepFee = shippingList.optJSONObject(j).optString("StepFee");
					shippingBean.transitTime = shippingList.optJSONObject(j).optString("TransitTime");
					shippingBean.freeLimit = shippingList.optJSONObject(j).optString("FreeLimit");
					
					this.shippingList.add(shippingBean);
				}
			}
		}
	}
}
