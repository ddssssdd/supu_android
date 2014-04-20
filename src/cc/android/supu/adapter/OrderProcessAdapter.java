package cc.android.supu.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cc.android.supu.R;
import cc.android.supu.bean.ProcessBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderProcessAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<ProcessBean>  list;
	private LayoutInflater inflater;
	
	public OrderProcessAdapter(Context context, ArrayList<ProcessBean>  list){
		mContext = context;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CacheView cacheView = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.orderprocess_item, null);
			cacheView = new CacheView();
			cacheView.noteTv = (TextView) convertView.findViewById(R.id.orderprocess_item_note);
			cacheView.timeTv = (TextView) convertView.findViewById(R.id.orderprocess_item_time);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView) convertView.getTag();
		}
		cacheView.noteTv.setText(list.get(position).note);
		cacheView.timeTv.setText(fomatTime(list.get(position).time));
		return convertView;
	}
	
	class CacheView{
		TextView timeTv;
		TextView noteTv;
	}
	
	/**
	 * 将时间格式化为yyyy/MM/dd   HH:mm:ss形式
	 * @param scrTime 
	 * 				需要被格式化的时间
	 * @return String 
	 * 				格式化后的时间
	 */
	private String fomatTime(String scrTime){
		String retime = "";
		if(scrTime.matches("[0-9]{0,}")){
			String strTime = scrTime.trim();
			int length = strTime.length();
			if(length < 13){
				for(int j=0;j<13-length;j++){
					strTime = strTime+"0";
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
			Long time = new Long(strTime);
			retime = format.format(time);
		}else{
			retime = scrTime;
		}
		
		return retime; 
	}

}
