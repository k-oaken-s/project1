# ベースイメージ
FROM openjdk:21-jdk-slim

# 必要なパッケージをインストール
RUN apt-get update && apt-get install -y postgresql-client inotify-tools

# Gradleキャッシュ用ディレクトリの指定
ENV GRADLE_USER_HOME=/root/.gradle

# 作業ディレクトリ
WORKDIR /app

# Gradleラッパーと設定ファイルをコピー（依存関係のキャッシュ用）
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts

# Gradleの実行権限を付与
RUN chmod +x ./gradlew

# 必要な依存関係をダウンロード
RUN ./gradlew dependencies --no-daemon --parallel
