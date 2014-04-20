package cc.android.supu.datebase;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cc.android.supu.tools.UserInfoTools;

/**
 * 城市 这里添加一个本地的默认数据库 但是在数据库更新的时候还是需要很长的时间大约2分钟（網速有關 插入數據有關）
 * 
 * @author zsx
 * 
 */
public class SQLiteHelperCity extends SQLiteOpenHelper {

	/** 数据库操作对象 */
	private SQLiteDatabase db = null;
	/** 数据库名 */
	private static final String DATABASE_NAME = "supu1.db";
	/** 数据库版本号 */
	private static final int DATABASE_VERSION = 1;
	private ArrayList<String> cityState;
	
	private Context context;

	public SQLiteHelperCity(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * 得到数据库isEmpty="0" 要是空 或者版本没变version1="1" 就用默认的数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		if (db != null && db.isOpen()) {
			db.close();
		}
//		cityState = UserInfoTools.getCity(context);
//		String version1 = "1";
//		if (cityState != null && cityState.size() > 0) {
//
//			version1 = cityState.get(1);
//			System.out.println("version = " + version1);
//		}
//		if (version1.equals("1")) {
//			System.out.println("现有数据库");
//			Log.e("现有数据库", "现有数据库");
//			dbm = new DBManager(context);
//			dbm.openDatabase();
//		 	db = dbm.getDatabase();
//			if (null != db) {
//				return db;
//			}
//		}
		Log.e("创建的数据库", "创建的数据库");
		db = getWritableDatabase();// 创建数据库对象

		return db;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		AllCityListDbHelper.creatTableAllCity(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + AllCityListDbHelper.TABLE_NAME);
		onCreate(db);
	}

}
