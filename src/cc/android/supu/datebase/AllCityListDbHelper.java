package cc.android.supu.datebase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.android.supu.bean.CityBean;

/**
 * 对所有城市信息的操作类
 * 
 */
public class AllCityListDbHelper {

	/** 城市表名 */
	public static final String TABLE_NAME = "tb_city";
	/** 城市名 */
	public static final String VOLUMN_NAME = "areaName";
	/** 城市编号 */
	public static final String VOLUMN_AREACODE = "areaCode";
	/** 父城市编号 */
	public static final String VOLUMN_PARENTID = "parentID";

	/** 数据库操作对象 */
	private SQLiteHelperCity helper;
	/** 数据库对象 */
	private SQLiteDatabase mdb;
	/** 数据库对象备用的数据库 */
	private SQLiteDatabase dataBase;

	private Context context;

	public AllCityListDbHelper(Context Kcontext) {
		context = Kcontext;
		helper = new SQLiteHelperCity(Kcontext);
	}

	/**
	 * 打开数据库
	 * 
	 * @return
	 */
	public SQLiteDatabase openDb() {
		if (helper != null) {
			helper.close();
		}
		return helper.getDatabase();
	}

	/**
	 * 创建城市表
	 * 
	 * @param db
	 */
	static void creatTableAllCity(SQLiteDatabase db) {
		StringBuffer creatTableSql = new StringBuffer();
		creatTableSql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
				.append("(").append(VOLUMN_NAME).append(" TEXT, ")
				.append(VOLUMN_AREACODE).append(" TEXT, ")
				.append(VOLUMN_PARENTID).append(" TEXT")

				.append(");");
		db.execSQL(creatTableSql.toString());
	}

	/**
	 * 保存数据
	 * 
	 * @param db
	 * @param value
	 */
	public void saveSomeDatas(SQLiteDatabase db, List<CityBean> value) {
		System.out.println("保存数据开始");
		if (value == null || value.size() <= 0) {
			return;
		}
		ContentValues values = new ContentValues();
		for (int i = 0; i < value.size(); i++) {
			values.put(VOLUMN_NAME, value.get(i).getAreaName());
			values.put(VOLUMN_AREACODE, value.get(i).getAreaCode());
			values.put(VOLUMN_PARENTID, value.get(i).getParentID());
			System.out.println("保存数据开始i" + i);
			db.insert(TABLE_NAME, null, values);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 得到所有城市
	 * 
	 * @return
	 */
	public List<CityBean> queryAllCity() {
		List<CityBean> cityList = null;
		Cursor cursor = null;
		try {
			mdb = openDb();
			cursor = mdb.query(TABLE_NAME, null, null, null, null, null, null);
			if (cursor.moveToFirst()) {
				cityList = new ArrayList<CityBean>();
				do {
					CityBean cityBean = new CityBean();
					cityBean.setAreaName(cursor.getString(cursor
							.getColumnIndex(VOLUMN_NAME)));
					cityBean.setAreaCode(cursor.getString(cursor
							.getColumnIndex(VOLUMN_AREACODE)));
					cityBean.setParentID(cursor.getString(cursor
							.getColumnIndex(VOLUMN_PARENTID)));

					cityList.add(cityBean);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (mdb != null) {
				mdb.close();
			}
		}

		return cityList;
	}

	/**
	 * 删除所有数据
	 * 
	 */
	public boolean deleteAllGoods() {
		mdb = openDb();
		int rowNum = mdb.delete(TABLE_NAME, null, null);
		if (mdb != null) {
			mdb.close();
		}
		return rowNum > 0 ? true : false;
	}

}
