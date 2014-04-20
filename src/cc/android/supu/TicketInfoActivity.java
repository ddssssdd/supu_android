package cc.android.supu;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.android.supu.bean.TicketBean;
import cc.android.supu.handler.TicketHandler;
import cc.android.supu.tools.ConstantUrl;
import cc.android.supu.tools.PageRequest;
import cc.android.supu.tools.PublicParams;
import cc.android.supu.tools.Tools;
import cc.android.supu.tools.UserInfoTools;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 优惠券信息页面
 * @author sheng
 *
 */
public class TicketInfoActivity extends BaseActivity {
	
	private TextView ticketNameTv;
	private TextView ticketNoTv;
	private TextView ticketDescribeTv;
	private TextView beginTimeTv;
	private TextView endTimeTv;
	/** 优惠券编码*/
	private String ticketNo;
	/** 优惠券信息解析器*/
	private TicketHandler ticketHandler;
	private TicketBean ticketBean;
	
	@Override
	protected String setTitle() {
		// TODO Auto-generated method stub
		return "我的优惠券";
	}
	
	@Override
	protected String setBackBtn() {
		// TODO Auto-generated method stub
		return "返回";
	}
	@Override
	protected void initPage() {
		if(getIntent() != null){
			ticketNo = getIntent().getStringExtra("ticketNo");
		}
		ticketNameTv = (TextView) findViewById(R.id.ticketinfo_name);
		ticketNoTv = (TextView) findViewById(R.id.ticketinfo_ticketNo);
		ticketDescribeTv = (TextView) findViewById(R.id.ticketinfo_describe);
		beginTimeTv = (TextView) findViewById(R.id.ticketinfo_beginTime);
		endTimeTv = (TextView) findViewById(R.id.ticketinfo_endTime);
		
		requestServer(requestTicketInfo);	
	}

	@Override
	int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.ticketinfo;
	}
			
	@Override
	int setBottomIndex() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	protected void dealwithMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case STATE_SUCCESS:
			ticketBean = ticketHandler.ticketBean;
			showContent(ticketBean);
			break;
		case STATE_FAILURE:
			if(ticketHandler.result_code == 1000){
				
			}else if(ticketHandler.result_code == 1){
				Toast.makeText(this, "优惠券编号错误！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
			}
			break;
		case STATE_ERROR:
			Toast.makeText(this, "网络异常！", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	/**
	 * 请求优惠券信息
	 */
	PageRequest requestTicketInfo = new PageRequest() {
		
		@Override
		public void requestServer() {
			// TODO Auto-generated method stub
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("TicketNo", ticketNo);
			
			ticketHandler = new TicketHandler();
//			PublicParams.setMEMBERID(UserInfoTools.getUserBean(TicketInfoActivity.this).memberId);
			Tools.requestToParse(TicketInfoActivity.this, ConstantUrl.GETTICKET, "GetTicket", params, ticketHandler, false);
			if(Tools.responseValue == 1){
				if(ticketHandler.result_code == 0){
					handler.sendEmptyMessage(STATE_SUCCESS);
				}else{
					handler.sendEmptyMessage(STATE_FAILURE);
				}
			}else{
				handler.sendEmptyMessage(STATE_ERROR);
			}
		}
	};
	/**
	 * 显示内容
	 * @param ticketBean
	 */
	private void showContent(TicketBean ticketBean){
		if(ticketBean != null){
			ticketNameTv.setText(ticketBean.ticketName);
			ticketNoTv.setText(ticketBean.ticketNo);
			ticketDescribeTv.setText(ticketBean.ticketDescribe);
			beginTimeTv.setText(fomatTime(ticketBean.beginTime));
			endTimeTv.setText(fomatTime(ticketBean.endTime));
		}else{
			Toast.makeText(this, "服务器异常！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 将1351871999000格式的时间格式化化为 "yyyy年MM月dd日" 格式
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		Long time = new Long(strTime);
		return  format.format(time);
	}
}
