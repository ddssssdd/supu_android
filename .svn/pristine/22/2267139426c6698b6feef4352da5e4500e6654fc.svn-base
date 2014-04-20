package cc.android.supu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cc.android.supu.adapter.FavoritesListAdapter;
import cc.android.supu.bean.GoodsBean;
import cc.android.supu.handler.CreateAddressHandler;
import cc.android.supu.handler.FavoritesListHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 我的收藏
 * 
 * @author zsx
 */
public class FavoritesActivity extends BaseActivity {
	/** 删除成功 */
	private final static int DELETE_SUCCESS = 100;
	/** 删除失败 */
	private final static int DELETE_FAILURE = 101;
	/** 无网络 */
	private final static int DELETE_ERROR = 102;
	/** 上下文 */
	private Context context = FavoritesActivity.this;
	/** 收藏列表 */
	private ListView List;
	/** 当前页 */
	private int Page = 1;
	/** 每页显示条数 */
	private int PageSize = 10, pagecount = 1;
	/** 适配器 */
	private FavoritesListAdapter favoritesListAdapter;
	/** 空布局 */
	private RelativeLayout favorites_empty;
	/** 键值对 */
	private TreeMap<String, String> params;
	/** 收藏列表的handler */
	private FavoritesListHandler favoritesListHandler;
	/** 通用的handler */
	private CreateAddressHandler createAddressHandler;
	/** 商品列表 */
	public List<GoodsBean> goodsList;
	/** 商品列表 */
	public List<String> timeList;
	/** 是否可编译 */
	private boolean isEdit;
	/** 商品的编号 */
	private String goodsSN = "";

	@Override
	protected void initPage() {
		goodsList = new ArrayList<GoodsBean>();
		timeList = new ArrayList<String>();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// scrollBoolean = 0;
		favorites_empty = (RelativeLayout) findViewById(R.id.favorites_empty);
		List = (ListView) findViewById(R.id.favoritesList);
		List.setOnScrollListener(scrollListener);

		favoritesListAdapter = new FavoritesListAdapter(context, goodsList);
		favoritesListAdapter.setEdit(isEdit);
		List.setAdapter(favoritesListAdapter);

		ItemClickListener();
		requestServer(requestFavoritesList);

	}

	/**
	 * List滚动到下面的效应事件
	 */
	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == SCROLL_STATE_IDLE) {
				favoritesListAdapter.setIsScrolling(false);
			} else {
				favoritesListAdapter.setIsScrolling(true);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem == 0)
				return;
			if (firstVisibleItem + visibleItemCount == totalItemCount && pagecount > Page) {
				Page++;
				// scrollBoolean++;
				requestServer(requestFavoritesList);
			}
		}
	};

	/**
	 * 得到最大页
	 */
	private int getMaxPage() {
		int maxPage = 0;
		int recordCount = favoritesListHandler.recordCount;
		if (PageSize == 0) {
			return maxPage;
		}
		recordCount = recordCount + PageSize - 1;
		return recordCount % PageSize;
	}

	@Override
	protected void onSubActivityClick(View view) {
		if (view.getId() == R.id.favorites_delete) {
			final GoodsBean favorite = (GoodsBean) view.getTag();
			if (isEdit) {
				new AlertDialog.Builder(FavoritesActivity.this).setTitle("您是否要删除？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								goodsSN = favorite.goodsSN;
								requestServer(requestRemoveFavorites);

							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {

							}
						}).show();
			}
		}

	}

	/**
	 * 响应事件
	 */
	private void ItemClickListener() {
		List.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (isEdit)
					return;
				Intent intent = new Intent(FavoritesActivity.this, GoodsDetailsActivity.class);
				intent.putExtra("goodsSN", goodsList.get(position).goodsSN);
				intent.putExtra("imgFile", goodsList.get(position).imgFile);
				FavoritesActivity.this.startActivity(intent);
			}
		});
	}

	/**
	 * 服务器的请求
	 */
	private PageRequest requestFavoritesList = new PageRequest() {
		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Page", Page + "");
			params.put("PageSize", PageSize + "");

			favoritesListHandler = new FavoritesListHandler();
			Tools.requestToParse(FavoritesActivity.this, ConstantUrl.GETFAVORITES, "GetFavorites", params,
					favoritesListHandler, false);
			if (Tools.responseValue == 1) {
				if (favoritesListHandler.result_code == 0) {
					timeList.addAll(favoritesListHandler.timeList);
					goodsList.addAll(favoritesListHandler.goodsList);
					pagecount = getMaxPage();
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};
	/**
	 * 删除收藏请求服务器
	 */
	private PageRequest requestRemoveFavorites = new PageRequest() {
		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);

			createAddressHandler = new CreateAddressHandler();
			Tools.requestToParse(FavoritesActivity.this, ConstantUrl.REMOVEFAVORITES, "RemoveFavorites", params,
					createAddressHandler, false);
			if (Tools.responseValue == 1) {
				if (createAddressHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(DELETE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(DELETE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(DELETE_ERROR));
			}
		}
	};

	@Override
	protected void dealwithMessage(android.os.Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			if (goodsList.size() > 0) {
				enterLabel.setVisibility(View.VISIBLE);
				favorites_empty.setVisibility(View.GONE);
//				List.setVisibility(View.VISIBLE);
				favoritesListAdapter.setEdit(isEdit);
				favoritesListAdapter.notifyDataSetChanged();
			} else {
				enterLabel.setVisibility(View.GONE);
				isEdit = false;
				List.setVisibility(View.GONE);
				favorites_empty.setVisibility(View.VISIBLE);
			}
			break;
		case STATE_FAILURE:
			Toast.makeText(FavoritesActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case DELETE_SUCCESS:
			Toast.makeText(FavoritesActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
			Page = 1;
			goodsList.clear();
			timeList.clear();
			requestServer(requestFavoritesList);
			break;
		case STATE_ERROR:
			Toast.makeText(FavoritesActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		case DELETE_FAILURE:
			Toast.makeText(FavoritesActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case DELETE_ERROR:
			Toast.makeText(FavoritesActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	};

	@Override
	protected String setTitle() {
		return "我的收藏";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected String setEnterBtn() {
		return "编辑";
	}

	/**
	 * 将得到的时间排序 得到的是从大到小的顺序
	 */
	private List<GoodsBean> getListSort(List<GoodsBean> list) {
		if (list == null || list.size() <= 0)
			return null;
		/** 冒泡排序 */
		int len = list.size();
		if (list.size() == 1)
			return list;
		// System.out.println("len" + len);
		GoodsBean GoodsBean;
		String time;
		for (int i = 0; i < len; ++i) {
			for (int j = len - 1; j > i; --j) {
				// System.out.println("进到这了j" + j);
				if (timeList.get(j) != null) {
					if (CompareTime(timeList.get(j), timeList.get(j - 1))) {
						GoodsBean = list.get(j);
						list.set(j, list.get(j - 1));
						list.set(j - 1, GoodsBean);

						time = timeList.get(j);
						timeList.set(j, timeList.get(j - 1));
						timeList.set(j - 1, time);
					}
				}
			}
		}
		return list;
	}

	/**
	 * yyyy-MM-dd-hh-mm-ss 时间的比较 要是返回true 就是前一个数大 返回false 就是后一个数大
	 */
	private boolean CompareTime(String time1, String time2) {
		if (time1 == null || time2 == null)
			return false;
		int start[] = { 0, 5, 8, 11, 14, 17 };
		int end[] = { 4, 7, 10, 13, 16, 19 };
		for (int i = 0; i < 6; i++) {

			String yyyy1 = time1.substring(start[i], end[i]);
			String yyyy2 = time2.substring(start[i], end[i]);
			int time11 = Integer.parseInt(yyyy1);
			int time22 = Integer.parseInt(yyyy2);
			if (time11 > time22) {
				return true;
			} else if (time11 < time22) {
				return false;
			} else {
				continue;
			}
		}

		return false;
	}

	@Override
	protected void enterBtnOnClick() {
		if (goodsList == null || goodsList.size() <= 0) {
			return;
		}
		if (!isEdit) {
			isEdit = !isEdit;
			enterLabel.setText("完成");
			enterLabel.setVisibility(View.VISIBLE);
			favoritesListAdapter.notifyDataSetChanged();
			favoritesListAdapter.setEdit(isEdit);
		} else {
			isEdit = !isEdit;
			enterLabel.setText("编辑");
			enterLabel.setVisibility(View.VISIBLE);
			favoritesListAdapter.notifyDataSetChanged();
			favoritesListAdapter.setEdit(isEdit);
		}
	}

	@Override
	int setLayout() {
		return R.layout.favorites;
	}

	@Override
	int setBottomIndex() {
		return 3;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		favoritesListAdapter.setIsScrolling(false);
	}
}
