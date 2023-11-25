use yuapi;

-- 接口表
-- auto-generated definition
create table interface_info
(
    id             bigint auto_increment comment 'id'
        primary key,
    name           varchar(256)                       not null comment '接口名称',
    description    varchar(256)                       null comment '描述',
    url            varchar(512)                       not null comment '接口地址',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态（0-关闭， 1-开启）',
    method         varchar(256)                       not null comment '请求类型',
    userId         bigint                             not null comment '创建人',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除（0-未删，1-删除）',
    requestParams  varchar(1024)                      null comment '接口请求参数',
    accessKey      varchar(512)                       null comment '签名accessKey',
    secretKey      varchar(512)                       null comment '签名secretKey'
)
    comment '接口表';