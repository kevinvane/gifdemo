package com.wifi;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.winext.gifdemo.R;

public class WifiRelayListAdapter extends BaseAdapter {

	private Context context;
	private List<ScanResult> wifiList;
	private Handler setWifiHandler = null;

	// List<ScanResult> wifiList = wmt.getScanResults();

	public WifiRelayListAdapter(Context context, List<ScanResult> wifiList,
								Handler setWifiHandler) {
		this.context = context;
		this.wifiList = wifiList;
		this.setWifiHandler = setWifiHandler;
	}

	@Override
	public int getCount() {
		return wifiList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convertView为null的时候初始化convertView。
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_wifi_child, null);
		}
		final ScanResult childData = wifiList.get(position);

		/**
		 * 加载资源
		 */
		ImageView wifi_state = (ImageView) convertView
				.findViewById(R.id.wifi_state);

		TextView wifi_info_text = (TextView) convertView
				.findViewById(R.id.wifi_info);
		TextView wifi_lock_text = (TextView) convertView
				.findViewById(R.id.wifi_lock);

		wifi_info_text.setText(childData.SSID); // + "(" + childData.BSSID + ")");

		String lock_str;
		boolean lock_type = true;

		// System.out.println("ssid=" + childData.SSID);
		// System.out.println("childData.capabilities=" +
		// childData.capabilities);
		// System.out.println("childData.level=" + childData.level);
		if (childData.capabilities.contains("WPA2-PSK")) {
			// WPA-PSK加密
			lock_str = "通过WPA2-PSK进行保护";
		} else if (childData.capabilities.contains("WPA-PSK")) {
			// WPA-PSK加密
			lock_str = "通过WPA-PSK进行保护";
		} else if (childData.capabilities.contains("WPA-EAP")) {
			// WPA-EAP加密
			lock_str = "通过WPA-EAP进行保护";
		} else if (childData.capabilities.contains("WEP")) {
			// WEP加密
			lock_str = "通过WEP进行保护";
		} else {
			// 无密码
			lock_str = "开放网络";
			lock_type = false;
		}

		LinkWifi linkWifi = new LinkWifi(context);
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Service.WIFI_SERVICE);

		if (linkWifi.IsExsits(childData.SSID) != null
				&& linkWifi.IsExsits(childData.SSID).networkId == wifiManager
				.getConnectionInfo().getNetworkId()) {
			lock_str+="(已连接)";
		}

		wifi_lock_text.setText(lock_str);

		// 点击的话，中继该无线
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (setWifiHandler != null) {
					Message msg = new Message();
					msg.what = 0;
					msg.obj = childData;
					setWifiHandler.sendMessage(msg);
				}
			}
		});

//		convertView.setOnTouchListener(new View.OnTouchListener() {
//
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
//					arg0.setBackgroundColor(0xaa333333);
//				} else {
//					arg0.setBackgroundColor(0x00ffffff);
//				}
//
//				return false; // 表示继续传递该消息，如果返回true则表示该消息不再被传递
//			}
//		});

//		if (childData.level < -90) {
//			if (lock_type)
//				wifi_state.setBackgroundResource(R.drawable.wifilevel0_lock);
//			else
//				wifi_state.setBackgroundResource(R.drawable.wifilevel0);
//		} else if (childData.level < -85) {
//			if (lock_type)
//				wifi_state.setBackgroundResource(R.drawable.wifilevel1_lock);
//			else
//				wifi_state.setBackgroundResource(R.drawable.wifilevel1);
//		} else if (childData.level < -70) {
//			if (lock_type)
//				wifi_state.setBackgroundResource(R.drawable.wifilevel2_lock);
//			else
//				wifi_state.setBackgroundResource(R.drawable.wifilevel2);
//		} else if (childData.level < -60) {
//			if (lock_type)
//				wifi_state.setBackgroundResource(R.drawable.wifilevel3_lock);
//			else
//				wifi_state.setBackgroundResource(R.drawable.wifilevel3);
//		} else if (childData.level < -50) {
//			if (lock_type)
//				wifi_state.setBackgroundResource(R.drawable.wifilevel4_lock);
//			else
//				wifi_state.setBackgroundResource(R.drawable.wifilevel4);
//		}

		convertView.setTag("wifi_" + childData.BSSID);

		return convertView;
	}

}
