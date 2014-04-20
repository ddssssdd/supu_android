package cc.android.supu.adapter;
/**
 * 消息的Adapter
 * zsx
 */
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.android.supu.R;
import cc.android.supu.bean.UserMessageBean;

public class MessageListAdapter extends BaseAdapter {
	/**消息列表**/
	private List<UserMessageBean> list;
	/**布局文件**/
	private LayoutInflater mInflater;
	
	private Context cont;

	public MessageListAdapter(Context context,List<UserMessageBean> userMessageBean) {
		cont = context;
		list = userMessageBean;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.message_list_item, null);
			holder = new ViewHolder();
			holder.messageInfo = (TextView) convertView.findViewById(R.id.messageShow);
			holder.messageTime = (TextView) convertView.findViewById(R.id.messageTime);
			holder.IsShowLayout = (RelativeLayout)convertView.findViewById(R.id.IsShowLayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String time =getTime(list.get(position).releaseTime);
		
		if (position > 0) {
			// 这里要换数据
			String time1 =getTime(list.get(position-1).releaseTime);
			if (time.equals(time1)) {
				holder.IsShowLayout.setVisibility(View.GONE);
			}else{
				holder.IsShowLayout.setVisibility(View.VISIBLE);
			}
		}
		if(position == 0){
			holder.IsShowLayout.setVisibility(View.VISIBLE);
		}
		holder.messageInfo.setText(Html.fromHtml(list.get(position).messageContent));
		holder.messageTime.setText(time);
		
		return convertView;
	}
	/**内部类**/
	class ViewHolder {
		/** 站内消息， 消息时间 */
		private TextView messageInfo;
		private TextView messageTime;
		private RelativeLayout IsShowLayout;
		
	}
	/**
	 * 得到时间
	 */
	private String getTime(String scrTime){
		String strTime = scrTime.trim();
		int length = strTime.length();
		if(length < 13){
			for(int j=0;j<13-length;j++){
				strTime = strTime+"0";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 ");
		Long time = new Long(strTime);
//	    System.out.println("Format To String(Date):"+time);
		return  format.format(time);
	}
//	private String getTime(String messageTime){
//		
//		   long l = Long.parseLong(messageTime+"000"); 
//		   SimpleDateFormat format =   new SimpleDateFormat( "yyyy年MM月dd日 " );
//	       Long time=new Long(l);
//	       String d = null;
//		try {
//		    d = format.format(time);
//		    
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(d);
//		return d; 
//
//	}
}
