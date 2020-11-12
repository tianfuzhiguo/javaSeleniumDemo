package common.flatmanager.taskmanager;

import org.openqa.selenium.By;
import utils.FileUtil;
import utils.Util;

/**
 * 任务管理页面
 * @author dujianxiao
 *
 */
public class TaskManager extends TaskManagerWebElement {
	/**
	 * 初始化

	public void init(){
		Util util = new Util();
		util.openBrower();
		util.login();
        super.click(By.id("navbar_hide"));
		super.click(By.className("l15"));
	}
	 */

	String url = FileUtil.getValue("grid.properties", "taskUrl");
	String broswer = FileUtil.getValue("grid.properties", "taskBroswer");
	/**
	 * 初始化
	 */
	public void init(){
		Util util = new Util();
		util.login("chrome");
		//util.gridLogin(url,broswer);
		super.click(By.id("navbar_hide"));
		super.click(By.className("l15"));
	}

	/**
	 * 查询结果某行某列的值
	 * @param row      行号
	 * @param column   1序号、2任务名称、3接口总数、4成功、5失败、6忽略、7成功率、8更新时间、9定时任务
	 * @return
	 */
	public String getValue(int row,int column){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		waitForLoad(By.xpath("//*[@id='content']/div/div/div/div[2]/div[2]/table/tbody/tr[" + row + "]/td[" + column + "]"));
		return driver.findElement(By.xpath("//*[@id='content']/div/div/div/div[2]/div[2]/table/tbody/tr[" + row + "]/td[" + column + "]")).getText().trim();
	}
}