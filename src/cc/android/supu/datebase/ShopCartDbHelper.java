//package cc.android.supu.datebase;
//
//import java.util.ArrayList;
//
//import cc.android.supu.bean.GoodsBean;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
///**
// * 购物车表操作类
// * @author sheng
// *
// */
//public class ShopCartDbHelper {
//	
//	/** 购物车表名*/
//	public static final String TABLE_NAME = "tb_shopcart";
//	/** 每条记录唯一标识*/
//	public static final String VOLUMN_ID = "_id";
//	/** 商品编号*/
//	public static final String VOLUMN_GOODS_SN = "goodsSN";
//	/** 商品名称*/
//	public static final String VOLUMN_GOODS_NAME = "goodsName";
//	/** 商品图片*/
//	public static final String VOLUMN_IMG_FILE = "imgFile";
//	/** 是否赠品*/
//	public static final String VOLUMN_IS_GIFT = "isGift";
//	/** 商品数量*/
//	public static final String VOLUMN_COUNT = "count";
//	/** 商品价格*/
//	public static final String VOLUMN_SHOP_PRICE = "shopPrice";
//	/** 是否缺货*/
//	public static final String VOLUMN_IS_NOSTOCK = "isNoStock";
//	/** 商品优惠金额*/
//	public static final String VOLUMN_DISCOUNT_AMOUNT = "discountAmount";
//	/** 修改后的数量*/
//	public static final String VOLUMN_MODIFIED_COUNT = "modifiedCount";
//	/** 数据库操作对象*/
//	private SQLiteHelper helper;
//	/** 数据库对象*/
//	private SQLiteDatabase mdb;
//	
//	private Context mContext;
//	
//	
//	public ShopCartDbHelper(Context context){
//		mContext = context;
//		helper = new SQLiteHelper(context);
//	}
//	
//	/**
//	 * 打开数据库
//	 * @return
//	 */
//	private SQLiteDatabase openDb(){
//		if(helper != null ){
//			helper.close();
//		}
//		return helper.getDatabase();
//	}
//	
//	/**
//	 * 创建购物车表
//	 * @param db
//	 */
//	static void creatTableShopcart(SQLiteDatabase db){
//		StringBuffer creatTableSql = new StringBuffer();
//		
//
//		creatTableSql.append("CREATE TABLE IF NOT EXISTS ")
//					.append(TABLE_NAME)
//					.append("(")
//					.append(VOLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT ,")
//					.append(VOLUMN_GOODS_SN).append(" TEXT, ")
//					.append(VOLUMN_GOODS_NAME).append(" TEXT, ")
//					.append(VOLUMN_IMG_FILE).append(" TEXT, ")
//					.append(VOLUMN_IS_GIFT).append(" INTEGER, ")
//					.append(VOLUMN_COUNT).append(" INTEGER, ")
//					.append(VOLUMN_SHOP_PRICE).append(" TEXT, ")
//					.append(VOLUMN_IS_NOSTOCK).append(" INTEGER, ")
//					.append(VOLUMN_DISCOUNT_AMOUNT).append(" TEXT, ")
//					.append(VOLUMN_MODIFIED_COUNT).append(" INTEGER")
//					.append(");");
//		//System.out.println("creatTableSql="+creatTableSql);
//		db.execSQL(creatTableSql.toString());
//	}
//	
//	/**
//	 * 查询指定商品在表中的id
//	 * @param goods
//	 * 				指定的商品
//	 * @return id
//	 * 				商品在表中的id，id=-1,表示不存在此商品
//	 * 			
//	 */
//	public int queryId(GoodsBean goods){
//		Cursor cursor = null;
//		int id = -1;
//		StringBuffer querySql = new StringBuffer(100);
//		querySql.append("select ")
//				.append(VOLUMN_ID).append(" ")
//				.append("from ")
//				.append(TABLE_NAME)
//				.append(" where ")
//				.append(VOLUMN_GOODS_SN).append("=? and ")
//				.append(VOLUMN_GOODS_NAME).append("=? and ")
//				.append(VOLUMN_IMG_FILE).append("=? and ")
//				.append(VOLUMN_IS_GIFT).append("=? and ")
//				.append(VOLUMN_SHOP_PRICE).append("=? and ")
//				.append(VOLUMN_IS_NOSTOCK).append("=?;");
//		//System.out.println("querySql="+querySql);
//		
//		try {
//			String[] selectionArgs = {    goods.goodsSN, 
//										  goods.goodsName, 
//										  goods.imgFile, 
//										  booleanToInt(goods.isGift)+"", 
//										  goods.shopPrice, 
//										  booleanToInt(goods.isNoStock)+""
//									  };
//			mdb = openDb();
//			cursor = mdb.rawQuery(querySql.toString(), selectionArgs);
//			if(cursor.moveToFirst()){
//				id = cursor.getInt(cursor.getColumnIndex(VOLUMN_ID));	
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			if(cursor != null){
//				cursor.close();
//			}
//			
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		return id;
//	}
//	
//	/**
//	 * 查询与指定的商品编号对应的数据表中的id
//	 * @param goodsSN
//	 * @return id
//	 * 			id等于-1时，表示数据表中没有对应的商品
//	 */
//	public int queryIdByGoodsSN(String goodsSN){
//		Cursor   cursor    = null;
//		int id = -1;
//		String[] columns   = {VOLUMN_ID+""};
//		String   selection = VOLUMN_GOODS_SN + "=?";
//		String[] selectionArgs = {goodsSN};
//		try {
//			mdb = openDb();
//			cursor = mdb.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//			if(cursor.moveToFirst()){
//				id = cursor.getInt(cursor.getColumnIndex(VOLUMN_ID));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			if(cursor != null){
//				cursor.close();
//			}
//			
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		return id;
//	}
//	
//	/**
//	 * 判断表中是否存在指定商品
//	 * @param goods
//	 * 				要查询的商品
//	 * @return true
//	 * 				存在
//	 * 		   false
//	 * 				不存在
//	 */
//	public boolean isExist(GoodsBean goods){
//		return queryId(goods) > -1 ? true : false;
//	}
//	
//	/**
//	 * 根据商品编号判定商品是否存在
//	 * @return
//	 */
//	public boolean isExistByGoodsSN(String goodsSN){
//		return queryIdByGoodsSN(goodsSN) > -1 ? true : false;
//	}
//	
//	/**
//	 * 插入指定的商品
//	 * @return
//	 */
//	public boolean insertGoods(GoodsBean goods){
//		boolean isSucced = false;
//		if(isExistByGoodsSN(goods.goodsSN)){//如果此商品存在
//			//System.out.println("goods.count---1="+goods.count);
//			if(goods.count == 0){
//				goods.count = 1;
//			}
//			goods.count += queryCount(goods);//将商品数量累加
//			//System.out.println("goods.count---2="+goods.count);
//			isSucced = updateGoodsCount(goods);//更新此商品的数据信息
//		}else{//不存在则插入
//			StringBuffer insertSql = new StringBuffer(100);
//			insertSql.append("insert into ")
//					.append(TABLE_NAME)
//					.append("(")
//					.append(VOLUMN_GOODS_SN).append(", ")
//					.append(VOLUMN_GOODS_NAME).append(", ")
//					.append(VOLUMN_IMG_FILE).append(", ")
//					.append(VOLUMN_IS_GIFT).append(", ")
//					.append(VOLUMN_COUNT).append(", ")
//					.append(VOLUMN_SHOP_PRICE).append(", ")
//					.append(VOLUMN_IS_NOSTOCK).append(", ")
//					.append(VOLUMN_DISCOUNT_AMOUNT).append(", ")
//					.append(VOLUMN_MODIFIED_COUNT)
//					.append(")")
//					.append("values(")
//					.append("'").append(goods.goodsSN).append("', ")
//					.append("'").append(goods.goodsName).append("', ")
//					.append("'").append(goods.imgFile).append("', ")
//					.append("'").append(booleanToInt(goods.isGift)).append("', ")
//					.append("'").append(defaultCount(goods.count)).append("', ")
//					.append("'").append(goods.shopPrice).append("', ")
//					.append("'").append(booleanToInt(goods.isNoStock)).append("', ")
//					.append("'").append(calculateDiscountAmount(goods)).append("', ")
//					.append("'").append(goods.modifiedCount)
//					.append("');");
//			System.out.println("insertSql="+insertSql);
//			try {
//				mdb = openDb();//打开数据库
//				mdb.execSQL(insertSql.toString());
//				isSucced = true;
//			} catch (Exception e) {
//				// TODO: handle exception
//			} finally {
//				if(mdb != null){
//					mdb.close();
//				}
//			}
//		}
//		return isSucced;
//	}
//	
//	/**
//	 * 以ArrayList<GoodsBean>形式返回购物车表的所有记录
//	 * @return
//	 */
//	public ArrayList<GoodsBean> queryAllGoods(){
//		ArrayList<GoodsBean> goodsList = null;
//		Cursor cursor = null;
//		try {
//			mdb = openDb();
//			cursor = mdb.query(TABLE_NAME, null, null, null, null, null, null);
//			if(cursor.moveToFirst()){
//				goodsList = new ArrayList<GoodsBean>();
//				do {
//					GoodsBean goodsBean = new GoodsBean();
//					goodsBean.id = cursor.getInt(cursor.getColumnIndex(VOLUMN_ID));
//					goodsBean.goodsSN = 
//							cursor.getString(cursor.getColumnIndex(VOLUMN_GOODS_SN));
//					goodsBean.goodsName = 
//							cursor.getString(cursor.getColumnIndex(VOLUMN_GOODS_NAME));
//					goodsBean.imgFile = 
//							cursor.getString(cursor.getColumnIndex(VOLUMN_IMG_FILE));
//					goodsBean.isGift = 
//							intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_GIFT)));
//					goodsBean.count = 
//							cursor.getInt(cursor.getColumnIndex(VOLUMN_COUNT));
//					goodsBean.shopPrice = 
//							cursor.getString(cursor.getColumnIndex(VOLUMN_SHOP_PRICE));
//					goodsBean.isNoStock = 
//							intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_NOSTOCK)));
//					goodsBean.discountAmount = 
//							cursor.getString(cursor.getColumnIndex(VOLUMN_DISCOUNT_AMOUNT));
//					goodsBean.modifiedCount = 
//							cursor.getInt(cursor.getColumnIndex(VOLUMN_MODIFIED_COUNT));
//					
//					goodsList.add(goodsBean);
//				} while (cursor.moveToNext());
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally{
//			if(cursor != null){
//				cursor.close();
//			}
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		
//		return goodsList;
//	}
//	
//	
//	/**
//	 * 删除一条商品记录
//	 * @param id
//	 * 			商品在数据库中的id
//	 * @return
//	 */
//	public boolean deleteGoods(int id){
//		String whereClause = VOLUMN_ID + "=?";
//		String[] whereArgs = {id+""};
//		mdb = openDb();
//		int rowNum = mdb.delete(TABLE_NAME, whereClause, whereArgs);
//		
//		if(mdb != null){
//			mdb.close();
//		}
//		return rowNum > 0 ? true : false;
//	}
//	
//	/**
//	 * 删除所有商品记录
//	 * @return
//	 */
//	public boolean deleteAllGoods(){
//		mdb = openDb();
//		int rowNum = mdb.delete(TABLE_NAME, null, null);
//		return rowNum > 0 ? true : false;
//	}
//	/**
//	 * 根据表中的id修改商品数量
//	 * @param goods
//	 * @return
//	 */
//	public boolean updateGoodsCount(GoodsBean goods){
//		boolean isSuccess = false;
//		StringBuffer updateCountSql = new StringBuffer(100);
//		updateCountSql.append("update ")
//		.append(TABLE_NAME)
//		.append(" set ")
//		.append(VOLUMN_COUNT).append(" = '").append(goods.count).append("' ")
//		.append("where ")
//		.append(VOLUMN_GOODS_SN).append(" = '").append(goods.goodsSN).append("';");
//		System.out.println("updateCountSql="+updateCountSql);
//		try {
//			mdb = openDb();
//			mdb.execSQL(updateCountSql.toString());
//			isSuccess = true;
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		System.out.println("update count isSuccess="+isSuccess);
//		return isSuccess;
//	}
//	
//	/**
//	 * 修改商品属性值
//	 * @return
//	 */
//	public boolean updateGoods(GoodsBean goods){
//		ContentValues values = new ContentValues();
//		values.put(VOLUMN_GOODS_SN, goods.goodsSN);
//		values.put(VOLUMN_GOODS_NAME, goods.goodsName);
//		values.put(VOLUMN_IMG_FILE, goods.imgFile);
//		values.put(VOLUMN_IS_GIFT, goods.isGift);
//		values.put(VOLUMN_COUNT, goods.count);
//		values.put(VOLUMN_SHOP_PRICE, goods.shopPrice);
//		values.put(VOLUMN_IS_NOSTOCK, goods.isNoStock);
//		values.put(VOLUMN_DISCOUNT_AMOUNT, goods.discountAmount);
//		values.put(VOLUMN_MODIFIED_COUNT, goods.modifiedCount);
//		String whereClause = VOLUMN_ID + "=?";
//		String[] whereArgs = {goods.id+""};
//		mdb = openDb();
//		int rowNum = mdb.update(TABLE_NAME, values, whereClause, whereArgs);
//		if(mdb != null){
//			mdb.close();
//		}
//		return rowNum > 0 ? true : false;
//		
//	}
//	
//	/**
//	 * 查询指定商品的数量
//	 * @return
//	 */
//	public int queryCount(GoodsBean goods){
//		Cursor cursor = null;
//		int count = 0;
//		String[] columns = {VOLUMN_COUNT};
//		String selection = VOLUMN_ID + "+?";
//		String[] selectionArgs = {goods.id+""};
//		try {
//			mdb = openDb();
//			cursor = mdb.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//			if(cursor.moveToFirst()){
//				count = cursor.getInt(cursor.getColumnIndex(VOLUMN_COUNT));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			if(cursor != null){
//				cursor.close();
//			}
//			
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		
//		return count;
//	}
//	
//	/**
//	 * 根据给定的id查询一个商品
//	 * @param id
//	 * 			数据表中的id
//	 * @return
//	 */
//	public GoodsBean queryGoods(int id){
//		Cursor cursor = null;
//		GoodsBean goods = null;
//		String selection = VOLUMN_ID + "=?";
//		String[] selectionArgs = {id+""};
//		try {
//			mdb = openDb();
//			cursor = mdb.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
//			if(cursor.moveToFirst()){
//				goods = new GoodsBean();
//				goods.goodsSN = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_GOODS_SN));
//				goods.goodsName = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_GOODS_NAME));
//				goods.imgFile = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_IMG_FILE));
//				goods.isGift = 
//						intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_GIFT)));
//				goods.count = cursor
//						.getInt(cursor.getColumnIndex(VOLUMN_COUNT));
//				goods.isNoStock = 
//						intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_NOSTOCK)));
//				goods.discountAmount = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_DISCOUNT_AMOUNT));
//				goods.modifiedCount = cursor
//						.getInt(cursor.getColumnIndex(VOLUMN_MODIFIED_COUNT));
//				goods.shopPrice = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_SHOP_PRICE));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally{
//			if(cursor != null){
//				cursor.close();
//			}
//			
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		return goods;
//	}
//	
//	/**
//	 * 根据给定的商品编号查询一个商品
//	 * @param goodsSN
//	 * 				商品编号
//	 * @return
//	 */
//	public GoodsBean queryGoods(String goodsSN){
//		Cursor cursor = null;
//		GoodsBean goods = null;
//		String selection = VOLUMN_GOODS_SN + "=?";
//		String[] selectionArgs = {goodsSN};
//		try {
//			mdb = openDb();
//			cursor = mdb.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
//			if(cursor.moveToFirst()){
//				goods = new GoodsBean();
//				goods.id = cursor.getInt(cursor.getColumnIndex(VOLUMN_ID));
//				goods.goodsSN = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_GOODS_SN));
//				goods.goodsName = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_GOODS_NAME));
//				goods.imgFile = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_IMG_FILE));
//				goods.isGift = 
//						intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_GIFT)));
//				goods.count = cursor
//						.getInt(cursor.getColumnIndex(VOLUMN_COUNT));
//				goods.isNoStock = 
//						intToBoolean(cursor.getInt(cursor.getColumnIndex(VOLUMN_IS_NOSTOCK)));
//				goods.discountAmount = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_DISCOUNT_AMOUNT));
//				goods.modifiedCount = cursor
//						.getInt(cursor.getColumnIndex(VOLUMN_MODIFIED_COUNT));
//				goods.shopPrice = cursor
//						.getString(cursor.getColumnIndex(VOLUMN_SHOP_PRICE));
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally{
//			if(cursor != null){
//				cursor.close();
//			}
//			
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		return goods;
//	}
//	
//	/**
//	 * 修改字段VOLUMN_MODIFIED_COUNT的值
//	 * @return
//	 */
//	public boolean updateModifiedCount(GoodsBean goods){
//		int rowNum = -1;
//		ContentValues values = new ContentValues();
//		values.put(VOLUMN_MODIFIED_COUNT, goods.modifiedCount);
//		String whereClause = VOLUMN_ID + "?";
//		String[] whereArgs = {goods.id+""};
//		try {
//			mdb = openDb();
//			rowNum = mdb.update(TABLE_NAME, values, whereClause, whereArgs);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}finally{
//			if(mdb != null){
//				mdb.close();
//			}
//		}
//		
//		return rowNum > -1 ? true : false;
//	}
//	
//	/**
//	 * boolean型转int型
//	 * @param boo
//	 * @return
//	 */
//	private int booleanToInt(boolean boo){
//		return boo ? 1 : 0;
//	}
//	
//	/**
//	 * int型转boolean型 
//	 * @param i
//	 * @return
//	 */
//	private boolean intToBoolean(int i){
//		return i==1 ? true : false;
//	}
//	
//	/**
//	 * 将商品的数量默认化
//	 * @param count
//	 * @return
//	 */
//	private int defaultCount(int count){
//		return (count == 0) ? 1 : count;
//	}
//	
//	/**
//	 * 计算商品的优惠金额
//	 * @param goods
//	 * @return
//	 */
//	private String calculateDiscountAmount(GoodsBean goods){
//		if(goods.discountAmount == null || goods.discountAmount.equals("")){
//			return (Float.parseFloat(goods.marketPrice) - 
//					Float.parseFloat(goods.shopPrice))+"";
//		}else{
//			return goods.discountAmount;
//		}
//	}
//	
////	private String getImgFile(GoodsBean goods){
////		if(goods.imgFile == null || goods.imgFile.equals("")){
////			return goods.goodsSN
////		}
////	}
//}
