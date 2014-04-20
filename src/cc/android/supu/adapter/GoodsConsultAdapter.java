package cc.android.supu.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.android.supu.R;
import cc.android.supu.bean.ConsultBean;
import cc.android.supu.bean.GoodsConsultBean;
import cc.android.supu.bean.ReplyBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/** 
 * 商品咨询listview适配器
 * @author sheng
 *
 */
public class GoodsConsultAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater inflater;
	/** 咨询 列表 */
	public List<GoodsConsultBean>  consultsList;
	
	public GoodsConsultAdapter(Context context, List<GoodsConsultBean>  consultsList){
		mContext = context;
		this.consultsList = consultsList;
		inflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return consultsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return consultsList.get(position);
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
			convertView = inflater.inflate(R.layout.goodsconsult_item, null);
			cacheView = new CacheView(convertView);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView) convertView.getTag();
		}
		
		
		cacheView.getAccountTv()
			.setText(consultsList.get(position).memberList.get(0).account);
		cacheView.getConsultTimeTv()
			.setText(fomatTime(consultsList.get(position).consultBean.consultTime));
		cacheView.getConsultContentTv()
			.setText(consultsList.get(position).consultBean.consultContent);
		cacheView.getReplyTimeTv()
			.setText(fomatTime(consultsList.get(position).replyBean.replyTime));
		cacheView.getReplyContentTv()
			.setText(consultsList.get(position).replyBean.replyContent);
		
		return convertView;
	}
	
	class CacheView{
		
		private View baseView;
		
		private TextView accountTv;
		
		private TextView consultTimeTv;
		
		private TextView consultContentTv;
		
		private TextView replyTimeTv;
		
		private TextView replyContentTv;
		
		public CacheView(View baseView){
			this.baseView = baseView;
		}
		
		public TextView getAccountTv(){
			if(accountTv == null){
				accountTv = (TextView) baseView.findViewById(R.id.goodsconsult_item_consultId);
			}
			return accountTv;
		}
		
		public TextView getConsultTimeTv(){
			if(consultTimeTv == null){
				consultTimeTv=(TextView) baseView.findViewById(R.id.goodsconsult_item_constultTime);
			}
			return consultTimeTv;
		}
		
		public TextView getConsultContentTv(){
			if(consultContentTv == null){
				consultContentTv=(TextView) baseView.findViewById(R.id.goodsconsult_item_consultContent);
			}
			return consultContentTv;
		}
		
		public TextView getReplyTimeTv(){
			if(replyTimeTv == null){
				replyTimeTv=(TextView) baseView.findViewById(R.id.goodsconsult_item_replyTime);
			}
			return replyTimeTv;
		}
		
		public TextView getReplyContentTv(){
			if(replyContentTv == null){
				replyContentTv=(TextView) baseView.findViewById(R.id.goodsconsult_item_replyContent);
			}
			return replyContentTv;
		}
	}
	/**
	 * 将1351871999000格式的时间格式化化为yyyy-MM-dd格式
	 * @param scrTime
	 * @return
	 */
	private String fomatTime(String scrTime){
		String strTime = "";
		String regEx = "[0-9]{10,}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(scrTime);
		if(m.find()){
			strTime = m.group();
		}
		int length = strTime.length();
		if(length < 13){
			for(int j=0;j<13-length;j++){
				strTime = strTime+"0";
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Long time = new Long(strTime);
		return  format.format(time);
	}
}
