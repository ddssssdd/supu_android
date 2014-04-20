package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import cc.android.supu.adapter.GreedAdapter;
import cc.android.supu.bean.GreedBean;
import cc.android.supu.handler.GreedSubHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.content.Intent;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author sss 孕育宝典
 */
public class GreedSubActivity extends BaseActivity {
	/** 宝典列表布局 */
	private ListView greedListView;
	/** 宝典列表 */
	private ArrayList<GreedBean> greedBeans;
	/** 宝典列表适配器 */
	private GreedAdapter adapter;
	/** 父Id */
	private String parentId;
	/** 当前的页码 */
	private int pageindex = 1, pageCount = 1;
	/** 每页的数量 */
	private int pageSize = 10;
	/** 是否显示上面的加载 */
	private boolean isLoading = false;
	/** 上缅的加载布局 */
	private View bottomView;

	@Override
	protected String setTitle() {
		return "孕婴宝典";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected void initPage() {

		if (getIntent() != null) {
			parentId = getIntent().getStringExtra("parentId");
		}
		greedBeans = new ArrayList<GreedBean>();
		adapter = new GreedAdapter(this, greedBeans);
		greedListView = (ListView) findViewById(R.id.greedlist);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		bottomView = LayoutInflater.from(this).inflate(R.layout.list_downing, null, false);
		bottomView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, (int) (45 * metrics.density)));
		greedListView.addFooterView(bottomView);
		greedListView.setOnScrollListener(scrollListener);
		greedListView.setAdapter(adapter);
		greedListView.setOnItemClickListener(itemClickListener);
		requestServer(defaultPageRequest, false);
	}

	@Override
	int setLayout() {
		return R.layout.greed_list;
	}

	@Override
	int setBottomIndex() {
		return 4;
	}

	@Override
	protected void defaultRequest() {
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("categoryId", parentId);
		map.put("page", "" + pageindex);
		map.put("pageSize", "" + pageSize);
		GreedSubHandler greedHandler = new GreedSubHandler();
		Tools.requestToParse(this, ConstantUrl.GETARTICLELIST, "GetArticleList", map, greedHandler, false);
		isLoading = true;
		if (Tools.responseValue == 1) {
			pageCount = (greedHandler.recordCount + pageSize - 1) / pageSize;
			greedBeans.addAll(greedHandler.greedBeans);
			handler.sendEmptyMessage(STATE_SUCCESS);
		} else if (Tools.responseValue == 3) {
			handler.sendEmptyMessage(STATE_ERROR);
		} else {
			handler.sendEmptyMessage(STATE_FAILURE);
		}
	}

	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			if (pageindex >= pageCount)
				greedListView.removeFooterView(bottomView);
			adapter.notifyDataSetChanged();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求错误!", Toast.LENGTH_LONG).show();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "请求失败!", Toast.LENGTH_LONG).show();
			break;
		}
	}

	/**
	 * 列表的响应事件
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (greedBeans.size() > position) {
				GreedBean bean = greedBeans.get(position);
				parentId = bean.ID;
				Intent intent = new Intent(GreedSubActivity.this, GreedInfoActivity.class);
				intent.putExtra("Id", parentId);
				startActivity(intent);
			}
		}
	};
	/**
	 * list滚动到底的效应事件
	 */
	private OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem == 0)
				return;
			if (!isLoading)
				return;
			if (pageindex > pageCount)
				return;
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				pageindex++;
				isLoading = false;
				requestServer(defaultPageRequest, false);
			}
		}
	};

}
