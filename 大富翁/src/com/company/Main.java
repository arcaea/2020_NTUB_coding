package com.company;
package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        // write your code here
        Dice.Dice();//測試用
    }
}
///////////////////////////////////////
//玩家
class Player {
    public static int[] num_player={1,2,3,4};//玩家
    public static String[] name_player={"","","",""};//玩家名稱
    public static long[] money={0,0,0,0};//玩家資產
    public static int[][] own_place={new int[40],new int[40],new int[40],new int[40]};//玩家擁有地
    public static int[] mul_pass_money_player={1,1,1,1};//過路費倍數(玩家部分)
}
///////////////////////////////////////
//地點
class Place {

    //儲存地點資料
    public static void Place() {
        int[] num_place = //地名編號
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                        21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                        31, 32, 33, 34, 35, 36, 37, 38, 39, 40};
        String[] place =//地名
                {"起點", "大分", "佐賀", "鹿兒島", "沖繩", "九州", "熊本", "長崎", "福岡", "宮崎",
                        "防疫隔離", "十勝支廳", "日高支廳", "檜山支廳", "渡島支廳", "北海道", "石狩支廳", "空知支廳", "根室支廳", "網走支廳",
                        "舉辦祭典", "宗谷支廳", "留萌支廳", "德島", "香川", "四國", "高知", "愛媛", "奈良", "靜岡",
                        "國內航線", "千葉", "埼玉", "京都", "本州", "橫濱", "大阪", "國稅廳", "東京"};
        String[] owner =//擁有者
                {"國有", "", "", "", "", "", "", "", "", "",
                        "國有", "", "", "", "", "", "", "", "", "",
                        "國有", "", "", "", "", "", "", "", "", "",
                        "國有", "", "", "", "", "", "", "", ""};
        int[] cnt_house =//房子數量
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] pass_money =//過路費
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] mul_pass_money =//過路費倍數(房價部分)
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    }

    public static void Place(int num_player,int num_place) {

    }
}
///////////////////////////////////////
//骰子
class Dice{
    public static void Dice() throws MalformedURLException {

String[] diceUML={"src/assets/touzidice1.gif","src/assets/touzidice2.gif",
        "src/assets/touzidice3.gif","src/assets/touzidice4.gif",
        "src/assets/touzidice5.gif","src/assets/touzidice6.gif",};

        int d1 = 0,d2=0;
        d1 = (int)(Math.random()*5)+1;
        d2 = (int)(Math.random()*5)+1;

        File dice1 = new File(diceUML[d1]);
        File dice2 = new File(diceUML[d2]);


        Icon icon1 = new ImageIcon(String.valueOf(dice1));
        JLabel label1 = new JLabel(icon1);

        Icon icon2 = new ImageIcon(String.valueOf(dice2));
        JLabel label2 = new JLabel(icon2);


        JFrame f = new JFrame("Animation");
        Container cp=f.getContentPane();
        cp.add(f.getContentPane().add(label1), BorderLayout.EAST);
        cp.add(f.getContentPane().add(label2), BorderLayout.WEST);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
