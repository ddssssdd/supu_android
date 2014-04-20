package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import cc.android.supu.adapter.ActivityAdapter;
import cc.android.supu.bean.ActivityItemBean;
import cc.android.supu.bean.FilterBean.PriceBean;
import cc.android.supu.handler.ActivityHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author sss 活动标题
 */
public class SearchResult extends BaseActivity {
	private final static int REQUEST_FILTER_CODE = 5353;
	/**
	 * sortField:<br/>
	 * GoodsSales,<br/>
	 * GoodsSalesDesc,<br/>
	 * Price,<br/>
	 * PriceDesc, <br/>
	 * CommentCount,<br/>
	 * CommentCountDesc,
	 */
	private String title, categoryId, brandId, sortField;

	/** 选择的品牌ID，分类ID */
	private String choosedBrandId, choosedCategoryId;

	// private CategoryBean categoryBean;
	// private BrandBean brandBean;
	private PriceBean priceBean;
	private ListView listView;

	private int page = 1, pagesize = 10, pagecount = 1, bottomIndex = 0;
	private boolean /* isLoading = false, */iscategory = false;

	private ArrayList<ActivityItemBean> itemBeans;
	private ActivityAdapter adapter;

	private RelativeLayout salevolumeLayout, priceLayout, evaluateLayout;
	private ImageView salevolumeArrow, priceArrow, evaluateArrow;
	private TextView salevolumeLabel, priceLabel, evluateLabel;
	private int currentType;
	/** 升序 */
	private boolean sortAsc;

	private ActivityHandler activityHandler;
	private View bottomView;
	/** 是否是一维码扫描 */
	private boolean barcode;

	private TextView nolist;

	private boolean search = false;

	private boolean filter = false;
	/** 是否可以点击 */
	private boolean isOnclick = true;

	// /** 第一个可见项 */
	// private int mFirstVisibleItem;
	// /** 可见的item数 */
	// private int mVisibleItemCount;

	@Override
	protected void initPage() {
		barcode = getIntent().getBooleanExtra("barcode", false);
		// if (!barcode) {
		title = getIntent().getStringExtra("key");
		search = getIntent().getBooleanExtra("search", false);
		// } else {
		// title = "二维码搜索";
		// }
		choosedCategoryId = categoryId = getIntent().getStringExtra("categoryId");
		choosedBrandId = brandId = getIntent().getStringExtra("brandId");
		iscategory = getIntent().getBooleanExtra("iscategory", false);

		bottomIndex = getIntent().getIntExtra("index", 0);
		sortField = "GoodsSalesDesc";
		currentType = 1;
		sortAsc = false;
		salevolumeLayout = (RelativeLayout) findViewById(R.id.salevolume_layout);
		salevolumeLayout.setOnClickListener(this);
		salevolumeLabel = (TextView) findViewById(R.id.salevolume_label);
		salevolumeArrow = (ImageView) findViewById(R.id.salevolume_arrow);
		salevolumeArrow.setImageResource(R.drawable.arrow_big_down);

		priceLayout = (RelativeLayout) findViewById(R.id.price_layout);
		priceLayout.setOnClickListener(this);
		priceLabel = (TextView) findViewById(R.id.price_label);
		priceArrow = (ImageView) findViewById(R.id.price_arrow);

		evaluateLayout = (RelativeLayout) findViewById(R.id.evaluate_layout);
		evaluateLayout.setOnClickListener(this);
		evluateLabel = (TextView) findViewById(R.id.evaluate_label);
		evaluateArrow = (ImageView) findViewById(R.id.evaluate_arrow);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		bottomView = LayoutInflater.from(this).inflate(R.layout.list_downing, null, false);
		bottomView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, (int) (45 * metrics.density)));
		nolist = (TextView) findViewById(R.id.nolist);
		listView = (ListView) findViewById(R.id.activity_list);
		listView.setOnScrollListener(scrollListener);
		listView.addFooterView(bottomView);
		// listView.setOnItemClickListener(itemClickListener);
		bottomView.setTag(true);
		itemBeans = new ArrayList<ActivityItemBean>();
		adapter = new ActivityAdapter(this, listView, itemBeans);
		listView.setAdapter(adapter);
		requestServer(defaultPageRequest, false);
	}

	@Override
	protected void onSubActivityClick(View view) {
		if (!isOnclick)
			return;
		switch (view.getId()) {
		case R.id.salevolume_layout:
			resetPage();
			if (currentType == 1 && !sortAsc) {
				sortField = "GoodsSales";
				sortAsc = true;
				salevolumeArrow.setImageResource(R.drawable.arrow_big_up);
			} else {
				sortField = "GoodsSalesDesc";
				changeSortType(currentType);
				sortAsc = false;
				salevolumeLayout.setBackgroundResource(R.drawable.partion_btn_normal);
				salevolumeLabel.setTextColor(Color.WHITE);
				salevolumeArrow.setImageResource(R.drawable.arrow_big_down);
			}
			currentType = 1;
			requestServer(defaultPageRequest, false);
			break;
		case R.id.price_layout:
			resetPage();
			if (currentType == 2 && !sortAsc) {
				sortField = "PriceDesc";
				sortAsc = true;
				priceArrow.setImageResource(R.drawable.arrow_big_down);
			} else {
				sortField = "Price";
				changeSortType(currentType);
				sortAsc = false;
				priceLayout.setBackgroundResource(R.drawable.partion_btn_normal);
				priceLabel.setTextColor(Color.WHITE);
				priceArrow.setImageResource(R.drawable.arrow_big_up);
			}
			currentType = 2;
			requestServer(defaultPageRequest, false);
			break;
		case R.id.evaluate_layout:
			resetPage();
			if (currentType == 3 && !sortAsc) {
				sortField = "CommentCount";
				sortAsc = true;
				evaluateArrow.setImageResource(R.drawable.arrow_big_up);
			} else {
				sortField = "CommentCountDesc";
				changeSortType(currentType);
				sortAsc = false;
				evaluateLayout.setBackgroundResource(R.drawable.partion_btn_normal);
				evluateLabel.setTextColor(Color.WHITE);
				evaluateArrow.setImageResource(R.drawable.arrow_big_down);
			}
			currentType = 3;
			requestServer(defaultPageRequest, false);
			break;
		default:
			break;
		}
	}

	private void resetPage() {
		isOnclick = false;
		itemBeans.clear();
		pagecount = 1;
		page = 1;
		nolist.setVisibility(View.GONE);
		boolean isAdd = (Boolean) bottomView.getTag();
		if (!isAdd) {
			bottomView.setTag(true);
			listView.addFooterView(bottomView);
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected String setEnterBtn() {
		return "筛选";
	}

	@Override
	protected void enterBtnOnClick() {
		Intent intent = new Intent(this, FilterActivity.class);
		if (barcode) {
			intent.putExtra("barCode", title);
			intent.putExtra("searchKey", "");
		} else {
			if (search) {
				intent.putExtra("searchKey", title);
			} else {
				intent.putExtra("searchKey", "");
			}
			intent.putExtra("barCode", "");
		}
		intent.putExtra("choosedCategoryId", choosedCategoryId);
		intent.putExtra("choosedBrandId", choosedBrandId);
		if (!search) {
			// if (iscategory) {
			intent.putExtra("categoryId", categoryId);
			// } else {
			intent.putExtra("brandId", brandId);
			// }
		} else {
			intent.putExtra("categoryId", categoryId);
			intent.putExtra("brandId", brandId);
		}
		intent.putExtra("price", priceBean);
		startActivityForResult(intent, REQUEST_FILTER_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_FILTER_CODE:
			if (data == null)
				return;
			else {
				choosedBrandId = data.getStringExtra("brandId");
				choosedCategoryId = data.getStringExtra("categoryId");
				priceBean = (PriceBean) data.getSerializableExtra("price");
				resetPage();
				filter = true;
				requestServer(defaultPageRequest);
			}
			break;

		default:
			break;
		}
		// super.onActivityResult(requestCode, resultCode, data);
	}

	private void changeSortType(int type) {
		switch (type) {
		case 1:
			salevolumeLayout.setBackgroundResource(R.drawable.partion_btn_toching);
			salevolumeArrow.setImageResource(0);
			salevolumeLabel.setTextColor(Color.BLACK);
			break;
		case 2:
			priceLayout.setBackgroundResource(R.drawable.partion_btn_toching);
			priceArrow.setImageResource(0);
			priceLabel.setTextColor(Color.BLACK);
			break;
		case 3:
			evaluateLayout.setBackgroundResource(R.drawable.partion_btn_toching);
			evaluateArrow.setImageResource(0);
			evluateLabel.setTextColor(Color.BLACK);
			break;
		}
	}

	@Override
	protected String setTitle() {
		return title == null ? "商品列表" : title;
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	int setLayout() {
		return R.layout.search_list;
	}

	@Override
	int setBottomIndex() {
		return bottomIndex;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (itemBeans.size() > 0) {
			enterLabel.setVisibility(View.VISIBLE);
		} else {
			// enterLabel.setVisibility(View.GONE);
		}
	}

	@Override
	protected void dealwithMessage(Message msg) {
		isLoading = false;
		switch (msg.what) {
		case STATE_SUCCESS:
			Log.e("sss", "count:" + pagecount + "  " + page);
			if (activityHandler.pageCount <= activityHandler.pageIndex) {
				listView.removeFooterView(bottomView);
				bottomView.setTag(false);
			}
			if (activityHandler.activityItemBeans != null && activityHandler.activityItemBeans.size() > 0) {
				itemBeans.addAll(activityHandler.activityItemBeans);
				adapter.notifyDataSetChanged();
			}
			if (itemBeans != null && itemBeans.size() > 0) {
				enterLabel.setVisibility(View.VISIBLE);
				nolist.setVisibility(View.GONE);
			} else {

				// System.out.println("itemBeans" + itemBeans.size());
				// enterLabel.setVisibility(filter ? View.VISIBLE :View.GONE);
				enterLabel.setVisibility(filter ? View.VISIBLE : View.VISIBLE);
				filter = false;
				nolist.setVisibility(View.VISIBLE);
			}

			break;
		case STATE_FAILURE:

		case STATE_ERROR:
			listView.removeFooterView(bottomView);
			bottomView.setTag(false);
			Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	// /**
	// * @param str
	// * @return 判断字符串是否是数据，且只有10位
	// */
	// public boolean checkIsNumber(String str) {
	// if (str.trim().length() != 10)
	// return false;
	// else {
	// char[] ss = str.toCharArray();
	// for (char c : ss) {
	// if (c < 48 || c > 57)
	// return false;
	// }
	// return true;
	// }
	// }

	@Override
	protected void defaultRequest() {
		nolist.setVisibility(View.GONE);
		TreeMap<String, String> map = new TreeMap<String, String>();
		activityHandler = new ActivityHandler();
		map.put("Page", page + "");
		map.put("PageSize", pagesize + "");
		if (!barcode) {
			if (search) {
				map.put("SearchKey", title == null ? "" : title);
			}
			map.put("BarCode", "");
		} else {
			map.put("BarCode", title == null ? "" : title);
			map.put("SearchKey", "");
		}

		map.put("CategoryId", choosedCategoryId == null ? "" : choosedCategoryId);
		// System.out.println("choosedCategoryId" + choosedCategoryId);
		map.put("BrandId", choosedBrandId == null ? "" : choosedBrandId);
		if (priceBean != null) {
			map.put("StartPrice", priceBean.min + "");
			map.put("EndPrice", priceBean.max == 0 ? "" : priceBean.max + "");
		} else {
			map.put("StartPrice", "");
			map.put("EndPrice", "");
		}
		map.put("SortField", sortField);
		Tools.requestToParse(this, ConstantUrl.GETPRODUCTLIST, "GetGoodsList", map, activityHandler, false);
		// isLoading = true;
		isOnclick = true;
		if (Tools.responseValue == 1) {
			pagecount = activityHandler.pageCount;
			page = activityHandler.pageIndex;
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_FAILURE);
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}

	}

	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == SCROLL_STATE_IDLE) {
				adapter.notifyThread();
			} else {
				adapter.lockThread();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem == 0)
				return;
			if (isLoading)
				return;
			if (firstVisibleItem + visibleItemCount == totalItemCount && pagecount > page) {
				page++;
				isLoading = true;
				requestServer(defaultPageRequest, false);
			}
		}
	};

	private boolean isLoading = false;

}
