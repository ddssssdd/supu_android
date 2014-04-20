package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.GoodsBean;

/**
 * 购物车解析类
 * 
 * @author sheng
 * 
 */
public class ShopCarHandler extends DefaultJSONData {

	/** 总金额 */
	public String sumAmount;
	/** 优惠金额 */
	public String discountAmount;
	
	public ArrayList<GoodsBean> list;

	private GoodsBean goodsBean;
	/** 是否处于编辑状态*/
	public static boolean isEditting = false;
	/** 是否编辑过*/
	public static boolean isEdited = false;
	/**　是否处于删除操作状态*/
	public static boolean isDeleting = false;
	/** 是否在编辑过程中推出过*/
	public static boolean haveExit = false;
	/** 保存编辑过的goodsList*/
	public static ArrayList<GoodsBean> goodsList = null;
	
	
	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub
		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			sumAmount = fomatString(data.optString("SumAmount"));
			discountAmount = fomatString(data.optString("DiscountAmount"));
			JSONArray cartList = data.optJSONArray("CartList");
			if (cartList != null) {
				list = new ArrayList<GoodsBean>();
				for (int i = 0; i < cartList.length(); i++) {
					if (cartList.optJSONObject(i) != null) {
						goodsBean = new GoodsBean();
						goodsBean.goodsSN = cartList.optJSONObject(i).optString("GoodsSN");
						goodsBean.goodsName = cartList.optJSONObject(i).optString("GoodsName");
						goodsBean.imgFile = cartList.optJSONObject(i).optString("ImgFile");
						goodsBean.isGift = cartList.optJSONObject(i).optBoolean("IsGift");
						goodsBean.count = cartList.optJSONObject(i).optInt("Count");
						goodsBean.tempCount = goodsBean.count + "";
						goodsBean.shopPrice = cartList.optJSONObject(i).optString("ShopPrice");
						goodsBean.isNoStock = cartList.optJSONObject(i).optBoolean("IsNoStock");
						goodsBean.discountAmount = cartList.optJSONObject(i).optString("DiscountAmount");
						goodsBean.stockCount = -1;
						if(isEditting && goodsList != null && !isDeleting){
							if(goodsList.size() > i && goodsList.get(i).goodsSN.equals(goodsBean.goodsSN) && !goodsBean.isGift){
								goodsBean.tempCount = goodsList.get(i).tempCount;
								if(!goodsList.get(i).isNoStock){
									int subCount = goodsBean.count - goodsList.get(i).count;
									if(subCount > 0){//处理在编辑状态时将商品加入购物车
										goodsBean.tempCount = Integer.toString(Integer.parseInt(goodsBean.tempCount) + subCount);
									}
								}
							}
						}
						
						list.add(goodsBean);
					}
				}
			}
		}
	}

	/**
	 * 将金额格式化为0.00形式
	 * @param str
	 * @return
	 */
	public String fomatString(String str) {
		int pointIndex = str.indexOf('.');
		if (pointIndex == -1) {
			return str + ".00";
		} else if (pointIndex == str.length() - 2) {
			return str + "0";
		} else if (pointIndex == str.length() - 3) {
			return str;
		}
		return null;
	}
	


}
