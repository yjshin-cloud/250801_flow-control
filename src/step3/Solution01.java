package step3;

import java.util.Scanner;

public class Solution01 {
    // main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("당신의 발사이즈는 몇 입니까? (mm) : ");
        // 한글로 대략 적고 -> 우클릭 -> 번역 및 교체
        int footSize = sc.nextInt();
        // 숫자로 변환된 값 -> int -> footSize
        // footSize는 이제 다시 '선언' X
        System.out.println("당신의 발사이즈는 " + footSize + "입니다.");

        System.out.println("당신의 발사이즈는 %d mm 입니다." .formatted(footSize));

        // (조건) { 조건을 만족시켰을 때 실행될 코드(구문) }
        // 200mm 넘는 신발 X. 300mm 넘는 신발도 X.
//        if (footSize < 200) { // < 미만, > 초과, <= 이하, >= 이상.
//            // 조건이 true로 평가 되었을 경우
//            System.out.println("200mm 미만입니다");
//        } else {
//            // 조건이 false로 평가 되었을 경우
//            System.out.println("신발이 존재합니다");
//        }
//        if (footSize > 300) { // < 미만, > 초과, <= 이하, >= 이상.
//            System.out.println("300mm 초과입니다");
//        }
//
//        if (footSize >= 200 && footSize <= 300) {
//            System.out.println("신발이 존재합니다.");
//        }

        if (footSize < 200) {
            // 이 조건을 만족시키는 경우가 이미 실행이 되서 뒤로 진척이 안나감
            System.out.println("200mm 미만입니다");
        } else if (footSize > 300) {
            // 앞의 if를 만족시키지 않아서 else로 떨어진 상황에서, 또 다시 if를 하는 것
            System.out.println("300mm 초과입니다");
        } else {
            // footSize >= 200 && footSize <= 300
            // !(footSize < 200) || !(footSize > 300)  : 논리연산
            System.out.println("신발이 존재합니다");
            // 블록은 중첩이 된다
            System.out.print("소지금이 얼마입니까? : ");
            // {} 밖에 있는 변수는 내부에서 호출 가능
            int money = sc.nextInt();
            System.out.println("당신의 소지금은 %d 원입니다".formatted(money));
            if (money < 50000) {
                System.out.println("살 수 있는 신발이 없습니다");
            } else {
                System.out.println("잘 오셨어요~ ");
                // 여기에 있으면 위에 조건과 상관없이...
            }
        }
    }
}
