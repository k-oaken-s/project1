# ベースイメージとしてOpenJDK 21を指定
FROM openjdk:21-jdk-slim

# 必要なパッケージのインストール
RUN apt-get update && apt-get install -y default-mysql-client

# アプリケーションを実行する作業ディレクトリを設定
WORKDIR /app

# キャッシュを利用してMaven依存関係を効率化
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
RUN ./gradlew build --no-daemon || return 0

# Gradleビルドを行ったjarファイルをコピー
COPY build/libs/*.jar app.jar

# ポート番号を指定（Spring Bootのデフォルトポート）
EXPOSE 8080

# ヘルスチェックを追加
HEALTHCHECK --interval=10s --timeout=3s --start-period=30s --retries=3 CMD curl --fail http://localhost:8080/actuator/health || exit 1

# Spring Bootアプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]
