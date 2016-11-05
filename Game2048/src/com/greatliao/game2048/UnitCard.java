package com.greatliao.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class UnitCard extends FrameLayout {
	//继承FrameLayout类，在FrameLayout中可以嵌入其它控件例如文本什么的
	private int num;
	private TextView card;

	/**
	 * 获得卡片所对应的数字
	 * @return
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 设置卡片上的数字
	 * @param num
	 *           
	 */
	public void setNum(int num) {
		this.num = num;
		if (num == 0) {	//为了排除出现的随机数为0
			card.setText("");	//“”，表示这是一个空字符串，该标签对象可以改变布局
		} else {
			card.setText(num + "");	//将int类型转换为字符串
		}
	}

	/**
	 * 设置每个卡片的颜色
	 * @param color
	 *          
	 */
	public void setbackColor(int color) {
		card.setBackgroundColor(color);
	}

	/**
	 *提供构造函数
	 * @param context
	 */
	public UnitCard(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		card = new TextView(getContext());	//初始化文本
		card.setTextSize(30);
		card.setGravity(Gravity.CENTER);	//把放在控件里的文本居中处理
		
		 //布局参数用类控制
		LayoutParams lParams = new LayoutParams(-1, -1);//该类用来初始化layout控件textView里的高和宽属性，-1表示fill_parent
		lParams.setMargins(10, 10, 0, 0);//给每个textView的左和上设置margin
		addView(card, lParams);//该方法是用来给label控件加上已经初始化了的高和宽属性

		setNum(0);

	}

}
