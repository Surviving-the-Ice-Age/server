package com.iceAge.server.instagram_post;

import com.iceAge.server.instagram_post.domain.service.InstagramApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "instagram.app-id=${INSTAGRAM_APP_ID:17841476257793092}",
        "instagram.access-token=${INSTAGRAM_ACCESS_TOKEN:test-token}"
})
@DisplayName("인스타그램 API 간단 테스트")
class InstagramApiSimpleTest {

    @Autowired
    private InstagramApiService instagramApiService;

    @Test
    @DisplayName("실제 이미지 컨테이너 생성 테스트")
    void testRealImageContainerCreation() {
        // given - 실제 이미지 URL 사용
        List<String> imageUrls = List.of(
                "https://media.timeout.com/images/103504186/image.jpg",
                "https://img1.newsis.com/2013/09/28/NISI20130928_0008731279_web.jpg",
                "https://d12zq4w4guyljn.cloudfront.net/300_300_20250401091016453_photo_f137134b9faf.webp");

        try {
            // when
            List<String> containerIds = instagramApiService.createImageContainer(imageUrls);

            // then
            assertThat(containerIds).isNotEmpty();
            assertThat(containerIds.get(0)).isNotNull();

            System.out.println("✅ 이미지 컨테이너 생성 성공!");
            System.out.println("컨테이너 ID: " + containerIds.get(0));

        } catch (Exception e) {
            System.err.println("❌ 이미지 컨테이너 생성 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("실제 포스트 게시 테스트")
    void testRealPostPublishing() {
        // given
        List<String> imageUrls = List.of(
                "https://media.timeout.com/images/103504186/image.jpg",
                "https://img1.newsis.com/2013/09/28/NISI20130928_0008731279_web.jpg",
                "https://d12zq4w4guyljn.cloudfront.net/300_300_20250401091016453_photo_f137134b9faf.webp");
        String caption = "안산에 진짜 스테이크 맛집이 생긴다고?"
                + "\n"
                + "스테이크 러버들, 주목해 주세요! ✨\n"
                + "\n"
                + "안산 25시광장에, 제대로 된 정통 스테이크 하우스를 준비하고 있어요.\n"
                + "상상만 해도 침이 고이는 립 스테이크를 메인으로, 최고급 재료와 정성으로 만든 스테이크를 선보일 예정입니다.\n"
                + "\n"
                + "따뜻하고 분위기 있는 공간에서 사랑하는 사람들과 함께, 또는 혼자만의 시간을 가지며 여유롭게 식사를 즐길 수 있는 곳을 만들고 싶어요.\n"
                + "입에서 살살 녹는 부드러운 스테이크 한 조각에, 일상의 스트레스는 잠시 잊어버리게 될 거예요.\n"
                + "\n"
                + "과연 어떤 맛일지, 벌써부터 궁금하지 않으세요?\n"
                + "여러분의 기대를 뛰어넘는 최고의 스테이크를 선보이기 위해 열심히 준비하고 있으니, 앞으로의 소식들을 놓치지 마세요!"
                + "#안산맛집 #안산스테이크 #안산25시광장 #스테이크맛집 #데이트코스 #립스테이크 #스테이크하우스 #먹스타그램\n";

        try {
            // 1. 이미지 컨테이너 생성
            List<String> containerIds = instagramApiService.createImageContainer(imageUrls);
            System.out.println("1단계 - 이미지 컨테이너 생성 완료: " + containerIds);

            // 2. 게시글 컨테이너 생성
            String creationId = instagramApiService.createPostContainer(containerIds, caption);
            System.out.println("2단계 - 게시글 컨테이너 생성 완료: " + creationId);

            // 3. 포스트 게시
            String postId = instagramApiService.publishPost(creationId);
            System.out.println("3단계 - 포스트 게시 완료: " + postId);

            System.out.println("✅ 전체 프로세스 성공!");
            System.out.println("최종 포스트 ID: " + postId);

        } catch (Exception e) {
            System.err.println("❌ 포스트 게시 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}