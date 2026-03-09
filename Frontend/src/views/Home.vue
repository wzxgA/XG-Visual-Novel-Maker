<template>
  <div class="min-h-screen flex flex-col items-center justify-center relative px-4">
    <!-- Logo区域 -->
    <div class="text-center mb-16 relative">
      <!-- 发光圆环装饰 -->
      <div class="absolute inset-0 flex items-center justify-center -z-10">
        <div class="w-64 h-64 rounded-full border border-alien-cyan/20 animate-pulse-glow"></div>
        <div class="absolute w-48 h-48 rounded-full border border-alien-magenta/20 animate-pulse-glow" style="animation-delay: 0.5s;"></div>
      </div>
      
      <h1 class="text-6xl md:text-8xl font-display font-black mb-4 tracking-tighter">
        <span class="text-gradient-alien">XG</span>
      </h1>
      <h2 class="text-2xl md:text-3xl font-display font-bold neon-text-cyan mb-2 tracking-widest">
        VISUAL NOVEL
      </h2>
      <h2 class="text-2xl md:text-3xl font-display font-bold neon-text-magenta tracking-widest">
        MAKER
      </h2>
      <p class="mt-6 text-gray-400 font-mono text-sm tracking-widest uppercase">
        Create Your Own Universe
      </p>
    </div>

    <!-- 操作按钮 -->
    <div class="flex flex-col sm:flex-row gap-6">
      <button 
        @click="createNewProject"
        class="alien-btn group flex items-center gap-3"
      >
        <svg class="w-5 h-5 transition-transform group-hover:rotate-90" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
        </svg>
        <span>新建项目</span>
      </button>
      
      <button 
        @click="openProject"
        class="alien-btn alien-btn-magenta group flex items-center gap-3"
      >
        <svg class="w-5 h-5 transition-transform group-hover:scale-110" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z"></path>
        </svg>
        <span>打开项目</span>
      </button>
    </div>

    <!-- 最近项目列表 -->
    <div v-if="recentProjects.length > 0" class="mt-16 w-full max-w-2xl">
      <h3 class="text-alien-cyan font-display font-bold text-lg mb-6 text-center tracking-widest">
        最近项目
      </h3>
      <div class="space-y-3">
        <div 
          v-for="project in recentProjects" 
          :key="project.id"
          @click="loadProject(project)"
          class="alien-card cursor-pointer group hover:border-alien-cyan transition-all duration-300"
        >
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-4">
              <div class="w-12 h-12 rounded-lg bg-gradient-to-br from-alien-cyan/20 to-alien-magenta/20 flex items-center justify-center">
                <svg class="w-6 h-6 text-alien-cyan" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"></path>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
              <div>
                <h4 class="font-display font-bold text-white group-hover:text-alien-cyan transition-colors">
                  {{ project.name }}
                </h4>
                <p class="text-sm text-gray-500 font-mono">
                  {{ project.author }} · {{ formatDate(project.createTime) }}
                </p>
              </div>
            </div>
            <svg class="w-5 h-5 text-gray-600 group-hover:text-alien-cyan transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
            </svg>
          </div>
        </div>
      </div>
    </div>

    <!-- 创建项目弹窗 -->
    <Teleport to="body">
      <Transition name="fade">
        <div v-if="showCreateDialog" class="fixed inset-0 z-50 flex items-center justify-center p-4">
          <!-- 遮罩 -->
          <div class="absolute inset-0 bg-alien-void/90 backdrop-blur-sm" @click="showCreateDialog = false"></div>
          
          <!-- 弹窗内容 -->
          <div class="alien-card w-full max-w-md relative z-10 animate-warp">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-2xl font-display font-bold neon-text-cyan">新建项目</h3>
              <button @click="showCreateDialog = false" class="text-gray-500 hover:text-white transition-colors">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </div>
            
            <form @submit.prevent="submitCreate">
              <div class="space-y-4">
                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">项目名称</label>
                  <input 
                    v-model="projectForm.name"
                    type="text"
                    class="alien-input"
                    placeholder="输入项目名称..."
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">作者</label>
                  <input 
                    v-model="projectForm.author"
                    type="text"
                    class="alien-input"
                    placeholder="输入作者名称..."
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">项目介绍</label>
                  <textarea 
                    v-model="projectForm.introduction"
                    class="alien-input h-24 resize-none"
                    placeholder="输入项目介绍..."
                  ></textarea>
                </div>
              </div>
              
              <div class="flex gap-3 mt-6">
                <button 
                  type="button"
                  @click="showCreateDialog = false"
                  class="flex-1 py-3 border border-gray-600 text-gray-400 font-display font-bold text-sm uppercase tracking-widest hover:border-gray-400 hover:text-white transition-all"
                >
                  取消
                </button>
                <button 
                  type="submit"
                  class="flex-1 alien-btn"
                  :disabled="isCreating"
                >
                  <span v-if="isCreating" class="flex items-center justify-center gap-2">
                    <svg class="animate-spin h-4 w-4" fill="none" viewBox="0 0 24 24">
                      <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                      <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    创建中...
                  </span>
                  <span v-else>创建项目</span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </Transition>
    </Teleport>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { gameApi } from '../api'

const router = useRouter()
const recentProjects = ref([])
const showCreateDialog = ref(false)
const isCreating = ref(false)

const projectForm = ref({
  name: '',
  author: '',
  introduction: ''
})

// 获取最近项目
const fetchRecentProjects = async () => {
  try {
    const res = await gameApi.getAllGames()
    if (res.code === 200) {
      recentProjects.value = res.data.slice(0, 5)
    }
  } catch (error) {
    console.error('获取项目列表失败:', error)
  }
}

// 创建新项目
const createNewProject = () => {
  showCreateDialog.value = true
  projectForm.value = { name: '', author: '', introduction: '' }
}

// 提交创建
const submitCreate = async () => {
  if (!projectForm.value.name || !projectForm.value.author) return
  
  isCreating.value = true
  try {
    const res = await gameApi.createGame(projectForm.value)
    if (res.code === 200) {
      showCreateDialog.value = false
      // 跳转到编辑器
      router.push('/editor')
    }
  } catch (error) {
    console.error('创建项目失败:', error)
    alert('创建项目失败，请重试')
  } finally {
    isCreating.value = false
  }
}

// 打开项目
const openProject = () => {
  // 可以打开文件选择器或显示项目列表
  fetchRecentProjects()
}

// 加载项目
const loadProject = (project) => {
  router.push('/editor')
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
}

onMounted(() => {
  fetchRecentProjects()
})
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
