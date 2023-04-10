# Promise And Callback





ES6提供Promise构造函数，我们创造一个Promise实例，Promise构造函数接收一个函数作为参数，这个传入的函数有两个参数，分别是两个函数 `resolve`和`reject`作用是，`resolve`将Promise的状态由未成功变为成功，将异步操作的结果作为参数传递过去；相似的是`reject`则将状态由未失败转变为失败，在异步操作失败时调用，将异步操作报出的错误作为参数传递过去。 实例创建完成后，可以使用`then`方法分别指定成功或失败的回调函数

这种调用方法实时上保证了函数执行的有序性。比如then方法会在构造函数执行完毕之后再执行。





```javascript
module.exports = Promise;
function Promise(fn) {
  if (typeof this !== 'object') {
    throw new TypeError('Promises must be constructed via new');
  }
  if (typeof fn !== 'function') {
    throw new TypeError('Promise constructor\'s argument is not a function');
  }
  this._deferredState = 0;//这个状态用于表明未来的状态会是怎么样的，只有当前的Promise状态依赖另一个Promise时才有用，也就是resolve了一个Promise
  this._state = 0;//当前Promise的状态
  this._value = null;//当前Promise的resolve的值
  this._deferreds = null;//当_deferredState变为成功，也就是大于2时，执行的回调函数数组
  if (fn === noop) return;
  doResolve(fn, this);
}

function doResolve(fn, promise) {
  var done = false;
    //注意，fn就是我们new Promise时传入的（resolve， reject）=> { if success resolve else reject},tryCallTwo会将第二个参数传给resolve,将第三个参数传给reject，这样当我们在声明的Promise中调用resolve时实际上调用的时trayCallTwo的第二个参数。
  var res = tryCallTwo(fn, function (value) { // try catch
    if (done) return;// 防止运行两次
    done = true;
    resolve(promise, value);
  }, function (reason) {
    if (done) return;
    done = true;
    reject(promise, reason);
  });
  if (!done && res === IS_ERROR) {
    done = true;
    reject(promise, LAST_ERROR);
  }
}

function resolve(self, newValue) {
 // 一个Promise的解决结果不能是自己 （因为根据一开始我们提到的then的原则中，如果你返回了一个新的Promise，那么当前Promise的状态就会依赖于新的Promise，如果自己依赖自己，那么就会一直循环依赖并处于pending状态）
    
// 这里的newValue其实就是我们定义的Promise实例中resolve的参数，依照前面的使用方法，它可以是字符串，数组等，也可以是另一个一个Promise
  if (newValue === self) {
    return reject(
      self,
      new TypeError('A promise cannot be resolved with itself.')
    );
  }
  // 当新的值也就是resolve函数的参数存在并且类型是对象或者函数的时候
  // typeof Promised实例 === 'object'
  // 也就是说这个if成功的条件是resolve了一个对象，函数或者Promise实例
  if (
    newValue &&
    (typeof newValue === 'object' || typeof newValue === 'function')
  ) {
    var then = getThen(newValue);  // let then = newValue.then
    if (then === IS_ERROR) { //IS_ERROR就是上面所说的工具中声明的，就是一个空对象，只有当return newValue.then发生异常时才会为IS_ERROR
      return reject(self, LAST_ERROR);
    }
    if (
      then === self.then &&
      newValue instanceof Promise // 如果resvole的是一个Promise,并且这个Promise的then与当前Promise的then相同时（这个then一般是相同的，都是定义在Promise的原型上的），直接就用当前Promise的结果为最终结果。
    ) {
      self._state = 3; //状态3说明采用另一个Promise作为结果
      self._value = newValue;
      finale(self); // 那么采用这个Promise的结果
      return;
    } else if (typeof then === 'function') {//走到这里就说明，resolve了一个有then方法的对象（或者一个Promise，并且和当前Promise的then不同，这种情况比较少见）
      doResolve(then.bind(newValue), self); // 递归调用doResolve，刚才我们也看了，doResolve的第一个参数是我们new Promise时传入的函数，第二个参数是当前的Promise实例的指针，也就是在递归中执行到这个then函数时，其内部的this指向的是这里的newValue，这里self没变
      return;
    }
  }
  //上面的if都没有return，才会走到这里，到了这里就说明，resolve了普通的值，比如数字，字符串，标记完成，进入结束流程
  self._state = 1; //状态1说明进入成功状态
  self._value = newValue;
  finale(self);
}

function reject(self, newValue) {
  //设置reject状态和理由
  self._state = 2;
  self._value = newValue;
  if (Promise._onReject) {
    Promise._onReject(self, newValue); //过程回调通知
  }
  finale(self); //结束 
}

作者：叶藏锋
链接：https://juejin.cn/post/6936886197817966606
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```



正常情况下，进入 doResolve 开始流程。

讲两个函数 (即外部编写的resolve和reject)作为参数传入 , 调用完成后检查下是否是没完成的情况下出错了，如果是直接reject. 

我们可以看出，实现原理是一个非常简洁的链式调用。







A "callback" is any function that is called by another function which takes the first function as a parameter. 



从根源上说，JS是一种异步执行语言，即使代码中注明了某些异步代码，这些异步代码也会被跳过，先去执行同步代码。



一般而言，函数的形参是指由外往内向函数体传递变量的入口，

但是，如果函数入口参数中添加了一个关键字callback，意思就是说此处不是一个普通的参数，而是一个函数名，
它是指函数体在完成某种使命后调用外部函数的出口！



### EventEmitter



`Node`采用了事件驱动机制，而`EventEmitter`就是`Node`实现事件驱动的基础

在`EventEmitter`的基础上，`Node`几乎所有的模块都继承了这个类，这些模块拥有了自己的事件，可以绑定／触发监听器，实现了异步操作

`Node.js` 里面的许多对象都会分发事件，比如 fs.readStream 对象会在文件被打开的时候触发一个事件



-  什么是回调函数 (Callback function)？
-  在 Node.js 中为什么会使用回调函数？
-  Explain callback in Node.js.
-  What are the advantages of using promises instead of callbacks?
-  What is callback hell?
-  What is an error-first callback in Node.js?