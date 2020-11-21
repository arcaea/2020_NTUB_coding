package com.company;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
	// write your code here
        Dice.Dice();
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
        String[] diceUML ={"file:///C:/Users/user/Desktop/%E5%9C%96/gif/Anime-Gifs.gif","file:///C:/Users/user/Desktop/%E5%9C%96/gif/oLTQoDf.gif",
                "file:///C:/Users/user/Desktop/%E5%9C%96/gif/200.gif","file:///C:/Users/user/Desktop/%E5%9C%96/gif/179026.gif",
                "file:///C:/Users/user/Desktop/%E5%9C%96/gif/200%20(1).gif","file:///C:/Users/user/Desktop/%E5%9C%96/gif/a7148d7107e74ff4ad43d2e6b74d6c53b872bad484b2d-s5skzh.gif"};

        int d1 = 0,d2=0;
        d1 = (int)(Math.random()*6);
        d2 = (int)(Math.random()*6);

        URL url1 = new URL(diceUML[d1]);//"<URL to your Animated GIF>"
        Icon icon1 = new ImageIcon(url1);
        JLabel label1 = new JLabel(icon1);

        URL url2 = new URL(diceUML[d2]);//"<URL to your Animated GIF>"
        Icon icon2 = new ImageIcon(url2);
        JLabel label2 = new JLabel(icon2);

        JFrame f = new JFrame("Animation");
        f.getContentPane().add(label1);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        JFrame f2 = new JFrame("Animation");
        f2.getContentPane().add(label2);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.pack();
        f2.setLocationRelativeTo(null);
        f2.setVisible(true);
    }
}