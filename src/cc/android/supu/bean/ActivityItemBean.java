package cc.android.supu.bean;

import java.io.Serializable;

/**
 * @author sss 活动列表Bean
 */
public class ActivityItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 商品编号 */
	public String goodsSN;
	/** 商品名称 */
	public String goodsName;
	/** 广告语 */
	public String goodsSlogan;
	/** 评论数 */
	public int commentCount;
	/** 类型编号 */
	public String categoryId;
	/** 品牌编号 */
	public String brandId;
	/** 商品评分 */
	public float goodsScore;
	/** 商品排序 */
	public int goodsSort;
	/** 商品积分 */
	public int integral;
	/** 商品图片 */
	public String imgFile;
	/**  市场价 */
	public String marketPrice;
	/**速普价 */
	public String shopPrice;
	/** 是否缺货 */
	public boolean isNoStock;
	/** 创建时间 */
	public String addTime;
	
	public boolean isLoaded = false;

	@Override
	public int hashCode() {
		return BeanTools.createHashcode(goodsSN, goodsName, goodsSlogan, commentCount, categoryId, brandId, goodsScore,
				goodsSort, integral, imgFile, marketPrice, shopPrice, isNoStock, addTime);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o instanceof ActivityItemBean) {
			ActivityItemBean other = (ActivityItemBean) o;
			if (other.hashCode() == this.hashCode())
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("goodsSN:").append(goodsSN).append(" goodsName:").append(goodsName).append(" goodsSlogan:")
				.append(goodsSlogan).append(" commentCount:").append(commentCount).append(" categoryId:")
				.append(categoryId).append(" brandID:").append(brandId).append(" goodsScore:").append(goodsScore)
				.append(" goodsSort:").append(goodsSort).append(" integral:").append(integral).append(" imgFile:")
				.append(imgFile).append(" marketPrice:").append(marketPrice).append(" shopPrice:").append(shopPrice)
				.append(" isNoStock:").append(isNoStock).append(" addTime:").append(addTime);
		return buffer.toString();
	}
}
