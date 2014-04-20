package cc.android.supu;

import java.util.ArrayList;

import cc.android.supu.adapter.ShippingListAdapter;
import cc.android.supu.bean.ShippingBean;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 选择配送方式
 * @author sheng
 *
 */
public class SelectShippingActivity extends BaseActivity {

	private ListView listView;
	/** 配送方式列表*/
	private ArrayList<ShippingBean> shippingList;
	private ShippingListAdapter adapter;
	private ShippingBean shippingBean;
	/** 在列表中选中的位置*/
	private int shippingPosi;
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "选择配送方式";
	}
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	
	@Override
	protected void backBtnOnClick() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("shippingPosi", shippingPosi);
		setResult(10, intent);
		finish();
	}
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.selectshipping_listview);
		if(getIntent() != null){
			shippingList = getIntent().getParcelableArrayListExtra("shippingList");
			shippingPosi = getIntent().getIntExtra("shippingPosi", shippingPosi);
		}
		if(shippingList != null && shippingList.size() > 0){
			if(shippingList.size() > shippingPosi){
				shippingBean = shippingList.get(shippingPosi);
			}else{
				shippingBean = shippingList.get(0);
			}
			shippingBean.isVisible = true;
			adapter = new ShippingListAdapter(this, shippingList);
			listView.setAdapter(adapter);
		}
		listView.setOnItemClickListener(mOnItemClickListener);
	}
	
	/**
	 * listview 的点击item监听器
	 */
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(shippingPosi != position){
				shippingList.get(shippingPosi).isVisible = false;
			}
			Intent intent = new Intent();
			intent.putExtra("shippingPosi", position);
			setResult(10, intent);
			finish();
			
		}
	};

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.selectshipping;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 2;
	}

}
