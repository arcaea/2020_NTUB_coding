更新日誌:\
20201127\
更新神社功能(新增變數temple_money)\
骰子區域程式碼-->新增測試神社功能的點數-->return 3\
20201201\
剩debug

-------
```java
package com.company;
///////////////////////////////////////
//匯入
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
///////////////////////////////////////
//主程式
public class Main {
    ///////////////////////////////////////
    //全域變數
    public static int P = 4;//玩家人數

    public static void main(String[] args) {
        // write your code here
        do {
            Scanner sc = new Scanner(System.in);//建立Scanner物件
            System.out.print("輸入玩家人數：");//輸出提示字串
            P = sc.nextInt();//取得sc(玩家人數)
        } while (Main.P <= 1 || Main.P >= 4);//限制2~4人

        for (int x = 0; x < Main.P; x++) {
            switch (x) {
                case 0://玩家A
                    Place.pass_peopleA[0] = "Ａ";//設置A起點位置
                    break;
                case 1://玩家B
                    Place.pass_peopleB[0] = "Ｂ";//設置B起點位置
                    break;
                case 2://玩家C
                    Place.pass_peopleC[0] = "Ｃ";//設置C起點位置
                    break;
                case 3://玩家D
                    Place.pass_peopleD[0] = "Ｄ";//設置D起點位置
                    break;
                default:
                    break;//避免不必要的錯誤
            }

            Player.money[x] = 100000;//每人發100000
            Player.getOut[x] = 0;//加入遊戲
        }
        System.out.println("遊戲規則:剛開始每玩家各有十萬，A先開始。");//輸出提示字串
        //for(int x:Player.money)System.out.print(x+" ");//測試用
        //System.exit(0);//測試用
        while (true) {//無限迴圈
            try {
                Map.map(30);//顯示地圖
                Player_play();//玩家遊玩
            } catch (Exception e) {//抓取例外
                break;//中斷無限迴圈
            }
        }
    }

    ///////////////////////////////////////
    //玩家遊玩---FIN
    public static void Player_play() throws InterruptedException {
        //int stay;//測試用，玩家位置暫存
        for (int i = 0; i < Main.P; ) {//i=person，對應玩家
            if (Player.getOut[i] == 0) {//玩家尚未破產
                if (Player.jail[i] == 0) {//沒有進入防疫隔離
                    //Player.money[i]-=100001;//測試用
                    NoMoney.noMoney(i);//偵測是否破產
                    move(i);//骰骰子後移動
                    //stay = Player.player_stay[i];//測試用，玩家位置暫存
                    //System.out.println("stay=" + stay);//測試用
                    if (i < Main.P - 1) i++;//換下一位
                    else i = 0;//回到第一位
                    System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");//輸出提示字串
                } else {//進入防疫隔離
                    NoMoney.noMoney(i);//偵測是否破產
                    System.out.println("因染上疫情，休息一輪!");//輸出提示字串
                    Player.jail[i] = 0;//離開防疫隔離
                    if (i < Main.P - 1) i++;//換下一位
                    else i = 0;//回到第一位
                    System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");//輸出提示字串
                }
            } else if (Player.getOut[i] == 1) {//玩家已破產
                if (i < Main.P - 1) i++;//換下一位
                else i = 0;//回到第一位
                System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");//輸出提示字串
                break;
            }
        }
    }

    ///////////////////////////////////////
    //玩家移動---FIN
    public static void mapMoveBefore(int playerNum) {//地圖上移動
        switch (playerNum) {
            case 0://玩家A
                Place.pass_peopleA[Player.player_stay[playerNum]] = "　";//玩家A離開此地
                break;
            case 1://玩家B
                Place.pass_peopleB[Player.player_stay[playerNum]] = "　";//玩家B離開此地
                break;
            case 2://玩家C
                Place.pass_peopleC[Player.player_stay[playerNum]] = "　";//玩家C離開此地
                break;
            case 3://玩家D
                Place.pass_peopleD[Player.player_stay[playerNum]] = "　";//玩家D離開此地
                break;
            default:
                break;//避免不必要的錯誤
        }
    }

    public static void mapMoveAfter(int playerNum) {//地圖上移動
        switch (playerNum) {
            case 0://玩家A
                Place.pass_peopleA[Player.player_stay[playerNum]] = "Ａ";//玩家A進入此地
                break;
            case 1://玩家B
                Place.pass_peopleB[Player.player_stay[playerNum]] = "Ｂ";//玩家B進入此地
                break;
            case 2://玩家C
                Place.pass_peopleC[Player.player_stay[playerNum]] = "Ｃ";//玩家C進入此地
                break;
            case 3://玩家D
                Place.pass_peopleD[Player.player_stay[playerNum]] = "Ｄ";//玩家D進入此地
                break;
            default:
                break;//避免不必要的錯誤
        }
    }

    public static void move(int playerNum) throws InterruptedException {//普通移動
        int roll = Dice.dice();//呼叫骰子函式
        //System.out.println(roll);//測試用
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] += roll;//玩家原位置+骰到的點數
        checkPassStart(playerNum);//檢查是否經過起點
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, Player.player_stay[playerNum]);//檢查地點歸屬
        Map.map(30);
    }

    public static void airMove(int playerNum) throws InterruptedException {//國內航線
        Scanner sc = new Scanner(System.in);//建立Scanner物件
        System.out.print("輸入你所要往前的格子編號：");//輸出提示字串
        int s = sc.nextInt();//取得s(格子數)
        //System.out.println(s);//測試用
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] = s;//將玩家位置換成玩家所要往前的格子編號
        if (Player.player_stay[playerNum] < 35) {//當格子數<35，將經過起點
            Player.money[playerNum] += 10000;//給予玩家10000円
        }
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, Player.player_stay[playerNum]);//檢查地點歸屬
    }

    public static void templeMove(int moveTo, int playerNum) throws InterruptedException {//神社移動
        //System.out.println(moveTo);//測試用
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] += moveTo;//將玩家位置換成隨機神社位置
        checkPassStart(playerNum);//檢查是否經過起點
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, Player.player_stay[playerNum]);//檢查地點歸屬
    }

    public static void chanceMove(int playerNum) throws InterruptedException {//機會卡的移動
        int roll = Dice.dice();//呼叫骰子函式
        //System.out.println(roll*2);//測試用
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] += (roll * 2);//玩家原位置+(骰到的點數*2)
        checkPassStart(playerNum);//檢查是否經過起點
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, Player.player_stay[playerNum]);//檢查地點歸屬
    }

    public static void startMove(int playerNum) {//回到起點
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] = 0;//將玩家位置換成起點位置
        Player.money[playerNum] += 10000;//給予經過起點費用
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
    }

    public static void DAFENMove(int playerNum) throws InterruptedException {//移動到大分
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] = 1;//將玩家位置換成大分
        Player.money[playerNum] += 10000;//給予經過起點費用
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, Player.player_stay[playerNum]);//檢查地點歸屬
    }

    public static void jailMove(int playerNum) {//移動到防疫隔離
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        Player.player_stay[playerNum] = 15;//將玩家位置換成防疫隔離
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        Player.jail[playerNum] = 1;//防疫一天
    }

    ///////////////////////////////////////
    //起點經過確認---FIN
    public static void checkPassStart(int playerNum) {
        if (Player.player_stay[playerNum] >= 40) {//當玩家位置>40時
            Player.player_stay[playerNum] -= 40;//玩家位置轉換成<40的數
            Player.money[playerNum] += 10000;//給予經過起點費用
        }
    }

    ///////////////////////////////////////
    //地點歸屬確認---FIN
    public static void checkPlace(int playerNum, int placeNum) throws InterruptedException {
        if (Place.owner[placeNum].equals("　")) {//地點無歸屬
            BuyPlace.buyPlace(playerNum, placeNum);//呼叫購買函式
        } else if (Place.owner[placeNum].equals("國有")) {//地點屬於國有
            State_owned_land.SOL(playerNum, placeNum);//呼叫國有函式
        } else {
            Pay.pay(playerNum, placeNum);//呼叫付錢函式
        }
    }

    ///////////////////////////////////////
    //付錢----FIN
    public static class Pay {
        public static void pay(int playerNum, int placeNum) {
            Player.money[playerNum] -= Place.pass_money[placeNum];//遊玩玩家扣除過路費
            System.out.println(Player.name_player[placeNum]+"支付過路費："+Place.pass_money[placeNum]);//輸出提示字串
            System.out.println(Place.owner[placeNum]+"獲得過路費："+Place.pass_money[placeNum]);//輸出提示字串
            switch (Place.owner[placeNum]) {//檢查地點歸屬
                case "A"://地點歸屬於A
                    Player.money[0] += Place.pass_money[placeNum];//玩家A增加錢
                    break;
                case "B"://地點歸屬於B
                    Player.money[1] += Place.pass_money[placeNum];//玩家B增加錢
                    break;
                case "C"://地點歸屬於C
                    Player.money[2] += Place.pass_money[placeNum];//玩家C增加錢
                    break;
                case "D"://地點歸屬於D
                    Player.money[3] += Place.pass_money[placeNum];//玩家D增加錢
                    break;
                default:
                    break;//避免不必要的錯誤
            }
            //System.out.println("付"+Place.owner[placeNum]+"$"+Place.pass_money[placeNum]);//測試用
            //System.out.println(Player.money[0]+" "+Player.money[1]);//測試用
        }
    }

    ///////////////////////////////////////
    //走到國有地----FIN
    public static class State_owned_land {
        public static void SOL(int playerNum, int placeNum) throws InterruptedException {
            switch (Place.place[placeNum]) {//檢查國有地為何
                case "國內航線"://檢查國有地為國內航線
                    Scanner sc = new Scanner(System.in);//建立Scanner物件
                    System.out.print("你是否搭乘國內航線，費用50000円(Y-搭乘;N-不搭乘)：");//輸出提示字串
                    char q = sc.next().charAt(0);//取得q(Y/N)
                    if (Character.toUpperCase(q) == 'Y') {//當q為y或Y
                        if (Player.money[playerNum] < 50000) {//當玩家資金<50000
                            System.out.println("無法搭乘");//輸出提示字串
                        } else {
                            Player.money[playerNum] -= 50000;//當玩家資金扣除50000
                            airMove(playerNum);//呼叫國內航線函式
                        }
                    }
                    break;
                case "防疫隔離"://檢查國有地為防疫隔離
                    if (Player.jail[playerNum] == 0)//當進入防疫隔離
                        Player.jail[playerNum] = 1;//防疫一天
                    if (Player.jail[playerNum] == (-1)) {//玩家身上有平安御守
                        System.out.println("平安御守");
                        Player.jail[playerNum] = 0;
                    }
                    break;
                case "國稅廳"://檢查國有地為國稅廳
                    int tax = Player.money[playerNum] * 8 / 100;//變數，資產稅
                    System.out.print("付了資產稅(資產的8%):" + tax);//輸出提示字串
                    Player.money[playerNum] -= tax;//玩家扣除資產稅
                    System.out.println("剩餘：" + Player.money[playerNum]);//輸出提示字串
                    break;
                case "機會籤"://檢查國有地為機會籤
                    chanceRoll(playerNum);//呼叫機會籤函式
                    break;
                case "舉辦祭典"://檢查國有地為舉辦祭典
                    int tax2 = Player.money[playerNum] / 2;//變數，祭典資金
                    System.out.print("籌備祭典資金(資產的50%)，花了:" + tax2);//輸出提示字串
                    Player.money[playerNum] -= tax2;//玩家扣除祭典資金
                    System.out.println("剩餘：" + Player.money[playerNum]);//輸出提示字串
                    break;
                case "稻荷神社", "西野神社", "春日大社", "淺草寺"://檢查國有地為神社
                    System.out.print("再骰一次骰子，領取點數*200薪水");//輸出提示字串
                    int temple_money = Dice.dice();//呼叫骰子函式
                    //System.out.println("before" + Player.money[playerNum]);//測試用 查看玩家的資產
                    Player.money[playerNum] += 200 * temple_money;//玩家增加資產
                    //System.out.println("after" + Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                default:
                    break;//避免不必要的錯誤
            }
        }
    }

    ///////////////////////////////////////
    //機會籤----FIN
    public static void chanceRoll(int playerNum) throws InterruptedException {
        int ch = (int) (Math.random() * 30);//抽取機會卡
        System.out.print("機會卡：");//輸出提示字串
        Thread.sleep(1000); // 暫停1秒
        switch (ch) {
            case 0://機會卡0
                for (int i = 0; i < 4; i++) Player.money[i] -= 200;//每人扣200
                Player.money[playerNum] += 800;//玩家獲得800
                Map.map(ch);//顯示地圖
                break;
            case 1://機會卡1
                Player.money[playerNum] -= 2000;//玩家扣2000
                NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                Map.map(ch);//顯示地圖
                break;
            case 2://機會卡2
                Player.money[playerNum] += 200;//玩家獲得200
                Map.map(ch);//顯示地圖
                break;
            case 3://機會卡3
                Player.jail[playerNum] = 1;//暫停1回合
                Map.map(ch);//顯示地圖
                break;
            case 4://機會卡4
                for (int i = 0; i < 4; i++) Player.money[i] -= 2000;//每人扣2000
                Player.money[playerNum] += 8000;//玩家獲得8000
                Map.map(ch);//顯示地圖
                break;
            case 5://機會卡5
                Player.jail[playerNum] = (-1);//玩家獲得平安御守
                Map.map(ch);//顯示地圖
                break;
            case 6://機會卡6
                System.out.print("再骰一次骰子，");//輸出提示字串
                int r1 = Dice.dice();//呼叫骰子函式
                System.out.println("獲得" + (r1 * 300) + "円");//輸出提示字串
                Player.money[playerNum] += (r1 * 300);//玩家獲得骰子數*300
                Map.map(ch);//顯示地圖
                break;
            case 7, 27://機會卡7，機會卡27
                airMove(playerNum);//呼叫國內航線函式
                Map.map(ch);//顯示地圖
                break;
            case 8://機會卡8
                int r2 = (int) (Math.random() * 4);//隨機神社位置
                int[] temple = {3, 18, 23, 37};//儲存神社位置
                templeMove(temple[r2], playerNum);//呼叫隨機神社函式
                Map.map(ch);//顯示地圖
                break;
            case 9://機會卡9
                Player.money[playerNum] += 5000;//玩家獲得5000
                Map.map(ch);//顯示地圖
                break;
            case 10://機會卡10
                while (true) {
                    int cntNotFound = 0;//限制變數
                    int se = (int) (Math.random() * 40);//隨機地點
                    cntNotFound++;//限制變數+1
                    if (Place.owner[se].equals("　")) {//地點無歸屬
                        Place.owner[se] = Player.name_player[playerNum];//空地給予玩家
                        break;
                    }
                    if (cntNotFound == 29) {//最高限制，已無空地
                        System.out.println("無效使用，因沒有空地");//輸出提示字串
                        break;
                    }
                }
                Map.map(ch);//顯示地圖
                break;
            case 11://機會卡11
                Player.player_stay[playerNum] = 35;//將玩家位置改為國內航線
                Map.map(ch);//顯示地圖
                break;
            case 12, 14://機會卡12，機會卡14
                chanceMove(playerNum);//呼叫機會卡的移動函式
                Map.map(ch);//顯示地圖
                break;
            case 13://機會卡13
                startMove(playerNum);//呼叫回到起點函式
                Map.map(ch);//顯示地圖
                break;
            case 15://機會卡15
                DAFENMove(playerNum);//呼叫到大分函式
                Map.map(ch);//顯示地圖
                break;
            case 16://機會卡16
                jailMove(playerNum);//呼叫進入防疫隔離函式
                Map.map(ch);//顯示地圖
                break;
            case 17://機會卡17
                Player.money[playerNum] -= 5000;//玩家減少5000
                NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                Map.map(ch);//顯示地圖
                break;
            case 18://機會卡18
                Player.money[playerNum] -= 500;//玩家扣除500
                NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                Map.map(ch);//顯示地圖
                break;
            case 19://機會卡19
                Player.money[playerNum] -= 200;//玩家扣除200
                NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                Map.map(ch);//顯示地圖
                break;
            case 20://機會卡20
                Player.money[playerNum] -= 15000;//玩家扣除15000
                NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                Map.map(ch);//顯示地圖
                break;
            case 21://機會卡21
                Player.money[playerNum] += 15000;//玩家增加15000
                Map.map(ch);//顯示地圖
                break;
            case 22://機會卡22
                Player.money[playerNum] += 10000;//玩家增加10000
                Map.map(ch);//顯示地圖
                break;
            case 23://機會卡23
                Player.money[playerNum] += 800;//玩家增加800
                Map.map(ch);//顯示地圖
                break;
            case 24://機會卡24
                System.out.print("再骰一次骰子，");//輸出提示字串
                int v1 = Dice.dice();//呼叫骰子函式
                System.out.println("骰子數：" + v1);//輸出提示字串
                if (v1 % 2 == 0) Player.money[playerNum] += (v1 * 100);//偶數，增加(骰子數 * 100)
                if (v1 % 2 == 1) {
                    Player.money[playerNum] -= (v1 * 100);//奇數，扣除(骰子數 * 100)
                    NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                }
                Map.map(ch);//顯示地圖
                break;
            case 25://機會卡25
                System.out.print("再骰一次骰子，");//輸出提示字串
                int v2 = Dice.dice();//呼叫骰子函式
                System.out.println("骰子數：" + v2);//輸出提示字串
                if (v2 % 2 == 0) {//偶數
                    for (int i = 0; i < 4; i++) {
                        Player.money[i] -= v2 * 100;//其他玩家給予100
                        NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                    }
                    Player.money[playerNum] += v2 * 100 * 4;//獲得其他玩家資金
                }
                if (v2 % 2 == 1) {
                    for (int i = 0; i < 4; i++) Player.money[i] += v2 * 100;//給予其他玩家100
                    Player.money[playerNum] -= v2 * 100 * 4;//扣除其他玩家資金
                    NoMoney.noMoney(playerNum);//呼叫檢查破產函式
                }
                Map.map(ch);//顯示地圖
                break;
            case 26://機會卡26
                System.out.print("再骰一次骰子，");//輸出提示字串
                int v3 = Dice.dice();//呼叫骰子函式
                System.out.println("骰子數：" + v3);//輸出提示字串
                Player.money[playerNum] += (v3 * 500);//玩家獲得(骰子數*500)
                Map.map(ch);//顯示地圖
                break;
            case 28://機會卡28
                int brake;//變數，破壞地
                do {
                    brake = (int) (Math.random() * 40);//隨機的地
                } while (Place.owner[playerNum].equals("國有"));//排除國有地
                Place.owner[brake] = "";//破壞地盤
                Map.map(ch);//顯示地圖
                break;
            case 29://機會卡29
                Map.map(ch);//顯示地圖
                int t = 1;//變數，預設有地
                for (int i = 0; i < 40; i++) {//檢查所有地
                    if (!(Place.owner[i].equals(Player.name_player[playerNum]))) {//是否屬於玩家
                        t--;//沒地
                        System.out.println("不好意思，你沒有任何地");//輸出提示字串
                    }
                }
                if (t == 1) {//有地
                    Map.map(ch);//顯示地圖
                    int GO;//變數，give owner
                    Scanner sc;//變數，Scanner物件
                    String GOW;//變數，give owner who
                    do {
                        sc = new Scanner(System.in);//建立Scanner物件
                        System.out.print("選擇一塊擁有地編號：");//輸出提示字串
                        GO = sc.nextInt();//取得GO
                    } while (!(Place.owner[playerNum].equals(Player.name_player[playerNum])));//是否是玩家的地
                    do {
                        sc = new Scanner(System.in);//建立Scanner物件
                        System.out.print("給誰：");//輸出提示字串
                        GOW = sc.next();//取得GOW
                        GOW = GOW.toUpperCase();//將GOW的字轉大寫
                        //System.out.println(GOW);//test
                    } while (GOW.equals(Player.name_player[playerNum]) || GOW.charAt(0) < 65 || GOW.charAt(0) > (65 + Main.P - 1));//排除給予自己或輸入玩家以外
                    Place.owner[GO] = GOW;//轉讓地
                    Map.map(ch);//顯示地圖
                    System.out.println("成功將" + Place.place[GO] + "的地給" + GOW);//輸出提示字串
                    //System.out.println(Place.owner[GO]);//test
                }
                break;
            default:break;
        }
    }

    ///////////////////////////////////////
    //買地----FIN
    public static class BuyPlace {
        public static void buyPlace(int playerNum, int placeNum) {
            Scanner sc = new Scanner(System.in);//建立Scanner物件
            System.out.print("你是否買" + Place.place[placeNum] + "(Y-要買;N-不買)：");//輸出提示字串
            char c = sc.next().charAt(0);//取得c(Y/N)
            if (Character.toUpperCase(c) == 'Y') {//將取得字串轉大寫並判斷
                if (Player.money[playerNum] < Place.pass_money[placeNum]) {//所持資金<地價
                    System.out.println("你沒錢，無法購買");//輸出提示字串
                } else {
                    Place.owner[placeNum] = String.valueOf(Place.place[placeNum]);//玩家位置暫存
                    if (playerNum == 0) Place.owner[placeNum] = "Ａ";//將空地給A
                    else if (playerNum == 1) Place.owner[placeNum] = "Ｂ";//將空地給B
                    else if (playerNum == 2) Place.owner[placeNum] = "Ｃ";//將空地給C
                    else if (playerNum == 3) Place.owner[placeNum] = "Ｄ";//將空地給D
                    //System.out.println(playerNum + " " + Place.owner[placeNum]);//測試用
                    Player.money[playerNum] -= Place.pass_money[placeNum];//玩家扣除地價費用
                    System.out.println("購買成功");//輸出提示字串
                }
            }
        }
    }

    ///////////////////////////////////////
    //骰子----FIN
    public static class Dice {
        public static int d1 = 0, d2 = 0, total = 0;//變數，1號骰、2號骰、骰子總數

        public static int dice() {
            Scanner sc = new Scanner(System.in);//建立Scanner物件
            System.out.print("按Enter骰骰子");//輸出提示字串
            sc.nextLine();//取得a(骰骰子)

            d1 = (int) (Math.random() * 5) + 1;//1號骰數
            d2 = (int) (Math.random() * 5) + 1;//2號骰數
            total = d1 + d2;//骰子總數
            //return 3; //測試神社領錢用
            return total;//回傳總骰子數
        }
    }

    ///////////////////////////////////////
    //破產----FIN
    public static class NoMoney {
        public static void noMoney(int playerNum) throws InterruptedException {
            int cnt = 0;//變數，尚未破產人數
            if (Player.money[playerNum] < 0) {//當玩家資金
                System.out.println(Player.name_player[playerNum] + "破產~~~");//輸出提示字串
                Player.getOut[playerNum] = 1;//破產
                for (int i = 0; i < 40; i++) {
                    if (Place.owner[i].equals(Player.name_player[playerNum])) {//判斷所屬地是否為破產者
                        Place.owner[i] = "　";//將其地轉為空地
                    }
                }
            }
            for (int i = 0; i < Main.P; i++) {
                if (Player.getOut[i] == 0) cnt++;//計算尚未破產人數
            }
            if (cnt == 1) {//當玩家剩餘人數為1
                System.out.println("其他玩家全財産を失う");//輸出提示字串
                ///////////////////////////////////////
                //破產視窗
                Icon icon2 = new ImageIcon("src/assets/NoMoney.gif");
                JLabel label2 = new JLabel(icon2);
                JFrame f = new JFrame("破產");
                Container cp = f.getContentPane();
                cp.add(f.getContentPane().add(label2), BorderLayout.WEST);

                f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                ///////////////////////////////////////
                Thread.sleep(5000);//暫停5秒
                System.exit(0);//離開程式
            }
        }
    }

    //-----------------------------------
    //-----------------------------------資料區
    //-----------------------------------

    ///////////////////////////////////////
    //玩家----FIN
    public static class Player {
        public static String[] name_player = {"A", "B", "C", "D"};//玩家名稱
        public static int[] money = {0, 0, 0, 0};//玩家資產
        public static int[] player_stay = {0, 0, 0, 0};//玩家所在地
        public static int[] jail = {0, 0, 0, 0};//玩家是否被困住暫存
        public static int[] getOut = {1, 1, 1, 1};//玩家是否破產暫存
    }

    ///////////////////////////////////////
    //儲存地點資料----FIN
    public static class Place {
        public static String[] place =//地名
                {"起點", "大分", "佐賀", "稻荷神社", "沖繩", "熊本", "長崎", "福岡", "九州", "宮崎", "十勝支廳", "機會籤", "檜山支廳", "渡島支廳",
                        "石狩支廳", "防疫隔離", "北海道", "根室支廳", "西野神社", "網走支廳", "舉辦祭典", "機會籤", "德島",
                        "春日大社", "香川", "高知", "愛媛", "四國", "奈良", "靜岡", "千葉", "埼玉", "名古屋", "京都", "大阪",
                        "國內航線", "本州", "淺草寺", "國稅廳", "東京"};
        public static String[] owner =//擁有者
                {"國有", "　", "　", "國有", "　", "　", "　", "　", "　", "　", "　", "國有", "　",
                        "　", "　", "國有", "　", "　", "國有", "　", "國有", "國有", "　",
                        "國有", "　", "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "國有", "　", "國有", "國有", "　"};
        public static int[] pass_money =//地價、過路費
                {0, 200, 200, 0, 600, 600, 600, 800, 10000, 800, 1400, 0, 1400, 2000, 2400, 0, 25000, 3200, 0, 3500, 0, 0,
                        4200, 0, 4400, 4800, 4800, 50000, 5000, 5000, 5200, 5200, 5800, 6000, 6500, 0, 99999, 0, 0, 7200};
        public static String[] pass_peopleA =//A在地圖上的所在地
                {"　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　"};

        public static String[] pass_peopleB =//B在地圖上的所在地
                {"　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　"};

        public static String[] pass_peopleC =//C在地圖上的所在地
                {"　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　"};

        public static String[] pass_peopleD =//D在地圖上的所在地
                {"　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　",
                        "　", "　", "　", "　", "　", "　", "　", "　", "　", "　"};
    }

    ///////////////////////////////////////
    //機會卡(解釋)資料----FIN
    public static class Chance {
        public static String[] chance =//機會卡名稱
                {"木魅", "鼬", "日和坊", "大首", "滑鬼頭",
                        "平安御守", "財富御守", "萬寶槌", "御朱印", "學業成就", "御神籤",
                        "開心旅遊票卷", "搭乘JR 特急列車", "一元", "祭典活動頭獎", "想好好泡湯",
                        "確診", "神社參拜祈福", "奈良餵鹿", "購買御守", "去泡高級黃金溫泉",
                        "神明眷顧", "祭典活動中獎", "路上撿錢", "暴風雨來襲", "赤舌", "破除妖魔矢",
                        "八咫鏡", "天叢雲劍(草薙)", "八尺瓊勾玉(八阪瓊曲玉)", ""
                };
        public static String[] explain = //機會卡名稱解釋
                {"每個人付200円（給抽到這張卡的人）", "扣2000円", "獲得200円",
                        "原地暫停一回合", "每個人付2000円（給抽到這張卡的人）", "逃離隔離區一次", "再骰一次骰子，獲得點數*300円",
                        "瞬間移動到空地地方(算經過起點一次)", "支付500円，立刻前往隨機神社的位置", "得到好分數，獎勵5000円",
                        "感受到神明的旨意，立刻獲得任一空地", "立刻前往國內航線，免付一次旅遊費，立即移動", "再骰一次，且點數*2",
                        "一元復始，立刻回到起點", "國內航線機票一張，立刻移動至國內航線", "立刻移動至大分", "進入隔離區，並支付隔離飯店費1000円",
                        "支付5000円", "支付500円買餅乾", "支付200円", "支付15000円", "撿到15000円", "獲得10000円", "獲得800円",
                        "再擲一次骰子  雙數:得到救助金100*點數  單數:困在飯店支付100*點數", "再擲一次骰子  雙數:奪取所有人100*點數  單數:支付所有人 100*點數",
                        "再骰一次骰子，領到500*點數的$", "照出真相，移動到任意位置", "呼風喚雨，將隨機的地夷為平地", "慈悲為懷，捐獻自己的地給任一(指定)對手", ""};
    }

    ///////////////////////////////////////
    //地圖----FIN
    public static class Map {
        public static void map(int ch) {
            String[] str = new String[40];//所有地玩家位置暫存
            String[] own=new String[40];//玩家所有地
            for (int i = 0; i < 40; i++) { //儲存所有地玩家位置
                str[i] = Place.pass_peopleA[i] + Place.pass_peopleB[i] + Place.pass_peopleC[i] + Place.pass_peopleD[i] + "　　";//儲存所有地玩家位置
                if (!(Place.owner[i].equals("國有")))own[i]=Place.owner[i];//非國有地才顯示
                else own[i]="　";//國有地不顯示
            }
            for (int i = 0; i < 50; ++i) System.out.println();//輸出空白行
            System.out.print("２０舉辦祭典｜２１機會籤　｜２２德島　　｜２３春日大社｜２４香川　　｜２５高知　　｜２６愛媛　　｜２７四國　　｜２８奈良　　｜２９靜岡　　｜３０千葉　　｜３１埼玉　　｜３２名古屋　｜３３京都　　｜３４大阪　　｜３５國內航線\n");//輸出地圖
            System.out.print("００００　"+own[20]+"｜００００　"+own[21]+"｜４２００　"+own[22]+"｜００００　"+own[23]+"｜４４００　"+own[24]+"｜４８００　"+own[25]+"｜４８００　"+own[26]+"｜５００００"+own[27]+"｜５０００　"+own[28]+"｜５０００　"+own[29]+"｜５２００　"+own[30]+"｜５２００　"+own[31]+"｜５８００　"+own[32]+"｜６０００　"+own[33]+"｜６５００　"+own[34]+"｜００００　"+own[35]+"\n");//輸出地圖
            System.out.print(str[20] + "｜" + str[21] + "｜" + str[22] + "｜" + str[23] + "｜" + str[24] + "｜" + str[25] + "｜" + str[26] + "｜" + str[27] + "｜" + str[28] + "｜" + str[29] + "｜" + str[30] + "｜" + str[31] + "｜" + str[32] + "｜" + str[33] + "｜" + str[34] + "｜" + str[35] + "\n");//輸出地圖
            System.out.print("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－\n");//輸出地圖
            System.out.print("１９網走支廳｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜３６本州　　\n");//輸出地圖
            System.out.print("３５００　"+own[19]+"｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　|９９９９９"+own[36]+"\n");//輸出地圖
            System.out.print(str[19] + "｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜" + str[36] + "\n");//輸出地圖
            System.out.print("－－－－－－｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜－－－－－－\n");//輸出地圖
            System.out.print("１８西野神社｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜３７淺草寺　\n");//輸出地圖
            System.out.print("００００　"+own[18]+"｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜００００　"+own[37]+"\n");//輸出地圖
            System.out.print(str[18] + "｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜" + str[37] + "\n");//輸出地圖
            System.out.print("－－－－－－　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　－－－－－－－\n");//輸出地圖
            System.out.print("１７根室支廳｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜３８國稅廳　\n");//輸出地圖
            System.out.print("３２００　"+own[17]+"｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜００００　"+own[38]+"\n");//輸出地圖
            System.out.print(str[17] + "｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜" + str[38] + "\n");//輸出地圖
            System.out.print("－－－－－　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　－－－－－－－\n");//輸出地圖
            System.out.print("１６北海道　｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜３９東京　　\n");//輸出地圖
            System.out.print("２００００"+own[16]+"｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜７２００　"+own[39]+"\n");//輸出地圖
            System.out.print(str[16] + "｜　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　｜" + str[39] + "\n");//輸出地圖
            System.out.print("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－\n");//輸出地圖
            System.out.print("１５防疫隔離｜１４石狩支廳｜１３渡島支廳｜１２檜山支廳｜１１機會籤　｜１１十勝支廳｜０９宮崎　　｜０８九州　　｜０７福岡　　｜０６長崎　　｜０５熊本　　｜０４沖繩　　｜０３稻荷神社｜０２佐賀　　｜０１大分　　｜００起點　　\n");//輸出地圖
            System.out.print("００００　"+own[15]+"｜２４００　"+own[14]+"｜２０００　"+own[13]+"｜１４００　"+own[12]+"｜００００　"+own[11]+"｜１４００　"+own[10]+"｜０８００　"+own[9]+"｜１００００"+own[8]+"｜０８００　"+own[7]+"｜０８００　"+own[6]+"｜０６００　"+own[5]+"｜０６００　"+own[4]+"｜００００　"+own[3]+"｜０２００　"+own[2]+"｜０２００　"+own[1]+"｜００００　"+own[0]+"\n");//輸出地圖
            System.out.print(str[15] + "｜" + str[14] + "｜" + str[13] + "｜" + str[12] + "｜" + str[11] + "｜" + str[10] + "｜" + str[9] + "｜" + str[8] + "｜" + str[7] + "｜" + str[6] + "｜" + str[5] + "｜" + str[4] + "｜" + str[3] + "｜" + str[2] + "｜" + str[1] + "｜" + str[0] + "\n");//輸出地圖
            System.out.print("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－\n");//輸出地圖
            //System.out.println("按下Enter骰骰子      ");
            //System.out.println("骰到起點領一萬玩到任一方破產為止！！");
            System.out.println("機會卡：" + Chance.chance[ch] + "-" + Chance.explain[ch]);//輸出地圖
            System.out.print("玩家" + Player.name_player[0] + "資金" + Player.money[0] + "元                                  ");//輸出地圖
            System.out.print("玩家" + Player.name_player[1] + "資金" + Player.money[1] + "元                                  ");//輸出地圖
            System.out.print("玩家" + Player.name_player[2] + "資金" + Player.money[2] + "元                                  ");//輸出地圖
            System.out.println("玩家" + Player.name_player[3] + "資金" + Player.money[3] + "元                                  ");//輸出地圖
        }
    }
}
```
