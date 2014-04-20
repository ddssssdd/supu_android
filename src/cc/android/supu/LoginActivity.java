package cc.android.supu;

import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.handler.LoginHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;

/**
 * 登陆页面
 * 
 * @author zsx
 * 
 */
public class LoginActivity extends BaseActivity {
	/** 用户名 */
	private EditText userName;
	/** 密码 */
	private EditText userPassword;
	/** 登录按钮 */
	private Button loginButton;
	/**用户名*/
	private String loginUser = "";
	/**密码*/
	private String loginPasseord = "";
	/** 登录handler */
	private LoginHandler LoginHandler;
	/** 传给服务器的键值对 */
	private TreeMap<String, String> params;
	/**标记从哪里跳到登录的*/
	public  String  close ="";
	
	@Override
	protected void initPage() {

		 
		if (this.getIntent().getData() != null) {
		
			close = this.getIntent().getStringExtra("shopcart");
		}
		userName = (EditText) findViewById(R.id.login_username);
		userPassword = (EditText) findViewById(R.id.login_password);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(button_listener);
		ClickListener();
	}

	@Override
	protected String setEnterBtn() {
		return "注册";
	}
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "登录";
	}
	/**
	 * 编辑框的监听事件
	 */
	private void ClickListener() {
		userName.addTextChangedListener(new TextWatcher() {
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
				loginUser = s.toString().trim();
			}
		});

		userPassword.addTextChangedListener(new TextWatcher() {
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
				loginPasseord = s.toString().trim();
			}
		});

	}
	/**
	 * 按钮的点击事件
	 */
	private Button.OnClickListener button_listener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.loginButton:
				if (Verification()) {
					requestServer(requestLogin);
				}
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 注册按钮的请求
	 */
	private PageRequest requestLogin = new PageRequest() {

		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Account", loginUser);
			params.put("Password", loginPasseord);
			LoginHandler = new LoginHandler();
			Tools.requestToParse(LoginActivity.this, ConstantUrl.LOGINURL, "Login", params,
					LoginHandler, false);
				if(Tools.responseValue == 1){
					if(LoginHandler.result_code == 0){
						handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
					}else{
						handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
					}
				}else{
					handler.sendMessage(handler.obtainMessage(STATE_ERROR));
				}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		if (PublicParams.getISFINISH() == 1) {
			this.finish();
			PublicParams.setISFINISH(0);
		}
	};
	@Override
	protected void dealwithMessage(Message msg) {
		Intent intent = null;
		switch (msg.what) {
		case STATE_SUCCESS:
			MemberBean memberBean =new MemberBean();
			memberBean.setAccount(LoginHandler.account);
			memberBean.setPassword(loginPasseord);
			memberBean.setMemberId(LoginHandler.memberId);
			memberBean.setLevel(LoginHandler.level);
//			memberBean.setPrice(LoginHandler.price);
//			memberBean.setScores(LoginHandler.scores);
			//登录成功后调用 这2个方法 保存用户数据
			PublicParams.setMEMBERID(LoginHandler.memberId);
			UserInfoTools.saveUserInfo(this, memberBean, true);
			this.finish();
			closeMethodManager();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, LoginHandler.result_message, Toast.LENGTH_LONG).show();
			break;
		case STATE_ERROR:
			Toast.makeText(LoginActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}
		/**
	 * 判断用户名和密码的合法性
	 */
	private boolean Verification(){
		boolean isVerify = true;
		if (loginUser.equals("")) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
					.show();
			isVerify = false;
			return isVerify;
		}
		if(loginPasseord.equals("")){
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
			.show();
			isVerify = false;
			return isVerify;
		}
		if(loginPasseord.length()<6){
			Toast.makeText(LoginActivity.this, "密码不能少于6位", Toast.LENGTH_SHORT)
			.show();
			isVerify = false;
			return isVerify;
		}
		return isVerify;
	}
	@Override
	int setLayout() {
		return R.layout.login;
	}

	@Override
	int setBottomIndex() {
		return 3;
	}
	@Override
	protected void enterBtnOnClick() {
		Intent intent = new Intent(this, RegistActivity.class);
		startActivity(intent);
	}
	/**
	 * 关闭软键盘
	 */
	private void closeMethodManager(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(userPassword.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
	}

}
