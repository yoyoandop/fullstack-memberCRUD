<template>
  <div id="app">
    <UserManagement />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import UserManagement from './components/UserManagement.vue';

export default defineComponent({
  components: {
    UserManagement
  }
});
</script>

<style>
/* 這裡可以添加一些樣式 */
</style>
