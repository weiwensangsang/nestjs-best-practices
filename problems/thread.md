# Thread



### Worker Thread



 JavaScript 起初是用於使用者端的互動(比如 web 頁面的互動或表單的驗證)，這些邏輯並不需要多執行緒這樣的機制來處理。

所以這也帶來了另一個缺點：如果你需要使用 CPU 密集型的任務，比如在記憶體中使用一個大的資料集進行復雜計算，它會阻塞掉其他程序的任務。同樣的，當你在發起一個有 CPU 密集型任務的遠端介面請求時，也同樣會阻塞掉其他需要被執行的請求。

區分開 CPU 密集型操作與 I/O(input/output) 密集型操作是很重要的。像前面所說的，Node.js 並不會同時執行多段程式碼，只有 I/O 操作才會同時去執行，因為它們是非同步的。

所以 Worker Threads 對於 I/O 密集型操作是沒有太大的幫助的，因為非同步的 I/O 操作比 worker 更有效率，Wokers 的主要作用是用於提升對於 CPU 密集型操作的效能。



外，目前已經存在很多對於 CPU 密集型操作的解決方案，比如多程序(cluster API)方案，保證了充分利用多核 CPU。



worker 工作线程与集群有何不同 ？ 

  -  How does Node.js handle the child threads?
  -  If Node.js is single-threaded, then how does it handle concurrency?
  -  如何在 Node.js 中实现多进程模型？有哪些模块可以使用？
  -  
  -  What is the difference between fork() and spawn() methods in Node.js?
  -  什么是线程池，Node.js 中哪个库处理它 ? 
  -  如何通过集群提高 Node.js 的性能 ？ 
  -  Describe Node.js exit codes.
  -  WASI What is WASI, and why is it being introduced?