# 使用 Node.js 作為基礎映像
FROM node:18

# 設定工作目錄
WORKDIR /app

# 複製 package.json 和 package-lock.json
COPY package*.json ./

# 安裝依賴
RUN npm install

# 複製其餘的應用程序代碼
COPY . .

# 構建應用程序
RUN npm run build

# 暴露應用程序的端口
EXPOSE 3000

# 執行應用程序
CMD ["npm", "run", "preview"]
