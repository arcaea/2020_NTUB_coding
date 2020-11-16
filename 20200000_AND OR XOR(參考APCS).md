題目
----
AND
|0|0|
|---|---|
|0|1|

OR
|0|1|
|---|---|
|1|1|

XOR
|0|1|
|---|---|
|1|0|


精簡版
----
```java
public class Main {

    public static void main(String[] args) {
        int a,b,c;  //
        int and,or,xor;///
        Scanner sc=new Scanner(System.in); //建立Scanner
        System.out.println("請輸入a,b,c以空白隔開:");
        a=sc.nextInt(); //抓a
        b=sc.nextInt(); //抓b
        c=sc.nextInt(); //抓c
        
        and=a & b;
        or= a|b;
        xor=a^b;

        if(c==and){
            System.out.println("and");
        }
        if(c==or){
            System.out.println("or");
        }
        if(c==xor){
            System.out.println("xor");
        }
    }
}
```
IF暴力解決法
----
```java
public class Main {
    public static void main(String[] args) {
        // write your code here
        //----------------------
        // 區域變數 local variable
        int a,b,c;//宣告變數
        java.util.Scanner sc=new java.util.Scanner(System.in);//建立Scanner物件
        //----------------------
        //----------------------
        //程式主體
        System.out.print("輸入a,b,c(以空白隔開)=");
        a=sc.nextInt();//取得a值
        b=sc.nextInt();//取得b值
        c=sc.nextInt();//取得c值

        if(a>=0 && b>=0){//a,b為非負整數
            if(c==0 || c==1){//c只能0或1
                if(a==0) {
                    if (b == 0) {
                        if (c == 0) {
                            System.out.println("AND");
                            System.out.println("OR");
                            System.out.println("XOR");
                        }else{
                            System.out.println("IMPOSSIBLE");
                        }
                    }else{
                        if(c==0){
                            System.out.println("AND");
                        }else{
                            System.out.println("OR");
                            System.out.println("XOR");
                        }
                    }
                }else{
                    if (b == 0) {
                        if (c == 0) {
                            System.out.println("AND");
                        }else{
                            System.out.println("OR");
                            System.out.println("XOR");
                        }
                    }else{
                        if(c==0){
                            System.out.println("XOR");
                        }else{
                            System.out.println("AND");
                            System.out.println("OR");
                        }
                    }
                }
            }
        }
    }
}
```
