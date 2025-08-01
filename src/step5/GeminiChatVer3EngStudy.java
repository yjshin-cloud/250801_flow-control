package step5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GeminiChatVer3EngStudy {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");

        if (GEMINI_API_KEY == null || GEMINI_API_KEY.isEmpty()) {
            System.out.println("GEMINI_API_KEY 환경변수를 설정해주세요.");
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        String myModel = "gemini-1.5-flash"; // 더 안정적인 모델로 변경
        String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent"
                .formatted(myModel);

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

        System.out.println("=== 영어 학습 도우미 프로그램 ===");

        while (true) {
            System.out.println("\n--- 학습 메뉴 ---");
            System.out.println("1. 단어 퀴즈");
            System.out.println("2. 영작 연습");
            System.out.println("3. 회화 연습");
            System.out.println("4. 문법 질문");
            System.out.println("5. 종료");
            System.out.print("메뉴를 선택하세요 (1-5): ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    wordQuiz(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "2":
                    writingPractice(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "3":
                    conversationPractice(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "4":
                    grammarQuestion(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "5":
                    System.out.println("프로그램을 종료합니다. 좋은 하루 되세요!");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 1-5 중에서 선택해주세요.");
                    continue;
            }
        }
    }

    // 단어 퀴즈 기능
    private static void wordQuiz(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n=== 단어 퀴즈 ===");
        System.out.print("난이도를 선택하세요 (초급/중급/고급): ");
        String level = sc.nextLine();

        String prompt = """
            영어 단어 퀴즈를 출제해줘.
            난이도: %s
            단어의 뜻을 한국어로 제시하고, 사용자가 영어 단어를 맞추는 형식으로 해줘.
            예시: 사과라는 뜻의 영어 단어는?
            한 번에 하나의 문제만 내줘.
            마크다운은 사용하지 마.
            """.formatted(level);

        String aiQuestion = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println(aiQuestion);

        System.out.print("답: ");
        String userAnswer = sc.nextLine();

        String checkPrompt = """
            다음 영어 단어 퀴즈에 대한 답을 채점해줘.
            문제: %s
            사용자 답: %s
            정답 여부와 함께 올바른 답과 간단한 설명을 제공해줘.
            50자 이내로 답해줘.
            마크다운은 사용하지 마.
            """.formatted(aiQuestion, userAnswer);

        String result = sendRequest(client, url, bodyTemplate, checkPrompt);
        System.out.println("결과: " + result);
    }

    // 영작 연습 기능
    private static void writingPractice(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n=== 영작 연습 ===");
        System.out.print("영작하고 싶은 한국어 문장을 입력하세요: ");
        String koreanSentence = sc.nextLine();

        System.out.print("영작해보세요: ");
        String userTranslation = sc.nextLine();

        String prompt = """
            다음 한국어 문장의 영작을 채점해줘.
            한국어: %s
            사용자 영작: %s
            문법적 오류와 더 자연스러운 표현을 제안해줘.
            80자 이내로 답해줘.
            마크다운은 사용하지 마.
            """.formatted(koreanSentence, userTranslation);

        String feedback = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("피드백: " + feedback);
    }

    // 회화 연습 기능
    private static void conversationPractice(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n=== 회화 연습 ===");
        System.out.print("회화 주제를 입력하세요 (예: 카페에서 주문하기): ");
        String topic = sc.nextLine();

        String[] conversation = new String[10];
        int turnCount = 0;

        String startPrompt = """
            '%s' 주제로 영어 회화를 시작해줘.
            당신이 먼저 영어로 말을 걸어주고, 사용자가 영어로 답할 수 있도록 해줘.
            자연스럽고 간단한 표현을 사용해줘.
            30자 이내로 답해줘.
            마크다운은 사용하지 마.
            """.formatted(topic);

        String aiResponse = sendRequest(client, url, bodyTemplate, startPrompt);
        System.out.println("AI: " + aiResponse);
        conversation[turnCount++] = "AI: " + aiResponse;

        while (turnCount < 10) {
            System.out.print("You: ");
            String userResponse = sc.nextLine();

            if (userResponse.equals("종료")) {
                System.out.println("회화 연습을 종료합니다.");
                break;
            }

            conversation[turnCount++] = "You: " + userResponse;

            // 이전 대화 내용 준비
            String prevConversation = "";
            for (int i = 0; i < turnCount; i++) {
                prevConversation += conversation[i] + " ";
            }

            String continuePrompt = """
                다음 영어 회화를 자연스럽게 이어가줘.
                주제: %s
                이전 대화: %s
                사용자의 영어 실력에 맞춰 답해주고, 30자 이내로 답해줘.
                마크다운은 사용하지 마.
                """.formatted(topic, prevConversation);

            String nextAiResponse = sendRequest(client, url, bodyTemplate, continuePrompt);
            System.out.println("AI: " + nextAiResponse);
            conversation[turnCount++] = "AI: " + nextAiResponse;
        }
    }

    // 문법 질문 기능
    private static void grammarQuestion(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n=== 문법 질문 ===");
        System.out.print("궁금한 영어 문법을 질문해주세요: ");
        String question = sc.nextLine();

        String prompt = """
            다음 영어 문법 질문에 답해줘.
            질문: %s
            초보자도 이해할 수 있도록 쉽게 설명해주고, 예시를 포함해줘.
            100자 이내로 답해줘.
            마크다운은 사용하지 마.
            """.formatted(question);

        String answer = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("답변: " + answer);
    }

    // API 요청을 보내는 공통 메서드 (재시도 로직 포함)
    private static String sendRequest(HttpClient client, String url, String bodyTemplate, String prompt) {
        // JSON에서 특수문자 이스케이프 처리
        String escapedPrompt = prompt
                .replace("\\", "\\\\")  // 백슬래시 이스케이프
                .replace("\"", "\\\"")  // 따옴표 이스케이프
                .replace("\n", "\\n")   // 개행문자 이스케이프
                .replace("\r", "\\r")   // 캐리지 리턴 이스케이프
                .replace("\t", "\\t");  // 탭 이스케이프

        // 최대 3번 재시도
        for (int retry = 0; retry < 3; retry++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json",
                            "X-goog-api-key", System.getenv("GEMINI_API_KEY"))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            bodyTemplate.formatted(escapedPrompt)
                    ))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                // 503 오류인 경우 재시도
                if (response.statusCode() == 503) {
                    if (retry < 2) {
                        Thread.sleep(2000 * (retry + 1)); // 점진적 대기 시간 증가
                    }
                    continue;
                }

                // 다른 오류 상태 코드 확인
                if (response.statusCode() != 200) {
                    return "API 오류가 발생했습니다.";
                }

                // 안전한 텍스트 파싱 - 더 정확한 패턴 매칭
                int textStart = responseBody.indexOf("\"text\": \"");
                if (textStart == -1) {
                    return "응답에서 텍스트를 찾을 수 없습니다.";
                }

                textStart += 9; // "text": " 길이만큼 이동
                int textEnd = responseBody.indexOf("\"", textStart);

                // 이스케이프된 따옴표 처리
                while (textEnd != -1 && responseBody.charAt(textEnd - 1) == '\\') {
                    textEnd = responseBody.indexOf("\"", textEnd + 1);
                }

                if (textEnd == -1) {
                    return "텍스트 파싱 오류가 발생했습니다.";
                }

                String extractedText = responseBody.substring(textStart, textEnd);

                return extractedText
                        .replace("\\n", " ")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\")
                        .trim();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "요청이 중단되었습니다.";
            } catch (Exception e) {
                if (retry == 2) { // 마지막 시도에서만 오류 메시지 출력
                    System.err.println("API 요청 실패: " + e.getMessage());
                }
            }
        }

        return "서버가 과부하 상태입니다. 잠시 후 다시 시도해주세요.";
    }
}