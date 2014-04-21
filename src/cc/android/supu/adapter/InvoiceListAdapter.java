package cc.android.supu.adapter;

import java.util.List;

import cc.android.supu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.android.supu.bean.InvoiceBean;
public class InvoiceListAdapter extends BaseAdapter {
	private List<InvoiceBean> _list;
	private InvoiceBean _invoiceBean;
	private LayoutInflater _inflater;
	private Context _context;
	public int selected_bean_index=0;
	public InvoiceListAdapter(Context context,List<InvoiceBean> list){
		_context = context;
		_list = list;
		_inflater = LayoutInflater.from(_context);
		for(int i=0;i<_list.size();i++){
			if (_list.get(i).ordering.trim().equals("0")){
				selected_bean_index=i;
				break;
			}
		}
	}
	public void setInvoiceID(String id){
		if (id==null || id.isEmpty()){
			return;
		}
		for(int i=0;i<_list.size();i++){
			if (_list.get(i).invoiceID.trim().equals(id.trim())){
				selected_bean_index=i;
				break;
			}
		}
	}
	@Override
	public int getCount() {
		return _list.size();
	}

	@Override
	public Object getItem(int position) {
		return _list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CacheView cacheView = null;
		if (convertView == null){
			convertView = _inflater.inflate(R.layout.selectinvoice_item, null);
			cacheView = new CacheView();
			cacheView.txtName =(TextView) convertView.findViewById(R.id.name);
			cacheView.checkmark = (ImageView)convertView.findViewById(R.id.checkmark);
			convertView.setTag(cacheView);
		}else{
			cacheView = (CacheView)convertView.getTag();
		}
		_invoiceBean = _list.get(position);
		cacheView.txtName.setText(_invoiceBean.invoiceName);
		
		if (position==selected_bean_index){
			cacheView.checkmark.setVisibility(View.VISIBLE);
		}else{
			cacheView.checkmark.setVisibility(View.GONE);
		}
		return convertView;
	}
	class CacheView{
		TextView txtName;
		ImageView checkmark;
	}
}
