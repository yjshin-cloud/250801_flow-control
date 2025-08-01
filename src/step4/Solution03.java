package step4;

import java.util.Random;
import java.util.Scanner;

public class Solution03 {
    // 반복문 (for, while)
    public static void main(String[] args) {
        // while
        // () -> 특정한 조건이 만족될 때까지 반복
        // true인 동안... 계속 반복
//        boolean condition = true;
//        while (condition) {
//            System.out.println("반복중입니다");
//            condition = false; // 종료
//        }
//        System.out.println("반복이 완료 되었습니다");
        // 반복이 '회수'가 정해지지 않을 때 쓴다.
        // (횟수가 정해져 있을 때 쓰는게 따로 있어요?)
        // 반복의 종료가 '내부 로직'과 관련이 있다 -> while
        // 반복의 종료가 별도로 외부의 조건과 증감과 관련이 있다 -> for
        String answer = "비밀";
        Scanner sc = new Scanner(System.in);
//        while (!answer.equals("비밀")){
//            System.out.println("secret의 뜻이 뭐야? : ");
//            answer = sc.nextLine();
//        }
//        System.out.println("맞췄어!");
        // while 1: true한 값. (true로 판정되는 값)
        // -> java는 무조건 true??
        // for (;;) {}
//        while(true){
//            System.out.println("secret의 뜻이 뭐야? : ");
//            answer = sc.nextLine();
//            if (answer.equals("비밀")) {
//                break; // 반복을 멈춘다
//            }
//        }
        Random r = new Random();
        while (true) {
            int v = r.nextInt(64); // 0~63
            // [
            // 뒤에 도달하지 않고 루프시키는 게 없을까?
            System.out.println("당신이 뽑은 숫자는 ! : ");
            // ]
            // 여기에 도달하면 멈추는 것
            if(v != 4) {
                continue; // 다음 루프로 진행한다.
            }
            // 4일때만 아래로 도달.
            break;
//            if (v == 4) {
//                break;
//            }
        }
    }
}
