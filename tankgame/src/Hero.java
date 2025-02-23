import java.util.Vector;

/**
 * 
 */

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class Hero extends Tank{
	//定义一个shot对象
	Shot shot = null; 
	//发射多颗子弹
	Vector<Shot> shots = new Vector<>();
	public Hero(int x, int y) {
		super(x, y);
		// TODO 自动生成的构造函数存根
	}
	//射击
	public void shotEnemyTank() {
		//控制子弹数量
		if (shots.size() == 5) {
			return;//中断就不能发射了
		}
		//创建Shot对象 根据当前Hero对象的位置和方向来创建Shot
		switch (getDirection()) {//得到Hero对象方向
        case 0: //向上
            shot = new Shot(getX() + 20, getY(), 0);
            break;
        case 1: //向右
            shot = new Shot(getX() + 60, getY() + 20, 1);
            break;
        case 2: //向下
            shot = new Shot(getX() + 20, getY() + 60, 2);
            break;
        case 3: //向左
            shot = new Shot(getX(), getY() + 20, 3);
            break;
    }
		//把新创建的shot放入到shots集合
		shots.add(shot);
		new Thread(shot).start();
		
	}
}
