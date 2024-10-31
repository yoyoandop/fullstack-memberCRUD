// src/services/userService.ts
import axios from 'axios';
import { Userdata } from '../models/Userdata'; // 使用解构导入

// 修改 API_URL 以指向后端服务
const API_URL = 'http://localhost:8080'; // 确保这里指向后端服务的正确地址

export const registerUser = async (userData: Userdata): Promise<string> => {
    try {
        const requestUrl = `${API_URL}/register`;

        // 打印请求的 URL 和用户数据
        console.log('Sending request to:', requestUrl);
        console.log('User data:', JSON.stringify(userData));

        // 定义请求头
        const headers = {
            'Content-Type': 'application/json',
        };

        // 发送 POST 请求，并传递请求头
        const response = await axios.post(requestUrl, userData, { headers });

        // 如果需要，也可以在此处打印响应头
        console.log('Response Headers:', response.headers);

        return response.data;
    } catch (error: any) {
        // 捕获错误并打印详细信息
        console.error('Error during registration:', error); // 打印错误信息
        throw new Error(error.response?.data || '註冊失敗');
    }
};
