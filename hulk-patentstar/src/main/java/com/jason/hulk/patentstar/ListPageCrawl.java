package com.jason.hulk.patentstar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListPageCrawl {

    //本机phantomjs的路径（需要更改）
    private static final String phantomjspath = "/Users/apple/Desktop/devloper/phantomjs/bin/phantomjs";

    /**
     * @param searchContent
     * @return
     * @description 1：账号密码是smollblue/sb123456 ； 2：默认返回检索内容的第一个页面的10条记录，可以根据你自己的需要把输出样式自定义下；
     */
    public static List<String> crawl(String searchContent) {
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjspath);
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);

        try {
            // 让浏览器访问首页
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://www.patentstar.cn");

            System.out.println("current page :" + driver.getCurrentUrl());

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            Thread.sleep(1000L);
//            WebElement pwdLoginbutton = driver.findElement(By.id("bottom_qlogin")).findElement(By.id("switcher_plogin"));
//            pwdLoginbutton.click();
            //获取账号密码输入框并输入账号和密码
            WebElement userNameElement = driver.findElement(By.id("TextBoxAccount"));
            WebElement pwdElement = driver.findElement(By.id("Password"));
            userNameElement.sendKeys("smollblue");
            pwdElement.sendKeys("sb123456");

            //获取登录按钮
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement loginButton = driver.findElement(By.id("ImageButtonLogin"));
            loginButton.click();
            //设置线程休眠时间等待页面加载完成
            Thread.sleep(1000L);

            //获取新页面窗口句柄并跳转，模拟登陆完成
            String windowHandle = driver.getWindowHandle();
            driver.switchTo().window(windowHandle);

            //设置说说详情数据页面的加载时间并跳转
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://www.patentstar.cn/My/SmartQuery.aspx");
            System.out.println("current page :" + driver.getCurrentUrl());

            //获取要抓取的元素,并设置等待时间,超出抛异常
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //设置设置线程休眠时间等待页面加载完成
            Thread.sleep(1000L);
            //获取搜索输入框并输入检索内容
            WebElement searchText = driver.findElementById("ctl00_ContentPlaceHolder1_searchContent");
            searchText.sendKeys(searchContent);

            //获取搜索按钮
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement searchButton = driver.findElementById("BtnSearch");
            searchButton.click();
            //设置线程休眠时间等待页面加载完成
            Thread.sleep(5000L);
            List<WebElement> contentList = driver.findElementsByCssSelector(".zltable li");
            List<String> result = new ArrayList<>();
            for (WebElement content : contentList) {
                result.add(content.getText());
            }
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("sorry , some question contact with me weixin d7880364");
            //关闭并退出浏览器
            driver.close();
            driver.quit();
        }
        return null;
    }
}
