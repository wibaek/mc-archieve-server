# 빌드 스테이지
#FROM bellsoft/liberica-openjdk-alpine:17 as builder
#WORKDIR /app

# 소스 코드와 필요한 파일들을 이미지 내로 복사
#COPY . .

# 빌드 실행
#RUN ./gradlew clean build

# 최종 이미지
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app

# 바이너리 복사
COPY /app/build/libs/*.jar ./app.jar

# 포트 8080 노출
EXPOSE 8080

# 론쳐 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
