package cc.android.supu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.android.supu.handler.DefaultJSONData;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cc.android.supu.adapter.InvoiceListAdapter;
import cc.android.supu.bean.InvoiceBean;

public class InvoiceListActivity extends BaseActivity {
	private TextView txt_high_description;
	private TextView txt_description;
	private EditText edt_invoice_head;
	private ListView listview;
	private InvoiceListHandler jsonData;
	private static final int LIST = 1 ;
	private InvoiceListAdapter adapter;
	@Override
	protected void initPage() {
		txt_high_description = (TextView)findViewById(R.id.txt_high_description);
		txt_description = (TextView)findViewById(R.id.txt_description);
		edt_invoice_head = (EditText)findViewById(R.id.edt_invoice_head);
		listview = (ListView)findViewById(R.id.selectinvoice_listview);
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				clickOnItem(position);
				
			}});
		
	}
	private void clickOnItem(int pos){
		InvoiceBean bean = jsonData.invoiceList.get(pos);
		adapter.selected_bean_index = pos;
		adapter.notifyDataSetChanged();
		txt_high_description.setText(bean.highDescription);
		txt_description.setText(bean.description);
	}
	protected String setEnterBtn() {
		return "完成";
	}
	protected void enterBtnOnClick() {
		this.hideKeyBoard();
		InvoiceBean bean = jsonData.invoiceList.get(adapter.selected_bean_index);
		Intent data = new Intent();
		data.putExtra("invoiceID", bean.invoiceID);
		data.putExtra("invoiceName", bean.invoiceName);
		data.putExtra("head", edt_invoice_head.getText().toString());
		this.setResult(60, data);
		finish();
	}

	@Override
	int setLayout() {
		return R.layout.selectinvoice;
	}

	@Override
	int setBottomIndex() {
		return 3;
	}
	@Override
	protected String setTitle() {
		return "发票信息";
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected void backBtnOnClick() {
		finish();
	}
	
	private PageRequest requestInvoiceList = new PageRequest(){
		
		@Override
		public void requestServer() {
			TreeMap<String,String> params = new TreeMap<String,String>();
			//params.put("nothing", "nothing");
			jsonData = new InvoiceListHandler();
			Tools.requestToParse(InvoiceListActivity.this, ConstantUrl.GETINVOICELIST,"GetInvoiceInfos",params, jsonData,false);
			System.out.println("Tools.responseValue="+Tools.responseValue);
			if (Tools.responseValue==1){
				if (jsonData.result_code==0){
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS, LIST));
				}else{
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE, LIST));
				}
			}else{
				handler.sendMessage(handler.obtainMessage(STATE_ERROR, LIST));
			}
			
		}};
	@Override
	protected void dealwithMessage(android.os.Message msg){
		int whichResponse = (Integer)msg.obj;
		switch(msg.what){
		case STATE_SUCCESS:
			if (jsonData!=null && jsonData.invoiceList!=null){
				bindData();
			}
			break;
		case STATE_FAILURE:
			break;
		case STATE_ERROR:
			break;
		}
	}
	private void bindData(){
		String invoiceID = this.getIntent().getStringExtra("invoiceID");
		if (adapter==null){
			adapter = new InvoiceListAdapter(this,jsonData.invoiceList);
			adapter.setInvoiceID(invoiceID);
			listview.setAdapter(adapter);
			
		}else{
			adapter.setInvoiceID(invoiceID);
			adapter.notifyDataSetChanged();
		}
		InvoiceBean bean = jsonData.invoiceList.get(adapter.selected_bean_index);
		if (bean!=null){
			txt_high_description.setText(bean.highDescription);
			txt_description.setText(bean.description);
		}
		String head = this.getIntent().getStringExtra("head");
		if (head!=null){
			edt_invoice_head.setText(head);
		}
		
	}
	private class InvoiceListHandler extends DefaultJSONData{
		private ArrayList<InvoiceBean> invoiceList;
		@Override
		public void parse(JSONObject object){
			if (object==null){
				return;
			}
			JSONObject data = object.optJSONObject("Data");
			JSONArray list = data.optJSONArray("InvoiceList");
			invoiceList = new ArrayList<InvoiceBean>();
			if (list!=null){
				for(int i=0;i<list.length();i++){
					if (list.optJSONObject(i)!=null){
						JSONObject obj = list.optJSONObject(i);
						InvoiceBean bean = new InvoiceBean();
						bean.invoiceID = obj.optString("InvoiceID");
						bean.invoiceName = obj.optString("InvoiceName");
						bean.highDescription = obj.optString("HighDescription");
						bean.description = obj.optString("Description");
						bean.ordering = obj.optString("Ordering");
						invoiceList.add(bean);
					}
				}
				Collections.sort(invoiceList,new Comparator<InvoiceBean>(){

					@Override
					public int compare(InvoiceBean lhs, InvoiceBean rhs) {
						
						return lhs.getOrder()-rhs.getOrder();
					}});
			}
		}
	};
	/**
	 * 提示登录对话框
	 */
	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提示对话框").setMessage("您未登录，请先登录！").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 跳到登录页面
				Intent intent = new Intent(InvoiceListActivity.this, LoginActivity.class);
				
				startActivity(intent);
			}
		}).setNegativeButton("取消", null);
		dialog.create().show();
	}
	@Override
	protected void onResume() {
		if (!UserInfoTools.isLogin(this)) {
			showDialog();
		} else {
			requestServer(requestInvoiceList);
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (this.getCurrentFocus() != null) {// 隐藏软键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this
					.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		super.onPause();
	}
}
