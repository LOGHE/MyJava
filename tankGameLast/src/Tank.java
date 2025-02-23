/**
 * 
 */

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class Tank {
	private int x;
	private int y;
	private int direction = 0;
	private int speed = 5;
	boolean isLive = true;
	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	//上右下左移动方法
	public void moveUp() {
	y -= speed;
	}
	public void moveRight() {
	x += speed;
	}
	public void moveDown() {
	y += speed;
	}
	public void moveLeft() {
	x -= speed;
	}

}
