package cc.android.supu;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.content.Intent;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.bean.MemberBean;
import cc.android.supu.handler.RegistHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.wheelview.NumericWheelAdapter;
import cc.android.supu.wheelview.OnWheelChangedListener;
import cc.android.supu.wheelview.WheelView;

/**
 * 注册页面
 * 
 * @author zsx
 * 
 */
public class RegistActivity extends BaseActivity {

	/** 用户名 ，密码 */
	private EditText registUser, registPassword, confirmat_password;
	/** 推荐人 */
	private EditText recommend_edit;
	/** 邮箱 */
	private EditText Email;
	/** 预产期 */
	private TextView childbirth;
	/** 注册提交 */
	private Button registButton;
	/** 提示框 */
	private Dialog dialog;
	/** 年的时间范围 */
	private static int START_YEAR = 1990, END_YEAR = 2100;
	/** 用户名 ，密码 */
	private String userName = "";
	private String firstPassword = "";
	private String secondPassword = "";
	/** 推荐人 */
	private String referee = "";
	/** 邮箱 */
	private String email = "";
	/** 预产期 */
	private String birthday = "";
	/** 传给服务器的键值对 */
	private TreeMap<String, String> params;
	/** 宝宝生日的布局 */
	private RelativeLayout birthdayLayout;
	/** 注册handler */
	private RegistHandler registHandler;
	/** 当前的选中的年月日 */
	private int repYear = 0, repMonth = 0, repDay = 0;

	/** 是否显示时间控件 */
	// private boolean isShowDate = true;

	@Override
	protected void initPage() {

		registUser = (EditText) findViewById(R.id.regist_username);
		registPassword = (EditText) findViewById(R.id.regist_password);
		confirmat_password = (EditText) findViewById(R.id.confirmat_password);
		recommend_edit = (EditText) findViewById(R.id.recommend_edit);
		Email = (EditText) findViewById(R.id.email_edit);
		childbirth = (TextView) findViewById(R.id.childbirth);
		registButton = (Button) findViewById(R.id.registButton);
		birthdayLayout = (RelativeLayout) findViewById(R.id.birthdayLayout);

		birthdayLayout.setOnClickListener(onClickListener);
		registButton.setOnClickListener(onClickListener);
		registHandler = new RegistHandler();
	}

	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "注册";
	}

	/**
	 * 按钮的点击事件
	 */
	private Button.OnClickListener onClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.birthdayLayout:
				if (dialog != null && dialog.isShowing()) {
					return;
				}

				showDateTimePicker();

				break;
			case R.id.registButton:
				setdata();
				if (Verification()) {
					requestServer(requestRegist);
				}
				break;
			default:
				break;
			}
		}

		/**
		 * 将输入的数据赋值
		 */
		private void setdata() {
			userName = registUser.getText().toString().trim();
			firstPassword = registPassword.getText().toString().trim();
			secondPassword = confirmat_password.getText().toString().trim();
			referee = recommend_edit.getText().toString().trim();
			email = Email.getText().toString().trim();
		}

		/**
		 * 验证方法
		 * 
		 * @return
		 */
		private boolean Verification() {
			boolean isVerify = true;
			if (userName.equals("")) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "用户名不能为空",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (firstPassword.equals("")) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "密码不能为空",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (firstPassword.length() < 6) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "密码长度不能小于6",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (secondPassword.equals("")) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "确认密码不能为空",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (!secondPassword.equals(firstPassword)) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "2次输入密码不一致",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (email.equals("")) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "邮箱不能为空",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			if (!EmailFormat(email)) {
				isVerify = false;
				Toast.makeText(RegistActivity.this, "邮箱的格式不对",
						Toast.LENGTH_SHORT).show();
				return isVerify;
			}
			// if (birthday.equals("")) {
			// isVerify = false;
			// Toast.makeText(RegistActivity.this,
			// "请选择宝宝生日",Toast.LENGTH_SHORT).show();
			// return isVerify;
			// }

			return isVerify;
		}
	};

	/**
	 * 邮箱判断正则表达式
	 * 
	 * @param eMAIL1
	 * @return
	 */
	private boolean EmailFormat(String eMail) {
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(eMail);
		return mc.matches();

	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	/**
	 * 注册按钮的请求
	 */
	private PageRequest requestRegist = new PageRequest() {
		@Override
		public void requestServer() {
			params = new TreeMap<String, String>();
			params.put("Account", userName);
			params.put("Password", firstPassword);
			params.put("Email", email);
			params.put("Recommend", referee);
			params.put("BabyBirthday", birthday);

			Tools.requestToParse(RegistActivity.this, ConstantUrl.REGISTURL,
					"Register", params, registHandler, false);
			System.out.println("1111111");
			if (Tools.responseValue == 1) {
				if (registHandler.result_code == 0) {
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
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.regist;
	}

	@Override
	protected void dealwithMessage(Message msg) {
		Intent intent = null;
		switch (msg.what) {
		case STATE_SUCCESS:
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			// saveData();
			MemberBean memberBean = new MemberBean();
			memberBean.setAccount(registHandler.account);
			memberBean.setPassword(firstPassword);
			memberBean.setMemberId(registHandler.memberId);
			memberBean.setLevel(registHandler.level);
			memberBean.setPrice(registHandler.price);
			memberBean.setScores(registHandler.scores);
			// 登录成功后调用 这2个方法 保存用户数据
			PublicParams.setMEMBERID(registHandler.memberId);
			UserInfoTools.saveUserInfo(this, memberBean, true);
			intent = new Intent(this, VipActivity.class);
			this.startActivity(intent);
			RegistActivity.this.finish();
			// PublicParams.setISFINISH(1);
			break;
		case STATE_FAILURE:
			Toast.makeText(this, registHandler.result_message,
					Toast.LENGTH_LONG).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	// /**
	// * 保存服务器传回来的数据
	// */
	// private void saveData() {
	//
	// String memberId = registHandler.memberId;
	// String account = registHandler.account;
	// int level = registHandler.level;
	// float price = registHandler.price;
	// float Scores = registHandler.scores;
	// String RecommendTicketMessage = registHandler.recommendTicketMessage;
	//
	// }
	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	// @Override
	// protected void backBtnOnClick() {
	// Intent intent = new Intent(this, LoginActivity.class);
	// intent.putExtra("shopcart", "");
	// startActivity(intent);
	// this.finish();
	// }
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // 按下返回键
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	// Intent intent = new Intent(this, LoginActivity.class);
	// intent.putExtra("shopcart", "");
	// startActivity(intent);
	// this.finish();
	// }
	//
	// return false;
	// }
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker() {
		// isShowDate = false;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		dialog = new Dialog(this);
		dialog.setTitle("请选择日期");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);

		// 年
		final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(false);// 可循环滚动
		wv_year.setLabel("年");// 添加文字

		if (repYear != 0) {
			wv_year.setCurrentItem(repYear - START_YEAR);
		} else {
			wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		}
		// 月
		final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(false);
		wv_month.setLabel("月");
		if (repMonth != 0) {
			wv_month.setCurrentItem(repMonth - 1);
		} else {
			wv_month.setCurrentItem(month);
		}

		// 日
		final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(false);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		if (repDay != 0) {
			wv_day.setCurrentItem(repDay - 1);
		} else {
			wv_day.setCurrentItem(day - 1);
		}

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				int Max_day;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					Max_day = 30;
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					Max_day = 29;
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						Max_day = 28;
					} else {
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
						Max_day = 27;
					}
				}
				if (wv_day.getCurrentItem() > Max_day) {

					wv_day.setCurrentItem(Max_day, true);
				}

			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				int Max_day;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					Max_day = 30;
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					Max_day = 29;
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						Max_day = 28;
					} else {
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
						Max_day = 27;
					}
				}
				if (wv_day.getCurrentItem() > Max_day) {
					wv_day.setCurrentItem(Max_day, true);
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;
		int itemHeight = 0;
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		itemHeight = metrics.heightPixels;
		if (0 <= itemHeight && itemHeight <= 500) {
			textSize = 12;
		} else if (500 <= itemHeight && itemHeight <= 800) {
			textSize = 16;
		} else {
			textSize = 17;
		}

		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// isShowDate = true;
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// 设置日期的显示
				childbirth.setText("宝宝生日："
						+ (wv_year.getCurrentItem() + START_YEAR) + "-"
						+ decimal.format((wv_month.getCurrentItem() + 1)) + "-"
						+ decimal.format((wv_day.getCurrentItem() + 1)) + " ");
				repYear = wv_year.getCurrentItem() + START_YEAR;
				repMonth = wv_month.getCurrentItem() + 1;
				repDay = wv_day.getCurrentItem() + 1;
				birthday = (wv_year.getCurrentItem() + START_YEAR) + "/"
						+ decimal.format((wv_month.getCurrentItem() + 1)) + "/"
						+ decimal.format((wv_day.getCurrentItem() + 1));
				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// isShowDate = true;
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
}
