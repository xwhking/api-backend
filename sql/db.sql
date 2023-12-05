use yuapi;

-- 接口表
-- auto-generated definition
create table interface_info
(
    id             bigint auto_increment comment 'id'
        primary key,
    name           varchar(256)                           not null comment '接口名称',
    description    varchar(256)                           null comment '描述',
    host           varchar(256) default 'localhost:8080'  not null comment '请求的host',
    uri            varchar(512)                           not null comment '接口地址',
    requestHeader  text                                   null comment '请求头',
    responseHeader text                                   null comment '响应头',
    requestParams  varchar(1024)                          null comment '接口请求参数',
    userId         bigint                                 not null comment '创建人',
    status         int          default 0                 not null comment '接口状态（0-关闭， 1-开启）',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    method         varchar(256)                           not null comment '请求类型',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint      default 0                 not null comment '是否删除（0-未删，1-删除）',
    constraint url
        unique (uri)
)
    comment '接口表';



-- 用户调用接口关系表
create table if not exists yuapi.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)',
) comment '用户调用接口关系';
use yuapi;
-- 一句话表定义
create table sentences
(
    id           bigint auto_increment comment 'id'
        primary key,
    content      varchar(1024)                      not null comment '句子主体',
    type         varchar(32)                        not null comment '句子类型',
    sentenceFrom varchar(256)                       null comment '句子来由',
    fromWho      varchar(1024)                      null comment '句子作者',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '句子' collate = utf8mb4_unicode_ci;


