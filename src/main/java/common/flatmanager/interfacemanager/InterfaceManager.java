package common.flatmanager.interfacemanager;

import org.openqa.selenium.By;

import utils.FileUtil;
import utils.Util;

/**
 * 接口管理页面
 * @author dujianxiao
 *
 */
public class InterfaceManager extends InterfaceManagerWebElement {
	String url = FileUtil.getValue("grid.properties", "interfaceUrl");
	String broswer = FileUtil.getValue("grid.properties", "interfaceBroswer");
	/**
	 * 初始化
	 */
	public void init(){
		Util util = new Util();
        util.login("chrome");
		//util.gridLogin(url,broswer);
	}


	/**
	 * 查询结果某行某列的值
	 * @param row      行号
	 * @param column   1序号、2项目、3模块、4url、5请求方式、6参数、7状态、8更新时间、9执行时间
	 * @return
	 */
	public String getValue(int row,int column){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		waitForLoad(By.xpath("//*[@id=\"infolist\"]/tbody/tr[" + row + "]/td[" + column + "]"));
		return driver.findElement(By.xpath("//*[@id=\"infolist\"]/tbody/tr[" + row + "]/td[" + column + "]")).getText().trim();
	}



}