# Event Loop

The core idea of Node is event-driven programming.

### Why is JavaScript single-threaded?

JavaScript was originally designed to manipulate DOM elements in browser verification forms. It is a scripting language. If js is multi-threaded, then two threads perform conflicting operations on a DOM element at the same time, then the browser's parser is unenforceable.

If there is no asynchrony in JavaScript, it can only be executed from top to bottom. If the parsing time of the previous line is very long, then the following code will be blocked. For users, blocking means "stuck", which leads to a poor user experience. For example, when making an ajax request, if the code behind the data is not returned, there is no way to execute it.

### Why does JavaScript need to be asynchronous?

The function of the event loop is to monitor the stack (call stack) and the task queue (task queue). When there is no execution item in the stack, the content in the queue is pulled to the stack for execution.

JavaScript can be used on the front end as well as on the back end. When used on the front end, async functions are a good fit. Because waiting for a long time will cause the user to refresh the page directly. However, in the backend, there are not many scenarios where asynchronous functions are used, because it is very normal to collect a large amount of third-party data in the backend task, and then calculate the result based on the aggregated data.



------



### Event Loop

The lightness and efficiency of Node.js comes from the fact that it only uses a single thread and Event Loop (event loop). Any function that needs to wait for the result and requests external resources to proceed will be thrown into the Event Loop to wait.



An event polling Event Loop requires three components:

1. The event queue Event Queue belongs to the FIFO model. One end pushes event data, and the other end pulls event data. The two ends only communicate through this queue, which belongs to an asynchronous loose coupling.
2. The read polling thread of the queue, the consumer of the event, and the protagonist of the Event Loop.
3. A separate thread pool, Thread Pool, is specially used to perform long tasks, heavy tasks, and heavy physical work.

![event-loop](../pictures/event-loop.png)

In the event loop, there is an event queue, which is used to store various events, such as network requests, timers, file reading and writing, and so on. These events are asynchronous in that they typically take a certain amount of time to complete. Once an event completes, it is added to the event queue to be processed by the event loop.

The event loop will process the events in the event queue in a certain order. Specifically, it will continuously perform the following steps:

1. Check whether there are timer timeout events (timers) that need to be processed, and if so, execute the corresponding callback function.
2. Checks if there are pending I/O events (I/O), and if so, executes the corresponding callback function.
3. Check whether there is an event in the idle state (idle), and if so, execute the corresponding callback function.
4. Check whether there is an event that needs to be checked (check), and if so, execute the corresponding callback function.
5. Check whether there is an event (close) that needs to be closed, and if so, execute the corresponding callback function.
6. Check whether there are signals (signals) that need to be processed, and if so, execute the corresponding callback function.
7. If there are still events in the event queue, go back to step 1.

![event-loop-queue](../pictures/event-loop-queue.png)

The Event Loop in Node.js is divided into 6 phases:

1. timers: Used to execute timer callback functions.
2. I/O callbacks: Used to execute almost all callback functions, except for the callback function of the shutdown event.
3. idle, prepare: In general, these two phases are empty and will only be triggered when used internally.
4. poll: used to wait for new I/O events, execute I/O-related callback functions, and enter the next stage when appropriate.
5. check: Used to execute the setImmediate() callback function.
6. close callbacks: Callback functions used to execute events such as socket.on('close') .



In Node.js, because there is only one single thread that continuously checks whether there are events in the queue, for I/O operations such as database file system, including HTTP requests, etc., which are easy to block and wait, if it is also in this single thread Realization will definitely block and affect the execution of other work tasks. Javascript/Node.js will delegate to the underlying thread pool for execution, and will tell the thread pool a callback function, so that the single thread continues to execute other things. When these blocking operations are completed, The result is put into the queue together with the provided callback function. When the single thread continuously reads events from the queue and reads the results of these blocked operations, it will use these operation results as the input parameters of the callback function, and then activate the operation Callback.

Please note that the single thread of Node.js is not only responsible for reading queue events, but also executes the running callback function. This is a main feature different from the multi-threaded mode. In multi-threaded mode, the single thread is only responsible for reading the queue. Events will no longer do other things, and will entrust other threads to do other things, especially in the case of multi-core, one CPU core is responsible for reading queue events, and one CPU core is responsible for executing activated tasks. This method is most suitable for CPU-intensive calculations. task. Conversely, the execution activation task of Node..js, that is, the task in the callback function, is still executed in the single thread responsible for polling, which is doomed that it cannot perform CPU-heavy tasks, such as converting JSON to other data formats, etc. , these tasks affect the efficiency of event polling.

Regardless of whether it is written by the user or the built-in javascript code (nodejs API) of nodejs itself, all javascript codes run in **the same thread**. From the point of view of nodejs, all javascript code is either synchronous code or asynchronous code. Perhaps we can say that the execution of all synchronous codes is done by v8, and the execution of all asynchronous codes is done by the event loop function module provided by libuv



------



### libuv and v8

The core of Node.js is composed of two parts: v8 engine and libuv library.

1. v8: is a JavaScript engine developed by Google, which is used to interpret and execute JavaScript code.
2. libuv: is a cross-platform C library for implementing asynchronous I/O operations and event loops.



In Node.js, v8 is responsible for parsing and executing JavaScript code, while libuv is responsible for managing the event loop and I/O operations.



------



### Running the Code

引出来很多其他概念，比如event table和event queue，我们来看运行过程：

1. 首先判断JS是同步还是异步，同步就进入主线程运行，异步就进入event table。
2. 异步任务在event table中注册事件，当满足触发条件后（触发条件可能是延时也可能是ajax回调），被推入event queue。
3. 同步任务进入主线程后一直执行，直到主线程空闲时，才会去event queue中查看是否有可执行的异步任务，如果有就推入主线程中。



```javascript
setTimeout(() => {
  console.log('2 second')
}, 2000)
```



我们分析一下这段代码，setTimeout是异步操作。首先进入event table，注册的事件就是他的回调，触发条件就是2秒之后，当满足条件回调被推入event queue，当主线程空闲时会去event queue里查看是否有可执行的任务。



当这段代码在 Node.js 环境下执行时，事件循环（Event Loop）会按照以下步骤工作：

1. Node.js 解析该段代码，并将其加入到执行栈（Call Stack）中。
2. 遇到 `setTimeout` 函数时，Node.js 会将其推送到定时器（Timer）模块中，该模块将在 2 秒后将回调函数添加到事件队列（Event Queue）中。
3. 执行栈（Call Stack）中的代码已经全部执行完毕，此时 Node.js 会检查事件队列（Event Queue）中是否有任务。由于 `setTimeout` 中的回调函数还没有到达执行时间，因此事件队列（Event Queue）为空。
4. 在经过 2 秒后，Timer 模块会将回调函数添加到事件队列（Event Queue）中。
5. 此时，事件循环（Event Loop）会检查事件队列（Event Queue）中是否有任务。由于存在一个回调函数，因此事件循环（Event Loop）将该函数推送到执行栈（Call Stack）中执行。
6. 执行栈（Call Stack）中的代码输出 '2 second'，然后被移出栈（Call Stack）。
7. 事件循环（Event Loop）继续检查事件队列（Event Queue）中是否有任务。

因此，该段代码的输出结果是：'2 second'。在运行 `setTimeout` 函数时，回调函数并不是立即执行的，而是在 2 秒后才会被添加到事件队列（Event Queue）中。在事件循环（Event Loop）检测到事件队列（Event Queue）中存在回调函数后，才会将其推送到执行栈（Call Stack）中执行。



```
console.log(1) // 同步任务进入主线程
setTimeout(fun(),0)   // 异步任务，被放入event table， 0秒之后被推入event queue里
console.log(3) // 同步任务进入主线程
```



1、3是同步任务马上会被执行，执行完成之后主线程空闲去event queue(事件队列)里查看是否有任务在等待执行，这就是为什么setTimeout的延迟时间是0毫秒却在最后执行的原因



setTimeout有一点要注意延时的时间有时候并不是那么准确。



```
setTimeout(() => {
  console.log('2 second')
}, 2000)
sleep(9999999999)
```

分析运行过程：

1. console进入Event Table并注册，计时开始。
2. 执行sleep函数，sleep方法虽然是同步任务但sleep方法进行了大量的逻辑运算，耗时超过了2秒。
3. 2秒到了，计时事件timeout完成，console进入Event Queue，但是sleep还没执行完，主线程还被占用，只能等着。
4. sleep终于执行完了，console终于从Event Queue进入了主线程执行，这个时候已经远远超过了2秒。

其实延迟2秒只是表示2秒后，setTimeout里的函数被会推入event queue，而event queue(事件队列)里的任务，只有在主线程空闲时才会执行。上述的流程走完，我们知道setTimeout这个函数，是经过指定时间后，把要执行的任务(本例中为console)加入到Event Queue中，又因为是单线程任务要一个一个执行，如果前面的任务需要的时间太久，那么只能等着，导致真正的延迟时间远远大于2秒。 



我们还经常遇到setTimeout(fn，0)这样的代码，它的含义是，指定某个任务在主线程最早的空闲时间执行，意思就是不用再等多少秒了，只要主线程执行栈内的同步任务全部执行完成，栈为空就马上执行。



以setInterval(fn，ms)为例，setInterval是循环执行的，setInterval会每隔指定的时间将注册的函数置入Event Queue，不是每过ms秒会执行一次fn，而是每过ms秒，会有fn进入Event Queue。需要注意的一点是，一旦setInterval的回调函数fn执行时间超过了延迟时间ms，那么就完全看不出来有时间间隔了。





对任务有更精细的定义：

宏任务(macro-task)：包括整体代码script、setTimeout、setInterval、MessageChannel、postMessage、setImmediate。
 微任务(micro-task)：Promise、process.nextTick、MutationObsever。



在划分宏任务、微任务的时候并没有提到async/await因为async/await的本质就是Promise。

事件循环机制到底是怎么样的？ *不同类型的任务会进入对应的Event Queue，比如setTimeout和setInterval会进入相同(宏任务)的Event Queue。而Promise和process.nextTick会进入相同(微任务)的Event Queue。*

1. 「宏任务」、「微任务」都是队列，一段代码执行时，会先执行宏任务中的同步代码。
2. 进行第一轮事件循环的时候会把全部的js脚本当成一个宏任务来运行。
3. 如果执行中遇到setTimeout之类宏任务，那么就把这个setTimeout内部的函数推入「宏任务的队列」中，下一轮宏任务执行时调用。
4. 如果执行中遇到 promise.then() 之类的微任务，就会推入到「当前宏任务的微任务队列」中，在本轮宏任务的同步代码都执行完成后，依次执行所有的微任务。
5. 第一轮事件循环中当执行完全部的同步脚本以及微任务队列中的事件，这一轮事件循环就结束了，开始第二轮事件循环。
6. 第二轮事件循环同理先执行同步脚本，遇到其他宏任务代码块继续追加到「宏任务的队列」中，遇到微任务，就会推入到「当前宏任务的微任务队列」中，在本轮宏任务的同步代码执行都完成后，依次执行当前所有的微任务。
7. 开始第三轮，循环往复...





再看这段代码。

```javascript
setTimeout(function() {
    console.log('4')
})

new Promise(function(resolve) {
    console.log('1') // 同步任务
    resolve()
}).Promise(function() {
    console.log('3')
})
console.log('2')
```



首先，这个script作为一个macroTask被执行。找出其中的同步代码, 先被执行。

```javascript
new Promise(function(resolve) {
    console.log('1') // 同步任务
    resolve()
})
console.log('2')
```



然后setTimeout 和 Promise.then会被置入任务队列，尽管 setTimeout 先被定义，但是setTimeout是macroTask, Promise.then 是microTask。microTask会被优先执行。



Try a hard one.



```javascript
console.log('1')
setTimeout(function() {
    console.log('2')
    process.nextTick(function() {
        console.log('3')
    })
    new Promise(function(resolve) {
        console.log('4')
        resolve()
    }).then(function() {
        console.log('5')
    })
})

process.nextTick(function() {
    console.log('6')
})

new Promise(function(resolve) {
    console.log('7')
    resolve()
}).then(function() {
    console.log('8')
})

setTimeout(function() {
    console.log('9')
    process.nextTick(function() {
        console.log('10')
    })
    new Promise(function(resolve) {
        console.log('11')
        resolve()
    }).then(function() {
        console.log('12')
    })
})
```



整体script作为第一个宏任务进入主线程，遇到console.log(1)输出1。

遇到setTimeout，其回调函数被分发到 macro Task 任务 Event Queue中。我们暂且记为setTimeout1。

遇到process.nextTick()，其回调函数被分发到micro Task 任务Event Queue中。我们记为process1。

遇到Promise，new Promise直接执行，输出7。

then被分发到 micro TaskEvent Queue中。我们记为then1。

又遇到了setTimeout，其回调函数被分发到宏任务Event Queue中，我们记为setTimeout2。



这个时候同步代码已经执行完毕了，marco Task List: setTimeout1, setTimeout2

micro Task List: process1, Promise.then

这一轮的结果是输出1，7。



现在开始执行micro Task, 因为在marco Task 和micro Task 列表同时存在时，优先执行micro Task。

我们发现了process1和then1两个微任务，执行process1,输出6。执行then1，输出8。 第一轮事件循

环正式结束，这一轮的结果是输出1，7，6，8。

marco Task List: setTimeout1, setTimeout2

micro Task List: 



那么第二轮事件循环从setTimeout1宏任务开始：

首先输出2。接下来遇到了又一个process.nextTick()，将其分发到微任务Event Queue中，记为process2。

new Promise立即执行输出4，then也分发到微任务Event Queue中，记为then2。

marco Task List: setTimeout2

micro Task List:  process2, then2



这一轮的结果是输出1，7，6，8,  2 , 4



现在开始执行微任务，我们发现有process2和then2两个微任务可以执行输出3，5。 



这一轮的结果是输出1，7，6，8,  2 , 4，3，5。





第三轮事件循环从setTimeout2宏任务开始：

这是类似的，会打印 9，11，10，12。



完整的输出为1，7，6，8，2，4，3，5，9，11，10，12。 (请注意，node环境下的事件监听依赖libuv与前端环境不完全相同，输出顺序可能会有误差)





Try this:

```javascript
new Promise(function (resolve) { 
    console.log('1')// Promise1
    resolve()
}).then(function () {
    console.log('3') // Promise1.then
})
setTimeout(function () { // setTimeout1
    console.log('4')
    setTimeout(function () { // setTimeout2
        console.log('7')
        new Promise(function (resolve) { //Promise2
            console.log('8')
            resolve()
        }).then(function () { //Promise2.then
            console.log('10')
            setTimeout(function () {  // setTimeout3
                console.log('12')
            })
        })
        console.log('9')
    })
})
setTimeout(function () { // setTimeout4
    console.log('5')
})
setTimeout(function () {  // setTimeout5
    console.log('6')
    setTimeout(function () { // setTimeout6
        console.log('11')
    })
})
console.log('2') 
```



1.  将完整代码作为一个macon任务去执行，分发 macro Task,  分发 micro Task,  

   1. 打印1，2
   2. macro : setTimeout1, setTimeout4, setTimeout5
   3. micro : Promise1.then

2. 选择下一个任务去执行。micro 的 Promise1.then

   1. 打印3
   2. macro : setTimeout1, setTimeout4, setTimeout5
   3. micro : 

3. 选择下一个任务去执行。macro 的 setTimeout1

   1. 打印4
   2. macro :  setTimeout4, setTimeout5, setTimeout2
   3. micro : 

4. 选择下一个任务去执行。macro 的 setTimeout4

   1. 打印5
   2. macro :   setTimeout5, setTimeout2
   3. micro : 

5. 选择下一个任务去执行。macro 的 setTimeout5

   1. 打印6
   2. macro :   setTimeout2, setTimeout6
   3. micro : 

6. 选择下一个任务去执行。macro 的 setTimeout2

   1. 打印7, 8, 9
   2. macro :   setTimeout6
   3. micro : Promise2.then

7. 选择下一个任务去执行。micro 的Promise2.then

   1. 打印10
   2. macro :   setTimeout6，  setTimeout3
   3. micro : 

8. 选择下一个任务去执行。macro 的setTimeout6

   1. 打印11
   2. macro :   setTimeout3
   3. micro : 

9. 选择下一个任务去执行。macro 的setTimeout3

   1. 打印12
   2. macro :   
   3. micro : 

   

   

   在 Promise 构造函数中，如果执行器函数（executor function）立即调用了 `resolve()` 方法解决 Promise 对象，那么 `then()` 方法中传入的回调函数将会被执行，并且在 Promise 对象的状态变为已解决（resolved）后立即执行。如果没有执行 `resolve()` 方法，`then()` 方法中的回调函数就不会被执行。

   下面是一个简单的例子：

   ```
   javascriptCopy codeconst promise = new Promise((resolve, reject) => {
     console.log('Promise constructor');
     resolve('resolved value');
   });
   
   promise.then((value) => {
     console.log(`Resolved with value: ${value}`);
   });
   
   console.log('End of script');
   ```
   
   在上面的代码中，当 Promise 构造函数被调用时，立即执行了 `resolve()` 方法，因此 `then()` 方法中的回调函数将会被执行，并且输出结果为：
   
   ```
   javascriptCopy codePromise constructor
   Resolved with value: resolved value
   End of script
   ```
   
   如果在 Promise 构造函数中不调用 `resolve()` 方法，则 `then()` 方法中的回调函数将不会被执行，输出结果为：
   
   ```
   javascriptCopy codePromise constructor
   End of script
   ```
   
   总之，当 Promise 对象的状态变为已解决（resolved）时，与其相关联的 `then()` 方法中的回调函数将会被执行。如果 Promise 构造函数中没有调用 `resolve()` 方法，那么 `then()` 方法中的回调函数将不会被执行。
   
   
   
   
   
   ### 
   





```c++
Environment* CreateEnvironment(Isolate* isolate, uv_loop_t* loop, Handle<Context> context, int argc, const char* const* argv, int exec_argc, const char* const* exec_argv) {
  HandleScope handle_scope(isolate);

  Context::Scope context_scope(context);
  Environment* env = Environment::New(context, loop);

  isolate->SetAutorunMicrotasks(false);

  uv_check_init(env->event_loop(), env->immediate_check_handle());
  uv_unref(reinterpret_cast<uv_handle_t*>(env->immediate_check_handle()));
  uv_idle_init(env->event_loop(), env->immediate_idle_handle());
  uv_prepare_init(env->event_loop(), env->idle_prepare_handle());
  uv_check_init(env->event_loop(), env->idle_check_handle());
  uv_unref(reinterpret_cast<uv_handle_t*>(env->idle_prepare_handle()));
  uv_unref(reinterpret_cast<uv_handle_t*>(env->idle_check_handle()));

  // Register handle cleanups
  env->RegisterHandleCleanup(reinterpret_cast<uv_handle_t*>(env->immediate_check_handle()), HandleCleanup, nullptr);
  env->RegisterHandleCleanup(reinterpret_cast<uv_handle_t*>(env->immediate_idle_handle()), HandleCleanup, nullptr);
  env->RegisterHandleCleanup(reinterpret_cast<uv_handle_t*>(env->idle_prepare_handle()), HandleCleanup, nullptr);
  env->RegisterHandleCleanup(reinterpret_cast<uv_handle_t*>(env->idle_check_handle()), HandleCleanup, nullptr);

  if (v8_is_profiling) {
    StartProfilerIdleNotifier(env);
  }

  Local<FunctionTemplate> process_template = FunctionTemplate::New(isolate);
  process_template->SetClassName(FIXED_ONE_BYTE_STRING(isolate, "process"));

  Local<Object> process_object = process_template->GetFunction()->NewInstance();
  env->set_process_object(process_object);

  SetupProcessObject(env, argc, argv, exec_argc, exec_argv);
  LoadAsyncWrapperInfo(env);

  return env;
}

```

可以看到，nodejs在创建v8环境的时候，会把libuv默认的event loop作为参数传递进去的。event loop是被v8所使用一个功能模块。**因此，我们可以说，v8包含了event loop**。

对于这个单一的线程，有些人称之为v8线程，有些人称之为event loop线程，还有些人称之为node线程。鉴于nodejs大多时候都被称为javascript的运行时，所以，我更倾向于称之为“node线程”。**不过，需要重申一次的是：“无论它叫什么，本质都是一样的。那就是它们都是指所有javascript运行所在的那一个线程。”**





### EventEmitter

EventEmitter is a module for implementing event handling in Node.js. It is a module based on the Observer Pattern, which is used to establish a one-to-many dependency relationship between objects to trigger and process events.

In Node.js, all objects capable of emitting events are instances of EventEmitter. It provides on, emit, once and other methods for adding listeners and triggering events.



```javascript
const EventEmitter = require('events');
const myEmitter = new EventEmitter();

// add a listener
myEmitter.on('event', () => {
   console.log('an event was triggered');
});
// trigger event
myEmitter. emit('event');
```


In the above code, an EventEmitter instance is created and an event event listener is added. When the event event is triggered using the emit method, the corresponding callback function will be executed and a message will be output to the console.



### Control Flow

- Control Flow


  -  What is the control flow function?
  -  How does control flow manage the function calls?
  -  List down the steps using which “Control Flow” controls the function calls in Node.js?
  -  What is the order of execution in control flow statements?

### Questions



10. What is a reactor pattern in Node.js?

11. 什么是事件循环 (Event Loop)？它是如何工作的？

12. What does event-driven programming mean?

    

13. Differentiate between process.nextTick() and setImmediate()?

14. 

15. What do you understand by Event-driven programming?

16. 

17. List down the tasks which should be done asynchronously using the event loop?

18. What is the difference between setImmediate() and setTimeout()?

19. 

20. 

21. 

22. 

23. What is the primary reason to use the event-based model in Node.js?

24. 

25. List down the various timing features of Node.js.

- 
