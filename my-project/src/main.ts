import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import router from './router'; // 導入路由器

// 創建 Vue 應用，使用路由器
createApp(App)
    .use(router) // 使用路由功能
    .mount('#app'); // 掛載應用
