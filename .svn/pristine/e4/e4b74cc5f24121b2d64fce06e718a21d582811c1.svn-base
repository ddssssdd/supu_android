package cc.android.supu.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.ArticleGoodsBean;

/**
 * 
 * @author zsx
 * 
 */
public class GreedInfoHandler extends DefaultJSONData {

	/** 分类 */
	public int CategoryID;
	/** id */
	public int ID;
	/** 文章标题 */
	public String Title;
	/** 文章内容 */
	public String Content;
	/** 时间 */
	public String ModifyTime;
	/** 孕婴列表 */
	public ArrayList<ArticleGoodsBean> goodsList;
	/** 孕育宝典列表页Bean */
	private ArticleGoodsBean articleGoodsBean;

	@Override
	public void parse(JSONObject object) {
		// TODO Auto-generated method stub

		if (object == null) {
			return;
		}
		JSONObject data = object.optJSONObject("Data");
		CategoryID = data.optJSONObject("Article").optInt("CategoryID");
		ID = data.optJSONObject("Article").optInt("ID");
		Title = data.optJSONObject("Article").optString("Title");
		Content = data.optJSONObject("Article").optString("Content");
		ModifyTime = data.optJSONObject("Article").optString("ModifyTime");
		JSONArray ArticleGoods = data.optJSONArray("ArticleGoods");
		goodsList = new ArrayList<ArticleGoodsBean>();
		if (ArticleGoods != null) {
			for (int index = 0; index < ArticleGoods.length(); index++) {
				articleGoodsBean = new ArticleGoodsBean();
				articleGoodsBean.GoodsName = ArticleGoods.optJSONObject(index)
						.optString("GoodsName");
//				System.out.println("articleGoodsBean.GoodsName"+articleGoodsBean.GoodsName);
				articleGoodsBean.GoodsSN = ArticleGoods.optJSONObject(index)
						.optString("GoodsSN");
				articleGoodsBean.ImgFile = ArticleGoods.optJSONObject(index)
						.optString("ImgFile");
				articleGoodsBean.Price = ArticleGoods.optJSONObject(
						index).optString("Price");
				articleGoodsBean.MarketPrice =  ArticleGoods
						.optJSONObject(index).optString("MarketPrice");
				articleGoodsBean.GoodsDescription = ArticleGoods.optJSONObject(
						index).optString("GoodsDescription");
				goodsList.add(articleGoodsBean);
			}
		}
	}

}
