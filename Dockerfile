FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/review
COPY . .
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USER_NAME=review
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} \
    && adduser -h /opt/review -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}
WORKDIR /opt/review
COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/review/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
