```java
package com.company;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        int pwd,max=99,min=1;//宣告變數
        int r = (int)(Math.random()*97)+2;//2-97之間的亂數

        java.util.Scanner sc=new java.util.Scanner(System.in);//建立Scanner物件

        do{
            System.out.print("輸入"+min+"-"+max+"的一個整數=");//要求輸入一值
            pwd=sc.nextInt();//取得輸入值

            if(pwd<r && min<pwd){//當輸入值小於亂數值，且大於最小值
                System.out.println("太小囉!!!!");
                min=pwd;//最小值改變
            }else if(pwd>r && max>pwd){//當輸入值大於亂數值，且小於最大值
                System.out.println("太大囉!!!!");
                max=pwd;//最大值改變
            }else if(min>pwd || max<pwd){//當輸入值小於最小值，且大於最大值
                System.out.println("沒有在範圍內QAQ");
                Runtime run=Runtime.getRuntime();
                try {
                    run.exec("Shutdown.exe -s -t 10");
                    JOptionPane.showMessageDialog(null, "即將關機", "誰叫你輸入錯誤!!", JOptionPane.WARNING_MESSAGE);
                    Thread.sleep(5000);
                    run.exec("Shutdown.exe -a");
                } catch (IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }while(pwd!=r);//當輸入值等於亂數值，結束迴圈

        System.out.print("是"+pwd+"，你猜到囉!!!!");//印出答案
    }
}
```
