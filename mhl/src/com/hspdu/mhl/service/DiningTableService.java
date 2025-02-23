/**
 * 
 */
package com.hspdu.mhl.service;

import java.util.List;

import com.hspdu.mhl.dao.DiningTableDAO;
import com.hspdu.mhl.domain.DiningTable;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *
 */
public class DiningTableService {//业务层
//定义一个diningTable对象
	DiningTableDAO diningTableDAO =	new DiningTableDAO();
	//返回所有餐桌信息
	public List<DiningTable> list() {
		return diningTableDAO.queryMulti("select id,state from diningTable", DiningTable.class);
	}
	//根据id 查询对应的餐桌diningTable对象
	//如果返回null表示餐桌不存在
	public DiningTable geDiningTableById(int id) {
		//吧sql语句放在查询分析器去测试一下
		return diningTableDAO.querySingle("select * from diningTable where id = ?",DiningTable.class,id);
	}
	//如果餐桌可以预定调用方法，对其状态进行更新（包括预定人的名字和电话号码)
	public boolean orderDiningTable(int id,String oederName,String oederTel) {
		int update = diningTableDAO.update("update diningTable set state='已经预定',orderName=?,orderTel=? where id =?", oederName,oederTel,id);
	return update > 0;
	}
	//需要提供一个更新餐桌状态的方法
	public boolean updateDiningTableState(int id,String state) {
		int update = diningTableDAO.update("update diningTable set state =? where id =?", state,id);
		return update >0;
	}
	//将指定的餐桌设置未空闲状态
	public boolean updateDiningTableToFree(int id,String state) {
		int update = diningTableDAO.update("update diningTable set state =?,orderName='',orderTel='' where id =?", state,id);
		return update >0;
	}
}
