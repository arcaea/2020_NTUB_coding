20201208
this 
setter
getter
```java
package com.company;
///////////////////////////////////////
//匯入
import java.util.Scanner;

//-----------------------------------
//---------------------資料區
//-----------------------------------
///////////////////////////////////////
//玩家

/*
class Player {
    String name;
    int money;
    int stay;
}
*/


class Player {
    private String name;
    private int money;
    private int stay;

    public void setName(String set) {this.name = set;}
    public void setMoney(int money) {this.money = money;}
    public void setStay(int stay) {this.stay = stay;}

    String getName(){return name;}
    int getMoney(){return money;}
    int getStay(){return stay;}

}
//System.out.println(user[i].getName().getClass().getName());
///////////////////////////////////////
//主程式
public class Main {
    ///////////////////////////////////////
    //全域變數
    //public static int P = 0;//玩家人數
    ///////////////////////////////////////
    public static void main(String[] args) {
        Player[] user=new Player[4];
        int i,p,player_no;

        do {
            Scanner sc = new Scanner(System.in);//建立Scanner物件
            System.out.print("輸入玩家人數：");//輸出提示字串
            player_no = sc.nextInt();//取得sc(玩家人數)
        } while (player_no <= 1 || player_no > 4);//限制2~4人

        Scanner sc = new Scanner(System.in);//建立Scanner物件
        for(int j=0;j<player_no;j++){
            user[j]=new Player();
            user[j].setStay(0);
            user[j].setMoney(2000000);
            System.out.println("Enter play name");//輸出提示字串
            user[j].setName(sc.next());

        }
        System.out.println("遊戲規則:剛開始每玩家各有2000000，A先開始。");//輸出提示字串
        for(int j=0;j<player_no;j++){
            System.out.println(user[j].getName()+"\n"+user[j].getStay()+"\n"+user[j].getMoney());

        }
        i=0;
        while (true) {//無限迴圈
            try {
                sc.nextLine();//玩家遊玩
                System.out.println(user[i].getName());
                System.out.println(user[i].getStay());
                p=dice();
                user[i].setStay(user[i].getStay()+p);
                if (user[i].getStay() >= 40) {//當玩家位置>40時
                    user[i].setStay(user[i].getStay()-40);//玩家位置轉換成<40的數
                    user[i].setMoney(user[i].getMoney()+100000);//給予經過起點費用
                    System.out.println(user[i].getName() + "移動到" + user[i].getStay());
                }else {
                    System.out.println(user[i].getName() + "移動到" + user[i].getStay());
                }
                i++;
                if(i>player_no-1)i=0;
            } catch (Exception e) {//抓取例外
                break;//中斷無限迴圈
            }
        }


    }


    ///////////////////////////////////////
    //玩家遊玩
/*
    public static void Player_play()  {
        //int stay;//測試用，玩家位置暫存
        for (int i = 0; i < Main.P;) {//i=person，對應玩家
            if (Player.getOut[i] == 0) {//玩家尚未破產
                user[i].money[i]-=5000;//測試用
                move(i);//骰骰子後移動
                System.out.println(Player.name_player[i] +"擁有：$" + Player.money[i]);//輸出提示字串
                if (i < Main.P - 1) i++;//換下一位
                else i = 0;//回到第一位
                System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");//輸出提示字串
            } else if (Player.getOut[i] == 1) {//玩家已破產
                if (i < Main.P - 1) i++;//換下一位
                else i = 0;//回到第一位
                System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");//輸出提示字串
                break;
            }
        }
    }
    public static void move(int playerNum) {//普通移動
        int roll = dice();//呼叫骰子函式
        NoMoney.noMoney(playerNum);//偵測是否破產
        System.out.print(Player.name_player[playerNum]+"目前位置"+Player.player_stay[playerNum]+ "，");
        System.out.print("骰了"+roll+"點，");//測試用
        Player.player_stay[playerNum] += roll;//玩家原位置+骰到的點數
        checkPassStart(playerNum);//檢查是否經過起點
        System.out.println(Player.name_player[playerNum] + "移動到" + Player.player_stay[playerNum]);
    }

    ///////////////////////////////////////
    //起點經過確認
    public static void checkPassStart(int playerNum) {
        if (Player.player_stay[playerNum] >= 40) {//當玩家位置>40時
            Player.player_stay[playerNum] -= 40;//玩家位置轉換成<40的數
            Player.money[playerNum] += 10000;//給予經過起點費用
            System.out.println(Player.name_player[playerNum] + "移動到" + Player.player_stay[playerNum]);
        }
    }
*/
    ///////////////////////////////////////
    //骰子

        public static int dice() {
            int d1, d2, total;//變數，1號骰、2號骰、骰子總數
            Scanner sc = new Scanner(System.in);//建立Scanner物件
            System.out.print("按Enter骰骰子");//輸出提示字串
            sc.nextLine();//取得a(骰骰子)
            System.out.println();

            d1 = (int) (Math.random() * 5) + 1;//1號骰數字
            d2 = (int) (Math.random() * 5) + 1;//2號骰數字
            total = d1 + d2;//骰子總數
            return total;//回傳總骰子數
        }
/*
    ///////////////////////////////////////
    //破產
    public static class NoMoney {
        public static void noMoney(int playerNum){
            int cnt=0;//變數，尚未破產人數
            if (Player.money[playerNum] < 0) {//當玩家資金
                System.out.println(Player.name_player[playerNum] + "破產~~~");//輸出提示字串
                Player.getOut[playerNum] = 1;//破產
            }
            for(int i=0;i<Main.P;i++){
                if(Player.getOut[i]==0) cnt++;//計算尚未破產人數
            }
            if (cnt == 1) {//當玩家剩餘人數為1
                System.out.println("其他玩家全財産を失う");//輸出提示字串
                System.exit(0);//離開程式
            }
        }
    }
*/
}
```
