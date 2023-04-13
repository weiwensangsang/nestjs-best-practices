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



It can be seen that when nodejs creates a v8 environment, it will pass libuv's default event loop as a parameter. The event loop is a functional module used by v8. **Therefore, we can say that v8 contains the event loop**.

For this single thread, some people call it a v8 thread, some call it an event loop thread, and some call it a node thread. Since nodejs is mostly called the runtime of javascript, I prefer to call it "node thread". **However, it needs to be reiterated: "No matter what it is called, the essence is the same. That is, they all refer to the thread where all javascript runs."**

------



### Running the Code

Let's see the running process:

1. First determine whether JavaScript is synchronous or asynchronous. Synchronously enters the main thread to run, and asynchronously enters the event table.
2. The asynchronous task registers an event in the event table, and when the trigger condition is met (the trigger condition may be a delay or an ajax callback), it is pushed into the event queue.
3. After the synchronous task enters the main thread, it will be executed until the main thread is idle, and then it will go to the event queue to check whether there is an executable asynchronous task, and if there is, it will be pushed into the main thread.
4. If await/async is encountered, then the latter part of the current function needs to be pushed into the Call Stack, waiting for execution.



```javascript
setTimeout(() => {
  console.log('2 second')
}, 2000)
```



Let's analyze this code, setTimeout is an asynchronous operation. First enter the event table, the registered event is its callback, and the trigger condition is 2 seconds later. When the condition is met, the callback is pushed into the event queue. When the main thread is idle, it will go to the event queue to check whether there are executable tasks.



When this code is executed in the Node.js environment, the event loop (Event Loop) will work in the following steps:

1. Node.js parses the code and adds it to the execution stack (Call Stack).
2. When a `setTimeout` function is encountered, Node.js will push it to the Timer module, which will add the callback function to the Event Queue after 2 seconds.
3. The code in the execution stack (Call Stack) has all been executed, and Node.js will check whether there are tasks in the Event Queue (Event Queue). Since the callback function in `setTimeout` has not yet reached the execution time, the Event Queue (Event Queue) is empty.
4. After 2 seconds, the Timer module will add the callback function to the event queue (Event Queue).
5. At this point, the event loop (Event Loop) will check whether there are tasks in the event queue (Event Queue). Since there is a callback function, the event loop (Event Loop) pushes the function to the execution stack (Call Stack) for execution.
6. The code in the execution stack (Call Stack) outputs '2 second', and then is removed from the stack (Call Stack).
7. The event loop (Event Loop) continues to check whether there are tasks in the event queue (Event Queue).

Therefore, the output of this code is: '2 second'. When the `setTimeout` function is run, the callback function is not executed immediately, but will be added to the event queue (Event Queue) after 2 seconds. After the event loop (Event Loop) detects that there is a callback function in the event queue (Event Queue), it will be pushed to the execution stack (Call Stack) for execution.





We can make a finer distinction between tasks:

- Macro task (macro-task): including the overall code script, setTimeout, setInterval, MessageChannel, postMessage, setImmediate.
- Micro-tasks: Promise, process.nextTick, MutationObsever.



When dividing macro tasks and micro tasks, async/await is not mentioned, because the essence of async/await is Promise.

What exactly is the event loop mechanism? * Different types of tasks will enter the corresponding Event Queue, such as setTimeout and setInterval will enter the same (macro task) Event Queue. And Promise and process.nextTick will enter the same (microtask) Event Queue. *

1. "Macrotask" and "microtask" are both queues. When a piece of code is executed, the synchronous code in the macrotask will be executed first.
2. During the first round of event loop, all js scripts will be run as a macro task.
3. If a macro task such as setTimeout is encountered during execution, then push the function inside the setTimeout into the "macro task queue" and call it when the next round of macro task is executed.
4. If a microtask such as promise.then() is encountered during execution, it will be pushed into the "microtask queue of the current macrotask". microtasks.
5. When all the synchronization scripts and events in the microtask queue are executed in the first round of event loop, this round of event loop ends and the second round of event loop starts.
6. In the second round of the event loop, the synchronization script is executed first. When encountering other macro task code blocks, it continues to be appended to the "macro task queue". When encountering a micro task, it will be pushed into the "micro task queue of the current macro task." In ", after the synchronous code execution of the current round of macro tasks is completed, all current micro tasks are executed in sequence.
7. Start the third round, and repeat



Let us try this code.

```javascript
setTimeout(function() {
    console.log('4')
})

new Promise(function(resolve) {
    console.log('1') // Sync code
    resolve()
}).Promise(function() {
    console.log('3')
})
console.log('2')
```



First, the script is executed as a macroTask. Find the synchronous code in it, which is executed first.

```javascript
new Promise(function(resolve) {
     console. log('1')
     resolve()
})
console. log('2')
```

Then setTimeout and Promise.then will be put into the task queue. Although setTimeout is defined first, setTimeout is a macroTask, and Promise.then is a microTask. microTask will be executed first.





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



The overall script enters the main thread as the first macro task, and outputs 1 when encountering console.log(1).

When setTimeout is encountered, its callback function is distributed to the macro Task task Event Queue. Let's record it as setTimeout1 for the time being.

When process.nextTick() is encountered, its callback function is distributed to the micro Task task Event Queue. We denote it as process1.

When Promise is encountered, new Promise is executed directly, and 7 is output.

then is dispatched to the micro TaskEvent Queue. We denote it as then1.

I encountered setTimeout again, and its callback function was distributed to the macro task Event Queue, which we recorded as setTimeout2.



At this time, the synchronization code has been executed, marco Task List: setTimeout1, setTimeout2

micro Task List: process1, Promise. then

The result of this round is to output 1,7.



Now start to execute micro Task, because when marco Task and micro Task list exist at the same time, micro Task will be executed first.

We found two microtasks, process1 and then1, execute process1, and output 6. Execute then1 and output 8. The first round of events

The ring is officially over, and the result of this round is to output 1, 7, 6, 8.

marco Task List: setTimeout1, setTimeout2

micro Task List:



Then the second round of event loop starts from the setTimeout1 macro task:

First output 2. Next, another process.nextTick() is encountered, and it is distributed to the microtask Event Queue, which is recorded as process2.

The new Promise immediately executes the output 4, and then is also distributed to the microtask Event Queue, which is recorded as then2.

marco Task List: setTimeout2

micro Task List: process2, then2



The result of this round is to output 1, 7, 6, 8, 2 , 4

Now start to execute microtasks, we found that there are two microtasks of process2 and then2 that can execute output 3, 5.

The result of this round is to output 1, 7, 6, 8, 2, 4, 3, 5.





The third round of event loop starts from the setTimeout2 macro task:

This is similar and will print 9, 11, 10, 12.



The complete output is 1, 7, 6, 8, 2, 4, 3, 5, 9, 11, 10, 12. (Please note that the event monitoring in the node environment depends on libuv and the front-end environment are not exactly the same, and the output order may be wrong)





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



1.  Execute the complete code as a macon task, distribute macro Task, distribute micro Task,

   1. print 1, 2
   2. macro: setTimeout1, setTimeout4, setTimeout5
   3. micro: Promise1. then

2. Select the next task to execute. micro's Promise1.then

     1. print 3
     2. macro: setTimeout1, setTimeout4, setTimeout5
     3. micro:

3. Select the next task to execute. macro's setTimeout1

     1. print 4
     2. macro: setTimeout4, setTimeout5, setTimeout2
     3. micro:

4. Select the next task to execute. macro's setTimeout4

     1. Print 5
     2. macro: setTimeout5, setTimeout2
     3. micro:

5. Select the next task to execute. macro's setTimeout5

     1. Print 6
     2. macro: setTimeout2, setTimeout6
     3. micro:

6. Select the next task to execute. macro's setTimeout2

     1. Print 7, 8, 9
     2. macro: setTimeout6
     3. micro: Promise2.then

7. Select the next task to execute. micro's Promise2.then

     1. print 10
     2. macro: setTimeout6, setTimeout3
     3. micro:

8. Select the next task to execute. macro's setTimeout6

     1. print 11
     2. macro: setTimeout3
     3. micro:

9. Select the next task to execute. macro's setTimeout3

     1. print 12
     2. macro:
     3. micro:
     
     
     
     

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


  -  What is the order of execution in control flow statements?

     -  The order of execution in control flow statements is determined by the sequence of statements in the program and the conditions that are evaluated to determine which statements to execute. The order of execution is not necessarily sequential and can be influenced by loops, conditions, and function calls. In general, the program executes statements in the order they appear in the code, but control flow statements can change this order.




### Questions



1. What is a reactor pattern in Node.js?
   1. The reactor pattern is a design pattern used in Node.js to handle I/O operations efficiently. It uses an event loop to monitor multiple input/output sources and responds to them in a non-blocking way. This pattern allows Node.js to handle a large number of simultaneous connections efficiently.
2. Differentiate between process.nextTick() and setImmediate()?
   1. `process.nextTick()` and `setImmediate()` are both used to schedule callbacks to be executed in the next iteration of the event loop, but they have different priorities. `process.nextTick()` callbacks are executed before I/O callbacks and timers, whereas `setImmediate()` callbacks are executed after I/O callbacks.
3. What do you understand by Event-driven programming?
   1. Event-driven programming is a programming paradigm that involves designing applications around the occurrence of events. In this approach, the application waits for events to occur and responds to them by triggering pre-defined actions. This paradigm is widely used in Node.js to build highly scalable and efficient applications.
4. List down the tasks which should be done asynchronously using the event loop?
   1. File I/O operations
   2. Network I/O operations
   3. Database operations
   4. Heavy computations
5. What is the primary reason to use the event-based model in Node.js?
   1. The primary reason to use the event-based model in Node.js is to build highly scalable and efficient applications. By using an event-driven approach, Node.js can handle a large number of simultaneous connections without blocking the event loop, which allows for high throughput and low latency.
6. List down the various timing features of Node.js.
   1. `setTimeout()` and `setInterval()` for executing code after a specified delay or at regular intervals.
   2. `process.nextTick()` and `setImmediate()` for scheduling callbacks in the next iteration of the event loop.
   3. `Date.now()` for getting the current timestamp.
   4. `process.hrtime()` for measuring the execution time of code.

