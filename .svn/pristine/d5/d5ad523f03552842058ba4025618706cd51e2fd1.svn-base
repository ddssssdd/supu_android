package cc.android.supu.bean;

import java.io.Serializable;

public class IndexGoodBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 商品编号 */
	public String goodsSN;
	/** 商品图片 */
	public String imageFile;
	/** 商品价格 */
	public String price;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(goodsSN, imageFile, price);
	}

	public IndexGoodBean(String goodsSN, String imageFile, String price) {
		this.goodsSN = goodsSN;
		this.imageFile = imageFile;
		this.price = price;
	}

	public IndexGoodBean() {
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof IndexGoodBean) {
			IndexGoodBean other = (IndexGoodBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
