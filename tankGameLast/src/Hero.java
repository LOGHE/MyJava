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
	Vector<Obstacle> obstacles = new Vector<>();
	static int  live = 3;
	
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
	//判断敌人坦克是否抵达障碍物
		public boolean isTouchEneyObstacle() {
			//判断地方是否抵达图腾
			for (int i = 3; i < 5; i++) {
				//判断当前敌人（this）方向
						switch (this.getDirection() ){
						case 0: {//上
									//1如果障碍物1 x的范围[obstacles.get(0).getX(),enemyTank.getX() + 40]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//左下角坐标[this.getX(),this.getY() + 60]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 50) {
											return true;
										}
										//右下角坐标 [this.getX() + 40,this.getY() + 60]
										if (this.getX() + 40 >= obstacles.get(i).getX() && this.getX() +40 <= obstacles.get(i).getX() + 20
												&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 50) {
											return true;
										}
									
									
									//如果敌人坦克是右/左
									//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 600]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 20]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//左下角坐标[this.getX(),this.getY() + 60]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() +60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//右下角坐标 [this.getX() + 40,this.getY() + 60]
										if (this.getX() + 40 >= obstacles.get(i).getX() && this.getX() +40 <= obstacles.get(i).getX() + 20
												&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
											return true;
										}
								
							
							break;
						}
						case 2: {//下
									//如果敌人坦克是上/下
									//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 600]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 20]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//右上角坐标[this.getX() + 60,this.getY()]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//右下角坐标 [this.getX() + 60,this.getY() + 40]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 20
												&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
											return true;
										}
									
									//如果敌人坦克是右/左
									//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
										//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//右上角坐标[this.getX(),this.getY()]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <=obstacles.get(i). getY() + 45) {
											return true;
										}
										//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 60
												&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
											return true;
										}
							break;
							}
						case 1: {//右
									//如果敌人坦克是上/下
									//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//右上角坐标[this.getX() + 60,this.getY()]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//右下角坐标 [this.getX() + 60,this.getY() + 40]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 20
												&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
											return true;
										}
									
									//如果敌人坦克是右/左
										//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//右上角坐标[this.getX(),this.getY()]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(0).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
										if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(0).getX() + 60
												&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= getY() + 45) {
											return true;
										}
							break;
						}
						case 3: {//左
									//如果敌人坦克是上/下
									//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//左上角坐标[this.getX(),this.getY() + 60]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//左下角坐标 [this.getX(),this.getY() + 60]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
											return true;
										}
									
									//如果敌人坦克是右/左
									//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
									//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
										//2当前坦克左上角的坐标[this.getX(),this.getY()]
										//重叠顾名思义就是坐标进入敌人坦克坐标里面
										
										//左上角坐标[this.getX(),this.getY() + 60]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
											return true;
										}
										//左下角坐标 [this.getX(),this.getY() + 40]
										if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 20
												&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
											return true;
										}
							}
							break;
						}
				} 
			
			//判断敌人子弹是否击中障碍物 上面那个障碍物
			//遍历出两个障碍物 
			for (int i = 0; i < 2; i++) {
			
			//判断当前敌人（this）方向
					switch (this.getDirection() ){
					case 0: {//上
								//1如果障碍物1 x的范围[obstacles.get(0).getX(),enemyTank.getX() + 40]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//左下角坐标[this.getX(),this.getY() + 60]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//右下角坐标 [this.getX() + 40,this.getY() + 60]
									if (this.getX() + 40 >= obstacles.get(i).getX() && this.getX() +40 <= obstacles.get(i).getX() + 600
											&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
										return true;
									}
								
								
								//如果敌人坦克是右/左
								//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 600]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 20]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//左下角坐标[this.getX(),this.getY() + 60]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() +60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//右下角坐标 [this.getX() + 40,this.getY() + 60]
									if (this.getX() + 40 >= obstacles.get(i).getX() && this.getX() +40 <= obstacles.get(i).getX() + 600
											&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
										return true;
									}
							
						
						break;
					}
					case 2: {//下
								//如果敌人坦克是上/下
								//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 600]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 20]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//右上角坐标[this.getX() + 60,this.getY()]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//右下角坐标 [this.getX() + 60,this.getY() + 40]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 600
											&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
										return true;
									}
								
								//如果敌人坦克是右/左
								//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
									//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//右上角坐标[this.getX(),this.getY()]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <=obstacles.get(i). getY() + 45) {
										return true;
									}
									//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 60
											&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
										return true;
									}
						break;
						}
					case 1: {//右
								//如果敌人坦克是上/下
								//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//右上角坐标[this.getX() + 60,this.getY()]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(i).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//右下角坐标 [this.getX() + 60,this.getY() + 40]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(i).getX() + 600
											&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
										return true;
									}
								
								//如果敌人坦克是右/左
									//2当前坦克左上角的坐标[this.getX() + 60,this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//右上角坐标[this.getX(),this.getY()]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() + 60 <= obstacles.get(0).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//右下角坐标 //左上角坐标[this.getX() + 60,this.getY() + 40]
									if (this.getX() + 60 >= obstacles.get(i).getX() && this.getX() +60 <= obstacles.get(0).getX() + 60
											&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= getY() + 45) {
										return true;
									}
						break;
					}
					case 3: {//左
								//如果敌人坦克是上/下
								//1如果敌人坦克是上下 x的范围[enemyTank.getX(),enemyTank.getX() + 40]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 60]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//左上角坐标[this.getX(),this.getY() + 60]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//左下角坐标 [this.getX(),this.getY() + 60]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() + 60 >= obstacles.get(i).getY() && this.getY() + 60 <= obstacles.get(i).getY() + 45) {
										return true;
									}
								
								//如果敌人坦克是右/左
								//1如果敌人坦克是右/左 x的范围[enemyTank.getX(),enemyTank.getX() + 60]
								//					y的范围[enemyTank.getY(),enemyTank.getY() + 40]
									//2当前坦克左上角的坐标[this.getX(),this.getY()]
									//重叠顾名思义就是坐标进入敌人坦克坐标里面
									
									//左上角坐标[this.getX(),this.getY() + 60]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() >= obstacles.get(i).getY() && this.getY() <= obstacles.get(i).getY() + 45) {
										return true;
									}
									//左下角坐标 [this.getX(),this.getY() + 40]
									if (this.getX() >= obstacles.get(i).getX() && this.getX() <= obstacles.get(i).getX() + 600
											&& this.getY() + 40 >= obstacles.get(i).getY() && this.getY() + 40 <= obstacles.get(i).getY() + 45) {
										return true;
									}
						}
						break;
					}
			}
			return false;
		}
}
