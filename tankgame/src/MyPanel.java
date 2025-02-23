/**
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Path2D;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
//为了让MyPanel不停重绘子弹需要将MyPanel做成线程实现Runnable接口
public class MyPanel extends JPanel implements KeyListener,Runnable{
	Hero hero = null;
	//定义三个敌方坦克
	Vector<EnemyTank> enemyTanks =new Vector<>();
	int enemyTankSize = 6;
	//定义vector存放炸弹
	//当我们的子弹击中敌人时就加入一个Bomb对象
	Vector<Bomb> bombs = new Vector<>();
	
	//定义三张炸弹图片用于显示爆炸效果
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	Image gameOver = null;
	Image gameOver2 = null;
	
	public MyPanel() {
		this.hero = new Hero(500, 500);
		bombs.add(new Bomb(500, 500));
		//初始化敌人坦克
		for (int i = 0; i < enemyTankSize; i++) {
			EnemyTank enemyTank = new EnemyTank((100 *(i+1)), 0);
			// ***** 将enemyTanks设置给enemyTank
			enemyTank.setEnemyTanks(enemyTanks);//重要为了不发生碰撞 给enemyTanks集合赋值
			
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
		//初始化图片对象
		// 指定图像文件的路径
        String imagePath = "Z:\\pro3\\tankgame\\bomb01.gif"; // 或者使用 "D:\\IT\\eclipse01\\chapter19\\bomb_1.gif"
        String imagePath2 = "Z:\\pro3\\tankgame\\bomb02.gif";
        String imagePath3 = "Z:\\pro3\\tankgame\\bomb03.gif";
        String overimage = "Z:\\pro3\\tankgame\\gameover.png";
        String overimage2 = "Z:\\pro3\\tankgame\\gameover.png";
        // 加载图像
		image1 = Toolkit.getDefaultToolkit().getImage(imagePath);
		image2 = Toolkit.getDefaultToolkit().getImage(imagePath2);
		image3 = Toolkit.getDefaultToolkit().getImage(imagePath3);
		gameOver = Toolkit.getDefaultToolkit().getImage(overimage);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        if(hero != null && hero.isLive) {//hero.isLive被击中就不画了直接爆炸
            //画出自己坦克-封装方法
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        }

        //画出hero射击的子弹
		/*
		 * if (hero.shot != null && hero.shot.isLive == true) {
		 * g.draw3DRect(hero.shot.x, hero.shot.y, 5, 5, false);
		 * 
		 * }
		 */
       
        //将hero的子弹集合 shots ,遍历取出绘制
        for(int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
            	g.setColor(Color.yellow); // 设置子弹颜色
                drawStar(g, shot.x, shot.y, 10); // 绘制实心的五角星，半径为10
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
        if (!hero.isLive || enemyTanks.size() == 0) {
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        	if (gameOver != null) {
                // 获取图片的宽度和高度
                int imgWidth = gameOver.getWidth(this);
                int imgHeight = gameOver.getHeight(this);
                
                // 计算图片绘制位置使其居中
                int x = (getWidth() - imgWidth) / 2;
                int y = (getHeight() - imgHeight) / 2;

                // 绘制图片
                g.drawImage(gameOver, x, y, this);
            }
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
                //创建Bomb对象，加入到bombs集合
                Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                bombs.add(bomb);
            }
            break;
			
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
				Shot shot = enemyTank.shots.get(j);
				//判断子弹是否击中我方坦克
				if (hero.isLive && shot.isLive) {
					hitTank(shot, hero);
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            hero.setDirection(0);//
            //修改坦克的坐标 y -= 1 if控制我方坦克不出边界
            if (hero.getY() > 0) {
            	hero.moveUp();
			}
            
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D键, 向右
        	hero.setDirection(1);
        	if (hero.getX() + 75 < 1000 ) {
        		hero.moveRight();
			}

        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S键
        	hero.setDirection(2);
        	if (hero.getY() + 100 < 750) {
        		hero.moveDown();
			}
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A键
        	hero.setDirection(3);
        	if (hero. getX() > 0) {
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
            hitTank();
            hitHero();
            this.repaint();
        }
			
	}

	
}
