package cc.android.supu;

import java.util.List;
import java.util.TreeMap;

import cc.android.supu.adapter.GoodsConsultAdapter;
import cc.android.supu.bean.GoodsConsultBean;
import cc.android.supu.handler.GoodsConsultHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品咨询页面
 * @author sheng
 *
 */
public class GoodsConsultActivity extends BaseActivity {
	
	
	private ListView listView;
	/** listview不显示时显示*/
	private TextView textView;
	/** 加载更多时底部显示的view*/
	private LinearLayout bottomLayout;
	/** 商品咨询 信息解析 器*/
	private GoodsConsultHandler jsonData;
	/** 商品编号*/
	private String goodsSN = "";
	/** 每页显示条数 */
	private int pageSize = 10;
	/** 当前第几页*/
	private int page = 1;
	
	private List<GoodsConsultBean>  consultsList;
	
	private GoodsConsultAdapter adapter;
	/** 滚动状态*/
	private int mScrollState;
	
	
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "商品咨询";
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
	protected void initPage() {
		// TODO Auto-generated method stub
		if(getIntent() != null){
			goodsSN = getIntent().getStringExtra("goodsSN");
		}
		listView = (ListView) findViewById(R.id.goodsconsult_listView);
		listView.setOnScrollListener(scrollListener);
		textView = (TextView) findViewById(R.id.goodsconsult_textview);
		bottomLayout = (LinearLayout) findViewById(R.id.goodsconsult_bottomLayout);
		requestServer(requestConsult);
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.goodsconsult;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * 商品咨询请求
	 */
	PageRequest requestConsult = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String , String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			params.put("PageSize", pageSize + "");
			params.put("Page", page + "");
			
			jsonData = new GoodsConsultHandler();
			Tools.requestToParse(GoodsConsultActivity.this, ConstantUrl.GETGOODSCONSULT, 
					"GetGoodsConsult", params, jsonData, false);
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
			if(consultsList == null){
				consultsList = jsonData.consultsList;
			}else{
				consultsList.addAll(jsonData.consultsList);
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
		if(consultsList != null){
			if(adapter == null){
				adapter = new GoodsConsultAdapter(this, consultsList);
				listView.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	/**
	 * 改变显示的控件
	 */
	private void changeVisible(){
		if(consultsList == null || consultsList.size() == 0){
			listView.setVisibility(View.GONE);
			textView.setVisibility(View.VISIBLE);
		}else{
			listView.setVisibility(View.VISIBLE);
			textView.setVisibility(View.GONE);
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
			if(firstVisibleItem + visibleItemCount == totalItemCount
					&& mScrollState != SCROLL_STATE_IDLE){
				nextPage();
			}
		}
	};
	
	/**
	 * 请求下一页
	 */
	private void nextPage(){
		if(jsonData != null){
			if(jsonData.recordCount >= jsonData.pageSize * jsonData.pageIndex
					&& !bottomLayout.isShown()){
				page++;
				bottomLayout.setVisibility(View.VISIBLE);
				requestServer(requestConsult, false);
			}
		}
	}
}
