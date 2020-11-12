package test.flatmanager.interfacemanager;

import common.flatmanager.interfacemanager.InterfaceManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Util;


/**
 * 接口管理－－接口查询
 * @author dujianxiao
 */
public class test1 extends InterfaceManager {

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		init();
	}

	@Test
	public void test1() throws Exception {
		    selValue(项目,ems);
		    selValue(模块, ems对标分析);
	    	selValue(状态, 全部状态);
		    inputValue(查询条件, 接口名称);
		    click(查询);
		    if(super.getValue(1,2)!="123") {
			    Util.screen("test.flatmanager.interfacemanager.test2");
		    }
			Assert.assertEquals(super.getValue(1,2),"123");

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		close();
	}
}