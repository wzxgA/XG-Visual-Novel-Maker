<template>
  <div class="app-container">
    <!-- 动态背景光球 -->
    <div class="fixed inset-0 overflow-hidden pointer-events-none">
      <div class="geo-orb w-96 h-96 -top-48 -left-48" style="--orb-color: #00F5FF; animation: float 8s ease-in-out infinite;"></div>
      <div class="geo-orb w-80 h-80 top-1/3 -right-40" style="--orb-color: #FF00FF; animation: float 10s ease-in-out infinite 1s;"></div>
      <div class="geo-orb w-64 h-64 bottom-20 left-1/4" style="--orb-color: #39FF14; animation: float 7s ease-in-out infinite 0.5s;"></div>
      <div class="geo-orb w-72 h-72 top-2/3 right-1/4" style="--orb-color: #8B5CF6; animation: float 9s ease-in-out infinite 2s;"></div>
    </div>
    
    <!-- 网格背景 -->
    <div class="fixed inset-0 grid-bg pointer-events-none opacity-50"></div>
    
    <!-- 扫描线效果 -->
    <div class="scan-line fixed inset-0 pointer-events-none opacity-30"></div>
    
    <!-- 主内容 -->
    <router-view v-slot="{ Component }">
      <transition name="warp" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
</template>

<script setup>
</script>

<style scoped>
.warp-enter-active,
.warp-leave-active {
  transition: all 0.5s ease;
}

.warp-enter-from {
  opacity: 0;
  transform: scale(0.9) translateY(20px);
}

.warp-leave-to {
  opacity: 0;
  transform: scale(1.1) translateY(-20px);
}

.app-container {
  min-height: 100vh;
  position: relative;
}
</style>
