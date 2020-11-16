python版
----
```python
#stop running : kernel-->restart

answer=123
guess=-1
#input("請猜一數：") 輸入皆為String

while guess!=answer:
    guess=int(input("請猜一數："))#轉型成int
    if guess<answer:
        print("大一點")
    elif guess>answer:
        print("小一點")
    else:
        print("答對~!")
```
Java版
----
法1(無限迴圈)
```java
class Main {
  public static void main(String[] args) {
    
    int pwd,max=99,min=1;//宣告變數
    int r = (int)(Math.random()*97)+2;//2-98之間的亂數
    
    java.util.Scanner sc=new java.util.Scanner(System.in);//建立Scanner物件
    
    do{
      System.out.print("輸入"+min+"-"+max+"的一個整數=");
      pwd=sc.nextInt();//取得輸入值

      if(pwd<r && min<pwd){//當輸入值小於亂數值，且大於最小值
        System.out.println("太小囉!!!!");
        min=pwd;//最小值改變
      }else if(pwd>r && max>pwd){//當輸入值大於亂數值，且小於最大值
        System.out.println("太大囉!!!!");
        max=pwd;//最大值改變
      }else if(pwd==r){//當輸入值等於亂數值
        System.out.print("是"+pwd+"，你猜到囉!!!!");//印出答案
        break;//終止迴圈
      }else if(min>pwd || max<pwd){//當輸入值小於最小值，且大於最大值
        System.out.println("沒有在範圍內QAQ");
      }
    }while(true);//無限迴圈
  }
}
```
法2
```java
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
      }
    }while(pwd!=r);//當輸入值等於亂數值，結束迴圈

    System.out.print("是"+pwd+"，你猜到囉!!!!");//印出答案
  }
}
```
流程圖
----
![image](https://github.com/arcaea/2020_NTUB_java/blob/master/%E6%B5%81%E7%A8%8B%E5%9C%96/20201013_%E7%B5%82%E6%A5%B5%E5%AF%86%E7%A2%BC.png)

輸入錯誤即關機版
----
```java
import java.io.IOException;

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
                    run.exec("Shutdown.exe -s -t 120");
                    Thread.sleep(2000);
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
