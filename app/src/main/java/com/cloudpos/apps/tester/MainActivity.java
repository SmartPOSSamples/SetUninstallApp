package com.cloudpos.apps.tester;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cloudpos.utils.Logger;
import com.cloudpos.utils.TextViewUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends AbstractActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_test1 = (Button) this.findViewById(R.id.btn_test1);
        Button btn_test2 = (Button) this.findViewById(R.id.btn_test2);
        Button btn_test3 = (Button) this.findViewById(R.id.btn_test3);

        log_text = (TextView) this.findViewById(R.id.text_result);
        log_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.settings).setOnClickListener(this);
        btn_test1.setOnClickListener(this);
        btn_test2.setOnClickListener(this);
        btn_test3.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == R.id.log_default) {
                    log_text.append("\t" + msg.obj + "\n");
                } else if (msg.what == R.id.log_success) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoBlueTextView(log_text, str);
                } else if (msg.what == R.id.log_failed) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoRedTextView(log_text, str);
                } else if (msg.what == R.id.log_clear) {
                    log_text.setText("");
                }
            }
        };

        String auto_start = getIntent().getStringExtra("auto_start");
        Logger.debug("auto_start:%s", auto_start);
        if (auto_start != null && auto_start.length() > 0) {
            writerInLog("auto start with : " + auto_start, R.id.log_default);
        }
    }

    @Override
    public void onClick(View arg0) {
        int index = arg0.getId();
        if (index == R.id.btn_test1) {
            addUninstallProhibitedApp();
        } else if (index == R.id.btn_test2) {
            removeUninstallProhibitedApp();
        } else if (index == R.id.btn_test3) {
            clearUninstallProhibitedApp();
        } else if (index == R.id.settings) {
            log_text.setText("");
        }
    }

    private void removeUninstallProhibitedApp() {
        try {
            writerInSuccessLog("+removeUninstallProhibitedApp: ");
            PackageManager pm = getPackageManager();
            Method remove = PackageManager.class.getMethod("removeUninstallProhibitedApp", String.class);
            boolean result = (Boolean) remove.invoke(pm, this.getPackageName());
            writerInSuccessLog("+removeUninstallProhibitedApp: " + result);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearUninstallProhibitedApp() {
        try {
            writerInSuccessLog("+clearUninstallProhibitedApp: ");
            PackageManager pm = getPackageManager();
            Method clear = PackageManager.class.getMethod("clearUninstallProhibitedApps");
            boolean result = (Boolean) clear.invoke(pm);
            writerInSuccessLog("+clearUninstallProhibitedApp: " + result);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUninstallProhibitedApp() {
        try {
            writerInSuccessLog("+addUninstallProhibitedApp: ");
            PackageManager pm = getPackageManager();
            Method add = PackageManager.class.getMethod("addUninstallProhibitedApp", String.class);
            boolean result = (Boolean) add.invoke(pm, this.getPackageName());
            writerInSuccessLog("+addUninstallProhibitedApp: " + result);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
