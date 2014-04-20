
package cc.android.supu.wheelview;

import java.util.List;

import cc.android.supu.bean.CityBean;

/**
 * The simple Array wheel adapter
 * 
 */
public class ArrayListWheelAdapter implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;
	//list
	List<CityBean> list ;	
	// length
	private int length;

	/**
	 * Constructor
	 * @param items the items
	 * @param length the max items length
	 */
	public ArrayListWheelAdapter(List<CityBean> list, int length) {
		this.list = list;
		this.length = length;
	}
	
	/**
	 * Contructor
	 * @param items the items
	 */
	public ArrayListWheelAdapter(List<CityBean> list) {
		this(list, DEFAULT_LENGTH);
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < list.size()) {
			return list.get(index).getAreaName().toString();
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return list.size();
	}

	@Override
	public int getMaximumLength() {
		return length;
	}

}
