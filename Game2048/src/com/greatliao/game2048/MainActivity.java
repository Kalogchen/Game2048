package com.greatliao.game2048;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvScore;
	private TextView tvMaxScore;
	private Button btRestart;
	private GameView gameView;

	public static MainActivity mainActivity;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private int score, maxscore;// 用来记录当前得分和历史最高高分

	/**
	 *提供一个单例设计模式给别的类去调用该类中的处理分数的方法
	 */
	public MainActivity() {
		// TODO Auto-generated constructor stub
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置游戏界面无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置窗体全屏

		super.onCreate(savedInstanceState);
	
		pref = getSharedPreferences("data", MODE_PRIVATE);
		if (pref.contains("MaxScore")) {
			maxscore = getData();

		}

		setContentView(R.layout.activity_main);

		tvScore = (TextView) findViewById(R.id.texscore);

		tvMaxScore = (TextView) findViewById(R.id.texMaxscore);
		showMaxScore();
		btRestart = (Button) findViewById(R.id.btRe);
		gameView = (GameView) findViewById(R.id.gvGameView);
		/**
		 * 给按钮btRestart设置监听器
		 */
		btRestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				//创建对话框
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("亲爱的玩家").setIcon(R.drawable.ico2048).setMessage("你确定要重新开始游戏吗？");
				builder.setNegativeButton("重新开始", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						gameView.startGame();
					}
				});
				builder.setPositiveButton("清除最高分", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						clearMaxScore();
					}
				});
				builder.show();

			}
		});

	}

	/**
	 * 获取最高分数据
	 * 
	 * @return
	 */
	private int getData() {

		int MaxScore = pref.getInt("MaxScore", maxscore);
		maxscore = MaxScore;
		return maxscore;
	}

	/**
	 * 储存最高分数据
	 */
	private void saveDate() {
		editor = getSharedPreferences("data", MODE_PRIVATE).edit();
		editor.putInt("MaxScore", maxscore);
		editor.commit();
	}

	@Override
	protected void onPause() {
		saveDate();
		// TODO Auto-generated method stub
		super.onPause();

	}

	/**
	 * 分数清零
	 */
	public void clearScore() {
		score = 0;
		showScore();

	}

	//使用方法添加分数，并显示出来
	public void addScore(int s) {
		score += s;
		showScore();

	}

	public int getScore() {
		return score;

	}

	//在控件上显示分数
	private void showScore() {
		// TODO Auto-generated method stub
		tvScore.setText(score + "");

	}

	/**
	 * 最高分清零
	 */
	public void clearMaxScore() {

		maxscore = 0;
		showMaxScore();

	}

	public void getMaxScore() {
		if (score > maxscore) {
			maxscore = score;
		}
		showMaxScore();

	}

	//在控件上显示最高分
	private void showMaxScore() {
		// TODO Auto-generated method stub
		tvMaxScore.setText(maxscore + "");

	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public static void setMainActivity(MainActivity mainActivity) {
		MainActivity.mainActivity = mainActivity;
	}

}
