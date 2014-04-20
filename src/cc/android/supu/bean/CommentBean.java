package cc.android.supu.bean;

import java.io.Serializable;
/**
 * 评论信息bean
 * @author sheng
 *
 */
public class CommentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 评论编号*/
	public String id;
	
	/** 评论分值*/
	public int goodsScore;
	
	/** 评论内容*/
	public String commentContent;
	
	/** 评论时间*/
	public String commentTime;
	
	
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(id, goodsScore, commentContent, commentTime);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof CommentBean) {
			CommentBean other = (CommentBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
