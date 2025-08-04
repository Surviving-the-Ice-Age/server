package com.iceAge.server.analysis.application.service;

import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.NOT_VALID_FILE;
import static com.iceAge.server.analysis.infrastructure.code.AnalysisCode.NO_FILE_HERE;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.iceAge.server.analysis.domain.service.ImageGenerationDomainService;
import com.iceAge.server.analysis.infrastructure.external.ImagenApiClient;
import com.iceAge.server.analysis.presentation.dto.request.ImageRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.ImageResponseDTO;
import com.iceAge.server.global.exception.BaseException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageGenerationService implements ImageGenerationDomainService {
  private final ImagenApiClient imagenApiClient;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final AmazonS3 s3Client;

  @Override
  public ImageResponseDTO generateImage(ImageRequestDTO imageRequestDTO) {
    Mono<List<String>> base64Images = imagenApiClient.generateImage(imageRequestDTO);
    return new ImageResponseDTO(uploadImagesToS3(base64Images).block());
  }

  // 여러개의 파일 업로드
  public Mono<List<String>> uploadImagesToS3(Mono<List<String>> base64Images) {
    return base64Images
        .flatMap(images -> {
          // Process each Base64 string, decode it, and upload to S3
          List<Mono<String>> urlMonos = images.stream()
              .map(this::uploadSingleImage)
              .collect(Collectors.toList());

          // Combine all Mono<String> into a single Mono<List<String>>
          return Mono.zip(urlMonos, urlArray -> {
            List<String> urls = new ArrayList<>();
            for (Object obj : urlArray) {
              urls.add((String) obj); // Safely cast each element to String
            }
            return urls;
          });
        });
  }

  private Mono<String> uploadSingleImage(String base64Image) {
    return Mono.fromCallable(() -> {
      try {
        // Decode Base64 string to byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Generate unique file name with extension validation
        String fileExtension = ".jpg"; // Default to .jpg
        String fileName = "ssgs/" + UUID.randomUUID() + fileExtension;

        // Set metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.setContentLength(imageBytes.length);

        // Build S3 put request
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName,
            new ByteArrayInputStream(imageBytes), metadata);

        // Upload to S3
        s3Client.putObject(putObjectRequest);

        // Generate and return the S3 URL
        String s3Url = String.format("https://%s.s3.amazonaws.com/%s", bucket, fileName);
        return s3Url;
      } catch (Exception e) {
        throw new BaseException("Failed to upload image to S3: " + e.getMessage());
      }
    }).onErrorResume(e -> Mono.error(new BaseException("Error uploading image: " + e.getMessage())));
  }

}