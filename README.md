# NestJS Best Practices

This project describes the architectural principles and best practices of NestJS.

### NodeJS


-  什么是事件循环 (Event Loop)？它是如何工作的？
-  什么是回调函数 (Callback function)？在 Node.js 中为什么会使用回调函数？
-  什么是 Promise？如何使用 Promise 处理异步操作？
-  如何在 Node.js 中实现多进程模型？有哪些模块可以使用？
-  如何处理内存泄漏 (Memory Leak)？你有哪些工具可以用来诊断和解决内存泄漏问题？
-  如何使用 Node.js 实现文件上传和下载？
-  如何使用 Node.js 实现 WebSocket 通信？
-  如何使用 Node.js 实现缓存 (Cache)？你有哪些选择？
-  如何使用 Node.js 实现加密和解密数据？
-  什么是流 (Stream)？在 Node.js 中如何使用流来处理大型数据集？



1. What is Node.js? Where can you use it?
2. Why use Node.js?
3. How does Node.js work?
4. Why is Node.js Single-threaded?
5. If Node.js is single-threaded, then how does it handle concurrency?
6. Explain callback in Node.js.
7. What are the advantages of using promises instead of callbacks?
8. How would you define the term I/O? 
9. How is Node.js most frequently used?
10. Explain the difference between frontend and backend development?
11. What is NPM?
12. What are the modules in Node.js?
13. What is the purpose of the module .Exports?
14. Why is Node.js preferred over other backend technologies like Java and PHP?
15. What is the difference between Angular and Node.js?
16. Which database is more popularly used with Node.js?
17. What are some of the most commonly used libraries in Node.js?
18. What are the pros and cons of Node.js?
19. What is the command used to import external libraries?
20. What does event-driven programming mean?
21. What is an Event Loop in Node.js?
22. Differentiate between process.nextTick() and setImmediate()?
23. What is an EventEmitter in Node.js?
24. What are the two types of API functions in Node.js?
25. What is the package.json file?
26. How would you use a URL module in Node.js?
27. What is the Express.js package?
28. How do you create a simple Express.js application?
29. What are streams in Node.js?
30. How do you install, update, and delete a dependency?
31. How do you create a simple server in Node.js that returns Hello World?
32. Explain asynchronous and non-blocking APIs in Node.js.
33. How do we implement async in Node.js?
34. What is a callback function in Node.js?
35. What is REPL in Node.js?
36. What is the control flow function?
37. How does control flow manage the function calls?
38. What is the difference between fork() and spawn() methods in Node.js?
39. What is the buffer class in Node.js?
40. What is piping in Node.js?
41. What are some of the flags used in the read/write operations in files?
42. How do you open a file in Node.js?
43. What is callback hell?
44. What is a reactor pattern in Node.js?
45. What is a test pyramid in Node.js?
46. For Node.js, why does Google use the V8 engine?
47. Describe Node.js exit codes.
48. Explain the concept of middleware in Node.js.
49. What are the different types of HTTP requests?
50. How would you connect a MongoDB database to Node.js?
51. What is the purpose of NODE_ENV?

52. List the various Node.js timing features.
53. What is WASI, and why is it being introduced?



1. Differentiate between JavaScript and Node.js.
2. What Is Node.js?
3. List down the major benefits of using Node.js?
4. What is the difference between Angular and Node.js?
5. Why Node.js is single threaded?
6. How do Node.js works?
7. Where Node.js can be used?
8. How many types of API functions are there in Node.js?
9. What is the difference between Asynchronous and Non-blocking?
10. What is package.json?
11. What do you understand by Event-driven programming?
12. What is an Event loop in Node.js and how does it work?
13. Explain  REPL in the context of Node.js.
14. List down the tasks which should be done asynchronously using the event loop?
15. List down the steps using which “Control Flow” controls the function calls in Node.js?
16. What do you understand by a test pyramid?
17. What is an error-first callback in Node.js?
18. Explain the purpose of module.exports?
19. What do you understand by Reactor Pattern in Node.js?
20. What’s the difference between ‘front-end’ and ‘back-end’ development?
21. What are LTS releases of Node.js?
22. List down the major security implementations within Node.js?
23. What do you understand by callback hell?
24. Explain libuv.
25. Explain the concept of middleware in Node.js?
26. Explain the concept of URL module.
27. What do you understand by ESLint?
28. For Node.js, why Google uses V8 engine?
29. Explain the working of the control flow function. 
30. List down the two arguments that async.queue takes as input?
31. Differentiate between spawn() and fork() methods in Node.js? 
32. What do you understand by global objects in Node.js? 
33. Explain the concept of stub in Node.js.
34. How assert works in Node.js?
35. Define the concept of the test pyramid. Explain the process to implement them in terms of HTTP APIs.
36. Explain the purpose of ExpressJS package?
37. Differentiate between process.nextTick() and setImmediate()?
38. Explain the usage of a buffer class in Node.js?
39. How does Node.js handle the child threads?
40. Explain stream in Node.js along with its various types.
41. What is the use of NODE_ENV?
42. Differentiate between readFile vs createReadStream in Node.js?
43. List down the various timing features of Node.js.
44. Explain the concept of Punycode in Node.js?
45. Differentiate between Node.js vs Ajax?
46. Does Node.js provide any Debugger?
47. Describe the exit codes of Node.js.
48. What do you understand by an Event Emitter in Node.js?
49. Is cryptography supported in Node.js?
50. Explain the reason as to why Express ‘app’ and ‘server’ must be kept separate.



1. What is the difference between Node.js and JavaScript?
2. What is Node.js?
3. Briefly explain the working of Node.js.
4. Where is Node.js used?
5. What is the difference between Node.js and Angular?
6. Why is Node.js single-threaded?
Check out our Full Stack Web Development Course video:
7. What are the different API functions supported by Node.js?
8. What is the difference between synchronous and asynchronous functions?
9. What is the control flow function?
10. Why is Node.js so popular these days?
11. What is an event loop in Node.js?
12. What are the asynchronous tasks that should occur in an event loop?
13. What is the order of execution in control flow statements?
14. What are the input arguments for an asynchronous queue?
15. Are there any disadvantages to using Node.js?
16. What is the primary reason to use the event-based model in Node.js?
17. How can you import external libraries into Node.js?
18. What is meant by event-driven programming in Node.js?
19. What is the difference between Ajax and Node.js?
20. What is the framework that is used majorly in Node.js today?
21. What are the security implementations that are present in Node.js?
22. What is the meaning of a test pyramid?
23. What is Libuv?
24. Why does Google use the V8 engine for Node.js?
25. What is the difference between spawn and fork methods in Node.js?
26. What is the use of middleware in Node.js?
27. What are global objects in Node.js?
28. Why is assert used in Node.js?
29. What are stubs in Node.js?
30. How is a test pyramid implemented using the HTML API in Node.js?
31. Why is a buffer class used in Node.js?
32. Why is ExpressJS used?
33. What is the use of the connect module in Node.js?
34. What are streams in Node.js?
35. What are the types of streams available in Node.js?
36. What is the use of REPL in Node.js?
37. What is meant by tracing in Node.js?
38. Where is package.json used in Node.js?
39. What is the difference between readFile and createReadStream in Node.js?
40. What is the use of the crypto module in Node.js?
41. What is a passport in Node.js?
42. How to get information about a file in Node.js?
43. How does the DNS lookup function work in Node.js?
44. What is the use of EventEmitter in Node.js?
45. What is the difference between setImmediate() and setTimeout()?
46. What is the use of module.exports in Node.js?
47. Why do you think you are the right fit for this Node.js role?
48. Do you have any past Node.js work experience?
49. Do you have any experience working in the same industry like ours?
50. Do you have any certification to boost your candidature for this Node.js role?

1、什么是线程池，Node.js 中哪个库处理它 ? 

2、如何通过集群提高 Node.js 的性能 ？ 

3、worker 工作线程与集群有何不同 ？ 

4、Node.js 中的事件发射器是什么 ？ 

5、如何测量异步操作的持续时间 ？

 6、如何衡量异步操作的性能 ？ 

7、对于 Node.js，为什么 Google 使用 V8 引擎 ？ 

8、为什么要把 Express 应用和[服务器](https://cloud.tencent.com/product/cvm?from=20065&from_column=20065)分开 ？

 9、 解释 Node.js 中的Reactor反应器模式是什么 ？ 

10、什么是[中间件](https://cloud.tencent.com/product/tdmq?from=20065&from_column=20065) ？ 

11、什么是 node.js 缓冲区 ？

 12、什么是node.js流 ？ 

13、我们如何在node.js中使用async await ？

 14、如何在 Node.js 中创建一个返回 Hello World 的简单服务器？





### Javascritpt

https://blog.51cto.com/u_15503053/5050894











### Typescript



https://exploringjs.com/tackling-ts/toc.html











NestJS

GraphQL

Typeorm

Sample

