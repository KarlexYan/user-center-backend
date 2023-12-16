-- auto-generated definition
create table user
(
    user_id       bigint auto_increment comment '用户ID'
        primary key,
    user_name     varchar(256) null comment '用户昵称',
    user_account  varchar(256) null comment '用户账号',
    avatar_url    varchar(1024) null comment '用户头像',
    user_gender   tinyint null comment '性别 1男2女',
    user_age      int null comment '年龄',
    user_password varchar(512)       not null comment '密码',
    telephone     varchar(128) null comment '电话',
    email         varchar(512) null comment '电子邮箱',
    user_status   int      default 0 not null comment '状态 0正常',
    created_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_time  datetime default CURRENT_TIMESTAMP null comment '修改时间',
    is_delete     tinyint  default 0 not null comment '是否删除 0否 1是',
    user_role     int      default 0 not null comment '角色 0 普通用户 1管理员'
) comment '用户表';

