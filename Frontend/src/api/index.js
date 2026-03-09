import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// @ts-check

// 请求拦截器
api.interceptors.request.use(
  config => {
    console.log(`[API Request] ${config.method?.toUpperCase()} ${config.url}`, config.data)
    return config
  },
  error => {
    console.error('[API Request Error]', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    console.log(`[API Response] ${response.config.url}`, response.data)
    return response.data
  },
  error => {
    console.error('[API Response Error]', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

// 游戏相关 API
export const gameApi = {
  // 获取所有游戏
  getAllGames: () => api.get('/game'),
  
  // 根据ID获取游戏
  getGameById: (id) => api.get(`/game/${id}`),
  
  // 创建游戏
  createGame: (data) => api.post('/game', data),
  
  // 更新游戏
  updateGame: (data) => api.put('/game', data),
  
  // 删除游戏
  deleteGame: (id) => api.delete(`/game/${id}`)
}

// 角色相关 API
export const characterApi = {
  // 获取所有角色
  getAllCharacters: () => api.get('/character'),
  
  // 根据ID获取角色
  getCharacterById: (id) => api.get(`/character/${id}`),
  
  // 根据游戏ID获取角色
  getCharactersByGameId: (gameId) => api.get(`/character/game/${gameId}`),
  
  // 创建角色
  createCharacter: (data) => api.post('/character', data),
  
  // 更新角色
  updateCharacter: (data) => api.put('/character', data),
  
  // 删除角色
  deleteCharacter: (id) => api.delete('/character/' + id)
}

// 剧情节点相关 API
export const plotApi = {
  // 获取所有剧情节点
  getAllPlotNodes: () => api.get('/plot'),
  
  // 根据ID获取剧情节点
  getPlotNodeById: (id) => api.get(`/plot/${id}`),
  
  // 根据游戏ID获取剧情节点
  getPlotNodesByGameId: (gameId) => api.get(`/plot/game/${gameId}`),
  
  // 创建剧情节点
  createPlotNode: (data) => api.post('/plot', data),
  
  // 更新剧情节点
  updatePlotNode: (data) => api.put('/plot', data),
  
  // 删除剧情节点
  deletePlotNode: (id) => api.delete(`/plot/${id}`)
}

// 文件上传 API
export const fileApi = {
  // 上传文件
  uploadFile: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.post('/file/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

export default api
