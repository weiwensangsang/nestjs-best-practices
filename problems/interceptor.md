### Interceptor

NestJS Interceptor is a middleware for manipulating requests or responses before or after a controller or route handler executes. Interceptor can preprocess or verify the request, record the request log, modify the response result, etc.

The implementation principle of Interceptor is to bind interceptor classes to controllers, routing handlers or global applications through decorators. When a NestJS application processes a request, it will match the corresponding controller or route handler according to the route, and then intercept the request or response according to the Interceptor class bound by the decorator.

The Interceptor class needs to implement the NestInterceptor interface in NestJS, which contains the intercept method, which receives two parameters: one is the current request context, and the other is the next function (next) of the request handler. Interceptor can operate on the request in the intercept method, and then pass the request to the next interceptor or request handler by calling the next.handle() method.

For example, here's a simple Interceptor class implementation that prints out the request's method and URL before processing it:



```javascript
import { Injectable, NestInterceptor, ExecutionContext, CallHandler } from '@nestjs/common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable()
export class LoggingInterceptor implements NestInterceptor {
   intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
     const req = context. switchToHttp(). getRequest();
     console.log(`Request ${req.method} ${req.url}`);
     return next.handle().pipe(
       tap(() => {
         console.log(`Response ${req.method} ${req.url}`);
       }),
     );
   }
}
```

Bind this interceptor class to a controller or route handler by using the @UseInterceptors decorator, for example:

```javascript
import { Controller, Get, UseInterceptors } from '@nestjs/common';
import { LoggingInterceptor } from './logging.interceptor';

@Controller('cats')
@UseInterceptors(LoggingInterceptor)
export class CatsController {
   @Get()
   findAll(): string {
     return 'This action returns all cats';
   }
}
```

In the above example, the LoggingInterceptor class is bound to CatsController, it will print out the method and URL of the request before processing the request, and print out the method and URL of the response after processing the request.



### Global Interceptor

Global interceptors can be registered using the useGlobalInterceptors method in your application's AppModule class. For example, the following code registers the LoggingInterceptor defined above:

typescript
copy code
import { Module } from '@nestjs/common';
import { APP_INTERCEPTOR } from '@nestjs/core';
import { LoggingInterceptor } from './logging.interceptor';

@Module({
   providers: [
     {
       provide: APP_INTERCEPTOR,
       useClass: LoggingInterceptor,
     },
   ],
})
export class AppModule {}
Note: APP_INTERCEPTOR is a special constant that identifies a global interceptor in the application.

Now, you have successfully registered a global interceptor.





### AOP

We can use interceptor to get AOP.