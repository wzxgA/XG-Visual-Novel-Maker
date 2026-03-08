-- 创建游戏表
CREATE TABLE `game_novel` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '游戏id',
  `name` VARCHAR(255) NOT NULL COMMENT '游戏名称',
  `author` VARCHAR(255) NOT NULL COMMENT '作者',
  `introduction` TEXT COMMENT '游戏介绍',
  `cover` VARCHAR(255) COMMENT '游戏封面',
  `bgm_url` VARCHAR(255) COMMENT '游戏背景音乐url',
  `cover_url` VARCHAR(255) COMMENT '游戏封面url',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏表';

-- 创建角色表
CREATE TABLE `character` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色id',
  `game_id` BIGINT NOT NULL COMMENT '游戏id',
  `name` VARCHAR(255) NOT NULL COMMENT '角色名称',
  `rate` BIGINT DEFAULT 0 COMMENT '角色进度',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`game_id`) REFERENCES `game_novel`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 创建角色形象表
CREATE TABLE `character_image` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '形象id',
  `character_id` BIGINT NOT NULL COMMENT '角色id',
  `image_url` VARCHAR(255) NOT NULL COMMENT '角色形象url',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`character_id`) REFERENCES `character`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色形象表';

-- 创建剧情节点表
CREATE TABLE `plot_node` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '剧情节点id',
  `game_id` BIGINT NOT NULL COMMENT '游戏id',
  `script_json` TEXT NOT NULL COMMENT '剧情脚本',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`game_id`) REFERENCES `game_novel`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='剧情节点表';

-- 创建剧情节点关系表
CREATE TABLE `plot_node_relation` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关系id',
  `from_node_id` BIGINT NOT NULL COMMENT '源节点id',
  `to_node_id` BIGINT NOT NULL COMMENT '目标节点id',
  `type` VARCHAR(20) NOT NULL COMMENT '关系类型: pre(前置) 或 next(后置)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (`from_node_id`) REFERENCES `plot_node`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`to_node_id`) REFERENCES `plot_node`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='剧情节点关系表';

-- 创建索引
CREATE INDEX idx_game_novel_name ON `game_novel`(`name`);
CREATE INDEX idx_character_game_id ON `character`(`game_id`);
CREATE INDEX idx_character_image_character_id ON `character_image`(`character_id`);
CREATE INDEX idx_plot_node_game_id ON `plot_node`(`game_id`);
CREATE INDEX idx_plot_node_relation_from_node ON `plot_node_relation`(`from_node_id`);
CREATE INDEX idx_plot_node_relation_to_node ON `plot_node_relation`(`to_node_id`);
