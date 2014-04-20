package cc.android.supu;

import java.util.ArrayList;

import cc.android.supu.bean.PaymentBean;
import cc.android.supu.adapter.PayMentListAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 选择支付方式
 * @author sheng
 *
 */
public class SelectPayMentActivity extends BaseActivity {
	
	private ListView listView;
	private PayMentListAdapter adapter;
	/** 支付方式列表*/
	private ArrayList<PaymentBean> paymentList;
	/** 支付方式bean*/
	private PaymentBean paymentBean;
	/** 选中的位置*/
	private int paymentPosi = 0;
	
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "选择支付方式";
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
		intent.putExtra("paymentPosi", paymentPosi);
		setResult(20, intent);
		finish();
	}
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.selectpayment_listview);
		if(getIntent() != null){
			paymentList = getIntent().getParcelableArrayListExtra("paymentList");
			paymentPosi = getIntent().getIntExtra("paymentPosi", 0);
		}
		
		if(paymentList != null){
			paymentBean = paymentList.get(paymentPosi);
			paymentBean.isVisible = true;
			adapter = new PayMentListAdapter(this, paymentList);
			listView.setAdapter(adapter);
		}
		listView.setOnItemClickListener(mOnItemClickListener);
		
	}
	
	/** 
	 * listeview的item点击事件
	 */
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(paymentPosi != position){
				paymentList.get(paymentPosi).isVisible = false;
			}
			Intent intent = new Intent();
			intent.putExtra("paymentPosi", position);
			setResult(20, intent);
			finish();
		}
	};
	
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.selectpayment;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 2;
	}

}
