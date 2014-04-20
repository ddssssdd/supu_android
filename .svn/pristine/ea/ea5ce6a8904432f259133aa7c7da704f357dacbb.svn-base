package cc.android.supu.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	/** 数据库操作对象 */
	private SQLiteDatabase db = null;
	/** 数据库名 */
	private static final String DATABASE_NAME = "supu.db";
	/** 数据库版本号 */
	private static final int DATABASE_VERSION = 1;

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public SQLiteDatabase getDatabase() {
		if (db != null && db.isOpen()) {
			db.close();
		}
		db = getWritableDatabase();// 创建数据库对象
		return db;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 创建购物车表
		String sql = "create table if not exists android_metadata(locale text);";// 回车
		String sql2 = "insert into android_metadata(locale) values(\"en_US\");";// 回车
		db.execSQL(sql);
		db.execSQL(sql2);
		RecentViewDbHelper.creatTableRecent(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + RecentViewDbHelper.TABLE_NAME);
		onCreate(db);
	}

}
