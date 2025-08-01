package step5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GeminiChatVer4MovieRecommender {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");

        if (GEMINI_API_KEY == null || GEMINI_API_KEY.isEmpty()) {
            System.out.println("GEMINI_API_KEY 환경변수를 설정해주세요.");
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        String myModel = "gemini-1.5-flash";
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

        System.out.println("🎬 영화 추천 시스템에 오신 것을 환영합니다! 🎬");

        while (true) {
            System.out.println("\n=== 메뉴 선택 ===");
            System.out.println("1. 장르별 추천");
            System.out.println("2. 기분별 추천");
            System.out.println("3. 명작 영화 추천");
            System.out.println("4. 영화 리뷰 분석");
            System.out.println("5. 종료");
            System.out.print("선택하세요 (1-5): ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    genreRecommendation(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "2":
                    moodRecommendation(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "3":
                    classicMovies(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "4":
                    movieReview(sc, client, GEMINI_URL, bodyTemplate);
                    break;
                case "5":
                    System.out.println("🎬 좋은 영화 감상하세요! 안녕히 가세요!");
                    return;
                default:
                    System.out.println("❌ 잘못된 입력입니다. 1-5 중에서 선택해주세요.");
                    continue;
            }
        }
    }

    // 장르별 영화 추천
    private static void genreRecommendation(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n🎭 장르별 영화 추천");
        System.out.print("좋아하는 장르를 입력하세요 (액션, 로맨스, 스릴러, 코미디, SF, 호러 등): ");
        String genre = sc.nextLine();

        System.out.print("최근에 본 영화 중 좋았던 작품이 있다면 알려주세요 (없으면 엔터): ");
        String recentMovie = sc.nextLine();

        String prompt;
        if (recentMovie.trim().isEmpty()) {
            prompt = String.format("""
                %s 장르의 영화 3편을 추천해줘.
                각 영화마다 제목, 간단한 줄거리, 추천 이유를 포함해서 설명해줘.
                최신작과 명작을 섞어서 추천해줘.
                마크다운 사용하지 마.
                """, genre);
        } else {
            prompt = String.format("""
                %s를 좋아하는 사람에게 %s 장르 영화 3편을 추천해줘.
                각 영화마다 제목, 간단한 줄거리, 추천 이유를 포함해서 설명해줘.
                마크다운 사용하지 마.
                """, recentMovie, genre);
        }

        String recommendation = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("\n🎬 추천 영화:");
        System.out.println(recommendation);
    }

    // 기분별 영화 추천
    private static void moodRecommendation(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n😊 기분별 영화 추천");
        System.out.println("현재 기분이나 상황을 선택하세요:");
        System.out.println("1. 스트레스 해소하고 싶어요");
        System.out.println("2. 감동받고 싶어요");
        System.out.println("3. 웃고 싶어요");
        System.out.println("4. 스릴을 느끼고 싶어요");
        System.out.println("5. 생각에 잠기고 싶어요");
        System.out.print("선택 (1-5): ");

        String moodChoice = sc.nextLine();
        String mood = switch (moodChoice) {
            case "1" -> "스트레스 해소";
            case "2" -> "감동";
            case "3" -> "유머와 웃음";
            case "4" -> "스릴과 긴장감";
            case "5" -> "철학적 사고";
            default -> "편안한 감상";
        };

        String prompt = String.format("""
            %s에 어울리는 영화 3편을 추천해줘.
            각 영화의 제목, 왜 이 기분에 맞는지 이유, 간단한 설명을 포함해줘.
            다양한 연도의 작품을 섞어서 추천해줘.
            마크다운 사용하지 마.
            """, mood);

        String recommendation = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("\n🎭 기분별 추천 영화:");
        System.out.println(recommendation);
    }

    // 명작 영화 추천
    private static void classicMovies(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n🏆 명작 영화 추천");
        System.out.print("관심 있는 시대나 국가를 입력하세요 (예: 1990년대, 한국, 할리우드 등, 없으면 엔터): ");
        String era = sc.nextLine();

        String prompt;
        if (era.trim().isEmpty()) {
            prompt = """
                영화사에 길이 남을 명작 영화 3편을 추천해줘.
                각 영화의 제목, 제작년도, 감독, 왜 명작인지 이유를 설명해줘.
                서로 다른 시대와 장르의 작품을 선택해줘.
                마크다운 사용하지 마.
                """;
        } else {
            prompt = String.format("""
                %s 시대/지역의 명작 영화 3편을 추천해줘.
                각 영화의 제목, 제작년도, 감독, 왜 명작인지 이유를 설명해줘.
                마크다운 사용하지 마.
                """, era);
        }

        String recommendation = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("\n🏆 명작 영화 추천:");
        System.out.println(recommendation);
    }

    // 영화 리뷰 분석
    private static void movieReview(Scanner sc, HttpClient client, String url, String bodyTemplate) {
        System.out.println("\n📝 영화 리뷰 분석");
        System.out.print("최근에 본 영화 제목을 입력하세요: ");
        String movieTitle = sc.nextLine();

        System.out.print("영화에 대한 당신의 간단한 감상을 적어주세요: ");
        String userReview = sc.nextLine();

        String prompt = String.format("""
            '%s' 영화에 대한 다음 감상을 분석해줘:
            "%s"
            
            이 감상의 주요 포인트를 정리하고, 비슷한 취향을 가진 사람들이 좋아할 만한 영화 2편을 추천해줘.
            마크다운 사용하지 마.
            """, movieTitle, userReview);

        String analysis = sendRequest(client, url, bodyTemplate, prompt);
        System.out.println("\n📊 리뷰 분석 및 추천:");
        System.out.println(analysis);
    }

    // API 요청을 보내는 공통 메서드
    private static String sendRequest(HttpClient client, String url, String bodyTemplate, String prompt) {
        // JSON에서 특수문자 이스케이프 처리
        String escapedPrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

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
                        Thread.sleep(2000 * (retry + 1));
                    }
                    continue;
                }

                // 다른 오류 상태 코드 확인
                if (response.statusCode() != 200) {
                    return "API 오류가 발생했습니다.";
                }

                // 텍스트 추출
                int textStart = responseBody.indexOf("\"text\": \"");
                if (textStart == -1) {
                    return "응답에서 텍스트를 찾을 수 없습니다.";
                }

                textStart += 9;
                int textEnd = responseBody.indexOf("\"", textStart);

                while (textEnd != -1 && responseBody.charAt(textEnd - 1) == '\\') {
                    textEnd = responseBody.indexOf("\"", textEnd + 1);
                }

                if (textEnd == -1) {
                    return "텍스트 파싱 오류가 발생했습니다.";
                }

                String extractedText = responseBody.substring(textStart, textEnd);

                return extractedText
                        .replace("\\n", "\n")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\")
                        .trim();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "요청이 중단되었습니다.";
            } catch (Exception e) {
                if (retry == 2) {
                    return "서버 연결에 실패했습니다.";
                }
            }
        }

        return "서버가 과부하 상태입니다. 잠시 후 다시 시도해주세요.";
    }
}