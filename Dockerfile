FROM openjdk:17-jdk-slim AS java-builder

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
COPY src ./src
RUN chmod +x gradlew
RUN ./gradlew build -x test

# 최종 이미지 (Java)
FROM openjdk:17-jdk-slim

# 작업 디렉토리
WORKDIR /app

# Java 애플리케이션 복사
COPY --from=java-builder /app/build/libs/server-0.0.1-SNAPSHOT.jar app.jar

# 포트 노출
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]