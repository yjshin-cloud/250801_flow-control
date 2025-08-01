package step5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GeminiChatVer2 {
    public static void main(String[] args) {
        // 반복문, 조건문?
        Scanner sc = new Scanner(System.in); // 반복문 내부에서 계속 사용해줄 scanner 초기화
        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
        System.out.println("GEMINI_API_KEY : " + GEMINI_API_KEY);
        // 요청을 보내기 위한 Client
        HttpClient client = HttpClient.newHttpClient();
        // https://ai.google.dev/gemini-api/docs/models?hl=ko
        // gemini-2.0-flash, gemini-2.5-flash, gemini-2.0-flash-lite ...
        String myModel = "gemini-2.0-flash-lite";
        // GEMINI API 주소
        String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent"
                .formatted(myModel);
        //
        String bodyTemplate = """
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }
                """;
        while (true) {
            // 계속 반복
            System.out.print("원하시는 명령을 입력해주세요. (시작, 종료) : ");
            String cmd = sc.nextLine();
            if (cmd.equals("시작")) {
                System.out.println("게임 시작");
            } else if (cmd.equals("종료")) {
                System.out.println("게임 종료");
                break; // switch를 썼을 때는 switch 내부에 break가 있어서;;;;
            } else {
                System.out.println("잘못된 입력");
                continue;
            }
            System.out.println("스무고개를 시작합니다!");
            // 스무고개용 문제 출제를 요청하는 프롬프트를 보내서 문제를 받고,
            // 이전 답변들과 중복되지 않게 새로운 답변을 받게 해서... 스무고개를 반복/조건문으로...
            // Gemini -> 개발자용 API에다가 요청하는... / 계속 이전 요청사항을 기억해서 소통하는...
            // 이전사항? 어디에다가 저장할까요?

            // 여기서 요청을 보낼 예정.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GEMINI_URL))
                    .headers("Content-Type", "application/json",
                            "X-goog-api-key", GEMINI_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(
                            bodyTemplate.formatted(
                                    "5글자 길이 이하의 스무고개용 띄어쓰기 없는 한글 단어 1개를 과정 없이 결과만 출력해줘."
                            )
                    ))
                    .build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
            } catch (Exception e) {
                System.err.println(e.getMessage()); // 에러가 날 경우 해당 메시지를 확인 (429? 403?)
                // 403 : 키를 잘 못넣은거고
                // 429 : 키를 너무 많이 쓴 것
            }
            String twenty = "고양이";

            System.out.println("스무고개 답은 %s입니다.".formatted(twenty));

//            System.out.print("원하시는 명령을 입력해주세요. (시작, 종료) : ");
//            String cmd = sc.nextLine();
//            switch (cmd) {
//                case "시작":
//                    System.out.println("스무고개를 시작하겠습니다.");
//                    break;
//                case "종료":
//                    System.out.println("스무고개를 종료하겠습니다.");
//                    break;
//                default:
//                    System.out.println("잘못된 입력입니다!");
//                    continue;
//            }
//            // ...
        }
    }
}
