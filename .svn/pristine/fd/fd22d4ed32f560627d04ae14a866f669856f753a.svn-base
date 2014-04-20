package cc.android.supu;

import java.util.List;
import java.util.TreeMap;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cc.android.supu.adapter.GoodsActivitesAdapter;
import cc.android.supu.bean.GoodsActivityBean;
import cc.android.supu.handler.GoodsActivitiesHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 可享受优惠活动页面
 * @author sheng
 *
 */
public class GoodsActivitiesActivity extends BaseActivity {
	
	private ListView listView;
	private TextView textView;

	/** 优惠活动的信息解析器*/
	private GoodsActivitiesHandler jsonData;
	private GoodsActivitesAdapter adapter;
	private List<GoodsActivityBean> list;
	/** 商品编号*/
	private String goodsSN = "";
	
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "可享受优惠活动";
	}
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		if(getIntent() != null){
			goodsSN = getIntent().getStringExtra("goodsSN");
		}
		listView = (ListView) findViewById(R.id.goodsactivities_listview);
		listView.setOnItemClickListener(mOnItemClickListener);
		textView = (TextView) findViewById(R.id.goodsactivities_textview);
		requestServer(requestGoodsActivities);
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
		return R.layout.goodsactivities;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * 可享受优惠活动的请求
	 */
	PageRequest requestGoodsActivities = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("GoodsSN", goodsSN);
			jsonData = new GoodsActivitiesHandler();
			Tools.requestToParse(GoodsActivitiesActivity.this, ConstantUrl.GETGOODSACTIVITIES,
					"GetGoodsActivities", params, jsonData, false);
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
			list = jsonData.list;
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
	
	private void setListViewData(){
		if(list != null){
			adapter = new GoodsActivitesAdapter(this, list);
			listView.setAdapter(adapter);
		}
	}
	
	private void changeVisible(){
		if(list != null && list.size() != 0){
			listView.setVisibility(View.VISIBLE);
			textView.setVisibility(View.GONE);
		}else{
			listView.setVisibility(View.GONE);
			textView.setVisibility(View.VISIBLE);
		}
	}
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			System.out.println("list.get(position).id="+list.get(position).id);
			Intent intent = new Intent(GoodsActivitiesActivity.this, ActivityTitle.class);
			intent.putExtra("linkdata", list.get(position).id);
			startActivity(intent);
		}
	};
}
