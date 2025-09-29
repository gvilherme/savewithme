# syntax=docker/dockerfile:1.7

# 1) ARG global (vale para qualquer FROM)
ARG BASE_IMAGE=public.ecr.aws/amazoncorretto:21-alpine

# ---------- builder ----------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

# (pré-aquece cache de deps)
COPY app/pom.xml app/pom.xml
COPY app/domain/pom.xml app/domain/pom.xml
COPY app/application/pom.xml app/application/pom.xml
COPY app/adapters/in/rest/pom.xml app/adapters/in/rest/pom.xml
COPY app/adapters/out/persistence/pom.xml app/adapters/out/persistence/pom.xml
COPY app/bootstrap/pom.xml app/bootstrap/pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn -f app/pom.xml -B -ntp -DskipTests package || true

# copia fontes após deps pra maximizar cache
COPY app/ app/
RUN --mount=type=cache,target=/root/.m2 mvn -f app/pom.xml -B -ntp -DskipTests package

# ---------- runtime ----------
# 2) Re-declare o ARG no estágio (boa prática p/ visibilidade)
ARG BASE_IMAGE
FROM ${BASE_IMAGE} AS runtime

WORKDIR /app
RUN addgroup -S app && adduser -S app -G app
COPY --from=builder /build/app/bootstrap/target/bootstrap-*.jar /app/app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
