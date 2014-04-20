package cc.android.supu.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.bean.FilterBean.BrandBean;
import cc.android.supu.bean.FilterBean.CategoryBean;
import cc.android.supu.bean.FilterBean.PriceBean;

/**
 * @author sss 筛选解析类
 */
public class FilterHandler extends DefaultJSONData {
	public ArrayList<CategoryBean> categoryBeans;
	public ArrayList<PriceBean> priceBeans;
	public ArrayList<BrandBean> brandBeans;
	private String categoryId, brandId, priceId;

	private BrandBean choosedBrandBean;

	public FilterHandler(String categoryId, String brandId, String priceId) {
		// TODO Auto-generated constructor stub
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.priceId = priceId;
		choosedBrandBean = null;
	}

	@Override
	public void parse(JSONObject object) {
		JSONObject data = object.optJSONObject("Data");
		if (data != null) {
			JSONArray jsonCategorys = data.optJSONArray("CategoryList");
			if (jsonCategorys != null && jsonCategorys.length() > 0) {
				categoryBeans = new ArrayList<CategoryBean>();
				int len = jsonCategorys.length();
				for (int i = 0; i < len; i++) {
					JSONObject jsonCategory = jsonCategorys.optJSONObject(i);
					CategoryBean bean = new CategoryBean();
					bean.id = jsonCategory.optString("CategoryId");
					bean.name = jsonCategory.optString("CategoryName", "");
					if (categoryId != null && categoryId.equals(bean.id)) {
						bean.type = 1;
					} else {
						bean.type = 0;
					}
					if (!"".equals(bean.name.trim())) {
						categoryBeans.add(bean);
					}
				}
			}
			JSONArray jsonPrices = data.optJSONArray("PriceList");
			if (jsonPrices != null && jsonPrices.length() > 0) {
				priceBeans = new ArrayList<PriceBean>();
				int len = jsonPrices.length();
				for (int i = 0; i < len; i++) {
					JSONObject jsonPrice = jsonPrices.optJSONObject(i);
					PriceBean bean = new PriceBean();
					bean.max = (float) jsonPrice.optDouble("Max", 0);
					bean.min = (float) jsonPrice.optDouble("Min", 0);
					bean.id = bean.name = jsonPrice.optString("Name", "");
					if (priceId != null && priceId.equals(bean.id)) {
						bean.type = 1;
					} else {
						bean.type = 0;
					}
					if (!"".equals(bean.name.trim())) {
						priceBeans.add(bean);
					}
				}
			}

			JSONArray jsonBrands = data.optJSONArray("BrandList");
			if (jsonBrands != null && jsonBrands.length() > 0) {
				brandBeans = new ArrayList<BrandBean>();
				int len = jsonBrands.length();
				for (int i = 0; i < len; i++) {
					JSONObject jsonBrand = jsonBrands.optJSONObject(i);
					BrandBean bean = new BrandBean();
					StringBuffer buffer = new StringBuffer();
					JSONArray jsoncIDs = jsonBrand.optJSONArray("CategoryIds");
					if (jsoncIDs != null && jsoncIDs.length() > 0) {
						int size = jsoncIDs.length();
						for (int j = 0; j < size; j++) {
							buffer.append(jsoncIDs.optString(j, "")).append(",");
						}
					}
					bean.categoryIds = buffer.substring(0, buffer.length() - 1);
					bean.id = jsonBrand.optString("BrandID");
					bean.name = jsonBrand.optString("BrandName", "");
					if (brandId != null && brandId.equals(bean.id)) {
						choosedBrandBean = bean;
						bean.type = 1;
					} else {
						bean.type = 0;
					}
					if (!"".equals(bean.name)) {
						brandBeans.add(bean);
					}
				}
			}
		}
		filterCategory(choosedBrandBean);
		filterBrand(categoryId);
	}

	private void filterCategory(BrandBean choosedBean) {
		if (categoryBeans == null || choosedBean == null)
			return;
		for (CategoryBean category : categoryBeans) {
			if (category.type != 1) {
				if (choosedBean.categoryIds.contains(category.id))
					category.type = 0;
				else
					category.type = -1;

			}
		}
	}

	private void filterBrand(String categoryId) {
		if (brandBeans == null || categoryId == null)
			return;
		for (BrandBean bean : brandBeans) {
			if (bean.type != 1) {
				if (bean.categoryIds.contains(categoryId))
					bean.type = 0;
				else
					bean.type = -1;
			}
		}

	}
}
