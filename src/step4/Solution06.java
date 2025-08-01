package step4;

import java.util.Arrays;

public class Solution06 {
    // 반복문 + 배열, forEach
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]); // 배열명[인덱스번호]
        }
        int sum = 0; // 반복되는 외부에 합을 처리할 변수를 두고,
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i]; // 각각의 변수들을 합을 시킨 다음에
        }
        System.out.println(sum); // 최종적으로 외부에서 합처리가 완료된 변수를 호출

        int[][] arr2 = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
        };
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr2[i].length; j++) {
//                System.out.println(arr2[i][j]);
                System.out.print(arr2[i][j] + " ");
            }
            System.out.println();
        }

        // 향상된 for문 (for-each) jdk 1.5
        // (해당 개별 데이터를 호출할 타입과 변수명 : 반복되는 형태의 데이터)
        for (int v : arr){ // 첫 번째 순서부터 차례대로 호출해서... v에 '개별적'으로 주입.
            System.out.println(v);
            // v는 해당 루프(반복) 내에서만 유지되는 변수
            // -> 새롭게 꺼내서 대입/할당
        }

        for (int i = 0; i < arr.length; i++){
            // 내부에서 i에 개입하면 영향을 받음
            i++; // 일종의 내부에 증감식/변화식 영향을 미칠 수 있음
            System.out.println(arr[i]);
            i = 0; // 이 블럭 내에서 i가 1개로 계속 유지가 되면서.. 루프로 도는 것
        }

        int[] ex = {5, 10, 15, 20, 25};
        for (int v : ex){ // 인덱스에 대한 정보를 주지 않음
            System.out.println(v);
            // v를 사용해서 앞뒤의 값을 수정할 수 있을까?
        }
        for (int i = 0; i < ex.length; i++){
            if (ex[i] == 10) {
                ex[i-1] *= 10;
                ex[i+1] *= 10;
            }
        }
        System.out.println(Arrays.toString(ex));
        int[] ex2 = {1, 2, 3, 4, 5};
        for (int i = 0; i < 5; i++) {
            System.out.println(ex[i] * ex2[i]);
        }
        // 향상된 for문은 할 수가 없다.

        // 일반적으로 for-each가 더 효율적이거나 간단하거나 선호되지만...
        // index를 가지고 같은 배열 내의 값을 호출, 수정되는 상황
        // 다른 여러 배열들, 반복되는 값을 한 루프 안에서 같이 호출하는 상황
        // -> index -> 일반 for문.
    }
}
