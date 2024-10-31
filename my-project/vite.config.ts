import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

//const backendUrl = process.env.BACKEND_URL || 'http://backend:8080'; // 使用 Docker Compose 的後端服務名稱
const backendUrl = process.env.BACKEND_URL || 'http://localhost:8080';  //使用本地端開發環境localhost
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000, // 指定前端服务运行的端口
    proxy: {
      '/api': {
        target: backendUrl, // 使用後端服務名稱
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''), // 重写路径
      },
    },
  },
});


