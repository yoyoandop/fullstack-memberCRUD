<template>
  <div class="register">
    <h2>註冊</h2>
    <form @submit.prevent="handleRegister">
      <div>
        <label for="email">電子郵件:</label>
        <input type="email" v-model="userData.email" required />
      </div>
      <div>
        <label for="password">密碼:</label>
        <input type="password" v-model="userData.password" required />
      </div>
      <div>
        <label for="roles">角色:</label>
        <input type="text" v-model="rolesInput" placeholder="逗號分隔角色" />
      </div>
      <button type="submit">註冊</button>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { registerUser } from '../services/userdataService';
import { Userdata } from '../models/Userdata';

export default defineComponent({
  setup() {
    const userData = ref<Userdata>({ email: '', password: '', roles: [] }); // roles 初始化為空數組
    const rolesInput = ref<string>(''); // 新的 ref 用於存儲輸入的角色
    const errorMessage = ref<string | null>(null);

    const handleRegister = async () => {
      errorMessage.value = null; // 重置錯誤信息
      try {
        // 將輸入的角色字符串轉換為數組
        if (rolesInput.value) {
          userData.value.roles = rolesInput.value.split(',').map((role: string) => role.trim());
        }

        // 定義 API 地址和請求類型
        const apiUrl = 'http://localhost:8080/register'; // 替換為您的實際後端API地址
        const requestType = 'POST'; // 設置請求類型

        // 打印請求類型、URL 和用戶數據
        console.log('Request Type:', requestType);
        console.log('API URL:', apiUrl);
        console.log('Sending user data:', JSON.stringify(userData.value)); // 打印用戶數據

        // 在這裡打印請求頭
        const headers = {
          'Content-Type': 'application/json',
        };
        console.log('Request Headers:', headers); // 打印請求頭

        const message = await registerUser(userData.value); // 传递请求头
        alert(message); // 或者其他方式通知用戶
      } catch (error: any) {
        console.error('Error during registration:', error); // 打印錯誤信息
        errorMessage.value = error.message; // 顯示錯誤信息
      }
    };

    return { userData, handleRegister, errorMessage, rolesInput }; // 返回 rolesInput
  }
});
</script>

<style scoped>
.error {
  color: red;
}
</style>
