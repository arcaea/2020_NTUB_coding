package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        while(true){
            //顯示地圖
            Player_play();//玩家遊玩
        }
    }
    ///////////////////////////////////////
    //玩家遊玩---NOT FIN
    public static void Player_play() throws InterruptedException {
        int stay;//玩家位置暫存
        for(int i=0;i<4;){//i=person
            if(Player.jail[i]==0) {
                move(i);
                stay = Player.player_stay[i];//玩家位置暫存
                System.out.println("stay=" + stay);//測試用

                if (i < 3) i++;
                else i = 0;
                System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");
            }else{
                System.out.println("因染上疫情，休息一輪!");
                Player.jail[i]=0;
                if (i < 3) i++;
                else i = 0;
                System.out.print("換下一位玩家：" + Player.name_player[(i)] + "，");
            }
        }
    }
    ///////////////////////////////////////
    //玩家移動---NOT FIN
    public static void move(int playerNum) throws InterruptedException {
        int roll=Dice.Dice();
        System.out.println(roll);//測試用
        Player.player_stay[playerNum]+=roll;//i=person
        checkPassStart(playerNum);
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void airmove(int playerNum) throws InterruptedException {//國內航空用
        Scanner sc=new Scanner(System.in);//建立Scanner物件
        System.out.print("輸入你所要往前的格子編號：");
        int s = sc.nextInt();//取得s(格數)
        System.out.println(s);//測試用
        Player.player_stay[playerNum]=s;//i=person
        if(Player.player_stay[playerNum]<35) {//i=person
            Player.money[playerNum]+=10000;//i=person
        }
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void templemove(int moveto,int playerNum) throws InterruptedException {
        int roll=moveto;
        System.out.println(roll);//測試用
        Player.player_stay[playerNum]+=roll;//i=person
        checkPassStart(playerNum);
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void chancemove(int playerNum) throws InterruptedException {
        int roll=Dice.Dice();
        System.out.println(roll*2);//測試用
        Player.player_stay[playerNum]+=(roll*2);//i=person
        checkPassStart(playerNum);
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void startmove(int playerNum) throws InterruptedException {
        Player.player_stay[playerNum]=40;//i=person
        checkPassStart(playerNum);
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void oitamove(int playerNum) throws InterruptedException {
        Player.player_stay[playerNum]=41;//i=person
        checkPassStart(playerNum);
        checkplace(playerNum, Player.player_stay[playerNum]);
    }
    public static void jailmove(int playerNum){
        Player.player_stay[playerNum]=15;//i=person
        Player.jail[playerNum]=1;//防疫一天
    }
    ///////////////////////////////////////
    //起點經過確認---FIN
    public static void checkPassStart(int playerNum){
        if(Player.player_stay[playerNum]>=40) {//i=person
            Player.player_stay[playerNum]-=40;//i=person
            Player.money[playerNum]+=10000;//i=person
        }
    }
    ///////////////////////////////////////
    //地點歸屬確認---FIN
    public static void checkplace(int playerNum,int placeNum) throws InterruptedException {
        if(Place.owner[placeNum]==""){
            BuyPlace.BuyPlace(playerNum,placeNum);//玩家位置暫存 i=person
        }else if(Place.owner[placeNum]=="國有"){
            State_owned_land.SOL(playerNum,placeNum);
        }else{
            Pay.Pay(playerNum,placeNum);//玩家位置暫存 i=person
        }
    }
    ///////////////////////////////////////
    //付錢----FIN
    public static class Pay{
        public static void Pay(int playerNum,int placeNum){
            Player.money[playerNum] -= Place.pass_money[placeNum];
            if (Place.owner[placeNum] == "A") Player.money[0] += Place.pass_money[placeNum];
            else if (Place.owner[placeNum] == "B") Player.money[1] += Place.pass_money[placeNum];
            else if (Place.owner[placeNum] == "C") Player.money[2] += Place.pass_money[placeNum];
            else if (Place.owner[placeNum] == "D") Player.money[3] += Place.pass_money[placeNum];
            System.out.println("付"+Place.owner[placeNum]+"$"+Place.pass_money[placeNum]);//測試用
            System.out.println(Player.money[0]+" "+Player.money[1]);//測試用
        }
    }
    ///////////////////////////////////////
    //走到國有地----NOT FIN
    public static class State_owned_land {
        public static void SOL(int playerNum,int placeNum) throws InterruptedException {
            switch (Place.place[placeNum].toString()){
                case "國內航線":
                    Scanner sc=new Scanner(System.in);//建立Scanner物件
                    System.out.print("你是否搭乘國內航線，費用50000円(Y-搭乘;N-不搭乘)：");
                    char q = sc.next().charAt(0);//取得q(Y/N)
                    if(Character.toUpperCase(q)=='Y'){
                        Player.money[playerNum] -= 50000;
                        airmove(playerNum);
                    }
                    break;
                case "防疫隔離":
                    if(Player.jail[playerNum]==0)
                        Player.jail[playerNum]=1;//防疫一天
                    if(Player.jail[playerNum]==(-1)){
                        System.out.println("平安御守");
                        Player.jail[playerNum]=0;
                    }
                    break;
                case "國稅廳":
                    System.out.print("付了資產稅(資產的8%)");
                    Player.money[playerNum] *= 0.92;
                    break;
                case "機會籤":
                    chanceroll(playerNum,placeNum);
                    break;
                case "稻荷神社","西野神社","春日大社","淺草寺":
                    System.out.print("再骰一次骰子，領取點數*200薪水");
                    int temple_money=Dice.Dice();
                    Player.money[playerNum] += 200*temple_money;
                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
            }
        }
    }
    ///////////////////////////////////////
    //機會籤----NOT FIN
    public static void chanceroll(int playerNum, int placeNum) throws InterruptedException {
        //int ch = (int) (Math.random() * 30);
        System.out.print("機會卡：");
        Thread.sleep(1000); // 暫停1秒

        //===========================================
        Scanner sc234 = new Scanner(System.in);//建立Scanner物件
        System.out.print("輸入測試編號：");
        int ch = sc234.nextInt();//
        //===========================================

            switch (ch) {
                case 0:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch] + Player.name_player[playerNum]);
                    for (int i = 0; i < 4; i++) Player.money[i] -= 200;
                    Player.money[playerNum] += 800;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 1:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] -= 2000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 2:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] += 200;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 3:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.jail[playerNum] = 1;
                    break;
                case 4:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    for (int i = 0; i < 4; i++) Player.money[i] -= 2000;
                    Player.money[playerNum] += 8000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 5:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.jail[playerNum] = (-1);
                    break;
                case 6:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    System.out.print("再骰一次骰子，");
                    int r1 = Dice.Dice();
                    Player.money[playerNum] += (r1 * 300);

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 7, 27:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    airmove(playerNum);
                    break;
                case 8:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);//18 3 37 23
                    int r2 = (int) (Math.random() * 4);
                    int[] temple = {3, 18, 23, 37};
                    templemove(temple[r2], playerNum);
                    break;
                case 9:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] += 5000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 10:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    while (true) {
                        int cntNotFound = 0;
                        int se = (int) (Math.random() * 40);
                        cntNotFound++;
                        if (Place.owner[se].equals("")) {
                            Place.owner[se] = Player.name_player[playerNum];
                            break;
                        }
                        if (cntNotFound == 29) {
                            System.out.println("無效使用，因沒有空地");
                            break;
                        }
                    }
                case 11:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.player_stay[playerNum] = 35;
                    break;
                case 12, 14:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    chancemove(playerNum);
                    break;
                case 13:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    startmove(playerNum);
                    break;
                case 15:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    oitamove(playerNum);
                    break;
                case 16:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    jailmove(playerNum);
                    break;
                case 17:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] -= 5000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 18:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] -= 500;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 19:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] -= 200;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 20:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] -= 15000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 21:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] += 15000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 22:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] += 10000;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 23:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Player.money[playerNum] += 800;

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 24:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    System.out.print("再骰一次骰子，");
                    int v1 = Dice.Dice();
                    if (v1 % 2 == 0) Player.money[playerNum] += (v1 * 100);
                    if (v1 % 2 == 1) Player.money[playerNum] -= (v1 * 100);

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 25:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    System.out.print("再骰一次骰子，");
                    int v2 = Dice.Dice();
                    if (v2 % 2 == 0) {
                        for (int i = 0; i < 4; i++) Player.money[i] -= v2 * 100;
                        Player.money[playerNum] += v2 * 100 * 4;
                    }
                    if (v2 % 2 == 1) {
                        for (int i = 0; i < 4; i++) Player.money[i] += v2 * 100;
                        Player.money[playerNum] -= v2 * 100 * 4;
                    }

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 26:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    System.out.print("再骰一次骰子，");
                    int v3 = Dice.Dice();
                    Player.money[playerNum] += (v3 * 500);

                    System.out.println(Player.money[playerNum]);//測試用 查看玩家的資產
                    break;
                case 28:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    int brake = (int) (Math.random() * 40);
                    Place.owner[brake] = "";
                    break;
                case 29:
                    System.out.println(Chance.chance[ch] + "-" + Chance.explain[ch]);
                    Scanner sc = new Scanner(System.in);//建立Scanner物件
                    System.out.print("選擇一塊擁有地編號：");
                    int GO = sc.nextInt();//give owner
                    String GOW;
                    do {
                        sc = new Scanner(System.in);//建立Scanner物件
                        System.out.print("給誰：");
                        GOW = sc.next();//give owner who
                        GOW = GOW.toUpperCase();
                        System.out.println(GOW);//test
                    } while (GOW.equals(Player.name_player[playerNum]) || GOW.charAt(0) < 65 || GOW.charAt(0) > 68);
                    Place.owner[GO] = GOW;
                    System.out.println("成功將" + Place.place[GO] + "的地給" + GOW);
                    ///test
                    System.out.println(Place.owner[GO]);//test
                    break;
            }

    }
    ///////////////////////////////////////
    //買地----FIN
    public static class BuyPlace {
        public static void BuyPlace(int playerNum,int placeNum) {
            Scanner sc=new Scanner(System.in);//建立Scanner物件
            System.out.print("你是否買"+Place.place[placeNum]+"(Y-要買;N-不買)：");
            char c = sc.next().charAt(0);//取得c(Y/N)
            if(Character.toUpperCase(c)=='Y'){
                Place.owner[placeNum]= String.valueOf(Place.place[placeNum]);//玩家位置暫存
                if(playerNum==0)Place.owner[placeNum]="A";
                else if(playerNum==1)Place.owner[placeNum]="B";
                else if(playerNum==2)Place.owner[placeNum]="C";
                else if(playerNum==3)Place.owner[placeNum]="D";
                System.out.println(playerNum+" "+Place.owner[placeNum]);//測試用
                System.out.println("購買成功");
            }
        }
    }
    ///////////////////////////////////////
    //骰子----Not FIN(return 要改 total)
    public static class Dice{
        public static int d1 = 0, d2 = 0, total = 0;
        public static int Dice(){
            Scanner sc=new Scanner(System.in);//建立Scanner物件
            System.out.print("按Enter骰骰子");
            String a = sc.nextLine();//取得a()

            d1 = (int) (Math.random() * 5) + 1;
            d2 = (int) (Math.random() * 5) + 1;
            total = d1 + d2;
            //return 3; //測試神社領錢用
            return 11; //測試機會籤
            //return total;

        }
    }
    ///////////////////////////////////////
    //玩家----FIN
    public static class Player {
        public static String[] name_player={"A","B","C","D"};//玩家名稱
        public static int[] jail={0,0,0,0};//玩家名稱
        public static int[] money={100000,100000,100000,100000};//玩家資產
        public static int[] player_stay={0,0,0,0};
    }
    ///////////////////////////////////////
    //儲存地點資料----FIN
    public static class Place{
        public static String[] place =//地名
                {"起點", "大分", "佐賀", "稻荷神社", "沖繩", "熊本", "長崎","福岡","九州", "宮崎","十勝支廳","機會籤", "檜山支廳", "渡島支廳",
                        "石狩支廳", "防疫隔離", "北海道","根室支廳", "西野神社",  "網走支廳", "舉辦祭典", "機會籤", "德島",
                        "春日大社", "香川", "高知",  "愛媛","四國", "奈良", "靜岡","千葉", "埼玉", "名古屋","京都", "大阪",
                        "國內航線",  "本州", "淺草寺", "國稅廳", "東京"};
        public static String[] owner =//擁有者
                {"國有", "", "", "國有", "", "", "", "", "", "", "","國有","",
                        "",  "", "國有", "", "", "國有","","國有","國有","",
                        "國有", "", "", "", "", "", "", "", "", "","","",
                        "國有", "",  "國有", "國有", ""};
        public static int[] pass_money =//過路費
                {0,200,200,0,600,600,600,800,10000,800,1400,0,1400,2000,2400,0,25000,3200,0,3500,0,0,
                        4200,0,4400,4800,4800,50000,5000,5000,5200,5200,5800,6000,6500,0,99999,0,0,7200};
    }
    ///////////////////////////////////////
    //機會卡(解釋)資料----FIN
    public static class Chance{
        public static String[] chance ={"木魅","鼬","日和坊","大首","滑鬼頭",
                "平安御守","財富御守","萬寶槌","御朱印","學業成就","御神籤",
                "開心旅遊票卷","搭乘JR 特急列車","一元","祭典活動頭獎","想好好泡湯",
                "確診","神社參拜祈福","奈良餵鹿","購買御守","去泡高級黃金溫泉",
                "神明眷顧","祭典活動中獎","路上撿錢","暴風雨來襲","赤舌","破除妖魔矢",
                "八咫鏡","天叢雲劍(草薙)", "八尺瓊勾玉(八阪瓊曲玉)"
        };
        public static String[] explain={"每個人付200円（給抽到這張卡的人）","扣2000円","獲得200円",
                "原地暫停一回合","每個人付2000円（給抽到這張卡的人）","逃離隔離區一次","再骰一次骰子，獲得點數*300円",
                "瞬間移動到空地地方(算經過起點一次)","支付500円，立刻前往隨機神社的位置","得到好分數，獎勵5000円",
                "感受到神明的旨意，立刻獲得任一空地","立刻前往國內航線，免付一次旅遊費，立即移動","再骰一次，且點數*2",
                "一元復始，立刻回到起點","國內航線機票一張，立刻移動至國內航線","立刻移動至大分","進入隔離區，並支付隔離飯店費1000円",
                "支付5000円","支付500円買餅乾","支付200円","支付15000円","撿到15000円","獲得10000円","獲得800円",
                "再擲一次骰子  雙數:得到救助金100*點數  單數:困在飯店支付100*點數","再擲一次骰子  雙數:奪取所有人100*點數  單數:支付所有人 100*點數",
                "再骰一次骰子，領到500*點數的$","照出真相，移動到任意位置","呼風喚雨，將別人的地夷為平地","慈悲為懷，捐獻自己的地給任一(指定)對手"};
    }
}
