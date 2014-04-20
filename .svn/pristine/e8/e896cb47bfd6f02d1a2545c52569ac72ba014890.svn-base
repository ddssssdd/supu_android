package cc.android.supu.bean;

import java.io.Serializable;
/** 
 * 购买评论bean
 * @author sheng
 *
 */
public class GoodsCommentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 评论信息*/
	public CommentBean commentBean;
	
	/** 客户bean*/
	public MemberBean memberBean;
	
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(commentBean, memberBean);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GoodsCommentBean) {
			GoodsCommentBean other = (GoodsCommentBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
	
	
	

}
