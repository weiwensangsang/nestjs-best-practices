# NestJS Modules

In NestJS, a module is a collection of related functions and services that share a specific context. They provide a way to organize and structure applications, making the application structure clearer, easier to maintain and expand.

In NestJS, modules can be created and used with the following steps:

Create a new module
Use the nest CLI (command line interface) to create a new module. Open a terminal, go to the root directory of the application and run the following command:

```
nest g module <module-name>
```

This command will create a new module folder in the application's src directory and create an empty module file inside it. For example:

```
nest g module users
```

A new module called "users" will be created.

configuration module
In the new module folder, open the module file (usually <module-name>.module.ts) and configure the module. The module file should look like this:

```javascript
import { Module } from '@nestjs/common';
import { UsersController } from './users.controller';
import { UsersService } from './users.service';

@Module({
   controllers: [UsersController],
   providers: [UsersService],
})
export class UsersModule {}
```

In this example, the module contains a controller and a service, which are defined as UsersController and UsersService respectively. In the module decorator, use the "controllers" and "providers" attributes to add them to the module.

import module
To use the newly created module, it needs to be imported into the main module of the application. Open the application's main module file (usually app.module.ts) and import the new module. For example:

```javascript
import { Module } from '@nestjs/common';
import { UsersModule } from './users/users.module';

@Module({
   imports: [UsersModule],
})
export class AppModule {}
```

In this example, UsersModule is imported into the main module of the application. In the "imports" attribute, add the new module to the array.

Use services from modules
To use services from modules, they can be injected in controllers. For example:

```javascript
import { Controller, Get } from '@nestjs/common';
import { UsersService } from './users.service';

@Controller('users')
export class UsersController {
   constructor(private usersService: UsersService) {}

   @Get()
   getUsers() {
     return this.usersService.getUsers();
   }
}
```

In this example, UsersController injects UsersService into its constructor and uses it to handle GET /users requests.

In short, modules provide a way to organize and structure applications, and make the application structure clearer, easier to maintain and extend. In NestJS, creating and using modules is easy, just follow the above steps.





如何在 NestJS 中使用 gRPC 实现微服务通信？

如何在 NestJS 中实现缓存（cache）功能？

如何在 NestJS 中进行日志记录（logging）？

如何在 NestJS 中实现异步任务（asynchronous task）？

什么是 NestJS 中的异步模块（async module）？如何使用异步模块？

如何在 NestJS 中使用 ACL 进行授权管理？

如何在 NestJS 中使用 Redis 实现高性能缓存？

如何在 NestJS 中处理异常（exception）和错误（error）？

如何在 NestJS 中进行测试（testing）？



### How to implement microservice communication using gRPC in NestJS?

NestJS provides the official @nestjs/microservices module for microservice communication, supporting multiple communication protocols, including gRPC. You can configure the gRPC server and client using the GrpcOptions object provided by @nestjs/microservices. Create a gRPC service by defining a gRPC method by defining the @GrpcMethod decorator. You can use the @Client decorator in your NestJS application to create gRPC clients and call service methods.



### How to implement cache function in NestJS?

NestJS provides the official @nestjs/cache module for caching. This module supports a variety of cache drivers, including memory, redis, memcached, and more. You can inject the cache service by importing the module via CacheModule and using the @InjectCache() decorator in your application. Then, you can use the methods provided by the cache service to set, get, and delete cached data.



### How to do logging in NestJS?

NestJS provides the official @nestjs/common module for logging functionality. You can use the Logger class for logging and inject it in your application. The Logger class supports multiple log levels, including log, error, warn, debug, verbose, etc. You can also use the logging interceptors provided by the @nestjs/common module to log all requests and responses from your application.



### How to implement asynchronous tasks in NestJS?

NestJS provides the official @nestjs/schedule module for implementing asynchronous tasks. The module supports multiple task types, including scheduled tasks, delayed tasks, and cyclic tasks. You can use the @Cron decorator in your NestJS application to define cron jobs, and use the scheduleJob method in the schedule service to execute the jobs. You can also use the setTimeout and setInterval methods to create delayed and recurring tasks.



### What is an async module in NestJS? How to use async module?

An async module is a NestJS module that can be loaded asynchronously. It is often used to resolve circular dependencies between modules, or when modules need to be loaded dynamically. You can create asynchronous modules by defining an async factory function that returns an object containing the configuration of the module. You can then use the forwardRef function in the imports array to reference the async module to avoid circular dependency issues.



### How to use ACL for authorization management in NestJS?

Access Control List (ACL) is used for authorization management in NestJS. It allows you to define a set of rules for determining which users can access specific resources or perform certain actions.

To implement ACL in NestJS, you can follow these steps:

1. Install the `@nestjs/passport` and `@nestjs/jwt` packages.
2. Create a new module called `AuthModule` that will handle authentication and authorization.
3. Define a new `AuthGuard` class that extends the `CanActivate` class from `@nestjs/common` and overrides the `canActivate` method to implement your ACL rules.
4. Add the `AuthGuard` to your controller methods or routes that require authorization.
5. Use the `@UseGuards()` decorator to apply the `AuthGuard` to your routes.



### How to implement high-performance caching with Redis in NestJS?

To implement high-performance caching with Redis in NestJS, you can follow these steps:

1. Install the `nestjs-redis` package.
2. Create a new module called `CacheModule` that will handle caching.
3. Define a new `CacheService` class that uses the `nestjs-redis` package to connect to a Redis server and perform caching operations.
4. Use the `CacheService` to cache frequently accessed data in your controller methods or routes.



### Nestjs Bull

NestJS Bull uses subprocess to run jobs in a separate Node.js process, separate from the main application. This is known as process isolation or forking.

When you add a job to the Bull queue in NestJS, it is serialized and sent to the child process using the Node.js built-in `child_process.fork()` method. This child process then executes the job independently of the main process. When the job is complete, the child process sends a message to the main process indicating that the job is complete.

Use subprocesses to separate computationally intensive or time-consuming tasks from the main process to take full advantage of multi-core processors and improve application performance.

How many child processes you can run depends on your server hardware configuration, operating system, and other running processes. Typically, you can run dozens of child processes on a single server, depending on your hardware and resource requirements. However, you should adjust the number of child processes according to your specific situation and application needs. You should also pay attention to memory footprint and CPU utilization when using subprocesses to avoid overusing server resources and affecting the performance of other processes.

