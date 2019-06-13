
use wind_auth;
insert into permission(name, value, status, create_time, update_time, parent_id) values
('用户管理', 'auth.user', 1, now(), now(), 1),
('列表', 'auth.user.list', 1, now(), now(), 1),
('详情', 'auth.user.detail', 1, now(), now(), 1),
('新增', 'auth.user.add', 1, now(), now(), 1),
('编辑', 'auth.user.update', 1, now(), now(), 1),
('启用', 'auth.user.enable', 1, now(), now(), 1),
('停用', 'auth.user.disable', 1, now(), now(), 1),

('角色管理', 'auth.role', 1, now(), now(), 1),
('列表', 'auth.role.list', 1, now(), now(), 1),
('详情', 'auth.role.detail', 1, now(), now(), 1),
('新增', 'auth.role.add', 1, now(), now(), 1),
('编辑', 'auth.role.update', 1, now(), now(), 1),
('启用', 'auth.role.enable', 1, now(), now(), 1),
('停用', 'auth.role.disable', 1, now(), now(), 1),

('权限管理', 'auth.permission', 1, now(), now(), 1),
('列表', 'auth.permission.list', 1, now(), now(), 1),
('详情', 'auth.permission.detail', 1, now(), now(), 1),
('新增', 'auth.permission.add', 1, now(), now(), 1),
('编辑', 'auth.permission.update', 1, now(), now(), 1),
('启用', 'auth.permission.enable', 1, now(), now(), 1),
('停用', 'auth.permission.disable', 1, now(), now(), 1),

('菜单管理', 'auth.menu', 1, now(), now(), 1),
('列表', 'auth.menu.list', 1, now(), now(), 1),
('详情', 'auth.menu.detail', 1, now(), now(), 1),
('新增', 'auth.menu.add', 1, now(), now(), 1),
('编辑', 'auth.menu.update', 1, now(), now(), 1),
('启用', 'auth.menu.enable', 1, now(), now(), 1),
('停用', 'auth.menu.disable', 1, now(), now(), 1),
;

insert into permission(role_id,permission_id, create_time, update_time) values
(1, 1, now(), now()),
(1, 5, now(), now()),
(1, 6, now(), now()),
(1, 7, now(), now()),
(1, 8, now(), now()),
(1, 9, now(), now()),
(1, 10, now(), now()),
(1, 11, now(), now()),
(1, 12, now(), now()),
(1, 13, now(), now()),
(1, 14, now(), now()),
(1, 15, now(), now()),
(1, 16, now(), now()),
(1, 17, now(), now()),
(1, 18, now(), now()),
(1, 19, now(), now()),
(1, 20, now(), now()),
(1, 21, now(), now()),
(1, 22, now(), now()),
(1, 23, now(), now()),
(1, 24, now(), now()),
(1, 25, now(), now()),
(1, 26, now(), now()),
(1, 27, now(), now()),
(1, 28, now(), now()),
(1, 29, now(), now()),
(1, 30, now(), now()),
(1, 31, now(), now()),
(1, 32, now(), now())
;


