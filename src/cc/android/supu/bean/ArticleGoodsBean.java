package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author zsx 孕育宝典列表页Bean
 */
public class ArticleGoodsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 货物名字 **/
	public String GoodsName;
	/** 货物编号 **/
	public String GoodsSN;
	/** 图片URL **/
	public String ImgFile;
	/** 商品价格 **/
	public String Price;
	/** 市场价格 **/
	public String MarketPrice;
	/** 时间 **/
	public String GoodTime;
	/** 商品描述 **/
	public String GoodsDescription;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(GoodsName, GoodsSN, ImgFile, Price,
				MarketPrice, GoodTime, GoodsDescription);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof ArticleGoodsBean) {
			ArticleGoodsBean other = (ArticleGoodsBean) o;
			if (this.toString().equals(other.toString()))
				return true;
			else
				return false;
		} else
			return false;
	}

}
