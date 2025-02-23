/**
 * 
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class HspTankGame01 extends JFrame{
	MyPanel mPanel = null;
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
	
	HspTankGame01 hspTankGame01 = new HspTankGame01();
}
public HspTankGame01() {
	System.out.println("请选择 1 ：新游戏 2 ：继续上局");
	String key = sc.next();
	
	mPanel = new MyPanel(key);
	//将mPanel放入到Thread 并启动
	Thread thread = new Thread(mPanel);
	thread.start();
	this.add(mPanel);//面板绘图区域
	this.setSize(1300,950);
	this.addKeyListener(mPanel);//窗口JFrame可以监听按键
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//当点击窗口的x就可以关闭程序
	this.setVisible(true);//可以显示
	
	//在JFfam中增加相应的关闭窗口的处理
	this.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			Recorder.keepRecord();
			System.exit(0);
		}
		
	});
}
}
