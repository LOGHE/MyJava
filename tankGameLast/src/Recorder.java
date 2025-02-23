/**
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *该类用于纪律相关信息和文件交互
 */
public class Recorder {
	//定义变量 记录我方击毁敌人坦克数量
	private static int allEneymTankNum = 0;
	//定义IO对象准备写数据到文件中
	private static BufferedReader br = null;
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private static String recordFile = "D:\\IT\\eclipse01\\tankGame\\myRecord.txt";
	//定义一个Node的Vector用于保存敌人信息的node
	static Vector<Node> nodes = new Vector<>();
	//定义Vector指向MyPanel对象敌人坦克Vector
	private static Vector<EnemyTank> enemyTanks = null;
	public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
		Recorder.enemyTanks = enemyTanks;
	}
	
	//添加一个方法用于读取recordFile 恢复相关战斗信息
	public static Vector<Node> getNodesAndAllEnemyTankRec() {
		  try {
			 br = new BufferedReader(new FileReader(recordFile));
			allEneymTankNum = Integer.parseInt(br.readLine());
			//循环读取文件 生成node集合
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] xyd = line.split(",");
				Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
				nodes.add(node);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	  
	  return nodes;
	}
	
	//添加一个方法当游戏退出时 将战绩保存到 recordFile
	//对keepRecord进行升级可以保存敌人坦克的坐标和方向
	public static void keepRecord() {//写入
		
		try {
			fw = new FileWriter(recordFile);
			bw = new BufferedWriter(fw);
			bw.write(allEneymTankNum + "\r\n");
			//遍历敌人坦克Vector 然后根据情况保存即可
			//定义一个属性通过setXXX得到敌人坦克的Vector
			for (int i = 0; i < enemyTanks.size(); i++) {
				//取出敌人
				EnemyTank enemyTank = enemyTanks.get(i);
				if (enemyTank.isLive) {
					//保存gai坦克
					String record = enemyTank.getX() + "," + enemyTank.getY() + "," + enemyTank.getDirection();
					//写入文件
					bw.write(record + "\r\n");
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	public static int getAllEnemTank() {
		return allEneymTankNum;
	}
	public static void setAllEnemyTankNum(int allEnemyTankNum) {
		Recorder.allEneymTankNum = allEnemyTankNum;
	}
	//当我方坦克击毁敌方坦克时allEneymTankNum++
	public static void addAllEnemyTankNum() {
		Recorder.allEneymTankNum++;
	}

	public static String getRecordFile() {
		return recordFile;
	}
}
