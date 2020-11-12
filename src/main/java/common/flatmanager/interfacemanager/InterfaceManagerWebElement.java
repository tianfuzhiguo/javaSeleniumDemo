package common.flatmanager.interfacemanager;

import org.openqa.selenium.By;

/**
 * 接口管理页面元素
 * @author dujianxiao
 *
 */
public class InterfaceManagerWebElement extends InterfaceManagerData {
      public By 项目 = By.id("project");
      public By 模块 = By.id("module");
      public By 状态 = By.id("status");
      public By 查询条件 = By.id("keyword");
      public By 查询 = By.id("querybtn");
}