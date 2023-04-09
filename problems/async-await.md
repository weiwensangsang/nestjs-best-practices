- 
- asynchronous function


  - How many types of API functions are there in Node.js?

  - What is the difference between Asynchronous and Non-blocking?

  - 我们如何在node.js中使用async await ？

  - How does the DNS lookup function work in Node.js?

  - 如何测量异步操作的持续时间 ？

  - 如何衡量异步操作的性能 ？ 

  - What are the input arguments for an asynchronous queue?

  - 

  - What are the two types of API functions in Node.js?

  - Explain asynchronous and non-blocking APIs in Node.js.

  - How do we implement async in Node.js?



1. ### async/await用来干什么？

   

   

   每次我们使用 await, 解释器都创建一个 promise 对象，然后把剩下的 async 函数中的操作放到 then 回调函数中

   

   

   

   https://juejin.cn/post/6844903988584775693

上面的案例只是用setTimeout和Promise模拟了一些场景来帮助理解，并没有用到async/await下面我们从什么是async/await开始讲起。

我们创建了 promise 但不能同步等待它执行完成。我们只能通过 then 传一个回调函数这样很容易再次陷入 promise 的回调地狱。

实际上，async/await 在底层转换成了 promise 和 then 回调函数。也就是说，这是 promise 的语法糖。

每次我们使用 await, 解释器都创建一个 promise 对象，然后把剩下的 async 函数中的操作放到 then 回调函数中。async/await 的实现，离不开 Promise。

从字面意思来理解，async 是“异步”的简写，而 await 是 async wait 的简写可以认为是等待异步方法执行完成。

### 

用来优化 promise 的回调问题，被称作是异步的终极解决方案。



单一的 Promise 链并不能发现 async/await 的优势，但是如果需要处理由多个 Promise 组成的 then 链的时候，优势就能体现出来了（Promise 通过 then 链来解决多层回调的问题，现在又用 async/await 来进一步优化它）。

1. async定义的是一个Promise函数和普通函数一样只要不调用就不会进入事件队列。
2. async内部如果没有主动return Promise，那么async会把函数的返回值用Promise包装。
3. await关键字必须出现在async函数中，await后面不是必须要跟一个异步操作，也可以是一个普通表达式。
4. 遇到await关键字，await右边的语句会被立即执行然后await下面的代码进入等待状态，等待await得到结果。 await后面如果不是 promise 对象, await会阻塞后面的代码，先执行async外面的同步代码，同步代码执行完，再回到async内部，把这个非promise的东西，作为 await表达式的结果。 await后面如果是 promise 对象，await 也会暂停async后面的代码，先执行async外面的同步代码，等着 Promise 对象 fulfilled，然后把 resolve 的参数作为 await 表达式的运算结果。



在执行 `async1()` 函数时，遇到 `await async2()` 语句，JavaScript 引擎会将 `async1()` 函数的执行上下文推入调用栈（Call Stack）中，并创建一个空的 Promise 对象。然后，它会暂停 `async1()` 函数的执行，并返回该 Promise 对象，作为 `await async2()` 的值。

当 `async2()` 函数返回的 Promise 对象 resolve 时，JavaScript 引擎会将该 Promise 对象的 then 回调函数推入 microtask 队列中等待执行。

```javascript
const firstPromise = new Promise((resolve, reject) => {
  // 异步操作
  setTimeout(() => {
    console.log('第一个 Promise 执行完成');
    resolve('成功');
  }, 1000);
});

const secondPromise = new Promise((resolve, reject) => {
  // 异步操作
  setTimeout(() => {
    console.log('第二个 Promise 执行完成');
    reject('失败');
  }, 2000);
});

firstPromise.then((result) => {
  console.log(result);
  return secondPromise;
}).then((result) => {
  console.log(result);
  console.log('所有 Promise 都已经执行完成');
}).catch((error) => {
  console.error(error);
});
```



```
const firstPromise = () => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('第一个 Promise 执行完成');
      resolve('成功');
    }, 1000);
  });
};

const secondPromise = () => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      console.log('第二个 Promise 执行完成');
      reject('失败');
    }, 2000);
  });
};

const executePromises = async () => {
  try {
    const result1 = await firstPromise();
    console.log(result1);
    const result2 = await secondPromise();
    console.log(result2);
    console.log('所有 Promise 都已经执行完成');
  } catch (error) {
    console.error(error);
  }
};

executePromises();
```





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



1. 将完整代码作为一个macon任务去执行，
   1. 打印1, 2
   2. macro : 
   3. micro : Promise1.then
   4. Call Stack: function context and await 自动生成的 空Promise (Wait for Promise resolve)
2. Promise 完成resolve， Call Stack 推入 micro Task,  
   1. 打印
   2. macro : 
   3. micro : Promise1.then， await  context Promise 
   4. Call Stack: ,
3. 完成
   1. 打印3, 4
   2. macro : 
   3. micro : 
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
    resolve('await的结果')
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



1. 将完整代码作为一个macon任务去执行，分发 macro Task,  分发 micro Task,  

   1. 打印1, 2, 3, 4, 5
   2. macro :  setTimeout
   3. micro : Promise1.then ,  Promise2.then
   4. Call Stack: ,await1 context Promise

2. Promise 完成resolve， Call Stack 推入 micro Task,

   1. 打印
   2. macro :  setTimeout
   3. micro : Promise1.then ,  Promise2.then，await1 context Promise
   4. Call Stack: 

3. Finish all Micro Task,

    

   







```javascript
async function async1() {
  console.log('2')
  const data = await async2() // await1
  console.log(data)
}
async function async2() {
  return new Promise(function (resolve) {
    console.log('3')
    resolve('8')
  }).then(function (data) { // Promise1
    console.log('6')
    return data
  })
}
async1()
new Promise(function (resolve) { // Promise2
  console.log('4')
  resolve()
}).then(function () {
  console.log('7')
})
```

https://juejin.cn/post/6844903740667854861