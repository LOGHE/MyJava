/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class HspTankGame01 extends JFrame{
	MyPanel mPanel = null;
	
	public static void main(String[] args) {
	HspTankGame01 hspTankGame01 = new HspTankGame01();
}
public HspTankGame01() {
	mPanel = new MyPanel();
	//将mPanel放入到Thread 并启动
	Thread thread = new Thread(mPanel);
	thread.start();
	this.add(mPanel);//面板绘图区域
	this.setSize(1000,750);
	this.addKeyListener(mPanel);//窗口JFrame可以监听按键
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//当点击窗口的x就可以关闭程序
	this.setVisible(true);//可以显示
}
}
