/**
 * 
 */

import java.util.Vector;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class EnemyTank extends Tank implements Runnable{
//敌方坦克也可以发射子弹
	Vector<Shot> shots = new Vector<>();
	boolean isLive = true;
	Vector<EnemyTank> enemyTanks = new Vector<>();
	public EnemyTank(int x, int y) {
		
		super(x, y);
		
	}
	
	//敌方坦克动起来
	@Override
	public void run() {//在创建敌人坦克时启动线程
		while (true) {
			//控制敌人子弹发射数量shots.size() == 0 就创建一颗子弹放入集合
			//shots.size() <5用来控制子弹发射数量 5课
			if (shots.size() <5 && isLive) {
				Shot s = null;
                //判断坦克的方向，创建对应的子弹
				switch (getDirection()) {
                case 0:
                    s = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1:
                    s = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2: //向下
                    s = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3://向左
                    s = new Shot(getX(), getY() + 20, 3);
                    break;
            }
				shots.add(s);
				//启动
				new Thread(s).start();
			}
			
			//根据坦克的方向来继续机动
		switch (getDirection()){
			case 0: {//向上
				//让坦克保持一个方向这批30秒
				for (int i = 0; i < 30; i++) {
					//考虑边界 不能超出边界 getY() > 0
					//继续考虑 不和别人坦克发生碰撞 !isTouchEneyTank()
					if (getY() > 0 && !isTouchEneyTank()) {
                        moveUp();
                    }
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				break;
			}
			case 1: {//xiang右
				for (int i = 0; i < 30; i++) {
					//继续考虑 不和别人坦克发生碰撞 !isTouchEneyTank()
					if (getX() + 60 < 1000 && !isTouchEneyTank()) {
                        moveRight();
                    }
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
						}
			case 2: {//下
				for (int i = 0; i < 30; i++) {
					if ((getY() + 100) < 750 && !isTouchEneyTank()) {
                        moveDown();
                    }
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			case 3: {//左
				for (int i = 0; i < 30; i++) {
					if (getX() > 0 && !isTouchEneyTank()) {
                        moveLeft();
                    }
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			}
			//休眠50毫秒不然敌方坦克会一直动
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//然后随机改变坦克方向
			setDirection((int)(Math.random() * 4));
			if (!isLive) {
				break;//退出线程
			}
			
		}
		
	}
	public Vector<EnemyTank> getEnemyTanks() {
		return enemyTanks;
	}
//设置enemyTank的成员到enemyTanks
	public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
		this.enemyTanks = enemyTanks;
	}
	//编写方法判断当前的这个敌人坦克是否和enemyTanks中的其他成员发生碰撞或者重叠 
	public boolean isTouchEneyTank() {
		//判断当前敌人（this）方向
		switch (this.getDirection() ){
		case 0: {//上
			//让当前敌人坦克和其他所有的敌人坦克做比较 类似于冒泡排序的比较
			for (int i = 0; i < enemyTanks.size(); i++) {
				//从数组中挨个取出
				EnemyTank enemyTank = enemyTanks.get(i);
				//不和自己比较
				if (enemyTank != this) {
					//如果敌人坦克是上/下
					//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
					if (enemyTank.getDirection() == 0 || enemyTank.getDirection()== 2) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//左下角坐标[this.getX(),this.getY() + 60]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
								&& this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= getY() + 60) {
							return true;
						}
						//右下角坐标 [this.getX() + 40,this.getY() + 60]
						if (this.getX() + 40 >= enemyTank.getX() && this.getX() +40 <= enemyTank.getX() + 40
								&& this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= getY() + 60) {
							return true;
						}
					}
					
					//如果敌人坦克是右/左
					//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
					if (enemyTank.getDirection() == 1 || enemyTank.getDirection()== 3) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//左下角坐标[this.getX(),this.getY() + 60]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60
								&& this.getY() +60 >= enemyTank.getY() && this.getY() + 60 <= getY() + 40) {
							return true;
						}
						//右下角坐标 [this.getX() + 40,this.getY() + 60]
						if (this.getX() + 40 >= enemyTank.getX() && this.getX() +40 <= enemyTank.getX() + 40
								&& this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= getY() + 60) {
							return true;
						}
					}
				}
			}
			break;
		}
		case 2: {//下
			//让当前敌人坦克和其他所有的敌人坦克做比较 类似于冒泡排序的比较
			for (int i = 0; i < enemyTanks.size(); i++) {
				//从数组中挨个取出
				EnemyTank enemyTank = enemyTanks.get(i);
				//不和自己比较
				if (enemyTank != this) {
					//如果敌人坦克是上/下
					//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
					if (enemyTank.getDirection() == 0 || enemyTank.getDirection()== 2) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//右上角坐标[this.getX() + 60,this.getY()]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 60) {
							return true;
						}
						//右下角坐标 [this.getX() + 60,this.getY() + 40]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() +60 <= enemyTank.getX() + 40
								&& this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= getY() + 60) {
							return true;
						}
					}
					
					//如果敌人坦克是右/左
					//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
					if (enemyTank.getDirection() == 1 || enemyTank.getDirection()== 3) {
						//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//右上角坐标[this.getX(),this.getY()]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 40) {
							return true;
						}
						//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() +60 <= enemyTank.getX() + 60
								&& this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= getY() + 40) {
							return true;
						}
					}
				}
			}
			break;
			}
		case 1: {//右
			//让当前敌人坦克和其他所有的敌人坦克做比较 类似于冒泡排序的比较
			for (int i = 0; i < enemyTanks.size(); i++) {
				//从数组中挨个取出
				EnemyTank enemyTank = enemyTanks.get(i);
				//不和自己比较
				if (enemyTank != this) {
					//如果敌人坦克是上/下
					//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
					if (enemyTank.getDirection() == 0 || enemyTank.getDirection()== 2) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//右上角坐标[this.getX() + 60,this.getY()]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 60) {
							return true;
						}
						//右下角坐标 [this.getX() + 60,this.getY() + 40]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() +60 <= enemyTank.getX() + 40
								&& this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= getY() + 60) {
							return true;
						}
					}
					
					//如果敌人坦克是右/左
					//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
					if (enemyTank.getDirection() == 1 || enemyTank.getDirection()== 3) {
						//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//右上角坐标[this.getX(),this.getY()]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 40) {
							return true;
						}
						//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
						if (this.getX() + 60 >= enemyTank.getX() && this.getX() +60 <= enemyTank.getX() + 60
								&& this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= getY() + 40) {
							return true;
						}
					}
				}
			}
			break;
		}
		case 3: {//左
			//让当前敌人坦克和其他所有的敌人坦克做比较 类似于冒泡排序的比较
			for (int i = 0; i < enemyTanks.size(); i++) {
				//从数组中挨个取出
				EnemyTank enemyTank = enemyTanks.get(i);
				//不和自己比较
				if (enemyTank != this) {
					//如果敌人坦克是上/下
					//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
					if (enemyTank.getDirection() == 0 || enemyTank.getDirection()== 2) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//左上角坐标[this.getX(),this.getY() + 60]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 60) {
							return true;
						}
						//左下角坐标 [this.getX(),this.getY() + 60]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
								&& this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= getY() + 60) {
							return true;
						}
					}
					
					//如果敌人坦克是右/左
					//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
					//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
					if (enemyTank.getDirection() == 1 || enemyTank.getDirection()== 3) {
						//2当前坦克左上角的坐标[this.getX(),this.getY()]
						//重叠顾名思义就是坐标进入敌人坦克坐标里面
						
						//左上角坐标[this.getX(),this.getY() + 60]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
								&& this.getY() >= enemyTank.getY() && this.getY() <= getY() + 60) {
							return true;
						}
						//左下角坐标 [this.getX(),this.getY() + 40]
						if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40
								&& this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= getY() + 60) {
							return true;
						}
					}
					}
				}
			}
			break;
		}
		return false;
}
}
