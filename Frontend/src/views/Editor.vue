<template>
  <div class="min-h-screen flex flex-col">
    <!-- 顶部导航栏 -->
    <header class="h-16 bg-alien-surface/90 backdrop-blur-md border-b border-alien-cyan/30 flex items-center justify-between px-6 sticky top-0 z-40">
      <div class="flex items-center gap-4">
        <button 
          @click="router.push('/')"
          class="text-gray-400 hover:text-alien-cyan transition-colors"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
          </svg>
        </button>
        <div class="h-6 w-px bg-alien-cyan/30"></div>
        <h1 class="font-display font-bold text-xl neon-text-cyan tracking-wider">
          {{ currentProject?.name || '未命名项目' }}
        </h1>
        <span class="text-xs text-gray-500 font-mono">{{ currentProject?.author }}</span>
      </div>
      
      <div class="flex items-center gap-3">
        <button 
          @click="saveProject"
          class="alien-btn py-2 px-4 text-xs"
          :disabled="isSaving"
        >
          <span v-if="isSaving">保存中...</span>
          <span v-else class="flex items-center gap-2">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4"></path>
            </svg>
            保存
          </span>
        </button>
        <button 
          @click="previewProject"
          class="alien-btn alien-btn-magenta py-2 px-4 text-xs"
        >
          <span class="flex items-center gap-2">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"></path>
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            预览
          </span>
        </button>
      </div>
    </header>

    <!-- 主编辑区域 -->
    <div class="flex-1 flex overflow-hidden">
      <!-- 左侧边栏 - 资源管理 -->
      <aside class="w-64 bg-alien-surface/50 backdrop-blur-sm border-r border-alien-cyan/20 flex flex-col">
        <!-- 标签切换 -->
        <div class="flex border-b border-alien-cyan/20">
          <button 
            v-for="tab in sidebarTabs" 
            :key="tab.id"
            @click="activeSidebarTab = tab.id"
            class="flex-1 py-3 font-display font-bold text-xs uppercase tracking-wider transition-all"
            :class="activeSidebarTab === tab.id ? 'text-alien-cyan border-b-2 border-alien-cyan' : 'text-gray-500 hover:text-gray-300'"
          >
            {{ tab.name }}
          </button>
        </div>
        
        <!-- 角色列表 -->
        <div v-if="activeSidebarTab === 'characters'" class="flex-1 overflow-y-auto p-4">
          <button 
            @click="showAddCharacter = true"
            class="w-full mb-4 py-2 border border-dashed border-alien-cyan/50 text-alien-cyan/70 font-mono text-sm hover:border-alien-cyan hover:text-alien-cyan transition-all flex items-center justify-center gap-2"
          >
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            添加角色
          </button>
          
          <div class="space-y-2">
            <div 
              v-for="char in characters" 
              :key="char.id"
              @click="selectCharacter(char)"
              class="alien-card p-3 cursor-pointer group hover:border-alien-cyan transition-all"
              :class="{ 'border-alien-cyan': selectedCharacter?.id === char.id }"
            >
              <div class="flex items-center gap-3">
                <div v-if="char.imageUrl" class="w-10 h-10 rounded-full overflow-hidden border border-alien-cyan/30">
                  <img :src="char.imageUrl" alt="角色图片" class="w-full h-full object-cover">
                </div>
                <div v-else class="w-10 h-10 rounded-full bg-gradient-to-br from-alien-cyan/30 to-alien-magenta/30 flex items-center justify-center">
                  <span class="font-display font-bold text-alien-cyan">{{ char.name?.[0] }}</span>
                </div>
                <div class="flex-1 min-w-0">
                  <h4 class="font-display font-bold text-sm text-white truncate">{{ char.name }}</h4>
                  <p class="text-xs text-gray-500 font-mono">进度: {{ char.rate || 0 }}%</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 背景资源 -->
        <div v-if="activeSidebarTab === 'backgrounds'" class="flex-1 overflow-y-auto p-4">
          <div class="grid grid-cols-2 gap-2">
            <div 
              v-for="(bg, index) in backgrounds" 
              :key="index"
              class="aspect-video rounded-lg bg-alien-deep border border-alien-cyan/20 overflow-hidden cursor-pointer hover:border-alien-cyan transition-all group"
            >
              <img v-if="bg" :src="bg" class="w-full h-full object-cover opacity-70 group-hover:opacity-100 transition-opacity">
              <div v-else class="w-full h-full flex items-center justify-center text-gray-600">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                </svg>
              </div>
            </div>
            <div class="aspect-video rounded-lg border border-dashed border-alien-cyan/30 flex items-center justify-center cursor-pointer hover:border-alien-cyan transition-all">
              <svg class="w-6 h-6 text-alien-cyan/50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
              </svg>
            </div>
          </div>
        </div>
        
        <!-- 音频资源 -->
        <div v-if="activeSidebarTab === 'audio'" class="flex-1 overflow-y-auto p-4">
          <div class="space-y-2">
            <div 
              v-for="(audio, index) in audioFiles" 
              :key="index"
              class="alien-card p-3 flex items-center gap-3 cursor-pointer hover:border-alien-cyan transition-all"
            >
              <div class="w-10 h-10 rounded-lg bg-alien-magenta/20 flex items-center justify-center">
                <svg class="w-5 h-5 text-alien-magenta" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3"></path>
                </svg>
              </div>
              <div class="flex-1 min-w-0">
                <h4 class="font-mono text-sm text-white truncate">{{ audio.name }}</h4>
                <p class="text-xs text-gray-500">{{ audio.duration }}</p>
              </div>
            </div>
          </div>
        </div>
      </aside>

      <!-- 中间编辑区 -->
      <main class="flex-1 flex flex-col bg-alien-deep/30">
        <!-- 场景预览 -->
        <div class="flex-1 relative overflow-hidden">
          <div class="absolute inset-8 rounded-xl border-2 border-alien-cyan/20 bg-alien-void/80 overflow-hidden">
            <!-- 场景背景 -->
            <div class="absolute inset-0 bg-gradient-to-b from-alien-violet/10 to-alien-deep"></div>
            
            <!-- 角色立绘位置 -->
            <div class="absolute bottom-0 left-1/2 -translate-x-1/2 w-64 h-80 bg-gradient-to-t from-alien-cyan/20 to-transparent rounded-t-full opacity-50"></div>
            
            <!-- 对话文本框 -->
            <div class="absolute bottom-8 left-8 right-8">
              <div class="alien-card">
                <div class="flex items-start gap-4">
                  <div class="w-20 h-20 rounded-lg bg-gradient-to-br from-alien-cyan/30 to-alien-magenta/30 flex-shrink-0 flex items-center justify-center">
                    <span class="font-display font-bold text-2xl text-alien-cyan">?</span>
                  </div>
                  <div class="flex-1">
                    <h4 class="font-display font-bold text-alien-cyan mb-2">角色名称</h4>
                    <p class="text-gray-300 font-mono leading-relaxed">
                      在这里输入对话文本...
                    </p>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 选项按钮 -->
            <div class="absolute top-1/2 right-8 -translate-y-1/2 space-y-2">
              <button class="alien-btn py-2 px-4 text-xs w-32">选项 A</button>
              <button class="alien-btn alien-btn-magenta py-2 px-4 text-xs w-32">选项 B</button>
            </div>
          </div>
        </div>
        
        <!-- 底部剧情节点编辑器 -->
        <div class="h-64 bg-alien-surface/50 backdrop-blur-sm border-t border-alien-cyan/20">
          <div class="flex items-center justify-between px-4 py-2 border-b border-alien-cyan/20">
            <h3 class="font-display font-bold text-sm text-alien-cyan tracking-wider">剧情节点</h3>
            <div class="flex gap-2">
              <button class="p-2 text-gray-400 hover:text-alien-cyan transition-colors">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path>
                </svg>
              </button>
              <button class="p-2 text-gray-400 hover:text-alien-magenta transition-colors">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                </svg>
              </button>
            </div>
          </div>
          
          <!-- 节点时间线 -->
          <div class="p-4 overflow-x-auto">
            <div class="flex gap-4 min-w-max">
              <div 
                v-for="(node, index) in plotNodes" 
                :key="node.id"
                class="relative"
              >
                <!-- 连接线 -->
                <div v-if="index < plotNodes.length - 1" class="absolute top-1/2 left-full w-8 h-0.5 bg-alien-cyan/30"></div>
                
                <!-- 节点卡片 -->
                <div 
                  class="alien-card w-48 p-3 cursor-pointer transition-all"
                  :class="{ 'border-alien-cyan': selectedNode?.id === node.id }"
                  @click="selectNode(node)"
                >
                  <div class="flex items-center gap-2 mb-2">
                    <span class="w-6 h-6 rounded-full bg-alien-cyan/20 flex items-center justify-center text-xs font-mono text-alien-cyan">
                      {{ index + 1 }}
                    </span>
                    <span class="text-xs text-gray-500 font-mono">{{ node.type || '对话' }}</span>
                  </div>
                  <p class="text-sm text-gray-300 truncate">{{ node.content || '节点内容...' }}</p>
                </div>
              </div>
              
              <!-- 添加节点按钮 -->
              <button 
                @click="addPlotNode"
                class="w-48 h-24 rounded-lg border-2 border-dashed border-alien-cyan/30 flex items-center justify-center text-alien-cyan/50 hover:border-alien-cyan hover:text-alien-cyan transition-all"
              >
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
                </svg>
              </button>
            </div>
          </div>
        </div>
      </main>

      <!-- 右侧属性面板 -->
      <aside class="w-72 bg-alien-surface/50 backdrop-blur-sm border-l border-alien-cyan/20 flex flex-col">
        <div class="p-4 border-b border-alien-cyan/20">
          <h3 class="font-display font-bold text-sm text-alien-cyan tracking-wider">属性面板</h3>
        </div>
        
        <div class="flex-1 overflow-y-auto p-4 space-y-4">
          <!-- 节点类型 -->
          <div>
            <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">节点类型</label>
            <select class="alien-input py-2 text-sm">
              <option value="dialogue">对话</option>
              <option value="choice">选项</option>
              <option value="action">动作</option>
              <option value="bgm">背景音乐</option>
            </select>
          </div>
          
          <!-- 角色选择 -->
          <div>
            <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">说话角色</label>
            <select class="alien-input py-2 text-sm">
              <option value="">无</option>
              <option v-for="char in characters" :key="char.id" :value="char.id">{{ char.name }}</option>
            </select>
          </div>
          
          <!-- 对话文本 -->
          <div>
            <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">对话文本</label>
            <textarea class="alien-input h-24 resize-none text-sm" placeholder="输入对话内容..."></textarea>
          </div>
          
          <!-- 背景设置 -->
          <div>
            <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">背景</label>
            <div class="aspect-video rounded-lg bg-alien-deep border border-alien-cyan/20 flex items-center justify-center cursor-pointer hover:border-alien-cyan transition-all">
              <svg class="w-8 h-8 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
            </div>
          </div>
          
          <!-- BGM设置 -->
          <div>
            <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">背景音乐</label>
            <select class="alien-input py-2 text-sm">
              <option value="">无</option>
              <option v-for="audio in audioFiles" :key="audio.name" :value="audio.name">{{ audio.name }}</option>
            </select>
          </div>
        </div>
      </aside>
    </div>

    <!-- 添加角色弹窗 -->
    <Teleport to="body">
      <Transition name="fade">
        <div v-if="showAddCharacter" class="fixed inset-0 z-50 flex items-center justify-center p-4">
          <div class="absolute inset-0 bg-alien-void/90 backdrop-blur-sm" @click="showAddCharacter = false"></div>
          <div class="alien-card w-full max-w-md relative z-10 animate-warp">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-2xl font-display font-bold neon-text-cyan">添加角色</h3>
              <button @click="showAddCharacter = false" class="text-gray-500 hover:text-white transition-colors">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </div>
            
            <form @submit.prevent="submitAddCharacter">
              <div class="space-y-4">
                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">角色名称</label>
                  <input v-model="characterForm.name" type="text" class="alien-input" placeholder="输入角色名称..." required>
                </div>
                
                <div>
                  <div class="flex items-center justify-between mb-2">
                    <label class="block text-alien-cyan font-mono text-sm tracking-wider">角色图片</label>
                    <button type="button" @click="addImageUrl" class="text-xs text-alien-cyan hover:text-white transition-colors">
                      添加图片
                    </button>
                  </div>
                  <div v-for="(url, index) in characterForm.imageUrls" :key="index" class="flex gap-2 mb-2">
                    <input v-model="characterForm.imageUrls[index]" type="text" class="hidden" placeholder="图片URL..." readonly>
                    <input type="file" @change="handleFileUpload($event, index)" class="text-xs text-gray-400">
                    <button type="button" @click="removeImageUrl(index)" class="text-gray-500 hover:text-red-500 transition-colors">
                      <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
              
              <div class="flex gap-3 mt-6">
                <button type="button" @click="showAddCharacter = false" class="flex-1 py-3 border border-gray-600 text-gray-400 font-display font-bold text-sm uppercase tracking-widest hover:border-gray-400 hover:text-white transition-all">
                  取消
                </button>
                <button type="submit" class="flex-1 alien-btn">添加</button>
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
import { gameApi, characterApi, plotApi, fileApi } from '../api'

const router = useRouter()
const currentProject = ref(null)
const isSaving = ref(false)
const activeSidebarTab = ref('characters')
const selectedCharacter = ref(null)
const selectedNode = ref(null)
const showAddCharacter = ref(false)

const sidebarTabs = [
  { id: 'characters', name: '角色' },
  { id: 'backgrounds', name: '背景' },
  { id: 'audio', name: '音频' }
]

const characters = ref([])
const backgrounds = ref([null, null, null])
const audioFiles = ref([
  { name: 'bgm_01.mp3', duration: '2:34' },
  { name: 'bgm_02.mp3', duration: '3:12' }
])
const plotNodes = ref([
  { id: 1, type: '对话', content: '欢迎来到游戏世界...' },
  { id: 2, type: '选项', content: '选择你的道路' }
])

const characterForm = ref({
  name: '',
  rate: 0,
  imageUrls: []
})

// 获取当前项目信息
const fetchCurrentProject = async () => {
  try {
    const res = await gameApi.getAllGames()
    if (res.code === 200 && res.data.length > 0) {
      currentProject.value = res.data[0]
      fetchCharacters()
    }
  } catch (error) {
    console.error('获取项目信息失败:', error)
  }
}

// 获取角色列表
const fetchCharacters = async () => {
  if (!currentProject.value) return
  try {
    const res = await characterApi.getCharactersByGameId(currentProject.value.id)
    if (res.code === 200) {
      characters.value = res.data
    }
  } catch (error) {
    console.error('获取角色列表失败:', error)
  }
}

// 选择角色
const selectCharacter = (char) => {
  selectedCharacter.value = char
}

// 添加图片URL
const addImageUrl = () => {
  characterForm.value.imageUrls.push('')
}

// 删除图片URL
const removeImageUrl = (index) => {
  characterForm.value.imageUrls.splice(index, 1)
}

// 处理文件上传
const handleFileUpload = async (event, index) => {
  const file = event.target.files[0]
  if (!file) return
  
  try {
    const res = await fileApi.uploadFile(file)
    if (res.code === 200) {
      characterForm.value.imageUrls[index] = res.data
    }
  } catch (error) {
    console.error('文件上传失败:', error)
    alert('文件上传失败')
  }
}

// 添加角色
const submitAddCharacter = async () => {
  if (!currentProject.value || !characterForm.value.name) return
  
  try {
    const res = await characterApi.createCharacter({
      ...characterForm.value,
      gameId: currentProject.value.id
    })
    if (res.code === 200) {
      showAddCharacter.value = false
      characterForm.value = { name: '', rate: 0, imageUrls: [] }
      fetchCharacters()
    }
  } catch (error) {
    console.error('添加角色失败:', error)
    alert('添加角色失败')
  }
}

// 选择节点
const selectNode = (node) => {
  selectedNode.value = node
}

// 添加剧情节点
const addPlotNode = () => {
  const newId = plotNodes.value.length + 1
  plotNodes.value.push({
    id: newId,
    type: '对话',
    content: '新节点...'
  })
}

// 保存项目
const saveProject = async () => {
  isSaving.value = true
  try {
    // 模拟保存
    await new Promise(resolve => setTimeout(resolve, 1000))
    alert('保存成功！')
  } catch (error) {
    console.error('保存失败:', error)
  } finally {
    isSaving.value = false
  }
}

// 预览项目
const previewProject = () => {
  alert('预览功能开发中...')
}

onMounted(() => {
  fetchCurrentProject()
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
