package com.greatliao.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class Rule extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method
		//设置窗体全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.rule);

	}

}
