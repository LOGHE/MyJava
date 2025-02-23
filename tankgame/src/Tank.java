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
/*
 int tanka = Integer.parseInt(request.getParameter("tankaa"));//formから来た値を受け取りnameの中に格納する
		int suryo = Integer.parseInt(request.getParameter("suryo"));
		int sum = tanka * suryo;
		PrintWriter writer = response.getWriter();
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>kada0801</title>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println("KINGAKU" + sum);
		writer.println("</body>");
		writer.println("</html>");
		
		<form action = "kada0801/kekka" method="get">
		<p>単価<input type="text" name="tankaa"/></p>
		<p>数量<input type="text" name="suryo"/></p>
		<input type="submit" value="計算"/>
	</form>
 
 */
}
