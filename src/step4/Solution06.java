package step4;

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
    }
}
