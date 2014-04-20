package cc.android.supu.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 商品咨询bean
 * @author sheng
 *
 */
public class GoodsConsultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConsultBean consultBean;
	
	public ReplyBean replyBean;
	
	public List<MemberBean> memberList;
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(consultBean, replyBean, memberList);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GoodsConsultBean) {
			GoodsConsultBean other = (GoodsConsultBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
