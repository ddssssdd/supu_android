package cc.android.supu;

/**
 * 新建地址
 * zsx
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.android.supu.bean.CityBean;
import cc.android.supu.bean.ConsigneeBean;
import cc.android.supu.datebase.AllCityListDbHelper;
import cc.android.supu.datebase.CityDbhelper;
import cc.android.supu.handler.CreateAddressHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import cc.android.supu.wheelview.ArrayListWheelAdapter;
import cc.android.supu.wheelview.OnWheelScrollListener;
import cc.android.supu.wheelview.WheelView;

public class NewAdressActivity extends BaseActivity {
	/** 成功 */
	private final static int DISTRICT_SUCCESS = 1;
	/** 成功 */
	private final static int DELETE_SUCCESS = 2;
	/** 失败 */
	private final static int DISTRICT_FAIL = 0;
	/** 无网络 */
	private final static int DISTRICT_ERROR = -1;
	/** 城市成功 */
	private final static int CITY_SUCCESS = 1000;
	/** 城市失败 */
	private final static int CITY_ERROR = 1001;
	private ArrayList<String> cityState;
	/** 收件人 */
	private EditText newConsignee;
	/** 收件地址 */
	private TextView newAddress;
	/** 地址的详细信息 */
	private EditText newAddressInfo;
	/** 邮编 */
	private EditText newZipCode;
	/** 收件人手机 */
	private EditText newMobile;
	/** 收件人固话 */
	private EditText newTel;
	/** 传给服务器的一些参数 */
	private int consigneeId;
	private String newConsigneeS, newAddressS, newAddressInfoS, areaID = "",
			cityID = "", provinceID = "", newTelS, newMobileS, newZipCodeS,
			newEmailS;
	private boolean IsDefault = false;

	/** 设置默认地址 ，删除编辑地址 */
	private Button setAddressButton, deleteAddressButton;
	/** 键值对 */
	private TreeMap<String, String> params;
	/** 点击新建的handler */
	private CreateAddressHandler createAddressHandler;
	/** 获得全部的省市 */
	// private GetAllDistrictHandler getAllDistrictHandler;
	/** 获得全部的省市 */
	private List<CityBean> cityBeanList;
	/** 获得全部的省 */
	private List<CityBean> provinceList;
	/** 获得全部的区 */
	private List<CityBean> districtList;
	/** 获得全部的区 */
	private List<CityBean> districtList1;
	/** 获得全部的市 */
	private List<CityBean> cityList;
	/** 获得全部的市 */
	private List<CityBean> cityList1;
	private RelativeLayout ButtonRelativeLayout;

	/** 获得全部的省市的对话框 */
	private Dialog dialog;
	/** 是从哪里传过来的 false 点击新建 还是item */
	private boolean showUp = false;
	/** 地址bean */
	private ConsigneeBean consigneeBean;
	/** 地址选择器控件 */
	private LinearLayout add_layout;
	/** 省View */
	WheelView provinceView;
	/** 市View */
	WheelView cityView;
	/** 区View */
	WheelView areaView;
	/** 省 */
	private String province;
	/** 站位文本 */
	private TextView emptyTextView;
	/** 省旧值 */
	int oldScroll = 1;
	/** 城市旧值 */
	int oldCityScroll = 1;
	/** 是不是默认地址的请求 0不是 1是 */
	private int defaultAdress = 0;
	/** 开启独立线程 */
	private CityThread cityThread = null;
	/** 是否可以点击 */
	private boolean IsButton = true;
	private int recordCity = -1;
	private int recordProvince = -1;
	private int recordDistrict = -1;

	@Override
	protected void initPage() {

		getInitData();
		cityThread = new CityThread();
		cityThread.start();
		IsButton = true;
		ButtonRelativeLayout = (RelativeLayout) findViewById(R.id.ButtonRelativeLayout);
		add_layout = (LinearLayout) findViewById(R.id.add_layout);
		provinceView = (WheelView) findViewById(R.id.add_wheel_province);
		cityView = (WheelView) findViewById(R.id.add_wheel_city);
		areaView = (WheelView) findViewById(R.id.add_wheel_area);
		emptyTextView = (TextView) findViewById(R.id.emptyTextView);

		newConsignee = (EditText) findViewById(R.id.newConsignee);
		newAddress = (TextView) findViewById(R.id.newAddress);
		ButtonRelativeLayout.setOnClickListener(buttonListener);
		newAddressInfo = (EditText) findViewById(R.id.newAddressInfo);

		newZipCode = (EditText) findViewById(R.id.newZipCode);
		newMobile = (EditText) findViewById(R.id.newMobile);
		newTel = (EditText) findViewById(R.id.newTel);
		newTel.setOnKeyListener(onKey);
		setText();

		setAddressButton = (Button) findViewById(R.id.setAddressButton);
		setAddressButton.setOnClickListener(buttonListener);
		deleteAddressButton = (Button) findViewById(R.id.deleteAddressButton);
		deleteAddressButton.setOnClickListener(buttonListener);
		createAddressHandler = new CreateAddressHandler();
		params = new TreeMap<String, String>();
		if (IsDefault) {
			setAddressButton.setText("已默认收货地址");
			setAddressButton.setTextColor(Color.BLACK);
			setAddressButton.setBackgroundResource(R.drawable.consignee_b);
		} else {
			setAddressButton.setText("设置默认收货地址");
			setAddressButton.setTextColor(Color.WHITE);
			setAddressButton.setBackgroundResource(R.drawable.consignee_red);
		}
		if (showUp) {
			deleteAddressButton.setVisibility(View.VISIBLE);
			emptyTextView.setVisibility(View.VISIBLE);
		} else {
			deleteAddressButton.setVisibility(View.GONE);
			emptyTextView.setVisibility(View.GONE);
		}
	}

	/**
	 ** 从数据库获取消息  *      
	 */
	class CityThread extends Thread {

		public void run() {
			try {
				getCityBeanlist();
				handler.sendMessage(handler.obtainMessage(CITY_SUCCESS));
			} catch (Exception e) {
				handler.sendMessage(handler.obtainMessage(CITY_ERROR));
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置修改界面的默认地址
	 * 
	 */
	private void setText() {
		if (consigneeBean != null) {
			showUp = true;
			consigneeId = consigneeBean.ConsigneeID;
			areaID = consigneeBean.AreaID;
			cityID = consigneeBean.CityID;
			provinceID = consigneeBean.ProvinceID;
			// 收件人地址
			newAddress.setText(consigneeBean.Address);
			newConsignee.setText(consigneeBean.Consignee);
			newAddressInfo.setText(consigneeBean.AddressInfo);
			newZipCode.setText(consigneeBean.ZipCode);
			newMobile.setText(consigneeBean.Mobile);
			newTel.setText(consigneeBean.Tel);
			if (consigneeBean.IsDefault) {
				IsDefault = true;
			}
		}
	}

	/**
	 * 得到省市区名字
	 */
	private String getNameSheng(String Id) {
		if (cityBeanList == null)
			return "";
		String name = "";

		for (int i = 0; i < cityBeanList.size(); i++) {
			if (cityBeanList.get(i).getAreaCode().equals(Id)) {
				return cityBeanList.get(i).getAreaName();
			}
		}
		return name;
	}

	/**
	 * 得到初始化的数据
	 */
	private void getInitData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		consigneeBean = (ConsigneeBean) bundle.get("ConsigneeBean");
	}

	/**
	 * 
	 * 按钮的响应事件
	 */
	private Button.OnClickListener buttonListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.setAddressButton:
				if (IsDefault)
					return;
				IsDefault = !IsDefault;
				getEditData();
				if (isVerificat()) {
					defaultAdress = 1;
					requestServer(requestNewAddress);
				} else {
					IsDefault = !IsDefault;
					return;
				}

				if (IsDefault) {
					setAddressButton.setText("已默认收货地址");
					setAddressButton.setTextColor(Color.BLACK);
					setAddressButton
							.setBackgroundResource(R.drawable.consignee_b);
				} else {
					setAddressButton.setText("设置默认收货地址");
					setAddressButton.setTextColor(Color.WHITE);
					setAddressButton
							.setBackgroundResource(R.drawable.consignee_red);
				}

				break;
			case R.id.deleteAddressButton:
				if (showUp) {
					showDeleteDialog();
				} else {
					Toast.makeText(NewAdressActivity.this, "你还没有成功创建",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.ButtonRelativeLayout:
				if (cityBeanList == null || cityBeanList.size() <= 0) {
					Toast.makeText(NewAdressActivity.this, "数据正在加载请等待",
							Toast.LENGTH_SHORT).show();
				} else {
					// if (IsButton)
					if (dialog == null || !dialog.isShowing())
						showDateTimePicker();
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event != null
				&& event.getRepeatCount() == 0) {
			if (IsButton)
				IsButton = true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 按钮的请求
	 */
	private PageRequest requestDeleteAddress = new PageRequest() {
		@Override
		public void requestServer() {

			params.put("ConsigneeId", consigneeId + "");

			Tools.requestToParse(NewAdressActivity.this,
					ConstantUrl.DELETECONSIGNEE, "DeleteConsignee", params,
					createAddressHandler, false);
			if (Tools.responseValue == 1) {
				if (createAddressHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(DELETE_SUCCESS));
				} else {
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};

	/**
	 * 按钮的请求
	 */
	private PageRequest requestNewAddress = new PageRequest() {
		@Override
		public void requestServer() {
			params.put("ConsigneeId", consigneeId + "");
			params.put("Consignee", newConsigneeS);
			params.put("Address", newAddressInfoS);

			params.put("AreaID", areaID);
			params.put("CityID", cityID);
			params.put("ProvinceID", provinceID);
			params.put("Tel", newTelS);
			params.put("Mobile", newMobileS);
			params.put("ZipCode", newZipCodeS);
			params.put("Email", "");
			params.put("IsDefault", IsDefault + "");

			Tools.requestToParse(NewAdressActivity.this,
					ConstantUrl.ADDORMOIDFYCONSIGNEE, "AddOrMoidfyConsignee",
					params, createAddressHandler, false);
			if (Tools.responseValue == 1) {
				if (createAddressHandler.result_code == 0) {
					handler.sendMessage(handler.obtainMessage(STATE_SUCCESS));
				} else {
					if (defaultAdress == 1) {
						IsDefault = !IsDefault;
					}
					handler.sendMessage(handler.obtainMessage(STATE_FAILURE));
				}
			} else {
				if (defaultAdress == 1) {
					IsDefault = !IsDefault;
				}
				handler.sendMessage(handler.obtainMessage(STATE_ERROR));
			}
		}
	};

	@Override
	protected void dealwithMessage(Message msg) {
		// Intent intent =null;
		switch (msg.what) {
		case STATE_SUCCESS:
			if (showUp) {
				Toast.makeText(NewAdressActivity.this, "修改成功",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(NewAdressActivity.this, "新建成功",
						Toast.LENGTH_SHORT).show();
			}
			this.finish();
			break;
		case DELETE_SUCCESS:
			Toast.makeText(NewAdressActivity.this, "删除成功", Toast.LENGTH_SHORT)
					.show();
			this.finish();
			break;
		case STATE_FAILURE:
			Toast.makeText(this, createAddressHandler.result_message,
					Toast.LENGTH_LONG).show();
			break;
		case STATE_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
			break;
		case DISTRICT_SUCCESS:
			// cityBeanList = getAllDistrictHandler.getCityBeanList();
			// getCityBeanlist();
			break;
		case DISTRICT_FAIL:
			Toast.makeText(this, "请求数据失败", Toast.LENGTH_LONG).show();
			break;
		case DISTRICT_ERROR:
			Toast.makeText(this, "网络异常，请检查网络", Toast.LENGTH_LONG).show();
			break;
		case CITY_SUCCESS:
			// Toast.makeText(this, "获取列表成功", Toast.LENGTH_LONG).show();
			setText_b();
			break;
		case CITY_ERROR:
			// Toast.makeText(this, "获取列表失败", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	};

	/**
	 * 添加空格
	 */
	private void setText_b() {

		String sheng = getNameSheng(provinceID);
		String shi = getNameSheng(cityID);
		String qu = getNameSheng(areaID);
		String sheng1 = sheng;
		String shi1 = shi;
		String qu1 = qu;
		if (sheng.length() > 5) {
			sheng = sheng.substring(0, 4) + "..";
		}
		if (shi.length() > 5) {
			shi = shi.substring(0, 4) + "..";
		}
		if (qu.length() > 5) {
			qu = qu.substring(0, 4) + "..";
		}

		if (!sheng.equals("") && !shi.equals("") && !qu.equals("")) {
			newAddress.setText(sheng1 + " " + shi1 + " " + qu1);
			// 设置默认值 有一点麻烦 但是也没有别的办法啊
			provinceList = getProvinceList();
			for (int i = 0; i < provinceList.size(); i++) {
				if (provinceList.get(i).getAreaName() != null) {
					if (provinceList.get(i).getAreaName().trim().equals(sheng)) {
						recordProvince = i;
						break;
					}
				}
			}

			if (recordProvince >= 0) {
				cityList = getCityList(provinceList.get(recordProvince)
						.getAreaCode().toString());

			} else {
				recordProvince = 0;
				cityList = getCityList(provinceList.get(0).getAreaCode()
						.toString());
			}
			if (cityList == null || cityList.size() <= 0) {
				recordProvince = -1;
				return;
			}

			for (int i = 0; i < cityList.size(); i++) {
				if (cityList.get(i).getAreaName() != null) {
					if (cityList.get(i).getAreaName().trim().equals(shi)) {
						recordCity = i;
						break;
					}
				}
			}
			if (recordCity >= 0) {
				districtList = getCityList(cityList.get(recordCity)
						.getAreaCode().toString());
			} else {
				districtList = getCityList(cityList.get(0).getAreaCode()
						.toString());
			}
			if (districtList == null || districtList.size() <= 0) {
				recordProvince = -1;
				recordCity = -1;
				return;
			}
			for (int i = 0; i < districtList.size(); i++) {
				if (districtList.get(i).getAreaName() != null) {
					if (districtList.get(i).getAreaName().trim().equals(qu)) {
						recordDistrict = i;
						break;
					}
				}
			}

		}
	}

	/**
	 * 得到全部的省市区
	 */
	private void getCityBeanlist() {
		try {
//			cityState = UserInfoTools.getCity(NewAdressActivity.this);
//			String version1 = "1";
//			if (cityState != null && cityState.size() > 0) {
//
//				version1 = cityState.get(1);
//				System.out.println("version = " + version1);
//			}
//			if (version1.equals("1")) {
//				System.err.println("现有数据库");
				CityDbhelper cityDbhelper = new CityDbhelper(
						NewAdressActivity.this);
				cityBeanList = cityDbhelper.queryAllCity();
//			} else {
//				System.err.println("创建数据库");
//				AllCityListDbHelper allCityListDbHelper = new AllCityListDbHelper(
//						NewAdressActivity.this);
//				cityBeanList = allCityListDbHelper.queryAllCity();
//			}

		} catch (Exception e) {
			handler.sendMessage(handler.obtainMessage(CITY_ERROR));
			e.printStackTrace();
		}
	}

	@Override
	protected String setBackBtn() {
		return "返回";
	}

	@Override
	protected String setTitle() {
		if (showUp) {
			return "收货地址";
		}
		return "新建收货地址";
	}

	@Override
	protected String setEnterBtn() {
		if (showUp) {
			return "修改";
		}
		return "完成";
	}

	@Override
	protected void enterBtnOnClick() {
		getEditData();
		if (isVerificat()) {
			defaultAdress = 0;
			requestServer(requestNewAddress);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		// requestServer(allCityRequest);

	}

	OnKeyListener onKey = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {

				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(
								NewAdressActivity.INPUT_METHOD_SERVICE);

				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);
				}
				getEditData();
				if (isVerificat()) {
					defaultAdress = 0;
					requestServer(requestNewAddress);
				}

				return true;

			}

			return false;
		}

	};

	/**
	 * 获得全部的省市区
	 */
	// protected PageRequest allCityRequest = new PageRequest() {
	//
	// @Override
	// public void requestServer() {
	// getAllDistrictHandler = new GetAllDistrictHandler();
	// Tools.requestToParse(NewAdressActivity.this, ConstantUrl.GETALLDISTRICT,
	// "GetAllDistrict", null,
	// getAllDistrictHandler, false);
	// if (Tools.responseValue == 1) {
	// handler.sendEmptyMessage(DISTRICT_SUCCESS);
	// } else if (Tools.responseValue == 2) {
	// handler.sendEmptyMessage(DISTRICT_FAIL);
	// } else {
	// handler.sendEmptyMessage(DISTRICT_ERROR);
	// }
	// }
	// };
	/**
	 * 是否通过验证
	 * 
	 */
	private boolean isVerificat() {
		if (newConsigneeS.equals("")) {
			Toast.makeText(NewAdressActivity.this, "收件人不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (newAddressInfoS.equals("")) {
			Toast.makeText(NewAdressActivity.this, "收货地址不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (newAddressInfoS.length() > 100) {
			Toast.makeText(NewAdressActivity.this, "收货地址超出100字符",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (provinceID.equals("")) {
			Toast.makeText(NewAdressActivity.this, "省不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (cityID.equals("")) {
			Toast.makeText(NewAdressActivity.this, "市不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (areaID.equals("")) {
			Toast.makeText(NewAdressActivity.this, "县区不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		// if (newZipCodeS.equals("")) {
		// Toast.makeText(NewAdressActivity.this, "邮编不能为空", Toast.LENGTH_SHORT)
		// .show();
		// return false;
		// }
		if (!newZipCodeS.equals("")) {
			if (!checkPostCode(newZipCodeS)) {
				Toast.makeText(NewAdressActivity.this, "邮编格式错误",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		if (newTelS.equals("") && newMobileS.equals("")) {
			Toast.makeText(NewAdressActivity.this, "手机和电话必填一项",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!newMobileS.equals("")) {
			if (!isCellphone(newMobileS)) {
				Toast.makeText(NewAdressActivity.this, "手机号码格式错误",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		if (!newTelS.equals("")) {
			System.out.println("newTelS" + newTelS);
			if (newTelS.length() == 11 || newTelS.length() == 12) {

			} else {
				Toast.makeText(NewAdressActivity.this, "固话号码格式错误",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查邮编是否 合法
	 * 
	 * @param value
	 * @return
	 */
	public boolean checkPostCode(String value) {
		return value.matches("[1-9]\\d{5}(?!\\d)");
	}

	/**
	 * 检查电话输入 是否正确 正确格 式 012-87654321、0123-87654321、0123－7654321
	 * 
	 * @param value
	 * @return
	 */
	// public boolean checkTel(String value) {
	// return value.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)");
	// }

	/**
	 * 手机号码验证
	 * 
	 * @param str
	 * @return
	 */
	public boolean isCellphone(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到文本框中填写的信息
	 */
	private void getEditData() {

		newConsigneeS = newConsignee.getText().toString();
		newAddressInfoS = newAddressInfo.getText().toString();
		newAddressInfoS = newAddressInfoS.replaceAll("\n", " ");
		newAddressInfoS = newAddressInfoS.replaceAll("\r", " ");
		newTelS = newTel.getText().toString();
		newMobileS = newMobile.getText().toString();
		newZipCodeS = newZipCode.getText().toString();
		newEmailS = null;

	}

	@Override
	int setLayout() {
		return R.layout.newadress;
	}

	@Override
	int setBottomIndex() {
		return 3;
	}

	/**
	 * 获得省的列表
	 * 
	 * @return
	 */
	private List<CityBean> getProvinceList() {
		if (cityBeanList == null || cityBeanList.size() <= 0) {
			return null;
		}
		List<CityBean> ProvinceListSet = new ArrayList<CityBean>();
		for (int i = 0; i < cityBeanList.size(); i++) {
			if (cityBeanList.get(i) != null
					&& cityBeanList.get(i).getParentID().equals("000000")) {
				CityBean cityBean = new CityBean();
				cityBean.setAreaCode(cityBeanList.get(i).getAreaCode());
				cityBean.setParentID(cityBeanList.get(i).getParentID());
				String name = cityBeanList.get(i).getAreaName();
				if (name.length() > 5) {
					String nameSub = name.substring(0, 4);
					cityBean.setAreaName(nameSub + "..");

				} else {
					cityBean.setAreaName(name);
				}
				ProvinceListSet.add(cityBean);
			}
		}
		return ProvinceListSet;
	}

	/**
	 * 获得市 或 区域的列表
	 * 
	 * @return
	 */
	private List<CityBean> getCityList(String AreaCode) {
		if (cityBeanList == null || cityBeanList.size() <= 0) {
			return null;
		}
		List<CityBean> CityListSet = new ArrayList<CityBean>();
		for (int i = 0; i < cityBeanList.size(); i++) {
			if (cityBeanList.get(i) != null
					&& cityBeanList.get(i).getParentID().equals(AreaCode)) {
				CityBean cityBean = new CityBean();
				cityBean.setAreaCode(cityBeanList.get(i).getAreaCode());
				cityBean.setParentID(cityBeanList.get(i).getParentID());
				String name = cityBeanList.get(i).getAreaName();
				if (name.length() > 5) {
					String nameSub = name.substring(0, 4);
					cityBean.setAreaName(nameSub + "..");

				} else {
					cityBean.setAreaName(name);
				}
				CityListSet.add(cityBean);
			}
		}
		return CityListSet;
	}

	/**
	 * 选择地址的方法
	 */
	private void showDateTimePicker() {
		IsButton = false;
		dialog = new Dialog(this);
		dialog.setTitle("请选择城市");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);

		// 省
		final WheelView province = (WheelView) view.findViewById(R.id.year);
		provinceList = getProvinceList();

		province.setAdapter(new ArrayListWheelAdapter(provinceList,
				provinceList.size()));
		province.setCyclic(false);// 可循环滚动
		if (recordProvince != -1) {
			province.setCurrentItem(recordProvince);
		}
		// province.setCurrentItem();// 初始化时显示的数据
		// 市
		final WheelView city = (WheelView) view.findViewById(R.id.month);
		if (provinceList != null && provinceList.size() > 0) {

			cityList = getCityList(provinceList.get(province.getCurrentItem())
					.getAreaCode().toString());
			city.setAdapter(new ArrayListWheelAdapter(cityList, cityList.size()));
		} else {
			city.setAdapter(null);
		}
		city.setCyclic(false);// 可循环滚动
		if (recordCity != -1) {
			city.setCurrentItem(recordCity);
		}

		// 区
		final WheelView district = (WheelView) view.findViewById(R.id.day);
		if (cityList != null && cityList.size() > 0) {
			districtList = getCityList(cityList.get(city.getCurrentItem())
					.getAreaCode().toString());
			district.setAdapter(new ArrayListWheelAdapter(districtList,
					districtList.size()));
		} else {
			district.setAdapter(null);
		}
		district.setCyclic(false);// 可循环滚动
		if (recordDistrict != -1) {
			district.setCurrentItem(recordDistrict);
		}
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

		province.TEXT_SIZE = textSize;
		city.TEXT_SIZE = textSize;
		district.TEXT_SIZE = textSize;
		//
		OnWheelScrollListener wheelProvinceListener1 = new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				cityList = getCityList(provinceList.get(wheel.getCurrentItem())
						.getAreaCode().toString());
				if (cityList != null && cityList.size() > 0) {
					oldScroll = wheel.getCurrentItem();
				}
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				int curwheel = wheel.getCurrentItem();
				if (provinceList != null && provinceList.size() > 0) {
					try {
						cityList = getCityList(provinceList
								.get(province.getCurrentItem()).getAreaCode()
								.toString());
						city.setAdapter(new ArrayListWheelAdapter(cityList,
								cityList.size()));
					} catch (Exception e) {
						city.setAdapter(null);
					}

				} else {
					city.setAdapter(null);
				}

				if (cityList != null && cityList.size() > 0) {
					try {
						districtList = getCityList(cityList
								.get(city.getCurrentItem()).getAreaCode()
								.toString());
						district.setAdapter(new ArrayListWheelAdapter(
								districtList, districtList.size()));
					} catch (Exception e) {
						district.setAdapter(null);
					}
				} else {
					district.setAdapter(null);
					try {
						// Thread.sleep(100);
						// province.setCurrentItem(oldScroll, true);
						String name = provinceList.get(curwheel).getAreaName()
								.toString();
						Toast.makeText(NewAdressActivity.this, "没有覆盖" + name,
								Toast.LENGTH_SHORT).show();
						// // 第二次加载
						if (provinceList != null && provinceList.size() > 0) {
							try {
								cityList = getCityList(provinceList
										.get(oldScroll).getAreaCode()
										.toString());
								city.setAdapter(new ArrayListWheelAdapter(
										cityList, cityList.size()));
								province.setAdapter(new ArrayListWheelAdapter(
										provinceList, provinceList.size()));
								// 根据城市找省
								// int index =
								// setSecondP(cityList,provinceList);
								province.setCurrentItem(oldScroll, true);
							} catch (Exception e) {
								city.setAdapter(null);
							}

						} else {
							city.setAdapter(null);
						}

						if (cityList != null && cityList.size() > 0) {
							try {
								districtList = getCityList(cityList
										.get(city.getCurrentItem())
										.getAreaCode().toString());
								if (districtList != null
										&& districtList.size() > 0) {
									district.setAdapter(new ArrayListWheelAdapter(
											districtList, districtList.size()));
								} else {
									district.setAdapter(null);
								}
							} catch (Exception e) {
								district.setAdapter(null);
							}
						} else {
							district.setAdapter(null);
						}

					} catch (Exception e) {

					}
				}

				if (city.getCurrentItem() > cityList.size() - 1) {

					city.setCurrentItem(cityList.size() - 1, true);
				}
				if (district.getCurrentItem() > districtList.size() - 1) {
					district.setCurrentItem(districtList.size() - 1, true);
				}
			}

		};

		// 添加"市"监听
		// OnWheelChangedListener wheelCityListener = new
		// OnWheelChangedListener() {
		// public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// if (cityList != null && cityList.size() > 0) {
		// try {
		// districtList = getCityList(cityList
		// .get(city.getCurrentItem()).getAreaCode()
		// .toString());
		// if (districtList != null && districtList.size() > 0) {
		// district.setAdapter(new ArrayListWheelAdapter(
		// districtList, districtList.size()));
		// }else{
		// district.setAdapter(null);
		// }
		// } catch (Exception e) {
		// district.setAdapter(null);
		// }
		// } else {
		// district.setAdapter(null);
		// }
		// if (district.getCurrentItem() > districtList.size()-1) {
		// district.setCurrentItem(districtList.size()-1, true);
		// }
		// }
		// };
		OnWheelScrollListener wheelCityListener1 = new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				oldCityScroll = wheel.getCurrentItem();

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				int curwheel = wheel.getCurrentItem();
				if (cityList != null && cityList.size() > 0) {
					try {
						districtList = getCityList(cityList
								.get(city.getCurrentItem()).getAreaCode()
								.toString());
						if (districtList != null && districtList.size() > 0) {
							district.setAdapter(new ArrayListWheelAdapter(
									districtList, districtList.size()));
						} else {
							city.setCurrentItem(oldCityScroll, true);
							String name = cityList.get(curwheel).getAreaName()
									.toString();
							Toast.makeText(NewAdressActivity.this,
									"没有覆盖" + name, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						district.setAdapter(null);
					}
				} else {
					district.setAdapter(null);
				}
				if (district.getCurrentItem() > districtList.size() - 1) {
					district.setCurrentItem(districtList.size() - 1, true);
				}

			}

		};
		// 设置监听
		province.addScrollingListener(wheelProvinceListener1);
		city.addScrollingListener(wheelCityListener1);
		// city.addChangingListener(wheelCityListener);

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (province.isScrollingPerformed) {
					System.out.println("isScrollingisScrolling11");
					return;
				}

				if (city.isScrollingPerformed) {
					System.out.println("isScrollingisScrolling22");
					return;
				}
				if (district.isScrollingPerformed)
					return;

				if (provinceList != null && provinceList.size() > 0) {
					provinceID = provinceList.get(province.getCurrentItem())
							.getAreaCode().toString();
					recordProvince = province.getCurrentItem();
					System.out.println("recordProvince" + recordProvince);
				}
				cityList1 = getCityList(provinceID);

				if (cityList != null && cityList.size() > 0) {
					cityID = cityList.get(city.getCurrentItem()).getAreaCode()
							.toString();
					if (cityList1.size() > city.getCurrentItem()) {
						String cityID1 = cityList1.get(city.getCurrentItem())
								.getAreaCode().toString();
						if (!cityID.trim().equals(cityID1)) {
//							System.out.println("hehehe111111");
							// provinceID = "";
							// cityID = "";
							// areaID = "";
							// newAddressS = "请选择地址";
							return;
						}
					} else {
//						System.out.println("hehehe2222222222");
						// provinceID = "";
						// cityID = "";
						// areaID = "";
						// newAddressS = "请选择地址";
						return;
					}

					recordCity = city.getCurrentItem();
					System.out.println("recordCity" + recordCity);
				}
				districtList1 = getCityList(cityID);

				if (districtList != null && districtList.size() > 0) {
					areaID = districtList.get(district.getCurrentItem())
							.getAreaCode().toString();
					if (districtList1.size() > district.getCurrentItem()) {
						String areaID1 = districtList1
								.get(district.getCurrentItem()).getAreaCode()
								.toString();
						if (!areaID.trim().equals(areaID1)) {
//							System.out.println("hehehe33333333");
							// provinceID = "";
							// cityID = "";
							// areaID = "";
							// newAddressS = "请选择地址";
							return;
						}
					} else {
//						System.out.println("hehehe4444444");
						// provinceID = "";
						// cityID = "";
						// areaID = "";
						// newAddressS = "请选择地址";
						return;
					}

					recordDistrict = district.getCurrentItem();
					System.out.println("recordDistrict" + recordDistrict);
				}

				if (districtList == null || districtList.size() <= 0) {
					provinceID = "";
					cityID = "";
					areaID = "";
					newAddressS = "请选择地址";
				} else {
					newAddressS = getName(provinceID) + " " + getName(cityID)
							+ " " + getName(areaID);
				}
				newAddress.setText(newAddressS);
				dialog.dismiss();
				IsButton = true;
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				IsButton = true;
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * 通过市 设置省
	 * 
	 * @param cityList2
	 */
	protected int setSecondP(List<CityBean> cityList2,
			List<CityBean> provinceList2) {
		if (cityList2 == null || cityList2.size() <= 0)
			return -1;

		if (provinceList2 == null || provinceList2.size() <= 0)
			return -1;

		for (int i = 0; i < provinceList2.size(); i++) {
			String id = provinceList2.get(i).getAreaCode();
			if (cityList2.get(0) != null) {
				if (cityList2.get(0).getParentID().equals(id)) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * 查找省 市 区
	 * 
	 */
	private String getName(String ID) {
		if (cityBeanList == null)
			return "";

		for (int i = 0; i < cityBeanList.size(); i++) {
			String areaCode = cityBeanList.get(i).getAreaCode();
			if (areaCode.equals(ID)) {
				return cityBeanList.get(i).getAreaName();
			}
		}
		return "";
	}

	/**
	 * 删除对话框
	 */
	private void showDeleteDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setMessage("您确定要删除?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						requestServer(requestDeleteAddress);
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				}).create();
		dialog.show();
	}
}
