package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import cc.android.supu.bean.ConsigneeBean;

/**
 * 获取货物的地址列表
 * 
 * @author zsx
 * 
 */
public class ConsigneeListHandler extends DefaultJSONData {

	/** 货物地址列表 */
	public ArrayList<ConsigneeBean> consigneeList;
	/** 货物地址bean */
	private ConsigneeBean consigneeBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if (object == null) {
			return;
		}

		consigneeList = new ArrayList<ConsigneeBean>();
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray Consigneelist = data.optJSONArray("Consigneelist");
			if (Consigneelist != null) {
				for (int index = 0; index < Consigneelist.length(); index++) {
					if (Consigneelist.optJSONObject(index) != null) {
						consigneeBean = new ConsigneeBean();
						String address = Consigneelist.optJSONObject(index)
								.optString("Address");
						String addressInfo = Consigneelist.optJSONObject(index)
								.optString("AddressInfo");
						
						int start = addressInfo.length() - address.length();
						if (start > 0) {
							String address1 = addressInfo.substring(0, start);
							consigneeBean.AddressInfo = address;
							consigneeBean.Address = address1;
						} else {
							consigneeBean.AddressInfo = addressInfo;
							consigneeBean.Address = address;
						}

						consigneeBean.AreaID = Consigneelist.optJSONObject(
								index).optString("AreaID");
						consigneeBean.CityID = Consigneelist.optJSONObject(
								index).optString("CityID");
						consigneeBean.Consignee = Consigneelist.optJSONObject(
								index).optString("Consignee");
						consigneeBean.ConsigneeID = Consigneelist
								.optJSONObject(index).optInt("ConsigneeID");
						consigneeBean.Email = Consigneelist
								.optJSONObject(index).optString("Email");
						consigneeBean.Mobile = Consigneelist.optJSONObject(
								index).optString("Mobile");
						consigneeBean.ProvinceID = Consigneelist.optJSONObject(
								index).optString("ProvinceID");
						consigneeBean.Tel = Consigneelist.optJSONObject(index)
								.optString("Tel");
						consigneeBean.ZipCode = Consigneelist.optJSONObject(
								index).optString("ZipCode");
						consigneeBean.IsDefault = Consigneelist.optJSONObject(
								index).optBoolean("IsDefault");

						consigneeList.add(consigneeBean);
					}
				}
			}
		}
	}
	/**得到列表**/
	public ArrayList<ConsigneeBean> getConsigneeBean() {
		return consigneeList;
	}

}
