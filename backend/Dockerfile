# ベースイメージとしてOpenJDK 21を指定
FROM openjdk:21-jdk-slim

# 必要なパッケージのインストール
RUN apt-get update && apt-get install -y default-mysql-client

# アプリケーションを実行する作業ディレクトリを設定
WORKDIR /app

# Gradleビルドを行ったjarファイルをコピー
COPY build/libs/*.jar app.jar

# ポート番号を指定（Spring Bootのデフォルトポート）
EXPOSE 8080

# Spring Bootアプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]
