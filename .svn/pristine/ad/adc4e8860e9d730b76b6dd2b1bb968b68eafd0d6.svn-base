package cc.android.supu;

import java.util.List;
import java.util.TreeMap;

import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cc.android.supu.adapter.GoodsCommentAdapter;
import cc.android.supu.bean.GoodsCommentBean;
import cc.android.supu.handler.GoodsCommentHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 购买评论列表页面
 * @author sheng
 *
 */
public class GoodsCommentActivity extends BaseActivity {
	
	
	
	
	private ListView listView;
	/** 当listview不显示时 显示*/
	private TextView textview;
	/** 加载更多时显示的view*/
	private LinearLayout bottomLayout;
	/** 商品评论的信息解析器*/
	private GoodsCommentHandler jsonData;
	/** 评论列表*/
	private List<GoodsCommentBean> list;
	/** 商品评论的适配器*/
	private GoodsCommentAdapter adapter;
	/** 滚动状态*/
	private int mScrollState;
	
	/** 商品编号*/
	private String goodsSN;
	/** 每页显示条数*/
	private int pageSize = 10;
	/** 当前第几页*/
	private int page = 1;
	
	@Override
	protected void initPage() {
		if(getIntent() != null){
			goodsSN = getIntent().getStringExtra("goodsSN");
		}
		
		listView = (ListView) findViewById(R.id.goodscomment_listview);
		listView.setOnScrollListener(scrollListener);
		textview = (TextView) findViewById(R.id.goodscomment_textview);
		bottomLayout = (LinearLayout) findViewById(R.id.goodscomment_bottomLayout);
		
		requestServer(requestComment);
	}
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "购买评论";
	}
	
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	
	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		finish();
	}
	
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.goodscomment;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * 评论请求
	 */
	PageRequest requestComment = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			params.put("PageSize", pageSize + "");
			params.put("Page", page + "");
			
			jsonData = new GoodsCommentHandler();
			Tools.requestToParse(GoodsCommentActivity.this, ConstantUrl.GETGOODSCOMMENT,
					"GetGoodsComment", params, jsonData, false);
			if(Tools.responseValue == 1){
				if(jsonData.result_code == 0){
					handler.sendEmptyMessage(STATE_SUCCESS);
				}else{
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			}else{
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};
	
	protected void dealwithMessage(android.os.Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			if(list == null){
				list = jsonData.list;
			}else{
				list.addAll(jsonData.list);
			}
			setListViewData();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求异常！", Toast.LENGTH_SHORT).show();
			break;
		}
		changeVisible();
	};
	
	/**
	 * 为listView设置数据
	 */
	private void setListViewData(){
		if(list != null){
			if(adapter == null){
				adapter = new GoodsCommentAdapter(this, list);
				listView.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	/**
	 * 控件的显示
	 */
	private void changeVisible(){
		if(list != null && list.size() > 0){
			listView.setVisibility(View.VISIBLE);
			textview.setVisibility(View.GONE);
		}else{
			listView.setVisibility(View.GONE);
			textview.setVisibility(View.VISIBLE);
		}
		bottomLayout.setVisibility(View.GONE);
	}
	/**
	 * listview的滚动监听器
	 */
	OnScrollListener scrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			mScrollState = scrollState;
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if(firstVisibleItem + visibleItemCount == totalItemCount && 
					mScrollState != SCROLL_STATE_IDLE){
				nextPageData();
			}
		}
	};
	
	/**
	 * 请求下一页数据
	 */
	private void nextPageData(){
		
		if(jsonData != null && jsonData.pageIndex * jsonData.pageSize 
				< jsonData.recordCount && !bottomLayout.isShown()){
			page++;
			bottomLayout.setVisibility(View.VISIBLE);
			requestServer(requestComment, false);
		}
		
	}
}
