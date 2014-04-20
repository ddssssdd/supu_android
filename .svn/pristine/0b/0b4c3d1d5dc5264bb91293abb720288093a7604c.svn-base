package cc.android.supu;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 留言界面
 * @author sheng
 *
 */
public class LeaveMsgActivity extends BaseActivity {
	
	private EditText contentEt;
	private TextView promptTv;
	/** 留言内容*/
	private String remark;
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "留言";
	}
	
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	@Override
	protected String setEnterBtn() {
		// TODO Auto-generated method stub
		return "完成";
	}
	
	@Override
	protected void enterBtnOnClick() {
		// TODO Auto-generated method stub
		String remark = contentEt.getText().toString().trim();
		if(remark.equals("")){
			Toast.makeText(this, "留言内容不能为空！", Toast.LENGTH_SHORT).show();
			contentEt.requestFocus();
			return;
		}
		Intent intent = new Intent();
		intent.putExtra("remark", remark);
		setResult(30, intent);
		finish();
	}
	
	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		contentEt = (EditText) findViewById(R.id.leavemsg_content);
		contentEt.addTextChangedListener(watcher);
		promptTv = (TextView)findViewById(R.id.leavemsg_promptTv);
		if(getIntent() != null){
			remark = getIntent().getStringExtra("remark");
			contentEt.setText(remark);
		}
	}
	
	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.leavemsg;
	}

	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	private TextWatcher watcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TOpromptTvDO Auto-generated method stub
			
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String content = s.toString().trim();
			int count = content.length();
			promptTv.setText("您还可以输入" + (200 - count) + "字");
		}
	};
	
	@Override
	protected void onPause() {
		if(this.getCurrentFocus() != null){//隐藏软键盘
			((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		super.onPause();
	}
}
