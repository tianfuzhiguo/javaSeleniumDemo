package test.flatmanager.interfacemanager;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import common.flatmanager.interfacemanager.UserManager;

/**
 * �û��������û���ѯ
 * @author dujianxiao
 */
public class test1 extends UserManager {

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		init();
	}

	@Test
	public void testEms022002() throws Exception {
		selValue(�Ƿ�����, ����);
		selValue(�Ƿ����, �����);
		selValue(״̬, ����);
		inputValue(��ѯ����, ��������);
		click(��ѯ);
		Assert.assertTrue(getValue(1, 4).equals(��������));
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		close();
	}
}