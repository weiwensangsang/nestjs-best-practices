# Promise And Callback



ES6 provides a Promise constructor. We create a Promise instance. The Promise constructor receives a function as a parameter. The function passed in has two parameters, which are two functions `resolve` and `reject`. `resolve` will The status of Promise changes from unsuccessful to successful, and the result of the asynchronous operation is passed as a parameter; similarly, `reject` changes the status from unfailed to failed, called when the asynchronous operation fails, and the error reported by the asynchronous operation passed as a parameter. After the instance is created, you can use the `then` method to specify the success or failure callback function respectively

This calling method guarantees the order of function execution in real time. For example, the then method will be executed after the constructor is executed.



Here is the code about Promise

```javascript
module.exports = Promise;
function Promise(fn) {
  if (typeof this !== 'object') {
    throw new TypeError('Promises must be constructed via new');
  }
  if (typeof fn !== 'function') {
    throw new TypeError('Promise constructor\'s argument is not a function');
  }
  this._deferredState = 0;
  this._state = 0;//current Promise status
  this._value = null;//current Promise resolve value
  this._deferreds = null;//callback function list
  if (fn === noop) return;
  doResolve(fn, this);
}

function doResolve(fn, promise) {
  var done = false;
  var res = tryCallTwo(fn, function (value) { // try catch
    if (done) return;
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
  if (newValue === self) {
    return reject(
      self,
      new TypeError('A promise cannot be resolved with itself.')
    );
  }

  if (
    newValue &&
    (typeof newValue === 'object' || typeof newValue === 'function')
  ) {
    var then = getThen(newValue);  // let then = newValue.then
    if (then === IS_ERROR) { 
      return reject(self, LAST_ERROR);
    }
    if (
      then === self.then &&
      newValue instanceof Promise 
    ) {
      self._state = 3; 
      self._value = newValue;
      finale(self);
      return;
    } else if (typeof then === 'function') 
      doResolve(then.bind(newValue), self);
      return;
    }
  }
  self._state = 1; 
  self._value = newValue;
  finale(self);
}

function reject(self, newValue) {

  self._state = 2;
  self._value = newValue;
  if (Promise._onReject) {
    Promise._onReject(self, newValue); 
  }
  finale(self); 
}
```



Normally, entering doResolve starts the process.

Two functions (resolve and reject written externally) are passed in as parameters, and after the call is completed, check whether there is an error in the case of incompleteness, and if it is directly reject.

We can see that the implementation principle is a very concise chain call.





### Callback

A "callback" is any function that is called by another function which takes the first function as a parameter. 

Generally speaking, the formal parameter of a function refers to the entry of passing variables from the outside to the inside of the function body.

However, if a keyword callback is added to the function entry parameter, it means that this is not an ordinary parameter, but a function name,
It refers to the external function that the function body needs to call to complete the call

Fundamentally speaking, JS is an asynchronous execution language. Even if some asynchronous codes are marked in the code, these asynchronous codes will be skipped and the synchronous codes will be executed first.





### Questions

-  What are the advantages of using promises instead of callbacks?
   -  Promises provide a more structured way of handling asynchronous operations, making the code easier to read and maintain.
   -  Promises allow for chaining multiple asynchronous operations together, which can simplify complex code.
   -  Promises have built-in error handling, making it easier to catch and handle errors.
   -  Promises can also be used to handle parallel operations, improving performance.
-  What is callback hell?
   -  Callback hell refers to a situation in which code that relies heavily on asynchronous callbacks becomes difficult to read and maintain due to the nested nature of the callbacks. This can happen when multiple asynchronous operations need to be performed in sequence, leading to a series of nested callbacks that become increasingly difficult to follow and debug. This can make the code difficult to read and lead to bugs and errors.
-  What is an error-first callback in Node.js?
   -  An error-first callback is a convention used in Node.js to handle errors in asynchronous callbacks. The convention specifies that the first parameter passed to a callback function should be an error object. If there is no error, this parameter will be null or undefined, while if there is an error, it will be an instance of the Error class. This convention allows for consistent and reliable error handling in Node.js code, and many Node.js APIs use this convention for their callbacks.
