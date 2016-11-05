package com.greatliao.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class Select extends Activity implements OnClickListener {
	// 四个按钮
	private Button btStart;
	private Button btRule;
	private Button btAbout;
	private Button btExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 去除标题栏
		// 注意：该句代码要放在任何显示view代码之前否则会报错，建议放在onCreate之后，紧邻onCreate
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置窗体全屏

		super.onCreate(savedInstanceState);
		// 将main.xml布局显示出来
		setContentView(R.layout.main);

		// 初始化按钮
		btStart = (Button) findViewById(R.id.btStart);
		btRule = (Button) findViewById(R.id.btRule);
		btAbout = (Button) findViewById(R.id.btAbout);
		btExit = (Button) findViewById(R.id.btExit);
		// 为按钮设置监听器，因为该类implements
		// OnClickListener，故用此形式调用监听器，点击执行的方法在下面重写的onClick
		btStart.setOnClickListener(this);
		btAbout.setOnClickListener(this);
		btRule.setOnClickListener(this);
		btExit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btStart:
			// 用Intent来打开另外一个activity
			Intent intent = new Intent(Select.this, MainActivity.class);
			startActivity(intent);

			break;

		case R.id.btRule:
			Intent intent2 = new Intent(Select.this, Rule.class);
			startActivity(intent2);

			break;
		case R.id.btAbout:
			Intent intent3 = new Intent(Select.this, About.class);
			startActivity(intent3);

			break;
			
		case R.id.btExit:
			isFinish();
			
			break;

		}

	}

	public void isFinish(){
    	AlertDialog.Builder dll=new AlertDialog.Builder(this);
    	dll.setTitle("警告！");
    	dll.setMessage("你想要退出游戏吗？");
    	dll.setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Select.this.finish();
			}
		}); 
    	dll.setNeutralButton("否", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 在此，当用户点cancel按钮，不做任何操作
				
			}
		});
    	dll.create();
    	dll.show();
    	}

}
