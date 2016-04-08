package com.wifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.winext.gifdemo.R;

public class WiFiListActivity extends AppCompatActivity {

    private WifiManager wifiManager = null;
    private Context context = null;
    public SetWifiHandler setWifiHandler;
    private WifiRelayListAdapter wifiListAdapter;
    private ListView wifi_list;
    private ImageButton refresh_list_btn;
    private ImageButton wifi_on_off_btn;

    private SwitchCompat switch_wifi;
    private LinkWifi linkWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wifi);
        context = this;

        linkWifi = new LinkWifi(context);

        wifiManager = (WifiManager) context
                .getSystemService(Service.WIFI_SERVICE);

        setWifiHandler = new SetWifiHandler(Looper.getMainLooper());

        wifi_list = (ListView) findViewById(R.id.wifi_list);
        refresh_list_btn = (ImageButton) findViewById(R.id.refresh_list_btn);
        wifi_on_off_btn = (ImageButton) findViewById(R.id.wifi_on_off_btn);
        switch_wifi = (SwitchCompat)findViewById(R.id.switch_wifi);

        refresh_list_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Toast.makeText(context, "请求刷新wifi列表！", Toast.LENGTH_LONG)
                        .show();
                // 刷新wifi列表
                wifiManager.startScan();
            }
        });

        if (linkWifi.checkWifiState()) {
            wifi_on_off_btn.setBackgroundResource(R.drawable.wifi_on);
            switch_wifi.setChecked(true);
        } else {
            wifi_on_off_btn.setBackgroundResource(R.drawable.wifi_off);
            switch_wifi.setChecked(false);
        }

//        switch_wifi.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "请求开关WIFI", Toast.LENGTH_LONG).show();
//                wifiManager.setWifiEnabled(!linkWifi.checkWifiState());
//            }
//        });
        switch_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Log.e("TAG","b="+b+",wifi status="+linkWifi.checkWifiState());
                if(!(b==(linkWifi.checkWifiState()))){


                    Toast.makeText(context, "请求开关WIFI", Toast.LENGTH_LONG).show();
                    wifiManager.setWifiEnabled(!linkWifi.checkWifiState());
                }
            }
        });

//        wifi_on_off_btn.setOnClickListener(new OnClickListener() {
//            public void onClick(View arg0) {
//                Toast.makeText(context, "请求开关WIFI", Toast.LENGTH_LONG).show();
//                wifiManager.setWifiEnabled(!linkWifi.checkWifiState());
//            }
//        });

        regWifiReceiver();

        scanAndGetResult();
    }


    private void regWifiReceiver() {
        System.out.println("注册一个当wifi热点列表发生变化时要求获得通知的消息");
        IntentFilter labelIntentFilter = new IntentFilter();
        labelIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        labelIntentFilter.addAction("android.net.wifi.STATE_CHANGE"); // ConnectivityManager.CONNECTIVITY_ACTION);
        labelIntentFilter.setPriority(1000); // 设置优先级，最高为1000
        context.registerReceiver(wifiResultChange, labelIntentFilter);

    }

    private void scanAndGetResult() {

        // 开始扫描
        wifiManager.startScan();
    }

    /**
     * 出品：xaonly 作者：时间如水 版本日期：2012-4-22 功能：接收wifi热点改变事件
     */
    private final BroadcastReceiver wifiResultChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                System.out.println("wifi列表刷新了");

                showWifiList();
            } else if (action.equals("android.net.wifi.STATE_CHANGE")) {
                System.out.println("wifi状态发生了变化");

                // 刷新状态显示
                showWifiList();

                if (linkWifi.checkWifiState()) {
                    wifi_on_off_btn.setBackgroundResource(R.drawable.wifi_on);
                    switch_wifi.setChecked(true);
                } else {
                    wifi_on_off_btn.setBackgroundResource(R.drawable.wifi_off);
                    switch_wifi.setChecked(false);
                }
            }
        }
    };

    private void showWifiList() {
        // 剔除ssid中的重复项，只保留相同ssid中信号最强的哪一个
        List<ScanResult> wifiList = wifiManager.getScanResults();
        List<ScanResult> newWifList = new ArrayList<ScanResult>();
        boolean isAdd = true;

        if (wifiList != null) {
            for (int i = 0; i < wifiList.size(); i++) {
                isAdd = true;
                for (int j = 0; j < newWifList.size(); j++) {
                    if (newWifList.get(j).SSID.equals(wifiList.get(i).SSID)) {
                        isAdd = false;
                        if (newWifList.get(j).level < wifiList.get(i).level) {
                            // ssid相同且新的信号更强
                            newWifList.remove(j);
                            newWifList.add(wifiList.get(i));
                            break;
                        }
                    }
                }
                if (isAdd)
                    newWifList.add(wifiList.get(i));
            }
        }

        wifiListAdapter = new WifiRelayListAdapter(context, newWifList,
                setWifiHandler);
        wifi_list.setAdapter(wifiListAdapter);
    }

    public class SetWifiHandler extends Handler {
        public SetWifiHandler(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:// 请求操作某一无线网络
                    ScanResult wifiinfo = (ScanResult) msg.obj;
                    configWifiRelay(wifiinfo);
                    // initLabelData();
                    break;

            }
        }
    }

    private void configWifiRelay(final ScanResult wifiinfo) {

        System.out.println("SSID=" + wifiinfo.SSID);

        // 如果本机已经配置过的话
        if (linkWifi.IsExsits(wifiinfo.SSID) != null) {
            final int netID = linkWifi.IsExsits(wifiinfo.SSID).networkId;

            String actionStr;
            // 如果目前连接了此网络
            if (wifiManager.getConnectionInfo().getNetworkId() == netID) {
                actionStr = "断开";
            } else {
                actionStr = "连接";
            }
            System.out
                    .println("wifiManager.getConnectionInfo().getNetworkId()="
                            + wifiManager.getConnectionInfo().getNetworkId());

            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("请选择你要进行的操作？")
                    .setPositiveButton(actionStr,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    if (wifiManager.getConnectionInfo()
                                            .getNetworkId() == netID) {
                                        wifiManager.disconnect();
                                    } else {
                                        WifiConfiguration config = linkWifi
                                                .IsExsits(wifiinfo.SSID);
                                        linkWifi.setMaxPriority(config);
                                        linkWifi.ConnectToNetID(config.networkId);
                                    }

                                }
                            })
                    .setNeutralButton("忘记",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    wifiManager.removeNetwork(netID);
                                    return;
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    return;
                                }
                            }).show();

            return;
        }

        String capabilities = "";

        if (wifiinfo.capabilities.contains("WPA2-PSK")) {
            // WPA-PSK加密
            capabilities = "psk2";
        } else if (wifiinfo.capabilities.contains("WPA-PSK")) {
            // WPA-PSK加密
            capabilities = "psk";
        } else if (wifiinfo.capabilities.contains("WPA-EAP")) {
            // WPA-EAP加密
            capabilities = "eap";
        } else if (wifiinfo.capabilities.contains("WEP")) {
            // WEP加密
            capabilities = "wep";
        } else {
            // 无密码
            capabilities = "";
        }

        if (!capabilities.equals("")) {
            // 有密码，提示输入密码进行连接

            // final String encryption = capabilities;

            LayoutInflater factory = LayoutInflater.from(context);
            final View inputPwdView = factory.inflate(R.layout.dialog_inputpwd,
                    null);
            new AlertDialog.Builder(context)
                    .setTitle("请输入该无线的连接密码")
                    .setMessage("无线SSID：" + wifiinfo.SSID)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(inputPwdView)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    EditText pwd = (EditText) inputPwdView
                                            .findViewById(R.id.etPassWord);
                                    String wifipwd = pwd.getText().toString();

                                    // 此处加入连接wifi代码
                                    int netID = linkWifi.CreateWifiInfo2(
                                            wifiinfo, wifipwd);

                                    linkWifi.ConnectToNetID(netID);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).setCancelable(false).show();

        } else {
            // 无密码
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("你选择的wifi无密码，可能不安全，确定继续连接？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    // 此处加入连接wifi代码
                                    int netID = linkWifi.CreateWifiInfo2(
                                            wifiinfo, "");

                                    linkWifi.ConnectToNetID(netID);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    return;
                                }
                            }).show();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(wifiResultChange); // 注销此广播接收器
    }

}
