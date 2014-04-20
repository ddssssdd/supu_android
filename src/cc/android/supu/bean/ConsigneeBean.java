package cc.android.supu.bean;

import java.io.Serializable;

/**
 * 地址信息bean
 * 
 * @author zsx
 * 
 */
public class ConsigneeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 地址 */
	public String Address;

	/** 包含省、市、区的详细地址 */
	public String AddressInfo;

	/** 县区编号 */
	public String AreaID;
	/** 市编号 */
	public String CityID;
	/** 收货人 */
	public String Consignee;
	/** 地址信息编号 */
	public int ConsigneeID;
	/** 邮箱 */
	public String Email;
	/** 手机 */
	public String Mobile;
	/** 省 */
	public String ProvinceID;
	/** 电话 */
	public String Tel;
	/** 邮编 */
	public String ZipCode;
	/** 默认地址 */
	public boolean IsDefault;

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(Address, AddressInfo, AreaID, CityID,
				Consignee, ConsigneeID, Email, Mobile, ProvinceID, Tel,
				ZipCode, IsDefault);
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ConsigneeBean) {
			ConsigneeBean other = (ConsigneeBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
