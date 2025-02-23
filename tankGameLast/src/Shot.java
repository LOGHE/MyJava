/**
 * 
 */

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class Shot implements Runnable {
    int x;//子弹左边 为了和坦克同步
    int y;
    int direct = 0;//方向
    int speed = 12;//速度
   boolean isLive = true;//子弹是否存活
   
	public Shot(int x, int y, int direct) {
	super();
	this.x = x;
	this.y = y;
	this.direct = direct;
}
	@Override
	public void run() {//设计行为
		while (true) {
			//休眠50毫秒
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch (direct){
			case 0: {//上
				y-= speed;
				
				break;
			}
			case 1://右
				x += speed;
				break;
			case 2://xia
				y += speed;
				break;
			case 3://zuo
				x -= speed;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + direct);
			}
			//当子弹碰到障碍物销毁
            if (x >= 200 && x <= 600 && y >= 500 && y<= 20) {
            	isLive = false;
                break;
			}
			//当子弹移动到面板的边界时，就应该销毁（把启动的子弹的线程销毁)
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
                isLive = false;
                break;
            }
            
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

}
