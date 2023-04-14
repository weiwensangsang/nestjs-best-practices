# Guard



Both Guard and Interceptor in NestJS are middleware used to intercept requests and execute specific logic.

Guard is a more fine-grained interceptor, which is used to verify that the request is authorized to access certain resources. Guards can be applied at the controller and handler method level. Before the request reaches the handler method, the Guard will authenticate the request and decide whether to allow the request based on the authentication result. If the request is denied, the Guard will return a response, otherwise the request will be passed on to the handler method.

Interceptor is more general, it can intercept the request before or after the request reaches the handler method to modify the request or response data. Interceptors can be applied at both controller and handler method levels. Interceptors can modify request data before the request reaches the handler method. After the request has been processed and the response generated, the Interceptor can modify the response data.

Therefore, Guard is mainly used to verify the authority of the request, while Interceptor is used to intercept and modify request and response data.