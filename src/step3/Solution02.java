package step3;

import java.util.Random;
import java.util.Scanner;

public class Solution02 {
    public static void main(String[] args) {
        // switch
        Scanner sc = new Scanner(System.in);

        // if 문으로 구현
        // size. S. M. L.
//        System.out.print("사이즈가 어떻게 되세요? (S, M, L) : ");
//        String size = sc.nextLine(); // new String으로 새로 만들어진 값이라...

        //        int price = 0;
//        if (size.equals("S")){
//            System.out.println("사이즈가 작은 편이시네요.");
//        } else if (size.equals("M")) {
//            System.out.println("사이즈가 덜 작은 편이시네요.");
//        } else if (size.equals("L")) {
//            System.out.println("사이즈가 덜덜 작은 편이시네요.");
//        } else {
//            System.out.println("그런 사이즈는 없습니다.");
//        }
//        System.out.println(size + "사이즈 이시군요!");



//        int price = 0;
//        if (size == "S"){
//            System.out.println("사이즈가 작은 편이시네요.");
//        } else if (size == "M") {
//            System.out.println("사이즈가 덜 작은 편이시네요.");
//        } else if (size == "L") {
//            System.out.println("사이즈가 덜덜 작은 편이시네요.");
//        } else {
//            System.out.println("그런 사이즈는 없습니다.");
//        }
//        System.out.println(size + "사이즈 이시군요!");

        // 예약어, 키워드
//        switch (size) {
//            case "S":
//                System.out.println("사이즈가 작은 편이시네요.");
//                break;
//            case "M":
//                System.out.println("사이즈가 덜 작은 편이시네요.");
//                break;
//            case "L":
//                System.out.println("사이즈가 덜덜 작은 편이시네요.");
//                break;
//            default:
//                System.out.println("그런 사이즈는 없습니다.");
                // fall through - 하나의 조건을 만족시켰는데 break가 없으면
                // 다른 조건도 다 만족했다고 판정하고 모두 실행시켜버림



        // 조건문 -> if. switch. 삼항조건연산자.
        // 연산자? - 피연산자. -> 몇 개임? 2개.
        // 사칙연산 a ? b -> ... 이항연산자.
        // 단항연산자 !(not). ++-- (증감연산자)
        // 삼항연산자? condition ? 참일 때의 값 : 거짓일 떄의 값 -> &&, ||.



        Random r = new Random();
        int num = r.nextInt(10); // 0(포함), 10(미포함) 0~9까지의 값을 불러옴
        System.out.println(num);
//          if (num >= 5) {
//              System.out.println("당첨!");
//          } else {
//              System.out.println("탈락!");
//          }
//          System.out.println(num >= 5 ? "당첨!" : "탈락!");
        // 특정한 조건이 true/false로 명확하게 나뉘고 -> 값을 가질 경우
        
        int num2 = 0;
        System.out.println(num >= 5 ? num2++ : num2--);
        System.out.println(num2);

        // java, javascript, c.
        // {condition} ? {when true} : {when false}
        // {when true} if {condition} else {when false} (python)

    }

}

