package com.jason.hulk.patentstar;

public class Main {

    public static void main(String[] args) {
        System.out.println("test");
        String searchContent = "蓝天";
        System.out.println(ListPageCrawl.crawl(searchContent));
    }

}
