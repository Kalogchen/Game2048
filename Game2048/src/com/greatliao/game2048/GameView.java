package com.greatliao.game2048;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/**
 * 自定义View
 */
public class GameView extends GridLayout {		//GridLayout网格布局
	
	private int row = 4;// 游戏界面行数、列数

	private UnitCard[][] cards = new UnitCard[row][row];// 创建二维数组来存储每个数字卡片对象
	private List<Point> emptyPoint = new ArrayList<Point>();//添加一个list来存放Point来控制随机数方法里的随机数

	/**
	 * 构造函数 在布局文件中调用是使用该构造函数
	 * 这两个构造函数的创建是为了可以访问到layout里面的Gridview（网格视图）控件
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		//添加类的入口地址
		initGameView();

	}

	/**
	 * 初始化游戏界面，自定义类的入口方法
	 */
	private void initGameView() {
		// TODO Auto-generated method stub
		setColumnCount(row);// 设置GridLayout的列数
		setRowCount(row);// 设置GridLayout的行数
		setBackgroundColor(0xffffefd5);

		/**
		 * 触摸监听器
		 */
		setOnTouchListener(new OnTouchListener() {
			private float startX, startY, moveX, moveY;
			//startX，startY表示手指按下去时的坐标，moveX, moveY表示手指离开时的坐标
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				// 判断手指滑动的方向
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					moveX = event.getX() - startX;
					moveY = event.getY() - startY;
					if (Math.abs(moveX) > Math.abs(moveY)) { //表示手势左右滑动不太偏离水平方向
						if (moveX < -5) {	//手势往左,-5表示范围
							// 侦听用户操作后执行向左滑动的方法
							left();
							// 设置卡片的背景色
							setbackColor();

						}
						if (moveX > 5) {	//手势往右，5表示范围
							// 侦听用户操作后执行向右滑动的方法
							right();
							setbackColor();

						}
					} else {	//表示手势上下滑动不太偏离垂直水平
						if (moveY > 5) {	//手势往下，5表示范围
							// 侦听用户操作后执行向下滑动的方法
							down();
							setbackColor();

						}
						if (moveY < -5) {	//手势往下，-5表示范围
							// 侦听用户操作后执行向下滑动的方法
							up();
							setbackColor();

						}
					}
					break;

				default:
					break;
				}

				return true;
			}
		});

	}

	/**
	 * 适应不同屏幕，获得屏幕的尺寸
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		//该方法就是宽高发生改变的时候我们可以得到当前的宽高是多少
        //该方法也是在游戏一被创建的时候就调用，也就是用来初始宽高的方法
		
		int unitCardSize = (Math.min(w, h) - 10) / row;
		//获取手机较窄的长度，-10是用来间隔每个卡片的距离，用手机的宽除以4就是每个卡片的长度
		
		//在该方法初始化的时候新建16个卡片
		addunitCard(unitCardSize, unitCardSize);
		startGame();

	}

	/**
	 * 添加数字卡片
	 * 
	 * @param uCardWidth
	 *            卡片宽度
	 * @param uCardHeight
	 *            卡片高度
	 */
	public void addunitCard(int uCardWidth, int uCardHeight) {
		UnitCard uCard;
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				uCard = new UnitCard(getContext());
				uCard.setNum(0);	//给卡片设置初始化数字
				addView(uCard, uCardWidth, uCardHeight);
				cards[x][y] = uCard;	//初始化时新建的卡片类存放到二维数组里

			}

		}
	}

	/**
	 * 初始化游戏刚开始的界面
	 */
	public void startGame() {
		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				cards[x][y].setNum(0);//将界面初始化为空
			}
		}
		//加入两个随机数字
		addRandomNum();
		addRandomNum();
		setbackColor();

	}

	/**
	 * 添加随机数
	 */
	public void addRandomNum() {
		emptyPoint.clear();//把emptyPoint清空，每次调用添加随机数时就清空之前所控制的指针
		
		//对所有的位置进行遍历：即为每个卡片加上了可以控制的指针
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				if (cards[x][y].getNum() == 0) {
					emptyPoint.add(new Point(x, y));//给List存放控制卡片用的指针（通过坐标轴来控制）

				}
			}
		}

		//从List里取出一个控制指针的Point对象
		Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
		//通过Point对象充当下标的角色来控制存放card的二维数组cards，然后随机给定位到的card对象赋值
		cards[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);//Math.random()返回值的范围为[0,1)
	}

	/**
	 * 向左滑动的方法
	 */
	private void left() {

		boolean judge = false;

		////以下两行for循环实现了一行一行的遍历
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				
				//在水平上固定了一个格子之后再继续在水平上遍历别的格子，且当格子有数的时候进行以下的操作
				for (int x1 = x + 1; x1 < row; x1++) {
					if (cards[x1][y].getNum() > 0) {
						if (cards[x][y].getNum() == 0) {//判断被固定的格子左边的格子是否为空，初始值设0为空
							cards[x][y].setNum(cards[x1][y].getNum());//将固定的格子的数字赋给左边的格子
							cards[x1][y].setNum(0);//固定的格子给左边的格子赋值过后自己归零
							judge = true;
							x--;//第二层循环，即同一层的不同列退一格继续循环，这样做的原因是为了继续固定这个格子而去检查与它同一水平上的别的格子的内容，因为其他格子是什么个情况还需要继续在第二层进行判断
	                        

						} else if (cards[x][y].getNum() == cards[x1][y].getNum()) {//判断相邻两个数是否相等

							cards[x][y].setNum(cards[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cards[x][y].getNum());//给MainActivity中的tvScore加上分数，MainActivity设计成了单例设计模式，所以使用get方法获得对象
							MainActivity.getMainActivity().getMaxScore();//判断当前得分与历史最高得分的大小
							cards[x1][y].setNum(0);
							judge = true;

						}
						break;

					}
				}
			}

		}
		if (judge) {
			checkWin();
			addRandomNum();
			checkComplete();
		}

	}

	/**
	 * 向右滑动的方法
	 */
	private void right() {
		boolean judge = false;
		for (int y = 0; y < row; y++) {
			for (int x = row - 1; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cards[x1][y].getNum() > 0) {
						if (cards[x][y].getNum() == 0) {
							cards[x][y].setNum(cards[x1][y].getNum());
							cards[x1][y].setNum(0);

							x++;
							judge = true;

						} else if (cards[x][y].getNum() == cards[x1][y].getNum()) {

							cards[x][y].setNum(cards[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cards[x][y].getNum());
							MainActivity.getMainActivity().getMaxScore();
							cards[x1][y].setNum(0);
							judge = true;

						}
						break;

					}
				}
			}

		}
		if (judge) {
			checkWin();
			addRandomNum();
			checkComplete();
		}

	}

	/**
	 *向上滑动的方法
	 */
	private void up() {
		boolean judge = false;
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				for (int x1 = x + 1; x1 < row; x1++) {
					if (cards[y][x1].getNum() > 0) {
						if (cards[y][x].getNum() == 0) {
							cards[y][x].setNum(cards[y][x1].getNum());
							cards[y][x1].setNum(0);

							x--;
							judge = true;
						} else if (cards[y][x].getNum() == cards[y][x1].getNum()) {

							cards[y][x].setNum(cards[y][x].getNum() * 2);
							MainActivity.getMainActivity().addScore(cards[y][x].getNum());
							MainActivity.getMainActivity().getMaxScore();
							cards[y][x1].setNum(0);
							judge = true;

						}
						break;

					}
				}
			}
		}
		if (judge) {
			checkWin();
			addRandomNum();
			checkComplete();
		}
	}

	/**
	 * 向下滑动的方法
	 */
	private void down() {
		boolean judge = false;
		for (int y = 0; y < row; y++) {
			for (int x = row - 1; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cards[y][x1].getNum() > 0) {
						if (cards[y][x].getNum() == 0) {
							cards[y][x].setNum(cards[y][x1].getNum());
							cards[y][x1].setNum(0);

							x++;
							judge = true;
						} else if (cards[y][x].getNum() == cards[y][x1].getNum()) {

							cards[y][x].setNum(cards[y][x].getNum() * 2);
							MainActivity.getMainActivity().addScore(cards[y][x].getNum());
							MainActivity.getMainActivity().getMaxScore();
							cards[y][x1].setNum(0);
							judge = true;

						}
						break;

					}
				}
			}

		}
		if (judge) {
			checkWin();
			addRandomNum();
			checkComplete();
		}

	}

	/**
	 * 检查游戏是否结束
	 */
	private void checkComplete() {
		boolean complete = true;
		All: for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {//判断是否有卡片为空，是否有卡片与它(x>0)上边，(y>0)左边的卡片数值相等
				if (cards[x][y].getNum() == 0 || (x > 0 && cards[x][y].getNum() == (cards[x - 1][y]).getNum())
						|| (y > 0 && cards[x][y].getNum() == (cards[x][y - 1]).getNum())) {
					complete = false;
					break All;

				}

			}

		}

		if (complete) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
			dialog.setTitle("亲爱的玩家").setMessage("你的得分为" + MainActivity.getMainActivity().getScore() + "分")
					.setIcon(R.drawable.ico2048);
			dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.getMainActivity().finish();

				}
			});
			dialog.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startGame();

				}
			});
			dialog.show();
		}
	}

	/**
	 * 检查是否得到2048，即游戏胜利
	 */
	private void checkWin() {
		boolean complete = false;
		All: for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {
				if (cards[x][y].getNum() == 2048) {
					complete = true;
					break All;
				}

			}

		}

		if (complete) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
			dialog.setTitle("亲爱的玩家，恭喜").setMessage("你成功得到了2048").setIcon(R.drawable.ico2048);
			dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.getMainActivity().finish();

				}
			});
			dialog.setPositiveButton("再来一次", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startGame();

				}
			});
			dialog.show();

		}

	}

	/**
	 * 设置卡片颜色，根据卡片的数值设置相应的颜色
	 */
	private void setbackColor() {
		for (int y = 0; y < row; y++) {
			for (int x = 0; x < row; x++) {

				if (cards[x][y].getNum() == 0) {
					cards[x][y].setbackColor(0xffffdead);
				}
				if (cards[x][y].getNum() == 2) {
					cards[x][y].setbackColor(0xffffb90f);
				}
				if (cards[x][y].getNum() == 4) {
					cards[x][y].setbackColor(0xffff8c00);
				}
				if (cards[x][y].getNum() == 8) {
					cards[x][y].setbackColor(0xffff7f50);
				}
				if (cards[x][y].getNum() == 16) {
					cards[x][y].setbackColor(0xffff6eb4);
				}
				if (cards[x][y].getNum() == 32) {
					cards[x][y].setbackColor(0xffff3030);
				}
				if (cards[x][y].getNum() == 64) {
					cards[x][y].setbackColor(0xffff1493);
				}
				if (cards[x][y].getNum() == 128) {
					cards[x][y].setbackColor(0xffff00ff);
				}
				if (cards[x][y].getNum() == 256) {
					cards[x][y].setbackColor(0xffff0000);
				}
				if (cards[x][y].getNum() == 512) {
					cards[x][y].setbackColor(0xffe066ff);
				}
				if (cards[x][y].getNum() == 1024) {
					cards[x][y].setbackColor(0xff7fff00);
				}
				if (cards[x][y].getNum() == 2048) {
					cards[x][y].setbackColor(0xffffff00);
				}

			}

		}

	}
}
