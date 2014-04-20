package cc.android.supu.bean;

import java.io.Serializable;

/**
 * 商品详情里的商品图片bean
 * 
 * @author sheng
 * 
 */
public class GoodsImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 商品编号 */
	// public String goodsSN;
	/** 图片名称 */
	public String bigImgUrl;
	/** 图片扩展名 */
	public String samllImgUrl;

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(bigImgUrl, samllImgUrl);
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GoodsImageBean) {
			GoodsImageBean other = (GoodsImageBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
