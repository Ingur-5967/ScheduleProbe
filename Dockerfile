FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./

COPY gradlew ./
COPY gradle ./gradle

RUN sed -i -e 's/\r$//' ./gradlew && chmod +x ./gradlew

RUN ./gradlew dependencies --no-daemon --info

COPY src ./src

RUN ./gradlew build

FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-XX:AOTMode=on", "-jar", "app.jar"]
