#!/bin/bash

# データベース初期化スクリプトの実行
if [ -n "$SPRING_DATASOURCE_URL" ] && [ -n "$DB_ADMIN_USERNAME" ] && [ -n "$DB_ADMIN_PASSWORD" ]; then
    mysql -h $(echo $SPRING_DATASOURCE_URL | cut -d'/' -f3 | cut -d':' -f1) \
          -P 3306 \
          -u "$DB_ADMIN_USERNAME" \
          -p"$DB_ADMIN_PASSWORD" \
          -e "
            CREATE DATABASE IF NOT EXISTS \`rankifyhub-database\`;
            CREATE USER IF NOT EXISTS '$APP_USERNAME'@'%' IDENTIFIED BY '$APP_PASSWORD';
            GRANT ALL PRIVILEGES ON \`rankifyhub-database\`.* TO '$APP_USERNAME'@'%';
            FLUSH PRIVILEGES;
          "
fi

# アプリケーションの起動
exec java -jar /app/app.jar
