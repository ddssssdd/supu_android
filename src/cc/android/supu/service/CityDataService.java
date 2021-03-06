package cc.android.supu.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cc.android.supu.bean.CityBean;
import cc.android.supu.datebase.AllCityListDbHelper;
import cc.android.supu.handler.GetAllDistrictHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;

public class CityDataService extends Service {
	/** 成功 */
	private final static int SUCCESS = 1;
	/** 失败 */
	private final static int ERROR = 2;
	/** 失败 */
	private final static int FAIL = 0;
	/** 获得全部的省市 */
	private static List<CityBean> cityBeanList;
	/** 获取消息线程 */
	private CityThread cityThread = null;
	/** 获得全部的省市 */
	private GetAllDistrictHandler getAllDistrictHandler;

	private ArrayList<String> cityState;

	private String version2;

	private int isEmpty1;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 开启线程
		cityThread = new CityThread();
		cityThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 ** 从服务器端获取消息  *      
	 */
	class CityThread extends Thread {

		public void run() {
			try {
				getCityData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得全部的省市区
	 */
	private void getCityData() {
		getAllDistrictHandler = new GetAllDistrictHandler();
		Tools.requestToParse(CityDataService.this, ConstantUrl.GETALLDISTRICT,
				"GetAllDistrict", null, getAllDistrictHandler, false);
		if (Tools.responseValue == 1) {
			handlerCity.sendEmptyMessage(SUCCESS);
		} else if (Tools.responseValue == 2) {
			handlerCity.sendEmptyMessage(FAIL);
		} else {
			handlerCity.sendEmptyMessage(ERROR);
		}
	}

	/**
	 * handler处理事件
	 */
	protected Handler handlerCity = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				setSuccess();
				break;
			case FAIL:
				// System.out.println("");
				break;
			case ERROR:

				break;
			default:
				break;
			}

		}
	};

	/**
	 * 请求成功之后处理数据
	 */
	private void setSuccess() {

		cityBeanList = getAllDistrictHandler.cityBeanList;

		if (cityBeanList == null || cityBeanList.size() <= 0) {
			System.out.println("没有数据");
		} else {
			System.out.println("有数据");
		}
		int version = getAllDistrictHandler.version;
		version2 = String.valueOf(version);
		// System.out.println("1111111111111");
		cityState = UserInfoTools.getCity(CityDataService.this);

		if (cityState != null && cityState.size() > 0) {
			String isEmpty = cityState.get(0);
			String version1 = cityState.get(1);

			int version3 = Integer.parseInt(version1);
			isEmpty1 = Integer.parseInt(isEmpty);
			 System.out.println("version3+" + version3);
			 System.out.println("isEmpty1+" + isEmpty1);
			if (isEmpty1 == 0) {
				// 在保存最新的
				 System.out.println("第一次进入");
				// ThreadManage.addTask(CityDataService.this);
				// ThreadManage.start();
				saveCityData();
			} else if (version > version3) {
				// System.out.println("1111111111111+++bbbb");
				// ThreadManage.addTask(CityDataService.this);
				// ThreadManage.start();
				saveCityData();
			}

		} else {
			// System.out.println("pppppppppppppppppp");
		}
		CityDataService.this.stopSelf();
	}

	// /**
	// * 得到全部的省市区
	// */
	// private void getCityBeanlist() {
	// AllCityListDbHelper allCityListDbHelper = new AllCityListDbHelper(
	// CityDataService.this);
	// if (cityBeanList == null || cityBeanList.size() <= 0) {
	// System.out.println("没有数据111");
	// }
	// }

	/**
	 * 保存数据到数据库
	 */
	private void saveCityData() {
		try {
			AllCityListDbHelper allCityListDbHelper = new AllCityListDbHelper(
					CityDataService.this);

			allCityListDbHelper.deleteAllGoods();

			SQLiteDatabase db = allCityListDbHelper.openDb();
			allCityListDbHelper.saveSomeDatas(db, cityBeanList);
			UserInfoTools.saveCity(CityDataService.this, "1", version2);
//			UserInfoTools.saveCity(CityDataService.this, "1", "2");
			if (db != null && db.isOpen()) {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		System.exit(0);
		super.onDestroy();
	}

	// @Override
	// public void Threading() {
	//
	// if (isEmpty1 == 0) {
	// saveCityData(1);
	// } else {
	// saveCityData(2);
	// }
	// CityDataService.this.stopSelf();
	// }

}
