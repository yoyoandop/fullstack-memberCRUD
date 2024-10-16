# 使用 OpenJDK 17 作為基礎映像
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製 target/fullstack-membersystem-0.0.1-SNAPSHOT.jar 到容器中
COPY target/fullstack-membersystem-0.0.1-SNAPSHOT.jar app.jar

# 檢查 JAR 文件是否存在（可選）
RUN ls -la /app

# 執行應用程序
ENTRYPOINT ["java", "-jar", "app.jar"]