import { createRouter, createWebHistory } from 'vue-router';
import Register from '../components/Register.vue'; // 註冊組件
import UserManagement from '../components/UserManagement.vue'; // 用戶管理組件

const routes = [
    { path: '/register', component: Register },
    { path: '/usermanagement', component: UserManagement },
    // 可以添加更多路由...
];

const router = createRouter({
    history: createWebHistory(), // 使用歷史模式
    routes,
});

export default router;
