package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.CategoryBrandBean;

/**
 * @author sss 分类、品牌列表解析类
 */
public class CategoryBrandHandler extends DefaultJSONData {
	/** 是分类还是品牌，分类true,品牌false */
	private boolean isCategory = true;
	/** 分类或品牌Beans */
	private ArrayList<CategoryBrandBean> beans;

	public ArrayList<CategoryBrandBean> getBeans() {
		return beans;
	}

	/**
	 * @param isCategory
	 *            是分类还是品牌，分类true,品牌false
	 */
	public CategoryBrandHandler(boolean isCategory) {
		this.isCategory = isCategory;
	}

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data == null)
			return;
		if (isCategory) {
			parseCategory(data);
		} else {
			parseBrand(data);
		}
	}

	/**
	 * 解析分类
	 * 
	 * @param object
	 */
	private void parseCategory(JSONObject object) {
		JSONArray list = object.optJSONArray("CategoryList");
		if (list != null) {
			int len = list.length();
			beans = new ArrayList<CategoryBrandBean>();
			for (int i = 0; i < len; i++) {
				CategoryBrandBean categoryBean = new CategoryBrandBean();
				JSONObject json = list.optJSONObject(i);
				categoryBean.Id = json.optString("CategoryID", "");
				categoryBean.name = json.optString("CategoryName", "");
				categoryBean.sortOrder = json.optString("SortOrder", "0");
				categoryBean.categoryImg = json.optString("Img");
				categoryBean.categroyParentId = json.optString("ParentID", "");
				String leaf = json.optString("IsLeaf", "");
				if ("True".equals(leaf))
					categoryBean.categoryIsLeaf = true;
				else
					categoryBean.categoryIsLeaf = false;
				beans.add(categoryBean);
			}
		}
	}

	/**
	 * 解析品牌
	 * 
	 * @param object
	 */
	private void parseBrand(JSONObject object) {
		JSONArray list = object.optJSONArray("BrandList");
		if (list != null) {
			int len = list.length();
			beans = new ArrayList<CategoryBrandBean>();
			for (int i = 0; i < len; i++) {
				CategoryBrandBean categoryBean = new CategoryBrandBean();
				JSONObject json = list.optJSONObject(i);
				categoryBean.Id = json.optString("BrandID", "");
				categoryBean.name = json.optString("BrandName", "");
				categoryBean.sortOrder = json.optString("SortOrder", "0");
				beans.add(categoryBean);
			}
		}
	}
}
