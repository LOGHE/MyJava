/**
 * 
 */
package com.hspdu.mhl.view;

import java.util.List;

import com.alibaba.druid.sql.visitor.functions.If;
import com.hspdu.mhl.domain.Bill;
import com.hspdu.mhl.domain.DiningTable;
import com.hspdu.mhl.domain.Employee;
import com.hspdu.mhl.domain.Menu;
import com.hspdu.mhl.domain.MultiTableBean;
import com.hspdu.mhl.service.*;
import com.hspdu.mhl.utils.Utility;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class MHLView {
	private boolean loop = true;//是否退出
	private String key = "";//接收用户的选择
	//定义EmployeeService对象
	private EmployeeService employeeService = new EmployeeService();
	private DiningTableService diningTableService = new DiningTableService();
	private MenuService menuService = new MenuService();
	//定义billService属性
	private BillService billService = new BillService();
	public static void main(String[] args) {
		new MHLView() .mainMenu();
	}
		
	public void mainMenu() {
		while (loop) {
		System.out.println("========================满汉楼=================");
		System.out.println("\t\t 1 登录满汉楼");
		System.out.println("\t\t 2 退出满汉楼");
		System.out.println("请输入你的选择:");
		key = Utility.readString(1);
		switch (key) {
		case "1":
			System.out.println("输入员工号");
			String empId = Utility.readString(50);
			System.out.println("输入员工密码：");
			String pwd = Utility.readString(50);
			//到数据库去判断
			Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
			if (employee!=null) {//说明返回一个员工
				System.out.println("==================登录成功[" + employee.getName() + "]==================");
				System.out.println("显示二级菜单");
				while (loop) {
					System.out.println();
					System.out.println("==============满汉楼二级菜单=============");
					System.out.println("\t\t1 显示餐桌");
					System.out.println("\t\t2 预定餐桌");
					System.out.println("\t\t3 显示所有菜品");
					System.out.println("\t\t4 点菜服务");
					System.out.println("\t\t5 查看账单");
					System.out.println("\t\t6 结账");
					System.out.println("\t\t9 退出满汉楼");
					System.out.println("请输入你的选择:");
					key = Utility.readString(1);
					switch (key) {
					case "1":
						listDiningTable();
						break;
					case "2":
						oederDiningTable();
						break;
					case "3":
						listMenuTable();
						break;
					case "4":
						orderMenu();
						break;
					case "5":
						lisBill();
						break;
					case "6":
						payBill();
						break;
					case "9":
						System.out.println("\t\t9 退出满汉楼");
						loop = false;
						break;
					default:
						break;
					}
				}
			}else {
				System.out.println("登录失败");
			}
			break;
		case "2":
			System.out.println("退出满汉楼");
			loop = false;
			break;
		default:
			System.out.println("你输入有误请重新输入");
			break;
		}
		System.out.println("\t\t 1 登录满汉楼");
		System.out.println("\t\t 1 登录满汉楼");
		System.out.println("\t\t 1 登录满汉楼");
		System.out.println("\t\t 1 登录满汉楼");
		System.out.println("\t\t 1 登录满汉楼");
		}
		System.out.println("你退出了满汉楼");
	}
	//显示所有餐桌
	public void listDiningTable() {
		List<DiningTable> list = diningTableService.list();
		System.out.println("餐桌编号\t\t\t餐桌状态" );
		for (DiningTable diningTable : list) {
			System.out.println(diningTable);
		}
		System.out.println("===========显示完毕===========");
	}
	//预定餐桌
	public void oederDiningTable() {
		System.out.println("=========预定餐桌==========");
		System.out.println("请选择要预定的餐桌编号（-1）退出");
		int orderId = Utility.readInt();
		if (orderId == -1) {
			System.out.println("==========取消预定餐桌=======");
			return;
		}
		char key = Utility.readConfirmSelection();
		if (key=='Y') {
			//根据orderId来判断餐桌的空闲状态
			DiningTable diningTable = diningTableService.geDiningTableById(orderId);
			if (diningTable==null) {
				System.out.println("你预定的餐桌不存在");
				return;
			}
			//判断该餐桌状态是否为“空”
			if (!("空".equals(diningTable.getState()))) {//取反说明当前不为空处于预定状态
				System.out.println("你预定的餐桌已存在");
				return;
			}
			//这时说明库真的预定了
			System.out.println("输入预定人名字");
			String orderName = Utility.readString(50);
			System.out.println("预定人电话号码");
			String orderTel = Utility.readString(50);
			boolean orderDiningTable = diningTableService.orderDiningTable(orderId, orderName, orderTel);
			if (orderDiningTable) {
				System.out.println("预定成功");
			}else {
				System.out.println("预定失败");
			}
		}else {
			System.out.println("==========取消预定餐桌=======");
		}
	}
	//显示所有菜品
	public void listMenuTable() {
		List<Menu> list = menuService.list();
		System.out.println("菜品编号\t\t菜品名字\t\t种类\t\t价格" );
		for (Menu menu : list) {
			System.out.println(menu);
		}
		System.out.println("显示完毕");
	}
	//完成点餐
	public void orderMenu() {
		System.out.println("===========点餐服务==========");
		System.out.println("请输入点餐桌号(-1)退出");
		int orderDiningTableId = Utility.readInt();
		if (orderDiningTableId == -1) {
			System.out.println("==========取消点餐=======");
			return;
		}
		System.out.println("请输入点餐的菜品号(-1)退出");
		int orderMenuId = Utility.readInt();
		if (orderMenuId == -1) {
			System.out.println("==========取消点餐=======");
			return;
		}
		System.out.println("请输入点餐的菜品数量(-1)退出");
		int orderMenuNums = Utility.readInt();
		if (orderMenuNums == -1) {
			System.out.println("==========取消点餐=======");
			return;
		}
		//验证餐桌号是否存在
		DiningTable diningTable = diningTableService.geDiningTableById(orderDiningTableId);
	if (diningTable == null) {
		System.out.println("=============餐桌号不存在=============");
		return;
	}
	//验证菜品是否存在
	Menu menu = menuService.getMenuById(orderMenuId);
	if (menu == null) {
		System.out.println("该菜品不存在");
		return;
	}
	//点餐
	if (billService.orderMenu(orderMenuId, orderMenuNums, orderDiningTableId)) {
		System.out.println("点餐成功");
	}else {
		System.out.println("点餐失败");
	}
	
	}
	//显示所有菜单
	public void lisBill() {
		List<MultiTableBean> list = billService.list2();
		System.out.println("编号\t菜品号\t菜品量\t金额\t桌号\t日期\t\t\t状态\t菜品名\t\t单价");
		for (MultiTableBean multiTableBean : list) {
			System.out.println(multiTableBean);
		}
	}
	//结账
	public void payBill() {
		System.out.println("===============结账===============");
		System.out.println("请选择要结账的餐桌编号(-1退出)");
		int diningTableId = Utility.readInt();
		if (diningTableId == -1) {
			System.out.println("取消结账");
			return;//return表示结束当前方法
		}
		//验证餐桌是否存在
		DiningTable diningTable = diningTableService.geDiningTableById(diningTableId);
		if (diningTable==null) {
			System.out.println("============该餐桌不存在=========");
			return;
		}
		//验证是否有需要结账的账单
		if (!billService.hasPayBillByDiningTableId(diningTableId)) {
			System.out.println("没有要结账的业务");
			return;
		}
		
		System.out.println("请选择结账方式(现金/支付宝/微信)：回车便是退出");
		String payMode = Utility.readString(20,"");//如果回车返回""
		if ("".equals(payMode)) {
			System.out.println("取消结账");
			return;
		}
		char key = Utility.readConfirmSelection();
		if (key=='Y') {//表示真的结账
			if (billService.payBill(diningTableId, payMode)) {
				System.out.println("完成结账");
			}else {
				System.out.println("结账失败");
			}
			
		}else {
			System.out.println("取消结账");
		}
	
	}
}
