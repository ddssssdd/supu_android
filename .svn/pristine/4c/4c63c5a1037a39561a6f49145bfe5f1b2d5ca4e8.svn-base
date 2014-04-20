package cc.android.supu;

import java.util.ArrayList;
import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cc.android.supu.adapter.AdressAdapter;
import cc.android.supu.bean.ConsigneeBean;
import cc.android.supu.handler.ConsigneeListHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

/**
 * 收货地址的列表页
 * @author zsx
 *
 */
public class AdressListActivity extends BaseActivity{
	/**收货地址的列表*/
	private ListView addressList;
	/**收货地址的适配器*/
	private AdressAdapter adressAdapter;
	/**收货地址的列表*/
	private ArrayList<ConsigneeBean> list;
	/**没有地址时的布局*/
	private RelativeLayout adress_empty;
	/**键值对*/
	private TreeMap params;
	/**地址列表的handler*/
	private ConsigneeListHandler consigneeListHandler;
	/** 页面类型，1：地址列表。2：选择地址 */
	private int type = -1;
	@Override
	protected void initPage() {
		if (getIntent() != null) {
			type = getIntent().getIntExtra("TYPE", 0);
		}		
		addressList = (ListView) findViewById(R.id.addressList);
		adress_empty = (RelativeLayout) findViewById(R.id.adress_empty);
		params = new TreeMap<String, String>();
		consigneeListHandler = new ConsigneeListHandler();
		
	}
	/**
	 * 适配器中的
	 */
	private void adapterData() {
		if (list != null && list.size() > 0) {
			addressList.setVisibility(View.VISIBLE);
			adress_empty.setVisibility(View.GONE);
			adressAdapter = new AdressAdapter(this, list);
			addressList.setAdapter(adressAdapter);
			addressListListener();
		} else {
			addressList.setVisibility(View.GONE);
			adress_empty.setVisibility(View.VISIBLE);
		}
	}

	/** 地址的列表监听 */
	private void addressListListener() {
		addressList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (type == 1) {
							Intent intent = new Intent(AdressListActivity.this,
									NewAdressActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("ConsigneeBean",
									list.get(arg2));
							intent.putExtras(bundle);
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.putExtra("ConsigneeBean", list.get(arg2));
							setResult(555, intent);
							finish();
						}
					}
				});
	}
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.adresslist;
	}
	@Override
	protected String setBackBtn() {
		return "返回";
	}
	@Override
	protected void onResume() {
		super.onResume();
		requestServer(requestAddressList);
	}
	/**
	 * 按钮的请求
	 */
	private PageRequest requestAddressList = new PageRequest() {
		@Override
		public void requestServer() {
		
			Tools.requestToParse(AdressListActivity.this,
					ConstantUrl.GETCONSIGNEElIST, "GetConsigneeList", null,
					consigneeListHandler, false);
			if (Tools.responseValue == 1) {
				if (consigneeListHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};
	@Override
	protected void dealwithMessage(Message msg) {
		switch (msg.what) {
		case STATE_SUCCESS:
			list = consigneeListHandler.getConsigneeBean();
			adapterData();
			break;
		case STATE_FAILURE:
			Toast.makeText(AdressListActivity.this, "服务器异常！", Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(AdressListActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	};
//	@Override
//	protected void backBtnOnClick() {
//		Intent intent = new Intent(this, VipActivity.class);
//		startActivity(intent);
//		this.finish();
//	}
	@Override
	protected String setTitle() {
		if (type == 1) {
			return "收货地址列表";
		} else {
			return "地址选择";
		}
	}
	
	@Override
	protected String setEnterBtn() {
		return "新建";
	}
	@Override
	protected void enterBtnOnClick() {
		Intent intent = new Intent(AdressListActivity.this,
				NewAdressActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("ConsigneeBean", null);
		intent.putExtras(bundle);
		startActivity(intent);
		//this.finish();
	}
	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// 按下返回键
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			Intent intent = new Intent(this, VipActivity.class);
//			startActivity(intent);
//			this.finish();
//		}
//
//		return false;
//	}
}
