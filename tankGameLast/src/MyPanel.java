/**
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
//为了让MyPanel不停重绘子弹需要将MyPanel做成线程实现Runnable接口
public class MyPanel extends JPanel implements KeyListener,Runnable{
	Hero hero = null;
	//定义一个node对象的Vector用于恢复敌人的坐标和方向恢复存档
	Vector<Node> node = new Vector<>();
	//加入音乐爆炸
			AePlayWave aePlayWave = new AePlayWave("D:\\IT\\eclipse01\\tankGame\\boom.wav");
	//定义三个敌方坦克
	Vector<EnemyTank> enemyTanks =new Vector<>();
	int enemyTankSize = 7;
	//定义vector存放炸弹
	//当我们的子弹击中敌人时就加入一个Bomb对象
	Vector<Bomb> bombs = new Vector<>();
	//定义三张炸弹图片用于显示爆炸效果
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	BufferedImage gameOver1 = null;
	BufferedImage win1 = null;
	BufferedImage gameOver = null;
	BufferedImage win = null;
	
	//定义障碍物显示
	Vector<Obstacle> obstacles = new Vector<>();
	//可能写在方法里 就是把MyPanel的obstacles赋值给EnemyTank的obstacles
	//图腾
	Map<Integer, Obstacle> oMap = new HashMap<>();
	public MyPanel(String key) {
		//判断文件是否存在不存在只能开启新游戏
		File file = new File(Recorder.getRecordFile());
		if (file.exists()) {
			node =  Recorder.getNodesAndAllEnemyTankRec();
		}else {
			System.out.println("文件不存在，只能开始新游戏");
			key = "1";
		}
		//将MyPanel对象的enemyTanks设置给Record的enemyTanks
		Recorder.setEnemyTanks(enemyTanks);
		this.hero = new Hero(500, 550);
		
		//初始化障碍物
		//this.obstacle = new Obstacle(200, 500);
			Obstacle obstacle = new Obstacle(200, 500);
			obstacles.add(obstacle);
			Obstacle obstacle1 = new Obstacle(200, 300);
			obstacles.add(obstacle1);
			//初始化水
			Obstacle wObstacle2 = new Obstacle(200, 100);
			obstacles.add(wObstacle2);
			//五角星图腾
			Obstacle greenObstacle1 = new Obstacle(500, 725);
			obstacles.add(greenObstacle1);
			oMap.put(0, greenObstacle1);
			//砖块三个
			Obstacle brickObstacle1 = new Obstacle(455, 700);
			obstacles.add(brickObstacle1);
			oMap.put(1, brickObstacle1);
			Obstacle brickObstacle2 = new Obstacle(530, 700);
			obstacles.add(brickObstacle2);
			oMap.put(2, brickObstacle2);
			Obstacle brickObstacle3 = new Obstacle(455, 680);
			obstacles.add(brickObstacle3);
			oMap.put(3, brickObstacle3);
		
		switch (key) {
		case "1": {
			//初始化敌人坦克
			for (int i = 0; i < enemyTankSize; i++) {
				EnemyTank enemyTank = new EnemyTank((100 *(i+1)), 0);
				// ***** 将enemyTanks设置给enemyTank
				enemyTank.setEnemyTanks(enemyTanks);//重要为了不发生碰撞 给enemyTanks集合赋值
				enemyTank.setObstacles(obstacles);
				enemyTank.setDirection(2);//敌方坦克的炮管方向
				new Thread(enemyTank).start();
				//给敌方坦克加入一颗子弹多颗
				Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
				//加入到enemyTank的对象 enemyTanks
				enemyTank.shots.add(shot);
				//启动shot对象
				new Thread(shot).start();
				enemyTanks.add(enemyTank);//加入数组里
		}
			Recorder.setAllEnemyTankNum(0);
			break;
		}
		case "2" :{//恢复敌方坦克
			
			
			//初始化敌人坦克
			for (int i = 0; i < node.size(); i++) {
				
				Node node1 = node.get(i);
				EnemyTank enemyTank = new EnemyTank(node1.getX(),node1.getY());
				// ***** 将enemyTanks设置给enemyTank
				enemyTank.setEnemyTanks(enemyTanks);//重要为了不发生碰撞 给enemyTanks集合赋值
				enemyTank.setObstacles(obstacles);
				enemyTanks.add(enemyTank);//加入数组里
				
				enemyTank.setDirection(node1.getDirection());//恢复敌方坦克的炮管方向
				new Thread(enemyTank).start();
				//给敌方坦克加入一颗子弹多颗
				Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
				//加入到enemyTank的对象 enemyTanks
				enemyTank.shots.add(shot);
				//启动shot对象
				new Thread(shot).start();
				
		}
			break;
		}
		default:
			System.out.println("你的输入有问题");
		}
		
		//加入音乐
		new AePlayWave("D:\\IT\\eclipse01\\tankGame\\111.wav").start();
		
		//初始化图片对象
		// 指定图像文件的路径
        String imagePath = "D:/IT/eclipse01/tankGame/bomb_1.gif"; // 或者使用 "D:\\IT\\eclipse01\\chapter19\\bomb_1.gif"
        String imagePath2 = "D:/IT/eclipse01/tankGame/bomb_2.gif";
        String imagePath3 = "D:/IT/eclipse01/tankGame/bomb_3.gif";
        String overimage = "D:/IT/eclipse01/tankGame/gameover.jpg";
        
		String	tutensString = "D:/IT/eclipse01/tankGame/win.jpg";
		
        // 加载图像
		image1 = Toolkit.getDefaultToolkit().getImage(imagePath);
		image2 = Toolkit.getDefaultToolkit().getImage(imagePath2);
		image3 = Toolkit.getDefaultToolkit().getImage(imagePath3);
		
		try {
			gameOver1 = ImageIO.read(new File(overimage));
			win1 = ImageIO.read(new File(tutensString));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} // 替换为你的图片路径
		
		 // 调整图像大小
		gameOver = resizeImage(gameOver1, 1000, 750);
		win = resizeImage(win1, 1000, 750);
	}
//编写方法 显示我方坦克击毁敌方坦克的信息
	public void showInfo(Graphics g) {
		//画出玩家的总成绩
		g.setColor(Color.BLACK);
		Font font = new Font("宋体",Font.BOLD,25);
		g.setFont(font);
		g.drawString("あなたが破壊した戦車",1020,30);
		drawTank(1020, 60, g,0, 0);//画出一个敌方坦克
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnemTank() + "" + "台", 1080, 100);
		
		g.drawString("あなたの復活チャンス",1020,150);
		drawTank(1020, 170, g,0, 1);//画出一个我方坦克
		g.setColor(Color.black);
		g.drawString(hero.live + "" + "回", 1080, 200);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色
        showInfo(g);
        if(hero != null && hero.live != 0) {//hero.isLive被击中就不画了直接爆炸
            //画出自己坦克-封装方法
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        }
        //画出障碍物
        for (int i = 0; i < obstacles.size(); i++) {
        	
			Obstacle obstacle = obstacles.get(i);
			
			if (i == 2) {
				 //画出水
		        drawObstacle(obstacle.getX(), obstacle.getY(), g, 1);
			}
			else if (i == 0 || i == 1){//画铁块
				drawObstacle(obstacle.getX(), obstacle.getY(), g, 0);
			}
		}
      //画图腾
        for (Map.Entry<Integer, Obstacle> oEntry : oMap.entrySet()) {
        	if (oEntry.getValue().isLive()) {
        		if (oEntry.getKey() == 0) {
    				drawObstacle(oEntry.getValue().getX(), oEntry.getValue().getY(), g, 3);
    			}
    			if (oEntry.getKey() == 1  || oEntry.getKey() == 2) {
    				drawObstacle(oEntry.getValue().getX(), oEntry.getValue().getY(), g, 2);
    			}
    			if (oEntry.getKey() == 3) {
    				drawObstacle(oEntry.getValue().getX(), oEntry.getValue().getY(), g, 4);
    			}
			}
			
			
		}
        //将hero的子弹集合 shots ,遍历取出绘制
        for(int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
            	g.setColor(Color.yellow); // 设置子弹颜色
                drawStar(g, shot.x, shot.y, 10); // 绘制实心的五角星，半径为10
            	//g.fillOval(shot.x, shot.y, 10, 10);
            	
            } else {//如果该shot对象已经无效 ,就从shots集合中拿掉
               hero.shots.remove(shot);           
              }
        }

        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为0, 就从bombs 的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

       
        //画出敌人的坦克, 遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector 取出坦克
        	
            EnemyTank enemyTank = enemyTanks.get(i);
           // enemyTank.setDirection((int)(Math.random() * 4));//敌方坦克出来就随机走动
            //判断当前坦克是否还存活
            if (enemyTank.isLive) {//当敌人坦克是存活的，才画出该坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
                //画出 enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) { //isLive == true
                    	g.fillOval(shot.x, shot.y, 10, 10);
                    } else {
                        //从Vector 移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
        //游戏结束
        if (!hero.isLive || !oMap.get(0).isLive()) {
        	//g.drawImage(gameOver, 0, 0, this);
		}
        if (enemyTanks.size() == 0) {
        	g.drawImage(win,0, 0, this);
		}
        
            
		}
        
        

	
	//绘制五角星子弹
	 private void drawStar(Graphics g, int x, int y, int radius) {
	        Graphics2D g2d = (Graphics2D) g;
	        Path2D path = new Path2D.Double();
	        
	        // 五角星的顶点数量
	        int numPoints = 5;
	        // 五角星的外半径和内半径
	        double outerRadius = radius;
	        double innerRadius = outerRadius / 2.5;

	        // 计算每个顶点的位置
	        double angleStep = Math.PI / numPoints;
	        for (int i = 0; i < 2 * numPoints; i++) {
	            double angle = i * angleStep;
	            double r = (i % 2 == 0) ? outerRadius : innerRadius;
	            double px = x + Math.cos(angle) * r;
	            double py = y - Math.sin(angle) * r;
	            if (i == 0) {
	                path.moveTo(px, py);
	            } else {
	                path.lineTo(px, py);
	            }
	        }
	        path.closePath();
	        g2d.fill(path);
	    }
	 //画基地
	 

	   

	 //画障碍物
	 public void drawObstacle(int x,int y ,Graphics g,int type) {
		 Color silver = new Color(192, 192, 192);
		 Color aqua = new Color(0, 191, 255);
		 Color grassGreen = new Color(124, 252, 0);
		switch (type) {
		case 0: {//银色铁块
	        g.setColor(silver);
	        g.fill3DRect(x, y, 600, 40, false);
			break;
		}
		case 1: {//水
			g.setColor(aqua);
	        g.fill3DRect(x, y, 600, 40, false);
			break;
				}
		case 2: {//砖块红色
			g.setColor(grassGreen);
			g.fill3DRect(x, y, 20, 50, false);
			break;
		}
		case 3: {//图腾
			g.setColor(Color.yellow);
			drawStar(g, x, y, 25);
			break;
				}
		case 4: {//砖块红色方向横向
			g.setColor(grassGreen);
			g.fill3DRect(x, y, 95, 20, false);
			break;
		}
		}
	}
	//画坦克的方法

	/**
	 * Description: TODO
	 * @param x 左上角x坐标
	 * @param y左上角y左边
	 * @param g画笔
	 * @param direction 坦克方向上下左右
	 * @param type坦克类型
	 */
	public void drawTank(int x, int y,Graphics g,int direction,int type) {
		switch (type){
		case 0: {//我们的坦克
			g.setColor(Color.cyan);
			break;
		}
		case 1://敌人的坦克
			g.setColor(Color.yellow);
			break;
		}
		switch (direction){
		case 0: {//表示方向上
			g.fill3DRect(x, y, 10, 60, false);
			g.fill3DRect(x+30, y, 10, 60, false);
			g.fill3DRect(x+10, y +10, 20, 40, false);
			g.fillOval(x + 10, y + 20, 20, 20);
			g.drawLine(x + 20, y + 30, x+20, y);
			
			break;
		}
		case 2:{//下
			g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
			g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
			g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
			g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
			g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
			break;
		}
		case 3:{//左
			g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
            g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
            g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
            g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
            g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
            break;
		}
		case 1:{//右
			 g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
             g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
             g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
             g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
             g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
             break;
		}
		default:
			System.out.println("暂时是没有处理");
		}
	}
	//这里因为是多颗子弹集合要把集合放入进去
	public void hitTank() {
		for (int j = 0; j < hero.shots.size(); j++) {
			//判断是否击中
            if (hero.shots.get(j) != null &&hero.shots.get(j).isLive) {//当我方子弹存活
				//遍历敌人所有的坦克
            	for (int i = 0; i < enemyTanks.size(); i++) {
					EnemyTank enemyTank = enemyTanks.get(i);
					hitTank(hero.shots.get(j), enemyTank);
					
				}
			}
		}
	}
	
//判断我方子弹是否击中敌人 
	public  void hitTank(Shot s,Tank enemyTank) {
		//判断s击中坦克
		switch (enemyTank.getDirection()){
		case 0: {//表示方向上
			/*
			 * if (s.x > enemyTank.getX() + 40 && s.x < enemyTank.getX() && s.y >
			 * enemyTank.getY() + 60 && s.y < enemyTank.getY()) { s.isLive = false;
			 * enemyTank.isLive = false; //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
			 * enemyTanks.remove(enemyTank); //创建Bomb对象，加入到bombs集合 Bomb bomb = new
			 * Bomb(enemyTank.getX(), enemyTank.getY()); bombs.add(bomb); } break;
			 */
			
		}
		case 2: {//下
			if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                    && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                s.isLive = false;
                enemyTank.isLive = false;
                
                //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
                enemyTanks.remove(enemyTank);
                //记录一次战绩
                if (enemyTank instanceof EnemyTank) {
                	Recorder.addAllEnemyTankNum();
				}
                //创建Bomb对象，加入到bombs集合
                Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                bombs.add(bomb);
            }
			
            break;
				}
		case 1: {//右
			/*
			 * if (s.x > enemyTank.getX() + 60 && s.x < enemyTank.getX() && s.y >
			 * enemyTank.getY() + 40 && s.y < enemyTank.getY()) { s.isLive = false;
			 * enemyTank.isLive = false; //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
			 * enemyTanks.remove(enemyTank); //创建Bomb对象，加入到bombs集合 Bomb bomb = new
			 * Bomb(enemyTank.getX(), enemyTank.getY()); bombs.add(bomb); } break;
			 */
					
				}
		case 3: {//左
			if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                    && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                s.isLive = false;
                enemyTank.isLive = false;
              //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
                enemyTanks.remove(enemyTank);
              //记录一次战绩
                if (enemyTank instanceof EnemyTank) {
                	Recorder.addAllEnemyTankNum();
				}
                //创建Bomb对象，加入到bombs集合
                Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                bombs.add(bomb);
            }
			
            break;
			
		}
		}
	}
	//判断我方是否击中障碍物
	public void hitObstacle(Shot s,Vector<Obstacle> obstacles,Map<Integer, Obstacle> oMap) {
		//判断我方子弹是否击中障碍物 上面那个障碍物
		for (int i = 0; i < 2; i++) {
			if (s.x > obstacles.get(1).getX() && s.x < obstacles.get(1).getX() +600
	                && s.y > obstacles.get(1).getY() && s.y < obstacles.get(1).getY() +45) {
				s.isLive = false;
				//创建Bomb对象，加入到bombs集合
	            Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
	            bombs.add(bomb);
	            
			}
			//判断我方子弹是否击中障碍物 下面那个障碍物
			if (s.x > obstacles.get(0).getX() && s.x < obstacles.get(0).getX() +600
	                && s.y > obstacles.get(0).getY() && s.y < obstacles.get(0).getY() +45) {
				s.isLive = false;
				//创建Bomb对象，加入到bombs集合
	            Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
	            bombs.add(bomb);
			}
		}
		//判断我方子弹是否击中障碍物 图腾哪里
					//左边模块
		for (Map.Entry<Integer, Obstacle> oEntry : oMap.entrySet()) {
			if (oEntry.getValue().isLive()) {
				if (oEntry.getKey() == 0) {//五角星x,y表示圆心
					if (s.x > oEntry.getValue().getX() -25 && s.x < oEntry.getValue().getX() + 25
							&& s.y > oEntry.getValue().getY() -25 && s.y < oEntry.getValue().getY() + 25) {
						s.isLive = false;
						//创建Bomb对象，加入到bombs集合
						Bomb bomb = new Bomb(s.getX() -40, s.getY()-40);
						bombs.add(bomb);
						aePlayWave.start();
						oEntry.getValue().setLive(false);
						oMap.remove(oEntry);
						hero.live = 0;
						hero.isLive = false;
						Bomb bomb1 = new Bomb(hero.getX() ,hero.getY());
						bombs.add(bomb1);
					}
				}
				if (oEntry.getKey() == 1) {//左
					if (s.x > oEntry.getValue().getX() && s.x < oEntry.getValue().getX() +20
							&& s.y > oEntry.getValue().getY() - 20 && s.y < oEntry.getValue().getY() + 100) {
						s.isLive = false;
						//创建Bomb对象，加入到bombs集合
						Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
						bombs.add(bomb);
						oEntry.getValue().setLive(false);
						oMap.remove(oEntry);
					}
				}
				if (oEntry.getKey() == 2) {//右
					
					//判断我方子弹是否击中障碍物 右边模块
					if (s.x > oEntry.getValue().getX() -3&& s.x < oEntry.getValue().getX() + 20
							&& s.y > oEntry.getValue().getY() -20 && s.y < oEntry.getValue().getY() + 100) {
						s.isLive = false;
						//创建Bomb对象，加入到bombs集合
						Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
						bombs.add(bomb);
						oEntry.getValue().setLive(false);
						oMap.remove(oEntry);
					} 
				}
				if (oEntry.getKey() == 3) {//上
					//判断我方子弹是否击中障碍物 上边模块
					if (s.x > oEntry.getValue().getX() && s.x < oEntry.getValue().getX() + 95
							&& s.y > oEntry.getValue().getY() -5&& s.y < oEntry.getValue().getY() + 20) {
						s.isLive = false;
						//创建Bomb对象，加入到bombs集合
						Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
						bombs.add(bomb);
						oEntry.getValue().setLive(false);
						oMap.remove(oEntry);
					}
				}
			}
		}
	}
	
	
	//判断敌方方是否击中障碍物
public void hitObstacle2(Shot s,Vector<Obstacle> obstacles) {
		//判断敌方子弹是否击中障碍物 图腾哪里
		//左边模块
	for (Map.Entry<Integer, Obstacle> oEntry : oMap.entrySet()) {
		if (oEntry.getValue().isLive()) {
			if (oEntry.getKey() == 0) {//五角星x,y表示圆心
				if (s.x > oEntry.getValue().getX() -25 && s.x < oEntry.getValue().getX() + 25
						&& s.y > oEntry.getValue().getY() -25 && s.y < oEntry.getValue().getY() + 25) {
					s.isLive = false;
					//创建Bomb对象，加入到bombs集合
					Bomb bomb = new Bomb(s.getX() -40, s.getY()-40);
					bombs.add(bomb);
					oEntry.getValue().setLive(false);
					oMap.remove(oEntry);
					hero.live = 0;
					hero.isLive = false;
					Bomb bomb1 = new Bomb(hero.getX() ,hero.getY());
					bombs.add(bomb1);
				}
			}
			if (oEntry.getKey() == 1) {//左
				if (s.x > oEntry.getValue().getX() && s.x < oEntry.getValue().getX() +20
						&& s.y > oEntry.getValue().getY() - 20 && s.y < oEntry.getValue().getY() + 100) {
					s.isLive = false;
					//创建Bomb对象，加入到bombs集合
					Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
					bombs.add(bomb);
					oEntry.getValue().setLive(false);
					oMap.remove(oEntry);
				}
			}
			if (oEntry.getKey() == 2) {//右
				
				//判断敌方方子弹是否击中障碍物 右边模块
				if (s.x > oEntry.getValue().getX() -3&& s.x < oEntry.getValue().getX() + 20
						&& s.y > oEntry.getValue().getY() -20 && s.y < oEntry.getValue().getY() + 100) {
					s.isLive = false;
					//创建Bomb对象，加入到bombs集合
					Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
					bombs.add(bomb);
					oEntry.getValue().setLive(false);
					oMap.remove(oEntry);
				} 
			}
			if (oEntry.getKey() == 3) {//上
				//判断敌方方子弹是否击中障碍物 上边模块
				if (s.x > oEntry.getValue().getX() && s.x < oEntry.getValue().getX() + 95
						&& s.y > oEntry.getValue().getY() -5&& s.y < oEntry.getValue().getY() + 20) {
					s.isLive = false;
					//创建Bomb对象，加入到bombs集合
					Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
					bombs.add(bomb);
					oEntry.getValue().setLive(false);
					oMap.remove(oEntry);
				}
			}
		}
	} 
		//判断敌人子弹是否击中障碍物 上面那个障碍物
		for (int i = 0; i < 2; i++) {
			if (s.x > obstacles.get(1).getX() && s.x < obstacles.get(1).getX() +600
	                && s.y > obstacles.get(1).getY() && s.y < obstacles.get(1).getY() +45) {
				s.isLive = false;
				//创建Bomb对象，加入到bombs集合
	            Bomb bomb = new Bomb(s.getX() - 20, s.getY()-25);
	            bombs.add(bomb);
			}
			//判断敌人子弹是否击中障碍物 下面那个障碍物
			if (s.x > obstacles.get(0).getX() && s.x < obstacles.get(0).getX() +600
	                && s.y > obstacles.get(0).getY() && s.y < obstacles.get(0).getY() +45) {
				s.isLive = false;
				//创建Bomb对象，加入到bombs集合
	            Bomb bomb = new Bomb(s.getX() - 20 , s.getY()-25);
	            bombs.add(bomb);
			}
		}
	}
	//判断敌方坦克是否击中我
	public void hitHero() {
		//遍历敌方坦克
		for (int i = 0; i < enemyTanks.size(); i++) {
			EnemyTank enemyTank = enemyTanks.get(i);
			//遍历坦克对象的所有值
			for (int j = 0; j < enemyTank.shots.size(); j++) {
				//取出子弹
				Shot s = enemyTank.shots.get(j);
				//判断子弹是否击中我方坦克
				if (hero.isLive && s.isLive) {
					//hitTank(s, hero);
					
					//判断s击中坦克
					switch (hero.getDirection()){
					case 0: {//表示方向上
						/*
						 * if (s.x > enemyTank.getX() + 40 && s.x < enemyTank.getX() && s.y >
						 * enemyTank.getY() + 60 && s.y < enemyTank.getY()) { s.isLive = false;
						 * enemyTank.isLive = false; //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
						 * enemyTanks.remove(enemyTank); //创建Bomb对象，加入到bombs集合 Bomb bomb = new
						 * Bomb(enemyTank.getX(), enemyTank.getY()); bombs.add(bomb); } break;
						 */
						
					}
					case 2: {//下
						if (s.x > hero.getX() && s.x < hero.getX() + 40
			                    && s.y > hero.getY() && s.y < hero.getY() + 60) {
							s.isLive = false;
			                //记录一次战绩
							hero.live -= 1;
			              //创建Bomb对象，加入到bombs集合
			                Bomb bomb = new Bomb(hero.getX(), hero.getY());
			                bombs.add(bomb);
			                if (hero.live == 0) {
			                	hero.isLive = false;
			                }
			                hero = new Hero(500, 550); // 设置坦克复活位置
			                this.repaint(); // 重绘面板
			                
			                
			            }
						
			            break;
							}
					case 1: {//右
						/*
						 * if (s.x > enemyTank.getX() + 60 && s.x < enemyTank.getX() && s.y >
						 * enemyTank.getY() + 40 && s.y < enemyTank.getY()) { s.isLive = false;
						 * enemyTank.isLive = false; //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
						 * enemyTanks.remove(enemyTank); //创建Bomb对象，加入到bombs集合 Bomb bomb = new
						 * Bomb(enemyTank.getX(), enemyTank.getY()); bombs.add(bomb); } break;
						 */
								
							}
					case 3: {//左
						if (s.x > hero.getX() && s.x < hero.getX() + 60
			                    && s.y > hero.getY() && s.y < hero.getY() + 40) {
							s.isLive = false;
			                //记录一次战绩
							hero.live -= 1;
			              //创建Bomb对象，加入到bombs集合
			                Bomb bomb = new Bomb(hero.getX(), hero.getY());
			                bombs.add(bomb);
			                hero = new Hero(500, 650); // 设置坦克复活位置
			                if (hero.live == 0) {
			                	hero.isLive = false;
			                }
			                this.repaint(); // 重绘面板
			                
			            }
						
			            break;
						
					}
					}
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            hero.setDirection(0);//
            //修改坦克的坐标 y -= 1 if控制我方坦克不出边界
            if (hero.getY() > 0 || !hero.isTouchEneyObstacle()) {
            	hero.moveUp();
			}
            
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D键, 向右
        	hero.setDirection(1);
        	if (hero.getX() + 75 < 1000 || !hero.isTouchEneyObstacle()) {
        		hero.moveRight();
			}

        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S键
        	hero.setDirection(2);
        	if (hero.getY() + 100 < 800 || !hero.isTouchEneyObstacle()) {
        		hero.moveDown();
			}
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A键
        	hero.setDirection(3);
        	if (hero. getX() > 0 || !hero.isTouchEneyObstacle()) {
                hero.moveLeft();
            }
        }

        //如果用户按下的是J,就发射
        if(e.getKeyCode() == KeyEvent.VK_J) {
			/*发射一颗子弹
			 * if (hero.shot == null || !hero.shot.isLive) {//效果为打出的子弹到达边界 消亡后才可以发射新子弹
			 * hero.shotEnemyTank(); }
			 */
            
            //发射多颗子弹
            hero.shotEnemyTank();
            
        }
        //让面板重绘
        this.repaint();
        
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void run() {//每100毫秒重绘区域
		while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			/*
			 * if (!hero.isLive || !oMap.get(0).isLive() || enemyTanks.size() == 0) { try {
			 * Thread.sleep(500); } catch (InterruptedException e) { // TODO 自动生成的 catch 块
			 * e.printStackTrace(); } }
			 */
            hitTank();
            
            hitHero();
            //我方击中障碍物
            for (int i = 0; i < hero.shots.size(); i++) {
            	hitObstacle(hero.shots.get(i), obstacles,oMap);
			}
            //敌方击中障碍物
            for (int i = 0; i < enemyTanks.size(); i++) {
            	EnemyTank enemyTank = enemyTanks.get(i);
				for (int j = 0; j < enemyTanks.get(i).shots.size(); j++) {
					hitObstacle2(enemyTanks.get(i).shots.get(j), obstacles);
				}
			}
            
            this.repaint();
        }
			
	}

	// 调整图像大小的方法
   private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }
}
