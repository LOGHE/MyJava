/**
 * 
 */
package com.hspdu.mhl.domain;

import java.time.LocalDateTime;

/**
 * @author 羅寿合(ラ　ジュゴウ)
 *这是一个javabean 可以和多张表就行对应
 */
public class MultiTableBean {
	private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private Double money;
    private Integer diningTableId;
    private LocalDateTime billDate;
    private String state;
    //增加一个来自Menu表的name
    private String name ;
    //增加一个来自Menu表的price
    private Double price;
	public MultiTableBean() {
		super();
	}
	
	public MultiTableBean(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId,
			LocalDateTime billDate, String state, String name,Double price) {
		super();
		this.id = id;
		this.billId = billId;
		this.menuId = menuId;
		this.nums = nums;
		this.money = money;
		this.diningTableId = diningTableId;
		this.billDate = billDate;
		this.state = state;
		this.name = name;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getDiningTableId() {
		return diningTableId;
	}
	public void setDiningTableId(Integer diningTableId) {
		this.diningTableId = diningTableId;
	}
	public LocalDateTime getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDateTime billDate) {
		this.billDate = billDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "\t" + menuId + "\t"  + nums + "\t" + money + "\t" + diningTableId + "\t" + billDate + "\t" + state +"\t" + name + "\t\t" +price ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
