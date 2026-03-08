# 打包指南

## 1. 打包为 EXE 文件

### 步骤：
1. 确保 Maven 已安装
2. 在 Backend 目录下执行命令：
   ```bash
   mvn clean package
   ```
3. 生成的 EXE 文件位于 `target/VNMaker.exe`

### 注意事项：
- 需要 JRE 17 或更高版本
- EXE 文件包含了完整的后端服务

## 2. 打包为 APK 文件

### 步骤：
1. **前端开发**：
   - 使用 React Native、Flutter 或原生 Android 开发前端应用
   - 前端应用通过 HTTP 请求与后端 API 通信

2. **后端部署**：
   - 将后端服务部署到云服务器或本地服务器
   - 确保前端能访问到后端 API

3. **打包 APK**：
   - 使用 Android Studio 构建前端应用
   - 配置网络请求指向后端服务地址
   - 生成 APK 文件

### 替代方案（嵌入式后端）：
- 使用 Spring Boot Mobile 依赖
- 将后端服务嵌入到 Android 应用中
- 但这种方式性能和稳定性可能不如分离部署

## 3. 运行说明

### 运行 EXE：
1. 双击 `VNMaker.exe` 启动服务
2. 服务默认运行在 `http://localhost:8080`
3. 前端应用可通过此地址访问 API

### 运行 APK：
1. 安装 APK 到 Android 设备
2. 确保设备能访问后端服务
3. 启动应用即可使用

## 4. API 接口

### 游戏管理：
- `POST /api/game` - 创建游戏
- `PUT /api/game` - 更新游戏
- `DELETE /api/game/{id}` - 删除游戏
- `GET /api/game/{id}` - 获取游戏详情
- `GET /api/game` - 获取游戏列表

### 角色管理：
- `POST /api/character` - 创建角色
- `PUT /api/character` - 更新角色
- `DELETE /api/character/{id}` - 删除角色
- `GET /api/character/{id}` - 获取角色详情
- `GET /api/character/game/{gameId}` - 获取游戏角色列表

### 剧情管理：
- `POST /api/plot` - 创建剧情节点
- `PUT /api/plot` - 更新剧情节点
- `DELETE /api/plot/{id}` - 删除剧情节点
- `GET /api/plot/{id}` - 获取剧情节点详情
- `GET /api/plot/game/{gameId}` - 获取游戏剧情节点列表

### 文件上传：
- `POST /api/upload/image` - 上传图片
- `POST /api/upload/audio` - 上传音频
