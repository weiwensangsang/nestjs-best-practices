# Async/Await



### async/await用来干什么？



A single Promise chain cannot find the advantages of async/await, but if you need to deal with then chains composed of multiple Promises, the advantages can be reflected (Promise solves the problem of multi-layer callbacks through then chains, and now uses async/await to further optimize it).

1. async defines a Promise function that will not enter the event queue as long as it is not called like a normal function.
2. If there is no active return Promise inside async, then async will wrap the return value of the function with Promise.
3. The await keyword must appear in the async function. The await is not necessarily followed by an asynchronous operation, but can also be an ordinary expression.
4. When the await keyword is encountered, the statement on the right of await will be executed immediately, and the code below await will enter the waiting state, waiting for await to get the result.
    1. If there is no promise object after await, await will block the following code, first execute the synchronous code outside async, after the synchronous code is executed, return to the interior of async, and use this non-promise thing as the result of the await expression.
    2. If there is a promise object after await, await will also suspend the code behind async, first execute the synchronization code outside of async, wait for the promise object to be fulfilled, and then use the resolve parameter as the operation result of the await expression.



check this code with await.

```javascript
async function async1() {
  console.log('1')
  await new Promise((resolve) => {
    console.log('2')
    resolve()
  }).then(() => {
    console.log('3')
  })
  console.log('4')
}
async1()
```



1. Execute the complete code as a macon task,
   1. print 1, 2
   2. macro:
   3. micro: Promise1. then
   4. Call Stack: function context and await Automatically generated empty Promise (Wait for Promise resolve)
2. Promise completes resolve, Call Stack is pushed into micro Task,
    1. print
    2. macro:
    3. micro: Promise1. then, await context Promise
    4. Call Stack: ,
3. finish
    1. print 3, 4
    2. macro:
    3. micro:
    4. Call Stack: ,



```javascript
async function async1() {
  console.log('2')
  const data = await async2() // await1
  console.log(data)
  console.log('8')
}

async function async2() {
  return new Promise(function (resolve) {
    console.log('3')
    resolve('await result')
  }).then(function (data) { // Promise1
    console.log('6')
    return data
  })
}
console.log('1')

setTimeout(function () {
  console.log('9')
}, 0)

async1()

new Promise(function (resolve) { // Promise2
  console.log('4')
  resolve()
}).then(function () {
  console.log('7')
})
console.log('5')
```



When the `async1()` function is executed and the `await async2()` statement is encountered, the JavaScript engine will push the execution context of the `async1()` function into the call stack (Call Stack) and create an empty Promise object . It then suspends the execution of the `async1()` function and returns that Promise object as the value of `await async2()`.

When the Promise object returned by the `async2()` function resolves, the JavaScript engine will push the then callback function of the Promise object into the microtask queue for execution.



1. Execute the complete code as a macon task, distribute macro Task, distribute micro Task,

    1. Print 1, 2, 3, 4, 5
    2. macro: setTimeout
    3. micro: Promise1.then, Promise2.then
    4. Call Stack: ,await1 context Promise

2. Promise completes resolve, Call Stack is pushed into micro Task,

    1. print
    2. macro: setTimeout
    3. micro: Promise1.then, Promise2.then，await1 context Promise
    4. Call Stack:

3. Finish all Micro Tasks



### Questions


  - What is the difference between Asynchronous and Non-blocking?


    - Asynchronous: In Node.js, an asynchronous operation means that the program can continue executing while waiting for a time-consuming operation to complete. When an asynchronous operation is initiated, the program moves on to the next line of code without waiting for the operation to finish. Once the operation is complete, a callback function is called to handle the result. Asynchronous operations are usually implemented using callback functions, promises, or async/await syntax.
    - Non-blocking: In Node.js, non-blocking I/O means that the program can continue to process other requests while waiting for I/O operations to complete. This is achieved through the use of an event loop that listens for I/O events and delegates the handling of these events to separate threads. When an I/O operation is initiated, the event loop moves on to the next request without blocking the current thread. Once the I/O operation is complete, the event loop signals the thread to resume processing the result.

  - How to measure the duration and performance of asynchronous operations?


    - console.time()
    - `performance` module 

  - What are the input arguments for an asynchronous queue?


    - An asynchronous queue is a data structure that allows you to execute asynchronous tasks in a sequential order. In Node.js, there are several implementations of asynchronous queues, each with its own set of input arguments. Here are some common input arguments for an asynchronous queue in Node.js:

      1. `concurrency`: This argument specifies the maximum number of tasks that can be executed concurrently in the queue. If not specified, the default value is usually set to 1.
      2. `task`: This argument represents the function to be executed in the queue. The function can be an asynchronous function that returns a Promise or a callback function.
      3. `tasks`: This argument is an array of functions to be executed in the queue. Each function can be an asynchronous function that returns a Promise or a callback function.
      4. `callback`: This argument is a function that is called after all tasks in the queue have been executed. The callback function typically receives an error object (if there is an error) and the results of all tasks in the queue.
      5. `autostart`: This argument specifies whether the queue should start executing tasks immediately after they are added to the queue. If set to `false`, you need to manually start the queue using a `start()` method.
      6. `priority`: This argument allows you to prioritize tasks in the queue. Tasks with a higher priority are executed before tasks with a lower priority.

      Here's an example of how to use the `async` package to create an asynchronous queue with some of these input arguments:

      ```javascript
      const async = require('async');
      
      const queue = async.queue(async (task, done) => {
        // Execute an asynchronous task
        const result = await doAsyncTask(task);
        done(null, result);
      }, 2);
      
      queue.push([{param1: 'value1'}, {param2: 'value2'}]);
      
      queue.drain(() => {
        console.log('All tasks have been completed');
      });
      ```

      In this example, the `concurrency` argument is set to `2`, which means that a maximum of two tasks can be executed concurrently in the queue. The `task` argument is an asynchronous function that executes an asynchronous task, and the `callback` argument is a function that is called after all tasks in the queue have been executed. Finally, the `push` method is used to add an array of tasks to the queue.

  - How do we implement async in Node.js?


    - Callbacks
    - Promises
    - Async/await