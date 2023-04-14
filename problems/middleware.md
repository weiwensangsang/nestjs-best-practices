# Middleware

NestJS middleware functions are implemented using the concept of a "middleware pipeline". A middleware pipeline is a series of functions that are executed sequentially for each incoming HTTP request. Each middleware function is responsible for processing incoming requests, performing some action, and then passing control to the next middleware function in the pipeline.

The core of NestJS middleware implementation is the MiddlewareProvider class. This class implements the NestJS MiddlewareInterface interface and provides methods for middleware execution. When a Nest application receives an HTTP request, Nest creates a MiddlewareProvider instance and adds it to the middleware pipeline. Nest then calls the use() method of the MiddlewareProvider instance, which calls the middleware handler function and passes control to the next middleware function in the pipeline.

In NestJS, developers can use built-in middleware functions such as authentication, error handling, and logging, or create custom middleware functions to handle specific needs. Developers only need to create a class that implements the MiddlewareInterface interface, and implement the use() method to process requests and perform operations.



In NestJS, middleware is a mechanism that can intercept and process HTTP requests and responses. Middleware can be used to implement some common functions, such as logging, request verification, authorization authentication, etc.

To implement a middleware, you can use the @Middleware() decorator provided by NestJS. Specific steps are as follows:

Create a middleware class that implements the NestMiddleware interface and implements the use() method. For example:


```javascript
import { Injectable, NestMiddleware } from '@nestjs/common';
import { Request, Response, NextFunction } from 'express';

@Injectable()
export class LoggerMiddleware implements NestMiddleware {
   use(req: Request, res: Response, next: NextFunction) {
     console.log('Request...');
     next();
   }
}
```

Register middleware in the module. Middleware can be registered using the middlewares attribute of the module decorator. For example:


```javascript
import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { LoggerMiddleware } from './logger.middleware';

@Module({
   imports: [],
   controllers: [AppController],
   providers: [LoggerMiddleware],
   middlewares: [LoggerMiddleware],
})
export class AppModule {}
```


Here, the middleware LoggerMiddleware is registered in providers and middlewares at the same time, both methods are available.

Use middleware on controllers or routes. You can use the @UseMiddleware() decorator to add middleware to controllers or routes. For example:

```javascript
import { Controller, Get, UseMiddleware } from '@nestjs/common';
import { LoggerMiddleware } from './logger.middleware';

@Controller()
@UseMiddleware(LoggerMiddleware)
export class AppController {
   @Get()
   getHello(): string {
     return 'Hello World!';
   }
}
```

In this way, when a request is sent, the middleware LoggerMiddleware will be called and process the request.

Note: If you want to use the same middleware on multiple routes, you can use the @UseInterceptors() decorator to add interceptors to the controller.