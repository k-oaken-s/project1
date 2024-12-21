# ビルドステージ
FROM node:18-alpine as build

WORKDIR /app

# package.jsonとlockファイルをコピーして依存関係をインストール
COPY package*.json ./
RUN npm install --production

# ソースコードをコピーしてビルド
COPY . .
ENV NODE_ENV=production
RUN npm run build

# 実行ステージ
FROM node:18-alpine

WORKDIR /app

# ビルド成果物と必要なファイルのみをコピー
COPY --from=build /app/.next /app/.next
COPY --from=build /app/package*.json ./
RUN npm install --production

EXPOSE 3000
CMD ["npm", "start"]
