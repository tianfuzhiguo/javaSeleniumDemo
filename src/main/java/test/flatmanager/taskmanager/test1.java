package test.flatmanager.taskmanager;

import common.flatmanager.taskmanager.TaskManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 任务管理－－任务查询
 * @author dujianxiao
 */
public class test1 extends TaskManager {

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		init();
	}

	@Test
	public void test1() throws Exception {
		inputValue(查询条件, 任务名称);
		click(查询);
		Assert.assertEquals(super.getValue(1,2),"あいしてる");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		close();
	}
}