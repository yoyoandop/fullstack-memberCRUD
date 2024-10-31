<template>
  <div id="app">
    <router-view /> <!-- 在這裡顯示當前路由對應的組件 -->
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'App',
});
</script>

<style>
/* 這裡可以添加一些樣式 */
</style>
