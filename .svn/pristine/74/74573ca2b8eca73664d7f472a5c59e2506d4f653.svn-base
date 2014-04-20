package cc.android.supu.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.android.supu.R;
import cc.android.supu.bean.TicketBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 优惠券列表适配器
 * 
 * @author sheng
 * 
 */
public class TicketListAdapter extends BaseAdapter {

	private Context mContext;
	/** 优惠券列表 */
	private ArrayList<TicketBean> ticketsList;
	/** 优惠券bean */
	private TicketBean ticketBean;

	private LayoutInflater inflater;

	public TicketListAdapter(Context context, ArrayList<TicketBean> ticketsList) {
		mContext = context;
		this.ticketsList = ticketsList;
		inflater = LayoutInflater.from(mContext);
	}

	public void setData(ArrayList<TicketBean> ticketsList) {
		this.ticketsList = ticketsList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ticketsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ticketsList.get(position);
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
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ticketlist_item, null);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		} else {
			cacheView = (CacheView) convertView.getTag();
		}
		ticketBean = ticketsList.get(position);
		cacheView.getTicketNameTv().setText(ticketBean.ticketName);
		cacheView.getTicketNoTv().setText(ticketBean.ticketNo);
		if (ticketBean.isUsed) {
			cacheView.getStatusTv().setText("已使用");
		} else if (isTimeout(ticketBean.endTime)) {
			cacheView.getStatusTv().setText("已过期");
		} else {
			cacheView.getStatusTv().setText("未过期");
		}
		//cacheView.getStatusTv().setText(isTimeout(ticketBean.endTime) || ticketBean.isUsed ? "已使用" : "未过期");

		return convertView;
	}

	class CacheView {

		private View baseView;
		private TextView ticketNameTv;
		private TextView ticketNoTv;
		private TextView statusTv;

		public CacheView(View baseView) {
			this.baseView = baseView;
		}

		public TextView getTicketNameTv() {
			if (ticketNameTv == null) {
				ticketNameTv = (TextView) baseView.findViewById(R.id.ticketlist_item_name);
			}
			return ticketNameTv;
		}

		public TextView getTicketNoTv() {
			if (ticketNoTv == null) {
				ticketNoTv = (TextView) baseView.findViewById(R.id.ticketlist_item_no);
			}
			return ticketNoTv;
		}

		public TextView getStatusTv() {
			if (statusTv == null) {
				statusTv = (TextView) baseView.findViewById(R.id.ticketlist_item_status);
			}
			return statusTv;
		}
	}

	/**
	 * 根据优惠券的结束时间返回优惠券是否过期
	 * 
	 * @param endTimeStr
	 *            优惠券结束时间
	 * @return true 已过期 false 未过期
	 */
	private Boolean isTimeout(String endTimeStr) {
		boolean isTimeout = false;
		String timeStr = "";
		long value = 0;
		long nowTime = Calendar.getInstance().getTime().getTime();
		String regEx = "[0-9]{10,}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(endTimeStr);
		if (m.find()) {
			timeStr = m.group();
			if (timeStr != null && timeStr.length() < 13) {
				int length = timeStr.length();
				for (int i = 0; i < 13 - length; i++) {
					timeStr = timeStr + "0";
				}
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Long time = new Long(timeStr);
		value = time - nowTime;
		if (value < 0) {
			isTimeout = true;
		}
		return isTimeout;
	}

}
