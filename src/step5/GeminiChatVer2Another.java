package step5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.Scanner;

public class GeminiChatVer2Another {
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
        Random rd = new Random();
        while (true) {
            // 계속 반복
            System.out.print("원하시는 명령을 입력해주세요. (시작, 종료) : ");
            String cmd = sc.nextLine();
            if (cmd.equals("시작")) {
                System.out.println("상담 시작");
            } else if (cmd.equals("종료")) {
                System.out.println("상담 종료");
                break; // switch를 썼을 때는 switch 내부에 break가 있어서;;;;
            } else {
                System.out.println("잘못된 입력");
                continue;
            }
            System.out.println("상담을 시작합니다!");
            // 스무고개용 문제 출제를 요청하는 프롬프트를 보내서 문제를 받고,
            // 이전 답변들과 중복되지 않게 새로운 답변을 받게 해서... 스무고개를 반복/조건문으로...
            // Gemini -> 개발자용 API에다가 요청하는... / 계속 이전 요청사항을 기억해서 소통하는...
            // 이전사항? 어디에다가 저장할까요?

            // 여기서 요청을 보낼 예정.
            String[] prevResponse = new String[20];
            for (int i = 0;;i++) {
                System.out.print((i+1) + "번째 질문을 해주세요! : ");
                String userQuestion = sc.nextLine();
                if (userQuestion.equals("종료")) {
                    System.out.println("상담을 종료합니다!");
                    break;
                }
                String prompt = "";
                if (i == 0) {
                    prompt = "[%s]에 대해서 상담해줘.".formatted(userQuestion);
                } else {
                    String prevQuestion = "";
                    for (int j = 0; j < i; j++) { // 이전 질문 목록을 묶고.
                        prevQuestion += prevResponse[j];
                        prevQuestion += ", ";
                    }
                    prompt = """
                        본인이 IT 전문가라고 생각하고 해줘.
                        [%s]에 대해서 상담해줘.
                        직전에는 [%s] 이러한 대화를 나누었어.
                        나눈 대화에 대해서는 언급하지 말고 잠재적으로만 반영해줘.
                        결과는 50자를 넘지 않았으면 해.
                        마크다운 쓰지마.
                        """.formatted(userQuestion, prevQuestion);
                }
                // 3~5글자 단어
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(GEMINI_URL))
                        .headers("Content-Type", "application/json",
                                "X-goog-api-key", GEMINI_API_KEY)
                        .POST(HttpRequest.BodyPublishers.ofString(
                                bodyTemplate.formatted(prompt)
                        ))
                        .build();
                String aiResult = "";
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    aiResult = response.body()
                            .split("\"text\": \"")[1] // 0, 1, 2....
                            .split("}")[0]
                            .replace("\\n", "")
                            .replace("\"", "")
                            .trim();
//                    System.out.println(aiResult2);
                } catch (Exception e) {
                    System.err.println(e.getMessage()); // 에러가 날 경우 해당 메시지를 확인 (429? 403?)
                    // 403 : 키를 잘 못넣은거고
                    // 429 : 키를 너무 많이 쓴 것
                }
                System.out.println("AI의 대답 : " + aiResult);
                // 이게 빠짐;;;;
                prevResponse[i] = userQuestion;
            }
        }
    }
}