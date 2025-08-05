package com.iceAge.server.instagram_post.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramImageService {

    /**
     * 인스타그램 이미지 컨테이너 생성
      */
    public List<Integer> createImageContainer(List<String> imageUrls) {
        // 이미지 URL을 기반으로 컨테이너 생성 로직 구현
        return null;
    }
}
