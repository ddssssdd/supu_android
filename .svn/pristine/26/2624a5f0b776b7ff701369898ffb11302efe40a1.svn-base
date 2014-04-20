package cc.android.supu.handler;

import org.json.JSONObject;

import cc.android.supu.bean.ConsigneeBean;

/**
 * 默认收获地址信息解析类
 * @author sheng
 *
 */
public class DefaultConsigneeHandler extends DefaultJSONData {
	
	/** 收件信息bean*/
	public ConsigneeBean consigneeBean;
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if(object != null){
			JSONObject data =  object.optJSONObject("Data");
			if(data != null){
				JSONObject consigneeObject = data.optJSONObject("Consignee");
				if(consigneeObject != null){
					consigneeBean = new ConsigneeBean();
					String address = consigneeObject.optString("Address");
					String addressInfo = consigneeObject.optString("AddressInfo");
					
					int start = addressInfo.length() - address.length();
					if (start > 0) {
						String address1 = addressInfo.substring(0, start);
						consigneeBean.AddressInfo = address;
						consigneeBean.Address = address1;
					} else {
						consigneeBean.AddressInfo = addressInfo;
						consigneeBean.Address = address;
					}
					
//					consigneeBean.Address = consigneeObject.optString("Address");
//					consigneeBean.AddressInfo = consigneeObject.optString("AddressInfo");
					consigneeBean.AreaID = consigneeObject.optString("AreaID");
					consigneeBean.CityID = consigneeObject.optString("CityID");
					consigneeBean.Consignee = consigneeObject.optString("Consignee");
					consigneeBean.ConsigneeID = consigneeObject.optInt("ConsigneeID");
					consigneeBean.Email = consigneeObject.optString("Email");
					consigneeBean.Mobile = consigneeObject.optString("Mobile");
					consigneeBean.ProvinceID = consigneeObject.optString("ProvinceID");
					consigneeBean.Tel = consigneeObject.optString("Tel");
					consigneeBean.ZipCode = consigneeObject.optString("ZipCode");
					consigneeBean.IsDefault = consigneeObject.optBoolean("IsDefault");
				}
			}
		}
		
	}
}
