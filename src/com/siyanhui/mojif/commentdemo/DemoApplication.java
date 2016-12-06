package com.siyanhui.mojif.commentdemo;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.melink.bqmmsdk.sdk.BQMM;
import com.tencent.bugly.crashreport.CrashReport;

public class DemoApplication extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();
		CrashReport.initCrashReport(getApplicationContext());
		/**
		 * BQMM集成
		 * 首先从AndroidManifest.xml中取得appId和appSecret，然后对BQMM SDK进行初始化
		 */
		try {
			Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;
			BQMM.getInstance().initConfig(this, bundle.getString("bqmm_app_id"), bundle.getString("bqmm_app_secret"));
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
