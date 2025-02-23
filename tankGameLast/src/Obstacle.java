/**
 * 
 */

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *障碍物
 */
public class Obstacle {
	private int x;
	private int y;
	private boolean isLive = true;
	
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
	public Obstacle(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
}
