<template>
  <div>
    <h2>User Management</h2>
    <!-- 導航到註冊頁面 -->
    <router-link to="/register">Go to Register</router-link>

    <form @submit.prevent="addUser">
      <input v-model="newUser.username" placeholder="Username" required />
      <input v-model="newUser.password" type="password" placeholder="Password" required />
      <input v-model="newUser.email" type="email" placeholder="Email" required />
      <button type="submit">Add User</button>
    </form>

    <button @click="fetchUsers">Fetch Users</button>
    <ul>
      <li v-for="user in users" :key="user.id">
        {{ user.username }} - {{ user.email }}
        <button @click="editUser(user)">Edit</button>
        <button @click="removeUser(user.id as number)">Delete</button>
      </li>
    </ul>

    <div v-if="editMode && currentUser">
      <h3>Edit User</h3>
      <form @submit.prevent="updateCurrentUser">
        <input v-model="currentUser.username" placeholder="Username" required />
        <input v-model="currentUser.password" type="password" placeholder="Password" required />
        <input v-model="currentUser.email" type="email" placeholder="Email" required />
        <button type="submit">Update User</button>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { User } from '../models/User';
import { getAllUsers, createUser, updateUser as apiUpdateUser, deleteUser } from '../services/userService';

export default defineComponent({
  setup() {
    const users = ref<User[]>([]);
    const newUser = ref<User>({ username: '', password: '', email: '' });
    const currentUser = ref<User | null>(null);
    const editMode = ref(false);

    const fetchUsers = async () => {
      try {
        users.value = await getAllUsers();
      } catch (error) {
        console.error('Failed to fetch users:', error);
      }
    };

    const addUser = async () => {
      console.log('Add User function called'); // 確認函數被調用

      try {
        await createUser(newUser.value);
        newUser.value = { username: '', password: '', email: '' }; // 清空表單
        await fetchUsers(); // 獲取更新的用戶列表
      } catch (error) {
        console.error('Failed to add user:', error);
      }
    };

    const editUser = (user: User) => {
      currentUser.value = { ...user }; // Copy user data for editing
      editMode.value = true;
    };

    const updateCurrentUser = async () => {
      if (currentUser.value) { // Ensure currentUser.value exists
        const userId = currentUser.value.id; // This id should be a number
        if (userId !== undefined) {
          try {
            await apiUpdateUser(userId, currentUser.value); // Use id to call updateUser
            currentUser.value = null; // Clear current user
            editMode.value = false;
            await fetchUsers();
          } catch (error) {
            console.error('Failed to update user:', error);
          }
        } else {
          console.error('User ID does not exist, unable to update.');
        }
      } else {
        console.error('Current user does not exist, unable to update.');
      }
    };

    const removeUser = async (id: number) => {
      try {
        await deleteUser(id);
        await fetchUsers();
      } catch (error) {
        console.error('Failed to delete user:', error);
      }
    };

    // Initialize user list
    fetchUsers();

    return {
      users,
      newUser,
      currentUser,
      editMode,
      fetchUsers,
      addUser,
      editUser,
      updateCurrentUser,
      removeUser,
    };
  },
});
</script>

<style scoped>
/* Add styles here */
</style>
