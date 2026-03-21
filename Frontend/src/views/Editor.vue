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
            @click="openAddCharacter"
            class="w-full mb-4 py-2 border border-dashed border-alien-cyan/50 text-alien-cyan/70 font-mono text-sm hover:border-alien-cyan hover:text-alien-cyan transition-all flex items-center justify-center gap-2"
          >
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            添加角色
          </button>
          
          <div class="space-y-2">
            <button
              v-for="char in characters" 
              :key="char.id"
              @click="openEditCharacter(char)"
              type="button"
              class="alien-card p-3 cursor-pointer group hover:border-alien-cyan transition-all text-left w-full"
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
            </button>
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
                    <span class="text-xs text-gray-500 font-mono">{{ nodeTypeLabel(node.type) }}</span>
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
          <div class="alien-card p-3" v-if="!selectedNode">
            <p class="text-sm text-gray-400 font-mono">点击下方剧情节点后，在这里编辑节点内容</p>
          </div>

          <template v-else>
            <div class="flex items-center justify-between">
              <div>
                <div class="text-xs text-gray-500 font-mono">当前节点</div>
                <div class="text-sm text-white font-display font-bold">#{{ selectedNode.id }}</div>
              </div>
            </div>

            <div>
              <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">节点类型</label>
              <select v-model="nodeForm.type" class="alien-input py-2 text-sm">
                <option v-for="opt in nodeTypeOptions" :key="opt.value" :value="opt.value">{{ opt.label }}</option>
              </select>
            </div>

            <div>
              <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">说话角色</label>
              <select v-model="nodeForm.characterId" class="alien-input py-2 text-sm">
                <option value="">无</option>
                <option v-for="char in characters" :key="char.id" :value="String(char.id)">{{ char.name }}</option>
              </select>
            </div>

            <div>
              <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">对话文本</label>
              <textarea v-model="nodeForm.text" class="alien-input h-24 resize-none text-sm" placeholder="输入对话内容..."></textarea>
            </div>

            <div>
              <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">背景</label>
              <div class="space-y-2">
                <input v-model="nodeForm.backgroundUrl" type="text" class="alien-input py-2 text-sm" placeholder="输入背景URL...">
                <div class="flex items-center gap-3">
                  <input type="file" @change="handleNodeBackgroundUpload" class="text-xs text-gray-400">
                  <div v-if="nodeForm.backgroundUrl" class="w-14 h-10 rounded-lg overflow-hidden border border-alien-cyan/30">
                    <img :src="nodeForm.backgroundUrl" alt="背景预览" class="w-full h-full object-cover">
                  </div>
                </div>
              </div>
            </div>

            <div>
              <label class="block text-gray-400 font-mono text-xs mb-2 uppercase tracking-wider">背景音乐</label>
              <select v-model="nodeForm.bgm" class="alien-input py-2 text-sm">
                <option value="">无</option>
                <option v-for="audio in audioFiles" :key="audio.name" :value="audio.name">{{ audio.name }}</option>
              </select>
            </div>
          </template>
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

    <!-- 编辑角色弹窗 -->
    <Teleport to="body">
      <Transition name="fade">
        <div v-if="showEditCharacter" class="fixed inset-0 z-50 flex items-center justify-center p-4">
          <div class="absolute inset-0 bg-alien-void/90 backdrop-blur-sm" @click="closeEditCharacter"></div>
          <div class="alien-card w-full max-w-md relative z-10 animate-warp">
            <div class="flex items-center justify-between mb-6">
              <h3 class="text-2xl font-display font-bold neon-text-cyan">编辑角色</h3>
              <button @click="closeEditCharacter" class="text-gray-500 hover:text-white transition-colors">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </div>

            <form @submit.prevent="submitUpdateCharacter">
              <div class="space-y-4">
                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">角色名称</label>
                  <input v-model="editCharacterForm.name" type="text" class="alien-input" placeholder="输入角色名称..." required>
                </div>

                <div>
                  <label class="block text-alien-cyan font-mono text-sm mb-2 tracking-wider">角色图片 URL</label>
                  <input v-model="editCharacterForm.imageUrl" type="text" class="alien-input" placeholder="输入图片URL...">
                  <div class="mt-2 flex items-center gap-3">
                    <input type="file" @change="handleEditFileUpload" class="text-xs text-gray-400">
                    <div v-if="editCharacterForm.imageUrl" class="w-10 h-10 rounded-full overflow-hidden border border-alien-cyan/30">
                      <img :src="editCharacterForm.imageUrl" alt="预览" class="w-full h-full object-cover">
                    </div>
                  </div>
                </div>
              </div>

              <div class="flex gap-3 mt-6">
                <button type="button" @click="submitDeleteCharacter" class="py-3 px-4 border border-red-500/50 text-red-400 font-display font-bold text-sm uppercase tracking-widest hover:border-red-400 hover:text-red-300 transition-all" :disabled="isUpdatingCharacter || isDeletingCharacter">
                  <span v-if="isDeletingCharacter">删除中...</span>
                  <span v-else>删除</span>
                </button>
                <button type="button" @click="closeEditCharacter" class="flex-1 py-3 border border-gray-600 text-gray-400 font-display font-bold text-sm uppercase tracking-widest hover:border-gray-400 hover:text-white transition-all" :disabled="isUpdatingCharacter || isDeletingCharacter">
                  取消
                </button>
                <button type="submit" class="flex-1 alien-btn" :disabled="isUpdatingCharacter || isDeletingCharacter">
                  <span v-if="isUpdatingCharacter">保存中...</span>
                  <span v-else>保存</span>
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
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { gameApi, characterApi, plotApi, fileApi } from '../api'

const router = useRouter()
const currentProject = ref(null)
const isLoadingProject = ref(false)
const isSaving = ref(false)
const activeSidebarTab = ref('characters')
const selectedCharacter = ref(null)
const selectedNode = ref(null)
const showAddCharacter = ref(false)
const showEditCharacter = ref(false)
const isUpdatingCharacter = ref(false)
const isDeletingCharacter = ref(false)

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
  { id: 1, type: 'dialogue', content: '欢迎来到游戏世界...' },
  { id: 2, type: 'choice', content: '选择你的道路' }
])

const characterForm = ref({
  name: '',
  rate: 0,
  imageUrls: []
})

const editCharacterForm = ref({
  id: null,
  name: '',
  imageUrl: ''
})

const nodeTypeOptions = [
  { value: 'dialogue', label: '对话' },
  { value: 'choice', label: '选项' },
  { value: 'action', label: '动作' },
  { value: 'bgm', label: '背景音乐' }
]

const nodeForm = ref({
  type: 'dialogue',
  characterId: '',
  text: '',
  backgroundUrl: '',
  bgm: ''
})

const nodeTypeLabel = (type) => {
  const mapping = {
    dialogue: '对话',
    choice: '选项',
    action: '动作',
    bgm: '背景音乐',
    对话: '对话',
    选项: '选项',
    动作: '动作',
    背景音乐: '背景音乐'
  }
  return mapping[type] || '对话'
}

const normalizeNodeType = (type) => {
  if (type === 'dialogue' || type === 'choice' || type === 'action' || type === 'bgm') return type
  const mapping = {
    对话: 'dialogue',
    选项: 'choice',
    动作: 'action',
    背景音乐: 'bgm'
  }
  return mapping[type] || 'dialogue'
}

// 获取当前项目信息
const fetchCurrentProject = async () => {
  if (isLoadingProject.value) return
  isLoadingProject.value = true
  try {
    const res = await gameApi.getAllGames()
    if (res.code === 200 && res.data.length > 0) {
      currentProject.value = res.data[0]
      fetchCharacters()
    }
  } catch (error) {
    console.error('获取项目信息失败:', error)
  } finally {
    isLoadingProject.value = false
  }
}

const ensureCurrentProject = async () => {
  if (currentProject.value) return true
  await fetchCurrentProject()
  return !!currentProject.value
}

const openAddCharacter = async () => {
  const ok = await ensureCurrentProject()
  if (!ok) {
    alert('当前没有项目，请先在首页创建项目')
    return
  }
  showAddCharacter.value = true
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

const openEditCharacter = (char) => {
  selectCharacter(char)
  editCharacterForm.value = {
    id: char?.id ?? null,
    name: char?.name ?? '',
    imageUrl: char?.imageUrl ?? ''
  }
  showEditCharacter.value = true
}

const closeEditCharacter = () => {
  showEditCharacter.value = false
  isUpdatingCharacter.value = false
  isDeletingCharacter.value = false
  editCharacterForm.value = { id: null, name: '', imageUrl: '' }
}

const handleEditFileUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  try {
    const res = await fileApi.uploadFile(file)
    if (res.code === 200) {
      editCharacterForm.value.imageUrl = res.data
    }
  } catch (error) {
    console.error('文件上传失败:', error)
    alert('文件上传失败')
  } finally {
    event.target.value = ''
  }
}

const submitUpdateCharacter = async () => {
  if (!editCharacterForm.value.id) {
    alert('角色缺少ID，无法保存。请刷新后重试。')
    return
  }

  isUpdatingCharacter.value = true
  try {
    const res = await characterApi.updateCharacter({
      id: editCharacterForm.value.id,
      name: editCharacterForm.value.name,
      imageUrl: editCharacterForm.value.imageUrl || null
    })
    if (res.code === 200) {
      await fetchCharacters()
      const updated = characters.value.find(c => c.id === editCharacterForm.value.id)
      if (updated) selectedCharacter.value = updated
      closeEditCharacter()
    }
  } catch (error) {
    console.error('更新角色失败:', error)
    alert('更新角色失败')
  } finally {
    isUpdatingCharacter.value = false
  }
}

const submitDeleteCharacter = async () => {
  if (!editCharacterForm.value.id) {
    alert('角色缺少ID，无法删除。请刷新后重试。')
    return
  }

  const ok = confirm('确认删除该角色吗？此操作不可撤销。')
  if (!ok) return

  isDeletingCharacter.value = true
  try {
    const res = await characterApi.deleteCharacter(editCharacterForm.value.id)
    if (res.code === 200) {
      if (selectedCharacter.value?.id === editCharacterForm.value.id) {
        selectedCharacter.value = null
      }
      await fetchCharacters()
      closeEditCharacter()
    }
  } catch (error) {
    console.error('删除角色失败:', error)
    alert('删除角色失败')
  } finally {
    isDeletingCharacter.value = false
  }
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
  const ok = await ensureCurrentProject()
  if (!ok) {
    alert('当前没有项目，请先在首页创建项目')
    return
  }

  const name = characterForm.value.name?.trim()
  if (!name) {
    alert('请输入角色名称')
    return
  }
  
  try {
    const res = await characterApi.createCharacter({
      name,
      rate: characterForm.value.rate || 0,
      imageUrls: (characterForm.value.imageUrls || []).filter(Boolean),
      gameId: currentProject.value.id
    })
    if (res.code !== 200) {
      alert(res.message || '添加角色失败')
      return
    }
    if (!res.data) {
      alert('添加角色失败')
      return
    }

    showAddCharacter.value = false
    characterForm.value = { name: '', rate: 0, imageUrls: [] }
    fetchCharacters()
  } catch (error) {
    console.error('添加角色失败:', error)
    alert('添加角色失败')
  }
}

// 选择节点
const selectNode = (node) => {
  selectedNode.value = node
}

const handleNodeBackgroundUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  try {
    const res = await fileApi.uploadFile(file)
    if (res.code === 200) {
      nodeForm.value.backgroundUrl = res.data
    }
  } catch (error) {
    console.error('文件上传失败:', error)
    alert('文件上传失败')
  } finally {
    event.target.value = ''
  }
}

watch(
  selectedNode,
  node => {
    if (!node) {
      nodeForm.value = { type: 'dialogue', characterId: '', text: '', backgroundUrl: '', bgm: '' }
      return
    }

    nodeForm.value = {
      type: normalizeNodeType(node.type),
      characterId: node.characterId != null ? String(node.characterId) : '',
      text: node.content || '',
      backgroundUrl: node.backgroundUrl || '',
      bgm: node.bgm || ''
    }
  },
  { immediate: true }
)

watch(
  nodeForm,
  form => {
    if (!selectedNode.value) return
    selectedNode.value.type = form.type
    selectedNode.value.content = form.text
    selectedNode.value.characterId = form.characterId ? Number(form.characterId) : null
    selectedNode.value.backgroundUrl = form.backgroundUrl || null
    selectedNode.value.bgm = form.bgm || null
  },
  { deep: true }
)

// 添加剧情节点
const addPlotNode = () => {
  const newId = plotNodes.value.length + 1
  plotNodes.value.push({
    id: newId,
    type: 'dialogue',
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
