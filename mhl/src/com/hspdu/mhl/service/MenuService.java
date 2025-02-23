/**
 * 
 */
package com.hspdu.mhl.service;

import java.util.List;

import com.hspdu.mhl.dao.MenuDAO;
import com.hspdu.mhl.domain.Menu;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *完成对menu表的各种操作（调用MenuDAO）
 *
 */
public class MenuService {
//定义MenuDAO属性
	private MenuDAO menuDAO = new MenuDAO();
	//返回所有菜单
	public List<Menu> list() {
		return menuDAO.queryMulti("select * from menu", Menu.class);
	}
	//需要方法根据id返回menu对象
	public Menu getMenuById(int id) {
		return menuDAO.querySingle("select * from menu where id =?", Menu.class, id);
		
	}
}
