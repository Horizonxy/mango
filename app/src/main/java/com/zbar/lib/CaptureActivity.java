package com.zbar.lib;

import android.os.Handler;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:25:31
 * 
 * 版本: V_1.0.0
 * 
 * 描述: 扫描界面
 */
public interface CaptureActivity {
	
	public boolean isNeedCapture();

	public int getX();

	public int getY();

	public int getCropWidth();

	public int getCropHeight();

	public Handler getHandler();

	public void handleDecode(String result);

}