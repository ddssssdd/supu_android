package cc.android.supu;

import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.handler.DefaultJSONData;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;

public class FeedbackActivity extends BaseActivity {

	/** 拨打电话的按钮 */
	private TextView callBtn;
	/** 提交*/
	private TextView cmm;
	/** 反馈*/
	private EditText edit;
	
	private String msg;
	
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		cmm = (TextView) findViewById(R.id.feedback_cmm);
		cmm.setOnClickListener(this);
		callBtn = (TextView) findViewById(R.id.feedback_call);
		callBtn.setOnClickListener(this);
		edit = (EditText) findViewById(R.id.feedback_edit);
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s == null) {
					return;
				}
			}
		});
	}

	/** 添加到收藏夹请求*/
	PageRequest requestFeedback = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("content", edit.getText().toString());
			
			DefaultJSONData feed = new DefaultJSONData();
			Tools.requestToParse(FeedbackActivity.this, ConstantUrl.FEEDBACK,
					"PostFeedBack", params, feed, false);
			if(Tools.responseValue == 1){
				if(feed.result_code == 0){
					msg = feed.result_message;
					handler.sendEmptyMessage(STATE_SUCCESS);
				}else{
					msg = feed.result_message;
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			}else{
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};
	
	
	@Override
	protected void dealwithMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case STATE_SUCCESS:
			alertDialogfinish(this.msg);
			break;
		case STATE_FAILURE:
			Toast.makeText(this, this.msg, Toast.LENGTH_SHORT).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onSubActivityClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId() == callBtn.getId()){
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006180055"));
			startActivity(intent);
		}else if(view.getId() == cmm.getId()){
			if(edit.getText() != null && "".equals(edit.getText().toString())){
				Toast.makeText(this, "请输入您的建议。", Toast.LENGTH_SHORT).show();
			}else{
				requestServer(requestFeedback);
			}
		}
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.feedback;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 4;
	}
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "意见反馈";
	}
	
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}

	/**
	 * 请求成功确定 确认后finish
	 * 
	 * @param title
	 *            对话框标题
	 * @param des
	 *            对话框内容
	 */
	private void alertDialogfinish(String des) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("温馨提示");
		adb.setMessage(des);
		adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

		});
		adb.show();
	}
}
