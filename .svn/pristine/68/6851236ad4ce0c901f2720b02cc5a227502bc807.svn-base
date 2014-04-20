package cc.android.supu.bean;
/**
 *  省 市 区
 * @author zsx
 *
 */
public class CityBean {
	/**城市名 */
	private String areaName;
	/**城市编号 */
	private String areaCode;
	/**父城市编号 */
	private String parentID;
//	/**更新时间 */
//	private String updated;
//	
//	public String getUpdated() {
//		return updated;
//	}
//	public void setUpdated(String updated) {
//		this.updated = updated;
//	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	@Override
	public int hashCode() {
		return BeanTools.createHashcode(areaName, areaCode, parentID);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof CityBean) {
			CityBean other = (CityBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else
			return false;
	}
}
