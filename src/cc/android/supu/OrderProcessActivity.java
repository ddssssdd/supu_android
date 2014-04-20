package cc.android.supu;

import java.util.TreeMap;

import cc.android.supu.adapter.OrderProcessAdapter;
import cc.android.supu.handler.OrderProcessHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 物流详情
 * @author sheng
 *
 */
public class OrderProcessActivity extends BaseActivity {
	
	/** 显示物流信息的listview*/
	private ListView listview;
	/** 无物流信息时显示的view*/
	private TextView hitTextView;
	/** 物流信息列表适配器*/
	private OrderProcessAdapter adapter;
	/** 订单编号*/
	private String orderSN;
	/** 物流信息解析器*/
	private OrderProcessHandler jsonData;
	
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "物流详情";
	}
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		if(getIntent() != null){
			orderSN = getIntent().getStringExtra("orderSN");
		}
		listview = (ListView) findViewById(R.id.orderprocess_listview);
		hitTextView = (TextView) findViewById(R.id.orderprocess_hitText);
		
		requestServer(defaultPageRequest);
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.orderprocess;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	protected void defaultRequest() {
		// TODO Auto-generated method stub
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("OrderSN", orderSN);
		
		jsonData = new OrderProcessHandler();
		Tools.requestToParse(this, ConstantUrl.GETORDERPROCESSINFOMATION, "GetOrderProcessInformation", params, jsonData, false);
		if (Tools.responseValue == 1) {
			if (jsonData.result_code == 0) {
				handler.sendEmptyMessage(STATE_SUCCESS);
			} else {
				handler.sendEmptyMessage(STATE_FAILURE);
			}
		} else {
			handler.sendEmptyMessage(STATE_ERROR);
		}
	}
	
	@Override
	protected void dealwithMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case STATE_SUCCESS:
			if(jsonData != null && jsonData.list != null){
				adapter = new OrderProcessAdapter(this, jsonData.list);
				listview.setAdapter(adapter);
			}
			changeVisible();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "请求异常！", Toast.LENGTH_SHORT).show();
			break;

		}
	}
	
	/**
	 * 改变控件的可见性
	 */
	private void changeVisible(){
		if(jsonData.list != null && jsonData.list.size() > 0){
			listview.setVisibility(View.VISIBLE);
			hitTextView.setVisibility(View.GONE);
		}else{
			listview.setVisibility(View.GONE);
			hitTextView.setVisibility(View.VISIBLE);
		}
	}
	
}
