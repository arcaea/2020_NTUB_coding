jframe那個視窗出不來

```java
package com.company;
///////////////////////////////////////
//匯入
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
///////////////////////////////////////
//主程式
public class Main {
    ///////////////////////////////////////
    //全域變數
    static int P = 4;//玩家人數
    static char fullA=65313;//代號A(全形)
    static Player[] user = new Player[P];
    static Place[] pl = new Place[40];
    static Chance[] Ch = new Chance[6];
    static Scanner sc;//建立Scanner物件
    static JFrame f=new JFrame();

    public static void main(String[] args) {
        // write your code here
        sc = new Scanner(System.in);//建立Scanner物件
        System.out.print("按Enter開始遊戲");//輸出提示字串
        sc.nextLine();
        do {
            sc = new Scanner(System.in);
            System.out.print("輸入玩家人數：");//輸出提示字串
            P = sc.nextInt();//取得sc(玩家人數)
        } while (Main.P <= 1 || Main.P > 4);//限制2~4人

        for (int x = 0; x < Main.P; x++) {
            user[x] = new Player();
            user[x].player();
            System.out.print("玩家輸入名稱：");//輸出提示字串
            sc = new Scanner(System.in);//輸出提示字串
            user[x].setName(sc.next());//設定名稱
            user[x].setNo((char) (fullA + x));//設定代號
        }
        for(int y=0;y<40;y++){
            pl[y] = new Place();
            pl[y].setPlace(PlaceAr.place[y]);
            pl[y].setOwner(PlaceAr.owner[y]);
            pl[y].setPass_money(PlaceAr.pass_money[y]);
            pl[y].setPass_peopleA(PlaceAr.pass_peopleA[y]);
            pl[y].setPass_peopleB(PlaceAr.pass_peopleB[y]);
            pl[y].setPass_peopleC(PlaceAr.pass_peopleC[y]);
            pl[y].setPass_peopleD(PlaceAr.pass_peopleD[y]);
        }
        for(int z=0;z<6;z++){//機會卡初始設定
            Ch[z] = new Chance();
            Ch[z].setChance(ChanceAr.chance[z]);
            Ch[z].setExplain(ChanceAr.explain[z]);
        }
        for(int q=0;q<P;q++){
            System.out.print("玩家" + user[q].getName() + " 代號" + user[q].getNo() + " 資金" + user[q].getMoney() + "円                                  ");//輸出地圖
        }
        System.out.println();//斷行
        System.out.println("遊戲規則:剛開始每玩家各有十萬，" + user[0].getName() + "先開始。");//輸出提示字串
        while (true) {//無限迴圈
            try {
                map(5);
                //Setup JFrame
                new JFrame();
                Player_play();//玩家遊玩
            } catch (Exception e) {//抓取例外
                System.out.println(e.getMessage());
                break;//中斷無限迴圈
            }
        }
    }

    public void JFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        f = new JFrame("Richman");
        f.setSize(400, 300);
        f.setLocationRelativeTo(null);
    }

    ///////////////////////////////////////
    //玩家遊玩---FIN
    public static void Player_play() throws InterruptedException {
        //int stay;//測試用，玩家位置暫存
        for (int i = 0; i < Main.P; ) {//i=person，對應玩家
            if (user[i].getGetOut() == 0) {//玩家尚未破產
                if (user[i].getJail() == 0) {//沒有進入防疫隔離
                    noMoney(i);//偵測是否破產
                    move(i);//骰骰子後移動
                    if (i < Main.P - 1) i++;//換下一位
                    else i = 0;//回到第一位
                    System.out.print("換下一位玩家：" + user[i].getName() + "，");//輸出提示字串
                } else {//進入防疫隔離
                    noMoney(i);//偵測是否破產
                    System.out.println("因染上疫情，休息一輪!");//輸出提示字串
                    user[i].setJail(0);//離開防疫隔離
                    if (i < Main.P - 1) i++;//換下一位
                    else i = 0;//回到第一位
                    System.out.print("換下一位玩家：" + user[i].getName()+ "，");//輸出提示字串
                }
            } else if (user[i].getGetOut() == 1) {//玩家已破產
                if (i < Main.P - 1) i++;//換下一位
                else i = 0;//回到第一位
                System.out.print("換下一位玩家：" + user[i].getName() + "，");//輸出提示字串
                break;
            }
        }
    }
    ///////////////////////////////////////
    //骰子----FIN
    public static int dice() {
        Scanner sc = new Scanner(System.in);//建立Scanner物件
        System.out.print("按Enter骰骰子");//輸出提示字串
        sc.nextLine();//取得a(骰骰子)

        int d1 = (int) (Math.random() * 5) + 1;//1號骰數
        int d2 = (int) (Math.random() * 5) + 1;//2號骰數
        return d1+d2;//回傳總骰子數
    }
    ///////////////////////////////////////
    //破產----FIN
    public static void noMoney(int playerNum) throws InterruptedException {
        int cnt = 0;//變數，尚未破產人數
        if (user[playerNum].getMoney() < 0) {//當玩家資金
            System.out.println(user[playerNum].getName() + "破產~~~");//輸出提示字串
            user[playerNum].setGetOut(1);//破產
            for (int i = 0; i < 40; i++) {
                if (pl[i].getOwner()==user[playerNum].getNo()) {//判斷所屬地是否為破產者
                    pl[i].setOwner((char)12288);//將其地轉為空地
                }
            }
        }
        for (int i = 0; i < Main.P; i++) {
            if (user[i].getGetOut() == 0) cnt++;//計算尚未破產人數
        }
        if (cnt == 1) {//當玩家剩餘人數為1
            System.out.println("其他玩家全財産を失う");//輸出提示字串
            Thread.sleep(5000);//暫停5秒
            System.exit(0);//離開程式
        }
    }

    ///////////////////////////////////////
    //玩家移動---FIN

    public static void mapMoveBefore(int playerNum) {//地圖上移動
        switch (playerNum) {
            case 0://玩家A
                pl[user[playerNum].getStay()].setPass_peopleA(0);//玩家A離開此地
                break;
            case 1://玩家B
                pl[user[playerNum].getStay()].setPass_peopleB(0);//玩家B離開此地
                break;
            case 2://玩家C
                pl[user[playerNum].getStay()].setPass_peopleC(0);//玩家C離開此地
                break;
            case 3://玩家D
                pl[user[playerNum].getStay()].setPass_peopleD(0);//玩家D離開此地
                break;
            default:
                break;//避免不必要的錯誤
        }
    }
    public static void mapMoveAfter(int playerNum) {//地圖上移動
        switch (playerNum) {
            case 0://玩家A
                pl[user[playerNum].getStay()].setPass_peopleA(1);//玩家A進入此地
                break;
            case 1://玩家B
                pl[user[playerNum].getStay()].setPass_peopleB(1);//玩家B進入此地
                break;
            case 2://玩家C
                pl[user[playerNum].getStay()].setPass_peopleC(1);//玩家C進入此地
                break;
            case 3://玩家D
                pl[user[playerNum].getStay()].setPass_peopleD(1);//玩家D進入此地
                break;
            default:
                break;//避免不必要的錯誤
        }
    }
    public static void move(int playerNum) throws InterruptedException {//普通移動
        int roll = dice();//呼叫骰子函式
        //System.out.println(roll);//測試用
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        user[playerNum].setStay(user[playerNum].getStay()+roll);//玩家原位置+骰到的點數
        checkPassStart(playerNum);//檢查是否經過起點
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        //wrong
        checkPlace(playerNum, user[playerNum].getStay());//檢查地點歸屬
        //
        map(5);
    }

    public static void airMove(int playerNum) throws InterruptedException {//國內航線
        Scanner sc = new Scanner(System.in);//建立Scanner物件
        System.out.print("輸入你所要往前的格子編號：");//輸出提示字串
        int s = sc.nextInt();//取得s(格子數)
        //System.out.println(s);//測試用
        mapMoveBefore(playerNum);//呼叫地圖上移動函式
        user[playerNum].setStay(user[playerNum].getStay()+s);//將玩家位置換成玩家所要往前的格子編號
        if (user[playerNum].getStay() < 35) {//當格子數<35，將經過起點
            user[playerNum].setMoney(user[playerNum].getMoney()+10000);//給予玩家10000円
        }
        mapMoveAfter(playerNum);//呼叫地圖上移動函式
        checkPlace(playerNum, user[playerNum].getStay());//檢查地點歸屬
    }
    ///////////////////////////////////////
    //起點經過確認---FIN
    public static void checkPassStart(int playerNum) {
        if (user[playerNum].getStay() >= 40) {//當玩家位置>40時
            user[playerNum].setStay(user[playerNum].getStay()-40);//玩家位置轉換成<40的數
            user[playerNum].setMoney(user[playerNum].getMoney()+10000);//給予經過起點費用
        }
    }
    ///////////////////////////////////////
    //地點歸屬確認---FIN
    public static void checkPlace(int playerNum, int placeNum) throws InterruptedException {
        if (pl[placeNum].getOwner()==12288) {//地點無歸屬
            buyPlace(playerNum, placeNum);//呼叫購買函式
        } else if (pl[placeNum].getOwner()==23) {//地點屬於國有
            SOL(playerNum, placeNum);//呼叫國有函式
        } else {
            pay(playerNum, placeNum);//呼叫付錢函式
        }
    }
    ///////////////////////////////////////
    //買地----FIN
    public static void buyPlace(int playerNum, int placeNum) {
        Scanner sc = new Scanner(System.in);//建立Scanner物件
        System.out.print("你是否買" + pl[placeNum].getPlace() + "(Y-要買;N-不買)：");//輸出提示字串
        char c = sc.next().charAt(0);//取得c(Y/N)
        if (Character.toUpperCase(c) == 'Y') {//將取得字串轉大寫並判斷
            if (user[playerNum].getMoney() < pl[placeNum].getPass_money()) {//所持資金<地價
                System.out.println("你沒錢，無法購買");//輸出提示字串
            } else {
                pl[placeNum].setOwner(user[playerNum].getNo());//玩家位置暫存
                //System.out.println(playerNum + " " + Place.owner[placeNum]);//測試用
                user[playerNum].setMoney(user[playerNum].getMoney()-pl[placeNum].getPass_money());//玩家扣除地價費用
                System.out.println("購買成功");//輸出提示字串
            }
        }
    }
    ///////////////////////////////////////
    //付錢----FIN
    public static void pay(int playerNum, int placeNum) {
        user[playerNum].setMoney(user[playerNum].getMoney()-pl[placeNum].getPass_money());//遊玩玩家扣除過路費

        System.out.println(user[playerNum].getName()+"支付過路費："+pl[placeNum].getPass_money());//輸出提示字串

        switch (pl[placeNum].getOwner()) {//檢查地點歸屬
            case 65313://地點歸屬於A
                user[0].setMoney(user[0].getMoney()+pl[placeNum].getPass_money());//玩家A增加錢
                System.out.println(user[0].getName()+"獲得過路費："+pl[placeNum].getPass_money());//輸出提示字串
                break;
            case 65314://地點歸屬於B
                user[1].setMoney(user[1].getMoney()+pl[placeNum].getPass_money());//玩家B增加錢
                System.out.println(user[1].getName()+"獲得過路費："+pl[placeNum].getPass_money());//輸出提示字串
                break;
            case 65315://地點歸屬於C
                user[2].setMoney(user[2].getMoney()+pl[placeNum].getPass_money());//玩家C增加錢
                System.out.println(user[2].getName()+"獲得過路費："+pl[placeNum].getPass_money());//輸出提示字串
                break;
            case 65316://地點歸屬於D
                user[3].setMoney(user[3].getMoney()+pl[placeNum].getPass_money());//玩家D增加錢
                System.out.println(user[3].getName()+"獲得過路費："+pl[placeNum].getPass_money());//輸出提示字串
                break;
            default: break;//避免不必要的錯誤
        }
    }
    ///////////////////////////////////////
    //走到國有地----FIN
    public static void SOL(int playerNum, int placeNum) throws InterruptedException {
        switch (pl[placeNum].getPlace()) {//檢查國有地為何
            case "國內航線"://檢查國有地為國內航線
                Scanner sc = new Scanner(System.in);//建立Scanner物件
                System.out.print("你是否搭乘國內航線，費用50000円(Y-搭乘;N-不搭乘)：");//輸出提示字串
                char q = sc.next().charAt(0);//取得q(Y/N)
                if (Character.toUpperCase(q) == 'Y') {//當q為y或Y
                    if (user[playerNum].getMoney() < 50000) {//當玩家資金<50000
                        System.out.println("無法搭乘");//輸出提示字串
                    } else {
                        user[playerNum].setMoney(user[playerNum].getMoney()-50000);//當玩家資金扣除50000
                        airMove(playerNum);//呼叫國內航線函式
                    }
                }
                break;
            case "防疫隔離"://檢查國有地為防疫隔離
                if (user[playerNum].getJail() == 0)//當進入防疫隔離
                    user[playerNum].setJail(1);//防疫一天
                break;
            case "國稅廳"://檢查國有地為國稅廳
                int tax = user[playerNum].getMoney() * 8 / 100;//變數，資產稅
                System.out.print("付了資產稅(資產的8%):" + tax);//輸出提示字串
                user[playerNum].setMoney(user[playerNum].getMoney()-tax);//玩家扣除資產稅
                System.out.println("剩餘：" + user[playerNum].getMoney());//輸出提示字串
                break;
            case "機會籤"://檢查國有地為機會籤
                chanceRoll(playerNum);//呼叫機會籤函式
                break;
            case "舉辦祭典"://檢查國有地為舉辦祭典
                int tax2 = user[playerNum].getMoney() / 2;//變數，祭典資金
                System.out.print("籌備祭典資金(資產的50%)，花了:" + tax2);//輸出提示字串
                user[playerNum].setMoney(user[playerNum].getMoney()-tax2);//玩家扣除祭典資金
                System.out.println("剩餘：" + user[playerNum].getMoney());//輸出提示字串
                break;
            case "稻荷神社", "西野神社", "春日大社", "淺草寺"://檢查國有地為神社
                System.out.print("再骰一次骰子，領取點數*200薪水");//輸出提示字串
                int temple_money = dice();//呼叫骰子函式
                //System.out.println("before" + Player.money[playerNum]);//測試用 查看玩家的資產
                user[playerNum].setMoney(user[playerNum].getMoney()-(200 * temple_money));//玩家增加資產
                //System.out.println("after" + Player.money[playerNum]);//測試用 查看玩家的資產
                break;
            default:
                break;//避免不必要的錯誤
        }
    }
    ///////////////////////////////////////
    //機會籤----FIN
    public static void chanceRoll(int playerNum) throws InterruptedException {
        int ch = (int) (Math.random() * 4);//抽取機會卡
        System.out.print("機會卡：");//輸出提示字串
        Thread.sleep(1000); // 暫停1秒
        switch (ch) {
            case 0,1,2,3,4://機會卡0
                user[playerNum].setMoney(user[playerNum].getMoney()+ChanceAr.money[ch]);//因為機會卡錢的變化
                noMoney(playerNum);//呼叫檢查破產函式
                map(ch);//顯示地圖
                break;
            default:break;
        }
    }
    ///////////////////////////////////////
    //地圖----FIN
    public static void map(int ch) {
        String[] str = new String[40];//所有地玩家位置暫存
        String[] own = new String[40];//玩家所有地
        for (int i = 0; i < 40; i++) {
            str[i] = "";
            if (pl[i].getPass_peopleA() == 1 && P >= 1) str[i] += "Ａ";
            if (pl[i].getPass_peopleB() == 1 && P >= 2) str[i] += "Ｂ";
            if (pl[i].getPass_peopleC() == 1 && P >= 3) str[i] += "Ｃ";
            if (pl[i].getPass_peopleD() == 1 && P >= 4) str[i] += "Ｄ";
            str[i] += ("　".repeat(6 - str[i].length()));//補滿全行空白

            if (!(pl[i].getOwner() == 23)) own[i] = Character.toString(pl[i].getOwner());//非國有地才顯示
            else if (pl[i].getOwner() == 0) own[i] = "　　";
            else own[i] = "　";//國有地不顯示
        }



            //Build Elements
            Object[][] data = {
                    {"２０舉辦祭典", "２１機會籤　", "２２德島　　", "２３春日大社", "２４香川　　", "２５高知　　", "２６愛媛　　", "２７四國　　", "２８奈良　　", "２９靜岡　　", "３０千葉　　", "３１埼玉　　", "３２名古屋　", "３３京都　　", "３４大阪　　", "３５國內航線"},
                    {"００００" + own[20], "００００" + own[21], "４２００" + own[22], "００００" + own[23], "４４００" + own[24], "４８００" + own[25], "４８００" + own[26], "５００００" + own[27], "５０００" + own[28], "５０００" + own[29], "５２００" + own[30], "５２００" + own[31], "５８００" + own[32], "６０００" + own[33], "６５００" + own[34], "００００" + own[35]},
                    {str[20], str[21], str[22], str[23], str[24], str[25], str[26], str[27], str[28], str[29], str[30], str[31], str[32], str[33], str[34], str[35]},
                    {"１９網走支廳", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "３６本州"},
                    {"３５００" + own[19], "", "", "", "", "", "", "", "", "", "", "", "", "", "", "９９９９９" + own[36]},
                    {str[19], "", "", "", "", "", "", "", "", "", "", "", "", "", "", str[36]},
                    {"１８西野神社", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "３７淺草寺"},
                    {"００００" + own[18], "", "", "", "", "", "", "", "", "", "", "", "", "", "", "0000" + own[37]},
                    {str[18], "", "", "", "", "", "", "", "", "", "", "", "", "", "", str[37]},
                    {"１７根室支廳", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "３８國稅廳"},
                    {"3200" + own[17], "", "", "", "", "", "", "", "", "", "", "", "", "", "", "0000" + own[38]},
                    {str[17], "", "", "", "", "", "", "", "", "", "", "", "", "", "", str[38]},
                    {"１６北海道", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "３９東京"},
                    {"20000" + own[16], "", "", "", "", "", "", "", "", "", "", "", "", "", "", "7200" + own[39]},
                    {str[17], "", "", "", "", "", "", "", "", "", "", "", "", "", "", str[39]},
                    {"１５防疫隔離", "１４石狩支廳", "１３渡島支廳", "１２檜山支廳", "１１機會籤　", "１１十勝支廳", "０９宮崎　　", "０８九州　　", "０７福岡　　", "０６長崎　　", "０５熊本　　", "０４沖繩　　", "０３稻荷神社", "０２佐賀　　", "０１大分　　", "００起點　　"},
                    {"００００　" + own[15], "２４００　" + own[14], "２０００　" + own[13], "１４００　" + own[12], "００００　" + own[11], "１４００　" + own[10], "０８００　" + own[9], "１００００" + own[8], "０８００　" + own[7], "０８００　" + own[6], "０６００　" + own[5], "０６００　" + own[4], "００００　" + own[3], "０２００　" + own[2], "０２００　" + own[1], "００００　" + own[0]},
                    {str[15], str[14], str[13], str[12], str[11], str[10], str[9], str[8], str[7], str[6], str[5], str[4], str[3], str[2], str[1], str[0]},
                    {"機會卡：", Ch[ch].getChance(), "-", Ch[ch].getExplain(), "", "", "", "", "", "", "", "", "", "", "", ""},
            };
            for (int q = 0; q < P; q++) {
                System.out.print("玩家" + user[q].getName() + " 代號" + user[q].getNo() + " 資金" + user[q].getMoney() + "円                                  ");//輸出地圖
            }
            String[] columns={"Name","Gender","Age","Vegetarian","E-mail"};
            JTable jt = new JTable(data,columns);
            jt.setPreferredScrollableViewportSize(new Dimension(400, 300));
            Container cp = f.getContentPane();
            cp.add(new JScrollPane(jt), BorderLayout.CENTER);
            f.setVisible(true);

            //Close JFrame
            f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            f.addWindowListener(new WindowHandler(f));
        }
    }

class WindowHandler extends WindowAdapter {
    JFrame f;
    public WindowHandler(JFrame f) {this.f=f;}
    public void windowClosing(WindowEvent e) {
        int result=JOptionPane.showConfirmDialog(f,
                "確定要結束程式嗎?",
                "確認訊息",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (result== JOptionPane.YES_OPTION) {System.exit(0);}
    }
}
//-----------------------------------
//-----------------------------------資料區
//-----------------------------------

///////////////////////////////////////
//玩家----FIN
class Player {
    private char no;//玩家代號
    private String name;//玩家名稱
    private int money;//玩家資產
    private int stay;//玩家所在地
    private int jail;//玩家是否被困住暫存 0:不受困 1:受困中
    private int getOut;//玩家是否破產暫存 0:遊戲中 1:破產

    public void player(){
        this.stay=0;//設定位置
        this.jail=0;//設定困住暫存
        this.money=100000;//發100000
        this.getOut=0;//設定破產暫存
    }

    public void setNo(char no){this.no=no;}//將資料存進變數
    public void setName(String name){this.name=name;}//將資料存進變數
    public void setMoney(int money){this.money=money;}//將資料存進變數
    public void setStay(int stay){this.stay=stay;}//將資料存進變數
    public void setJail(int jail){this.jail=jail;}//將資料存進變數
    public void setGetOut(int getOut){this.getOut=getOut;}//將資料存進變數

    public char getNo(){return this.no;}//回傳資料
    public String getName(){return this.name;}//回傳資料
    public int getMoney(){return this.money;}//回傳資料
    public int getStay(){return this.stay;}//回傳資料
    public int getJail(){return this.jail;}//回傳資料
    public int getGetOut(){return this.getOut;}//回傳資料
}

///////////////////////////////////////
//儲存地點資料----FIN
class Place {
    private String place;//地名
    private char owner;//擁有者(12288:空地 23:國有地 65:A 66:B 67:C 68:D)
    private int pass_money ;//地價、過路費
    private int pass_peopleA;//A在地圖上的所在地 1:此地 0:不在此地
    private int pass_peopleB;//B在地圖上的所在地 1:此地 0:不在此地
    private int pass_peopleC;//C在地圖上的所在地 1:此地 0:不在此地
    private int pass_peopleD;//D在地圖上的所在地 1:此地 0:不在此地

    public void setPlace(String place){this.place=place;}//將資料存進變數
    public void setOwner(char owner){this.owner=owner;}//將資料存進變數
    public void setPass_money(int pass_money){this.pass_money=pass_money;}//將資料存進變數
    public void setPass_peopleA(int pass_peopleA){this.pass_peopleA=pass_peopleA;}//將資料存進變數
    public void setPass_peopleB(int pass_peopleB){this.pass_peopleB=pass_peopleB;}//將資料存進變數
    public void setPass_peopleC(int pass_peopleC){this.pass_peopleC=pass_peopleC;}//將資料存進變數
    public void setPass_peopleD(int pass_peopleD){this.pass_peopleD=pass_peopleD;}//將資料存進變數

    public String getPlace(){return this.place;}//回傳資料
    public char getOwner(){return this.owner;}//回傳資料
    public int getPass_money(){return this.pass_money;}//回傳資料
    public int getPass_peopleA(){return this.pass_peopleA;}//回傳資料
    public int getPass_peopleB(){return this.pass_peopleB;}//回傳資料
    public int getPass_peopleC(){return this.pass_peopleC;}//回傳資料
    public int getPass_peopleD(){return this.pass_peopleD;}//回傳資料

}
///////////////////////////////////////
//機會卡(解釋)資料----FIN
class Chance {
    private String chance;//機會卡名稱
    private String explain;//機會卡名稱解釋

    public void setChance(String chance){this.chance=chance;}//將資料存進變數
    public void setExplain(String explain){this.explain=explain;}//將資料存進變數

    public String getChance(){return this.chance;}//回傳資料
    public String getExplain(){return this.explain;}//回傳資料
}

//-----------------------------------
//-----------------------------------資料區
//-----------------------------------

///////////////////////////////////////
//儲存地點資料----FIN
class PlaceAr {
    public static String[] place =//地名
            {"起點", "大分", "佐賀", "稻荷神社", "沖繩", "熊本", "長崎", "福岡", "九州", "宮崎", "十勝支廳", "機會籤", "檜山支廳", "渡島支廳",
                    "石狩支廳", "防疫隔離", "北海道", "根室支廳", "西野神社", "網走支廳", "舉辦祭典", "機會籤", "德島",
                    "春日大社", "香川", "高知", "愛媛", "四國", "奈良", "靜岡", "千葉", "埼玉", "名古屋", "京都", "大阪",
                    "國內航線", "本州", "淺草寺", "國稅廳", "東京"};
    public static char[] owner =//擁有者
            {23,12288, 12288, 23, 12288, 12288, 12288, 12288, 12288, 12288, 12288, 23, 12288,
                    12288, 12288, 23, 12288, 12288, 23, 12288, 23, 23, 12288,
                    23, 12288, 12288, 12288, 12288, 12288, 12288, 12288, 12288, 12288, 12288, 12288,
                    23, 12288, 23, 23, 12288};
    public static int[] pass_money =//地價、過路費
            {0, 200, 200, 0, 600, 600, 600, 800, 10000, 800, 1400, 0, 1400, 2000, 2400, 0, 25000, 3200, 0, 3500, 0, 0,
                    4200, 0, 4400, 4800, 4800, 50000, 5000, 5000, 5200, 5200, 5800, 6000, 6500, 0, 99999, 0, 0, 7200};
    public static int[] pass_peopleA =//A在地圖上的所在地
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static int[] pass_peopleB =//B在地圖上的所在地
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static int[] pass_peopleC =//C在地圖上的所在地
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static int[] pass_peopleD =//D在地圖上的所在地
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
}

///////////////////////////////////////
//機會卡(解釋)資料----FIN
class ChanceAr {
    public static String[] chance =//機會卡名稱
            {"當棉被一條","急難救助金","路上撿到錢","下大雨滑倒","想好好泡湯",""};
    public static String[] explain = //機會卡名稱解釋
            {"得到５００元","得到９９９元","得到３００元","損失５００元","付出４００元",""};
    public static int[] money =////機會卡錢的變化
            {500,999,300,-500,-400,0};
    //{"當棉被一條得到５００元","急難救助金得到９９９元","路上撿到錢得到３００元","下大雨滑倒損失５００元","想好好泡湯付出４００元"};  //預留存檔備用
}
```
