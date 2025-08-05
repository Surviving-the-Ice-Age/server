package com.iceAge.server.instagram_post.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramPostService {

    /**
     * 인스타그램 포스트 컨테이너 생성
     */
    public Integer createPostContainer(List<String> imageIds, String postContent) {
        return null;
    }

    // 인스타그램에 포스트를 푸시하는 로직 구현
    public Integer pushPostToInstagram(Integer postContainerId) {

        return null;
    }
}
