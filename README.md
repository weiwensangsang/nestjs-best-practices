# NestJS Best Practices

This project describes the architectural principles and best practices of NestJS.

### NodeJS


-  [Promise and Callback](problems/promise-callback.md) 


- [Event Loop, Timers, and process.nextTick()](problems/event-loop.md) 
- [Async and Await](problems/async-await.md) 

- [NPM and modules](problems/npm.md) 

- [Express.js](problems/express.md) 

- [Thread and Cluster](problems/thread.md) 


- [Libuv, V8 and Common Tools](problems/tools.md) 
- [Test and Security](problems/test-security.md) 


  -  [Build some things](problems/build.md) 
  -  [Cons of Node.js](problems/cons.md) 
  -  [44 very hard pure JavaScript questions](problems/44-hard-questions.md) 



### NestJS

- [Framework: a JavaScript Version Spring](problems/framework.md) 



- 什么是 NestJS？它有什么优点和特点？
- 什么是依赖注入（DI）？在 NestJS 中如何使用 DI？
- 如何在 NestJS 中创建控制器（controller）和服务（service）？
- 如何在 NestJS 中实现中间件（middleware）？
- 什么是管道（pipe）？如何在 NestJS 中实现管道？
- 如何在 NestJS 中使用拦截器（interceptor）？
- 如何在 NestJS 中进行数据验证（data validation）？
- 如何在 NestJS 中实现身份验证（authentication）和授权（authorization）？
- 如何在 NestJS 中处理异常（exception）和错误（error）？
- 如何在 NestJS 中使用 GraphQL？
- 如何在 NestJS 中进行测试（testing）？
- 什么是 NestJS 中的模块（module）？如何在 NestJS 中创建和使用模块？
- 如何在 NestJS 中使用 TypeORM 进行数据库操作？
- 如何在 NestJS 中使用 gRPC 实现微服务通信？
- 如何在 NestJS 中实现缓存（cache）功能？
- 如何在 NestJS 中进行日志记录（logging）？
- 如何在 NestJS 中使用微服务（microservices）进行分布式系统开发？
- 什么是 NestJS 中的全局拦截器（global interceptor）？如何在 NestJS 中使用全局拦截器？
- 如何在 NestJS 中使用 Passport 实现多种身份验证策略？
- 如何在 NestJS 中使用 JWT 进行身份验证？
- 如何在 NestJS 中使用 ACL 进行授权管理？
- 如何在 NestJS 中使用 WebSockets 进行实时通信？
- 如何在 NestJS 中使用 Redis 实现高性能缓存？
- 如何在 NestJS 中实现异步任务（asynchronous task）？
- 如何在 NestJS 中使用 AWS Lambda 进行无服务器开发？
- 如何在 NestJS 中使用 Docker 进行容器化部署？
- 如何在 NestJS 中使用 Kubernetes 进行容器编排和部署？
- 什么是 NestJS 中的异步模块（async module）？如何使用异步模块？
- nestjs Bull

是的，NestJS Bull使用子进程在单独的Node.js进程中运行作业，与主应用程序分离。这被称为进程隔离或分叉。

当您在NestJS中将作业添加到Bull队列时，它会被序列化并使用Node.js内置的`child_process.fork()`方法发送到子进程。然后，该子进程独立于主进程执行作业。作业完成后，子进程向主进程发送一条消息，指示作业已完成。

使用子进程可以将计算密集型或耗时的任务从主进程中分离出来，以充分利用多核处理器和提高应用程序的性能。

具体可以运行多少个子进程，取决于您的服务器硬件配置、操作系统和其他正在运行的进程。通常，您可以在单个服务器上运行数十个子进程，具体取决于您的硬件和资源需求。不过，您应该根据您的具体情况和应用程序需求来调整子进程的数量。在使用子进程时，您还应该注意内存占用和CPU利用率，以避免过度使用服务器资源和影响其他进程的性能。

### Best Practice

- React + Nodejs + Typeorm + GraphQL + PostgreSQL
