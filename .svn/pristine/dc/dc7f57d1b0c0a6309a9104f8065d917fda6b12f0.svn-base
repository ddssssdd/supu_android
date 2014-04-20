package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import cc.android.supu.adapter.FilterAdapter;
import cc.android.supu.adapter.FilterAdapter.ItemChoosedListener;
import cc.android.supu.bean.FilterBean;
import cc.android.supu.bean.FilterBean.BrandBean;
import cc.android.supu.bean.FilterBean.CategoryBean;
import cc.android.supu.bean.FilterBean.PriceBean;
import cc.android.supu.handler.FilterHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;

/**
 * @author sss 筛选界面
 */
public class FilterActivity extends BaseActivity {

	private FilterHandler filterHandler;

	private ArrayList<CategoryBean> categoryBeans;
	private ArrayList<PriceBean> priceBeans;
	private ArrayList<BrandBean> brandBeans;

	private GridView categoryGridView, priceGridView, brandGridView;
	private TextView categroyLabel, priceLabel, brandLabel;
	private FilterAdapter categoryAdapter, priceAdapter, brandAdapter;
	/** 选择的品牌ID，分类ID */
	private String choosedBrandId, choosedCategoryId;
	private CategoryBean categoryBean;
	private PriceBean priceBean;
	/** 上一次点击的价格 */
	private PriceBean priceBeanFro = null;
	/** 是否以选中 */
	private boolean select = false;
	private BrandBean brandBean;
	private TextView categoryView, priceView, brandView;

	private String barCode, searchKey;
	private String brandId, categoryId;

	@Override
	protected void initPage() {

		categoryGridView = (GridView) findViewById(R.id.category_gridview);
		priceGridView = (GridView) findViewById(R.id.price_gridview);
		brandGridView = (GridView) findViewById(R.id.brand_gridview);
		categroyLabel = (TextView) findViewById(R.id.categorylabel);
		priceLabel = (TextView) findViewById(R.id.pricelabel);
		brandLabel = (TextView) findViewById(R.id.brandlabel);
		categroyLabel.setOnClickListener(this);
		priceLabel.setOnClickListener(this);
		brandLabel.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = getIntent();
		barCode = intent.getStringExtra("barCode");
		searchKey = intent.getStringExtra("searchKey");
		
		brandId = intent.getStringExtra("brandId");
		categoryId = intent.getStringExtra("categoryId");
		System.out.println("brandId00000000000000"+brandId);
		System.out.println("categoryId0000000000"+categoryId);
		choosedBrandId = intent.getStringExtra("choosedBrandId");
		choosedCategoryId = intent.getStringExtra("choosedCategoryId");
		priceBean = (PriceBean) intent.getSerializableExtra("price");
		categoryBeans = new ArrayList<CategoryBean>();
		priceBeans = new ArrayList<PriceBean>();
		brandBeans = new ArrayList<BrandBean>();

		categoryAdapter = new FilterAdapter(this, categoryBeans,
				categoryClickListener);
		priceAdapter = new FilterAdapter(this, priceBeans, priceClickListener);
		brandAdapter = new FilterAdapter(this, brandBeans, brandClickListener);
		categoryGridView.setOnItemClickListener(categoryItemClickListener);
		priceGridView.setOnItemClickListener(priceItemClickListener);
		brandGridView.setOnItemClickListener(brandItemClickListener);
		categoryGridView.setAdapter(categoryAdapter);
		priceGridView.setAdapter(priceAdapter);
		brandGridView.setAdapter(brandAdapter);
		requestServer(defaultPageRequest);
	}

	@Override
	protected void onSubActivityClick(View view) {
		switch (view.getId()) {
		case R.id.categorylabel:
			// System.out.println("----");
			// if (categoryBeans.size() < 1)
			// return;
			categroyLabel.setText("分类:");
			if (categoryBean != null) {
				categoryBean.type = 0;
			}
			// categoryAdapter.setID(null);
			categoryBean = null;
			notifyBrand(true);
			notifyCategory(false);
			break;
		case R.id.pricelabel:
			if (priceBeans.size() < 1)
				return;
			priceLabel.setText("价格:");
			if (priceBean != null) {
				priceBean.type = 0;
			}
			priceBean = null;
			priceBeanFro = null;
			// priceAdapter.setID(null);
			priceAdapter.notifyDataSetChanged();
			break;
		case R.id.brandlabel:
			if (brandBeans.size() < 1)
				return;
			if (brandBean != null) {
				brandBean.type = 0;
			}
			// brandAdapter.setID(null);
			brandBean = null;
			brandLabel.setText("品牌:");
			brandAdapter.notifyDataSetChanged();
			notifyBrand(false);
			notifyCategory(true);
			break;
		default:
			break;
		}
	}

	@Override
	protected String setTitle() {
		return "筛选";
	}

	@Override
	protected String setBackBtn() {
		return "取消";
	}

	@Override
	protected String setEnterBtn() {
		return "完成";
	}

	@Override
	protected void enterBtnOnClick() {
		Intent intent = getIntent();
		intent.putExtra("categoryId", categoryBean == null ? categoryId
				: categoryBean.id);
		intent.putExtra("price", priceBean);
		intent.putExtra("brandId", brandBean == null ? brandId : brandBean.id);
		System.out.println("categoryId11111111111111"+categoryId);
		System.out.println("brandId11111111111111111"+brandId);
		setResult(RESULT_OK, intent);
		this.finish();
	}

	@Override
	int setLayout() {
		return R.layout.filter;
	}

	@Override
	int setBottomIndex() {
		return 1;
	}

	@Override
	protected void defaultRequest() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("BarCode", barCode == null ? "" : barCode);
		map.put("SearchKey", searchKey == null ? "" : searchKey);
		System.out.println("searchKey"+searchKey);
		System.out.println("barCode"+barCode);
		map.put("CategoryId", categoryId == null ? "" : categoryId);
		map.put("BrandId", brandId == null ? "" : brandId);
		map.put("StartPrice", "" /* priceBean == null ? "" : priceBean.min + "" */);
		map.put("EndPrice", ""/* priceBean == null ? "" : priceBean.max + "" */);
		filterHandler = new FilterHandler(choosedCategoryId, choosedBrandId,
				priceBean == null ? null : priceBean.id);
		Tools.requestToParse(this, ConstantUrl.GETFILTERLIST,
				"GetScreeningInfo", map, filterHandler, true);
		if (Tools.responseValue == 1) {
			if (filterHandler.categoryBeans != null
					&& filterHandler.categoryBeans.size() > 0) {
				categoryBeans.addAll(filterHandler.categoryBeans);
			}
			if (filterHandler.brandBeans != null
					&& filterHandler.brandBeans.size() > 0) {
				brandBeans.addAll(filterHandler.brandBeans);
			}
			if (filterHandler.priceBeans != null
					&& filterHandler.priceBeans.size() > 0) {
				priceBeans.addAll(filterHandler.priceBeans);
			}
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_FAILURE);
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}
	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			showData();
			break;
		case STATE_FAILURE:
		case STATE_ERROR:
			Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	private void showData() {
		if (categoryBeans.size() == 1) {
			categoryBean = categoryBeans.get(0);
			categroyLabel.setText("分类: " + categoryBean.name);
			categoryBean.type = 1;
		}
		if (brandBeans.size() == 1) {
			brandBean = brandBeans.get(0);
			brandLabel.setText("品牌: " + brandBean.name);
			brandBean.type = 1;
		}
		if (priceBeans.size() == 1) {
			priceBean = priceBeans.get(0);
			priceBean.type = 1;
			priceLabel.setText("价格: " + priceBean.name);
		}
		categoryAdapter.notifyDataSetChanged();
		priceAdapter.notifyDataSetChanged();
		brandAdapter.notifyDataSetChanged();
	}

	private OnItemClickListener categoryItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (categoryBeans.get(position).type == -1
					|| categoryBeans.size() < 1)
				return;
			// categoryAdapter.setID(null);
			categoryBean = categoryBeans.get(position);
			for (CategoryBean category : categoryBeans) {
				if (category == categoryBean) {
					if (category.type == 1) {
						if (categoryBean != null) {
							categoryBean.type = 0;
						}
						categoryBean = null;
						categroyLabel.setText("分类: ");
						categoryAdapter.notifyDataSetChanged();
						notifyBrand(true);
						notifyCategory(false);
					} else {
						categoryBean.type = 1;
					}
				} else if (category.type != -1) {
					category.type = 0;
				}
				
			}
//			if (brandBean != null) {
//				brandBean.type = 0;
//			}
			// brandAdapter.setID(null);
//			categoryBean.type = 1;
			filterBrand(categoryBean);
		}
	};
	private OnItemClickListener priceItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (priceBeans.size() < 1)
				return;
			// priceAdapter.setID(null);
			priceBean = priceBeans.get(position);
			for (PriceBean price : priceBeans) {
				if (price == priceBean) {
					if (priceBean.type == 1) {
						priceBean.type = 0;
						priceBean = null;
						priceBeanFro = null;
						priceLabel.setText("价格: ");
					} else {
						priceBean.type = 1;
					}
				} else if (price.type != -1) {
					price.type = 0;
				}
			}
			// System.out.println("00000000000000000");
			// if ( priceBean == priceBeanFro &&!select) {
			// priceBean.type = 0;
			// select = true;
			// } else {
			// select = false;
			// if (priceBean.type == 1)` {
			// priceBean.type = 0;
			// } else {
			// priceBean.type = 1;
			// }
			// }
			// priceBeanFro =priceBeans.get(position);
			priceAdapter.notifyDataSetChanged();
		}
	};
	private OnItemClickListener brandItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (brandBeans.get(position).type == -1 || brandBeans.size() < 1)
				return;
			// brandAdapter.setID(null);
			brandBean = brandBeans.get(position);
			for (BrandBean brand : brandBeans) {
				if (brand == brandBean) {
					if (brandBean.type == 1) {
						if (brandBean != null) {
							brandBean.type = 0;
						}
						brandBean = null;
						brandLabel.setText("品牌: ");
						brandAdapter.notifyDataSetChanged();
						notifyBrand(false);
						notifyCategory(true);
					} else {
						brandBean.type = 1;
					}
				} else if (brand.type != -1)
					brand.type = 0;
			}
//			brandBean.type = 1;
			
			filterCategory(brandBean);
		}
	};

	private void filterCategory(BrandBean brandBean) {
		brandAdapter.notifyDataSetChanged();
		if (brandBean == null)
			return;
		for (CategoryBean category : categoryBeans) {
			if (category.type != 1) {
				if (brandBean.categoryIds.contains(category.id))
					category.type = 0;
				else
					category.type = -1;

			}
		}
		categoryAdapter.notifyDataSetChanged();

	}

	private void filterBrand(CategoryBean categoryBean) {
		categoryAdapter.notifyDataSetChanged();
		if (categoryBean == null)
			return;
		for (BrandBean bean : brandBeans) {
			if (bean.type != 1) {
				if (bean.categoryIds.contains(categoryBean.id))
					bean.type = 0;
				else
					bean.type = -1;
			}
		}
		brandAdapter.notifyDataSetChanged();

	}

	/**
	 * @param isBrand
	 *            true改变非选中项为常态 false改变非缺货态为常态
	 * */
	private void notifyBrand(boolean isBrand) {
		for (BrandBean bean : brandBeans) {
			if (isBrand) {
				if (bean.type != 1)
					bean.type = 0;
			} else {
				if (bean.type != -1)
					bean.type = 0;
			}
		}
		brandAdapter.notifyDataSetChanged();
	}

	/**
	 * @param isCategory
	 *            true改变非选中项为常态 false改变非缺货态为常态
	 * */
	private void notifyCategory(boolean isCategory) {
		for (CategoryBean bean : categoryBeans) {
			if (isCategory) {
				if (bean.type != 1)
					bean.type = 0;
			} else {
				if (bean.type != -1) {
					bean.type = 0;
				}
			}
		}
		categoryAdapter.notifyDataSetChanged();
	}

	private ItemChoosedListener brandClickListener = new FilterAdapter.ItemChoosedListener() {

		@Override
		public void itemChoosed(int position, FilterBean bean, boolean isDefault) {
			if (bean == null) {
				brandBean = null;
				brandLabel.setText("品牌: ");
			} else if (bean instanceof BrandBean) {
				brandBean = (BrandBean) bean;
				brandLabel.setText("品牌: " + bean.name);
				if (isDefault) {
					filterCategory(brandBean);
				}
			}
		}
	};
	private ItemChoosedListener priceClickListener = new FilterAdapter.ItemChoosedListener() {

		@Override
		public void itemChoosed(int position, FilterBean bean, boolean isDefault) {
			if (bean == null) {
				System.out.println("-----------");
				priceBean = null;
				priceBeanFro = null;
				priceLabel.setText("价格: ");
			} else if (bean instanceof PriceBean) {
				// if (priceBean != null /*&& priceBean == priceBeanFro*/) {
				// priceBean = null;
				// priceBeanFro = null;
				// priceLabel.setText("价格: ");
				// } else {
				System.out.println("-----" + bean.name);
				priceLabel.setText("价格: " + bean.name);
				priceBean = (PriceBean) bean;
				priceBeanFro = priceBean;
				// }
			}
		}
	};
	private ItemChoosedListener categoryClickListener = new FilterAdapter.ItemChoosedListener() {

		@Override
		public void itemChoosed(int position, FilterBean bean, boolean isDefault) {
			if (bean == null) {
				categoryBean = null;
				categroyLabel.setText("分类: ");
			} else if (bean instanceof CategoryBean) {
				categoryBean = (CategoryBean) bean;
				categroyLabel.setText("分类: " + bean.name);
				if (isDefault) {
					filterBrand(categoryBean);
				}
			}
		}
	};
}
