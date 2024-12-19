# ベースイメージ
FROM openjdk:21-jdk-slim

# 必要なパッケージをインストール
RUN apt-get update && apt-get install -y default-mysql-client

# 作業ディレクトリ
WORKDIR /app

# 初期化スクリプトをコピー
COPY entrypoint.sh /app/entrypoint.sh

# エントリーポイントスクリプトに実行権限を付与
RUN chmod +x /app/entrypoint.sh

# Gradleラッパーと設定ファイルをコピー
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./src ./src

# Gradleの実行権限を付与
RUN chmod +x ./gradlew

# ビルドプロセスの実行
RUN ./gradlew build --no-daemon

# ビルド成果物をコピー
COPY build/libs/kotlin-project.jar app.jar

# ポート番号を指定
EXPOSE 8080

# エントリーポイントを変更
ENTRYPOINT ["/app/entrypoint.sh"]
