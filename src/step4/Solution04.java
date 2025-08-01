package step4;

// for문, Array.
public class Solution04 {
    public static void main(String[] args) {
        // for (초기식; 조건식; 증감식) { 반복할 코드 }
        // 조건식이 true인 동안에 작동 (예도 여전히 true일 때 작동)
        // i = index? iteration? integer?
        // 중첩 for문에서는 j를 쓰잖아? i 다음이라서?
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
//        }
        // 초기식, 조건식, 증감식(변화식)
        // 초기식
//        int w = 0;
//        while (w < 10) { // 조건식
//            // 변화식
//            System.out.println(w++);
////            w++;
//        }

        // 이런 접근도 가능하다
//        for (String s = ""; !s.equals("11111"); s += "1") {
//            System.out.println(s);
//        }

        int sum = 0;
//        for (int i = 0; i < 10; i++) {
//            sum += i + 1; // 연산자에선 사칙연산자가 대입연산자보다 우선권을 가짐
//        }
//        for (int i = 1; i < 11; i++) {
//            sum += i;
//        }
//        for (int i = 1; i <= 10; i++) {
//            sum += i;
//        }
//        int i = 0;
//        for (;;) {
//            sum += i;
//            i++;
//            if (i < 10) {
//                continue;
//            } else {
//                break;
//            }
//        }
//        while (true) {
//            sum += i;
//            i++;
//            if (i <= 10) {
//                continue;
//            } else {
//                break;
//            }
//        }
//        System.out.println(sum); // 55

//        String star = "";
//        for (int i = 0; i < 5; i++) {
//            star += "*";
//            System.out.println(star);
//            // *
//            // **
//            // ***
//            // ****
//            // *****
//        }
//        for (int i = 0; i < 5; i++) {
        for (int i = 1; i <= 5; i++) { // N
//            for (int i = 0; i < 5; i++) {
            System.out.println(i + "번째 줄");
//            for (int j = 0; j < 5; j++) {
//                System.out.print("*");
//            }
            for (int j = 0; j < i; j++) { // N^2
                System.out.print("*");
            }
//            System.out.println(j); // 바깥에서는 안쪽 블록에서 선언된 변수를 사용할 수 없음
            System.out.println(); // 이렇게 쓰면 그냥 줄바꿈
        }
        // 1중포문 -> 많이 쓰임
        // 2중포문 -> 어쩔 수 없이 써야만하는 로직이 있음. 중간에 백트래킹을 하거나 강제 종료, 투 포인터... 기법을 사용을 해서 하다가 멈추게 함.
        // 3중포문 이상 -> 바로 CTO님이 호출. - 복잡성이 높은 코드.
        // 데이터베이스나, 여러 가지 계층에서 전처리를 해서 해당 로직에서 웬만하면 2중 포문 초과로 넘어가게 하지 않음

//        int example = 0;
        int example; // 쓸 수가 없다.
//        example = 10;
        // 할당과 선언을 함께 한다 -> 초기화
        for (int i = 0; i < 10; i++) {
//            example
            example = 10;
            System.out.println(example);
            for (int j = 0; j < 10; j++) {
//                example
                for (int k = 0; k < 10; k++) {
//                    int example = 0;
                    System.out.println("%d %d %d".formatted(i, j, k));
                    // 중첩된 for문을 쓰게 되면
                    // 1. 가장 하위의 for문에서 상위 변수를 가져다 쓰거나
                    // 2. 아예 for문 밖에 선언을 하고, 안에서는 수정만 하게 해서 써야함
                }
            }
        }
//        System.out.println(example); // 할당이 될런지 안될런지 java가 헷갈려함.
        // 컴파일러 -> 어? 너 아무것도 할당 안했는데 example을 쓸꺼야? 나 동의 못해.

    }
}
