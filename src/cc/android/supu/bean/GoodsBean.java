package cc.android.supu.bean;

import java.io.Serializable;

public class GoodsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 在数据表中的唯一标识id*/
	public int id;
	
	/** 商品编号*/
	public String goodsSN;
	
	/** 商品名称*/
	public String goodsName;
	
	/** 商品图片*/
	public String imgFile;
	
	/** 广告语*/
	public String goodsSlogan;
	
	/** 商品评分*/
	public String goodsScore;
	
	/** 商品的排序*/
	public String goodsSort;
	
	/** 商品的积分*/
	public String integral;
	
	/** 是否赠品*/
	public boolean isGift;
	
	/** 商品在购物车中的数量*/
	public int count;
	public String tempCount;
	
	/** 市场价*/
	public String marketPrice;
	
	/** 速普价格*/
	public String shopPrice;
	
	/** 是否缺货*/
	public boolean isNoStock;
	
	/** 商品优惠金额*/
	public String discountAmount;
	
	/** 创建时间*/
	public String addTime;
	
	/** 类型编号*/
	public String categoryID;
	
	/** 品牌编号*/
	public String brandID;
	
	/** 评论数*/
	public String commentCount;
	
	/** 咨询数*/
	public String consultCount;
	
	/** 修改后的数量*/
	public int modifiedCount;
	/** */
	public boolean isContentChange;
	/** 分享内容*/
	public String shareText;
	/** 是否正在删除*/
	public boolean isDeleting;
	/** 正在删除的位置*/
	public int deletePosition;
	/** 库存数量*/
	public int stockCount;
	
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return BeanTools.createHashcode(id, goodsSN, goodsName, marketPrice, shopPrice,
		goodsSlogan, goodsScore,goodsSort,integral, categoryID, brandID, commentCount, consultCount, imgFile,
		isGift, count, isNoStock, discountAmount, addTime,modifiedCount, stockCount);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof GoodsBean) {
			GoodsBean other = (GoodsBean) o;
			if (this.hashCode() == other.hashCode())
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}
