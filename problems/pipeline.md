# Pipeline



In NestJS, a Pipeline is a mechanism for modifying request and response objects before or after passing them to a handler. Pipelines can intercept requests and responses, and perform processes such as validation, transformation, and filtering on them to ensure that they meet the requirements of the application. Pipelines can be either synchronous or asynchronous.

Pipelines can be applied to an entire controller (Controller) or a single route handler (Route Handler). Pipelines are added to controllers or route handlers by using the @UsePipes() decorator.

In the pipeline, multiple middleware (Middleware) can be used to process the request and response, and the middleware can be executed in the specified order in the pipeline. During the pipeline, if any middleware returns an Observable or a Promise, it is considered an asynchronous operation and NestJS will wait for it to complete before moving on to the next middleware or route handler.

In the pipeline, you can also use Interceptors and Guards to perform similar tasks. Interceptors can intercept requests and responses and modify them. Guards can authenticate and authorize requests to ensure they meet the requirements of the application.

By using pipelines, common processing logic (such as validation, conversion, filtering, etc.) can be extracted from handlers and encapsulated in pipelines, thereby improving code maintainability and reusability.