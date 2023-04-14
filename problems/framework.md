# Framework: a JavaScript Version Spring

Diffs betweem

NestJS and Spring share many similarities in their design and implementation because NestJS was inspired by Spring. NestJS is a Node.js framework based on TypeScript that provides features similar to Spring, such as dependency injection and aspect-oriented programming (AOP). This results in code structures that are very similar between NestJS and Spring, as they both follow the same design patterns and principles.

Specifically, NestJS and Spring both adopt a design pattern based on inversion of control (IoC) and dependency injection (DI). This pattern allows developers to separate the dependencies between objects from the code and let the framework handle these dependencies. This approach improves the testability and maintainability of the code.

In addition, NestJS also provides features similar to Spring Boot, such as auto-configuration and fast application startup. This makes NestJS and Spring share similar workflows and development experiences when building web applications.

In summary, NestJS and Spring share many similarities in their design and implementation, which makes it easy for developers to transition from Spring to NestJS or vice versa.

什么是 NestJS？它有什么优点和特点？

Nestjs 如何实现依赖注入？

https://zhuanlan.zhihu.com/p/546947975   spring 都是通过反射机制来实现依赖注入的。当 Spring 容器创建一个 Bean 对象时，它会通过反射机制来查找该对象所依赖的其他对象，并将这些对象自动注入到该对象中



Nestjs如何实现中间件



Nestjs 中的模块



# [Providers](https://docs.nestjs.cn/9/providers?id=providers)

Providers 是 `Nest` 的一个基本概念。许多基本的 `Nest` 类可能被视为 provider - `service`,` repository`, `factory`, `helper` 等等。 他们都可以通过 `constructor` **注入**依赖关系。 这意味着对象可以彼此创建各种关系，并且“连接”对象实例的功能在很大程度上可以委托给 `Nest`运行时系统。 Provider 只是一个用 `@Injectable()` 装饰器注释的类。

We have controller in Nestjs

这些概念在spring和nest中没有区别





## [依赖注入](https://docs.nestjs.cn/9/providers?id=依赖注入)

Nest 是建立在强大的设计模式，通常称为依赖注入。我们建议在官方的 [Angular文档](https://angular.cn/guide/dependency-injection)中阅读有关此概念的精彩文章。

在 `Nest` 中，借助 **TypeScript** 功能，管理依赖项非常容易，因为它们仅按类型进行解析。在下面的示例中，`Nest` 将 `catsService` 通过创建并返回一个实例来解析 `CatsService`（或者，在单例的正常情况下，如果现有实例已在其他地方请求，则返回现有实例）。解析此依赖关系并将其传递给控制器的构造函数（或分配给指定的属性）：

```typescript
constructor(private readonly catsService: CatsService) {}
```





NestJS和Spring框架都是用来构建后端应用程序的框架，它们之间有一些区别。

1. 组织代码的方式

在NestJS中，代码是通过将应用程序拆分为模块来组织的。每个模块都有自己的控制器、服务、管道等，并且模块之间可以相互依赖。模块化的方式可以让应用程序的代码更加清晰、易于维护。

而在Spring框架中，代码是通过将应用程序拆分为包和类来组织的。这些类包括控制器、服务、存储库等，可以通过依赖注入来组合和使用。Spring框架也提供了模块化的方式，但是相对NestJS来说，更多的是通过将不同的功能分别打包成jar文件来实现。



中间件是在路由处理程序 **之前** 调用的函数。 

在NestJS中，管道（Pipe）是一个中间件机制，用于在请求进入控制器之前或离开控制器之后对请求进行处理。其名称“Pipe”（管道）来源于计算机科学中的管道概念，是一种将数据从一个地方传输到另一个地方的方式。



## [截取切面](https://docs.nestjs.cn/9/interceptors?id=截取切面)

第一个用例是使用拦截器在函数执行之前或之后添加额外的逻辑。当我们要记录与应用程序的交互时，它很有用，例如 存储用户调用，异步调度事件或计算时间戳。作为一个例子，我们来创建一个简单的例子