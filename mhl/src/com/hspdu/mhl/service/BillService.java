/**
 * 
 */
package com.hspdu.mhl.service;

import java.util.List;
import java.util.UUID;

import com.hspdu.mhl.dao.BillDAO;
import com.hspdu.mhl.dao.MultiTableDAO;
import com.hspdu.mhl.domain.Bill;
import com.hspdu.mhl.domain.Menu;
import com.hspdu.mhl.domain.MultiTableBean;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *处理和账单相关的业务逻辑
 */
public class BillService {
	private BillDAO billDAO = new BillDAO();
	//定义MenuService来获取对应表的属性
	private MenuService menuService = new MenuService();
	//定义DiningTableService属性
	private DiningTableService diningTableService = new DiningTableService();
	MultiTableDAO multiTableDAO = new MultiTableDAO();
	//编写点单方法
	//1生成账单
	//2需要更新对应餐桌的状态
	public boolean orderMenu(int menuId,int nums,int diningTableId) {
		//生成一个账单号UUID
		String billId = UUID.randomUUID().toString();
		
		//将账单生成到bill表
	int update = billDAO.update("insert into bill value(null,?,?,?,?,?,now(),'未结账')", billId,menuId,nums,
				menuService.getMenuById(menuId).getPrice()*nums,diningTableId);
		if (update <= 0) {
			return false;
		}
		//需要更新对应餐桌的状态
		return diningTableService.updateDiningTableState(diningTableId, "就餐中");
	}
	//返回所有账单信息提供给view调用
	public List<Bill> list() {
		return billDAO.queryMulti("select * from bill", Bill.class);
	}
	//返回所有账单信息并且有name 提供给view调用
	public List<MultiTableBean> list2() {
		return multiTableDAO.queryMulti("select bill.* , menu.name,menu.price from bill,menu where bill.menuId = menu.id", MultiTableBean.class);
	}
	//查看是否有未结账的桌子
	//查看某个餐桌是否有未结账的账单
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill =
                billDAO.querySingle("SELECT * FROM bill WHERE diningTableId=? AND state = '未结账' LIMIT 0, 1", Bill.class, diningTableId);
        return bill != null;
    }
	//完成结账
	public boolean payBill(int diningTableId,String payMode) {
		//1修改bill表
		int update = billDAO.update("update bill set state=? where diningTableId=? and state='未结账'", payMode,diningTableId);
		if (update <= 0) {//修改失败
			return false;
		}
		//修改diningTable
		//注意不要在这里操作而应该调用DiningTableService 方法 各司其职
		if (diningTableService.updateDiningTableToFree(diningTableId, "空")) {
			return true;
		}
		return false;
	}
}
