# pan-paas-backend

[English](https://baidu.com) | [中文](https://github.comyplove156/pan-paas-backend/master/README.md)

pan-paas 是一个通用的、基于 Web 的 **[Kubernetes](https://kubernetes.io) 、[Istio](https://istio.io)  多集群管理平台**。
通过可视化 Kubernetes 对象表单模板编辑的方式，降低业务接入成本，
拥有完整的权限管理系统，适应多租户场景，是一款适合企业级微服务应用使用的**Paas管理平台**。

## Features

- 自定义权限管理：用户通过角色与部门和项目关联（每个项目对应各自的环境），拥有项目角色允许操作项目资源，适合多用户场景。
- 简化 Kubernetes 对象创建：提供基础 Kubernetes 对象表单添加方式，简化k8s应用部署流程。
- 支持多集群、多租户：可以同时管理多个 Kubernetes 集群，并针对性添加特定配置，更方便的多集群、多租户管理。
- 提供基于 APIKey 的开放接口调用：用户可自主申请相关 APIKey 并管理自己的部门和项目，运维人员也可以申请全局 APIKey 进行特定资源的全局管理。
- 保留完整的发布历史：用户可以便捷的找到任何一次历史发布，并可轻松进行回滚，以及基于特定历史版本更新 Kubernetes 资源。
- 提供站内通知系统：方便管理员推送集群、业务通知和故障处理报告等。

## 架构设计

整体采用前后端分离的方案，其中前端采用 Vue全家桶 框架进行数据交互和展示。后端采用 SpringBoot 框架做数据接口处理，
使用 fabric8 与 Kubernetes 进行交互，数据使用 MySQL 存储。

![Dashboard UI workloads page](https://github.com/yplove156/kubernetes/blob/master/1.jpg)

## 项目依赖

- JDK 1.8+
- MySQL 5.6+

## 快速启动



- 创建配置文件



- 写入数据库相关配置（请修改为数据库实际地址）


- 启动服务



通过上述命令，您可以从通过 http://127.0.0.1:9413/ 访问, 默认管理员账号 admin:panpaas。


## 文档



