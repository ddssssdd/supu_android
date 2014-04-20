package cc.android.supu.datebase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.android.supu.bean.ArticleGoodsBean;
import cc.android.supu.bean.GoodsBean;

/**
 * 最近浏览的存储 zsx
 */
public class RecentViewDbHelper {

	/** 表名 */
	public static final String TABLE_NAME = "tb_recent1";
	/** 图片url */
	public static final String VOLUMN_IMGFILE = "imgFile";
	/** 名称 */
	public static final String VOLUMN_NAME = "name";
	/** 价格 */
	public static final String VOLUMN_SHOP = "shop";
	/** 货品编号 */
	public static final String VOLUMN_GOODSSN = "GoodsSN";
	/** 浏览货物的时间 */
	public static final String VOLUMN_TIME = "GoodsTime";
	/** 数据库操作对象 */
	private SQLiteHelper helper;
	/** 数据库对象 */
	private SQLiteDatabase mdb;

	private ArrayList<ArticleGoodsBean> list;
	private Context context;

	public RecentViewDbHelper(Context Kcontext) {
		context = Kcontext;
		helper = new SQLiteHelper(context);
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
		// helper = new SQLiteHelper(context);
		return helper.getDatabase();
	}

	/**
	 * 创建最近浏览表
	 * 
	 * @param db
	 */
	static void creatTableRecent(SQLiteDatabase db) {
		StringBuffer creatTableSql = new StringBuffer();
		creatTableSql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
				.append("(").append(VOLUMN_IMGFILE).append(" TEXT, ")
				.append(VOLUMN_NAME).append(" TEXT, ").append(VOLUMN_SHOP)
				.append(" TEXT, ").append(VOLUMN_GOODSSN).append(" TEXT,")
				.append(VOLUMN_TIME).append(" TEXT").append(");");
		// System.out.println("sql1:"+creatTableSql.toString());
		db.execSQL(creatTableSql.toString());
	}

	/**
	 * 保存数据
	 * 
	 * @param db
	 * @param value
	 */
	public void saveSomeDatas(SQLiteDatabase db, List<ArticleGoodsBean> value) {
		list = getListSort(queryAllCity());
		if (list != null && list.size() > 0) {
			if (list.size() >= 10) {
				for (int i = 9; i < list.size(); i++) {
					String GoodsSN = list.get(i).GoodsSN;
					deleteGoods(GoodsSN);
				}
			}
		}
		mdb = openDb();
		ContentValues values = new ContentValues();
		for (int i = 0; i < value.size(); i++) {
			values.put(VOLUMN_IMGFILE, value.get(i).ImgFile);
			values.put(VOLUMN_NAME, value.get(i).GoodsName);
			values.put(VOLUMN_SHOP, value.get(i).Price);
			values.put(VOLUMN_GOODSSN, value.get(i).GoodsSN);
			values.put(VOLUMN_TIME, value.get(i).GoodTime);

			mdb.insert(TABLE_NAME, null, values);
		}
		if (mdb != null) {
			mdb.close();
		}
	}

	/**
	 * 删除一条
	 * 
	 * @return
	 */
	public boolean deleteGoods(String GoodsSN) {
		String whereClause = VOLUMN_GOODSSN + "=?";
		String[] whereArgs = { GoodsSN + "" };
		mdb = openDb();
		int rowNum = mdb.delete(TABLE_NAME, whereClause, whereArgs);

		if (mdb != null) {
			mdb.close();
		}
		return rowNum > 0 ? true : false;
	}

	/**
	 * 修改商品属性值
	 * 
	 * @return
	 */
	public boolean updateGoods(ArticleGoodsBean articleGoodsBean) {
		System.out.println("进入更新了数据。。。。");
		ContentValues values = new ContentValues();
		values.put(VOLUMN_IMGFILE, articleGoodsBean.ImgFile);
		values.put(VOLUMN_NAME, articleGoodsBean.GoodsName);
		values.put(VOLUMN_SHOP, articleGoodsBean.Price);
		values.put(VOLUMN_GOODSSN, articleGoodsBean.GoodsSN);
		values.put(VOLUMN_TIME, articleGoodsBean.GoodTime);

		String whereClause = VOLUMN_NAME + "=?";
		String[] whereArgs = { articleGoodsBean.GoodsName + "" };
		mdb = openDb();
		int rowNum = mdb.update(TABLE_NAME, values, whereClause, whereArgs);
		// if(rowNum<=0){
		// System.out.println("更新了数据失败。。。。");
		// }else{
		// System.out.println("更新了数据成功。。。。");
		// }
		if (mdb != null) {
			mdb.close();
		}
		return rowNum > 0 ? true : false;

	}

	/**
	 * 得到最近浏览的商品
	 * 
	 * @return
	 */
	public ArrayList<ArticleGoodsBean> queryAllCity() {
		ArrayList<ArticleGoodsBean> GoodsBeanList = null;
		Cursor cursor = null;
		try {
			mdb = openDb();
			cursor = mdb.query(TABLE_NAME, null, null, null, null, null, null);
			if (cursor.moveToFirst()) {
				GoodsBeanList = new ArrayList<ArticleGoodsBean>();
				do {
					ArticleGoodsBean articleGoodsBean = new ArticleGoodsBean();
					articleGoodsBean.ImgFile = cursor.getString(cursor
							.getColumnIndex(VOLUMN_IMGFILE));
					articleGoodsBean.GoodsName = cursor.getString(cursor
							.getColumnIndex(VOLUMN_NAME));
					articleGoodsBean.Price = cursor.getString(cursor
							.getColumnIndex(VOLUMN_SHOP));
					articleGoodsBean.GoodsSN = cursor.getString(cursor
							.getColumnIndex(VOLUMN_GOODSSN));
					articleGoodsBean.GoodTime = cursor.getString(cursor
							.getColumnIndex(VOLUMN_TIME));

					GoodsBeanList.add(articleGoodsBean);
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

		return GoodsBeanList;
	}

	/**
	 * 删除所有商品记录
	 * 
	 * @return
	 */
	public boolean deleteAllGoods() {
		mdb = openDb();
		int rowNum = mdb.delete(TABLE_NAME, null, null);
		if (mdb != null) {
			mdb.close();
		}
		return rowNum > 0 ? true : false;
	}

	/**
	 * 将得到的时间排序 得到的是从大到小的顺序
	 */
	private ArrayList<ArticleGoodsBean> getListSort(
			ArrayList<ArticleGoodsBean> list) {
		if (list == null || list.size() <= 0)
			return null;
		/** 冒泡排序 */
		int len = list.size();
		if (list.size() == 1)
			return list;
		System.out.println("len" + len);
		ArticleGoodsBean GoodsBean;
		for (int i = 0; i < len; ++i) {
			for (int j = len - 1; j > i; --j) {
				if (list.get(j).GoodTime != null
						&& list.get(i).GoodTime != null) {
					if (CompareTime(list.get(j).GoodTime,
							list.get(j - 1).GoodTime)) {
						GoodsBean = list.get(j);
						list.set(j, list.get(j - 1));
						list.set(j - 1, GoodsBean);
					}
				}
			}
		}
		return list;
	}

	/**
	 * yyyy-MM-dd-hh-mm-ss 时间的比较 要是返回true 就是前一个数大 返回false 就是后一个数大
	 */
	private boolean CompareTime(String time1, String time2) {
		if (time1 == null || time2 == null)
			return false;
		int start[] = { 0, 5, 8, 11, 14, 17 };
		int end[] = { 4, 7, 10, 13, 16, 19 };
		for (int i = 0; i < 6; i++) {

			String yyyy1 = time1.substring(start[i], end[i]);
			String yyyy2 = time2.substring(start[i], end[i]);
			int time11 = Integer.parseInt(yyyy1);
			int time22 = Integer.parseInt(yyyy2);
			if (time11 > time22) {
				return true;
			} else if (time11 < time22) {
				return false;
			} else {
				continue;
			}
		}

		return false;
	}
}
