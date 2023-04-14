

# 44 very hard questions(fixme)

1. parseInt with map

  ```
   ["1", "2", "3"].map(parseInt)
  
  // A. ["1", "2", "3"]
  // B. [1, 2, 3]
  // C. [0, 1, 2]
  // D. other
  ```

  

答案是D。实际上返回的结果是 [1, NaN, NaN] ，因为 parseInt 函数只需要两个参数 parseInt(value, radix) ，而 map 的回调函数需要三个参数 callback(currentValue, index, array)。

MDN文档中指明 parseInt 第二个参数是一个2到36之间的整数值，用于指定转换中采用的基数。如果省略该参数或其值为0，则数字将以10为基础来解析。

如果该参数小于2或者大于36，则 parseInt 返回 NaN。此外，转换失败也会返回 NaN。

现在来分析问题。parseInt("1", 0) 的结果是当作十进制来解析，返回 1；parseInt("2", 1) 的第二个参数非法，返回 NaN；parseInt("3", 2) 在二进制中，"3" 是非法字符，转换失败，返回 NaN。

参考资料：

MDN: Array.prototype.map()
MDN: parseInt





2. 神奇的null
[typeof null, null instanceof Object]


// A. ["object", false]
// B. [null, false]
// C. ["object", true]
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是A。在MDN关于 null 的文档中也特别指出来了，typeof null 的结果是 "object"，它是ECMAScript的bug，其实应该是 "null"。但这个bug由来已久，在JavaScript中已经存在了将近二十年，也许永远不会修复，因为这牵扯到太多的Web系统，修复它会产生更多的bug，令许多系统无法正常工作。而 instanceof 运算符是用来测试一个对象在其原型链构造函数上是否具有 prototype 属性，null 值并不是以 Object 原型创建出来的，所以 null instanceof Object 返回 false。

参考资料：


MDN：null
MDN：instanceof
博客：null instanceof Object 求解？

3. 愤怒的reduce
[ [3,2,1].reduce(Math.pow), [].reduce(Math.pow) ]


// A. an error
// B. [9, 0]
// C. [9, NaN]
// D. [9, undefined]
1.
2.
3.
4.
5.
6.
7.
答案是A。MDN文档中关于 Array.prototype.reduce() 写得很清楚：

如果数组为空并且没有提供initialValue， 会抛出TypeError 。如果数组仅有一个元素（无论位置如何）并且没有提供initialValue， 或者有提供initialValue但是数组为空，那么此唯一值将被返回并且callback不会被执行。

参考资料：

MDN：Array.prototype.reduce()
4. 该死的优先级
var val = 'smtg';
console.log('Value is ' + (val === 'smtg') ? 'Something' : 'Nothing');


// A. Value is Something
// B. Value is Nothing
// C. NaN
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
答案是D。实际上输出 "Something"，因为 + 的优先级比条件运算符 condition ? val1 : val2 的优先级高。

参考资料：

MDN：运算符优先级
5. 神鬼莫测之变量提升
var name = 'World!';
(function () {
    if (typeof name === 'undefined') {
      var name = 'Jack';
      console.log('Goodbye ' + name);
    } else {
      console.log('Hello ' + name);
    }
})();


// A. Goodbye Jack
// B. Hello Jack
// C. Hello undefined
// D. Hello World
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
答案是A。看如下MDN官方文档的解释：

在 JavaScript中， functions 和 variables 会被提升。变量提升是JavaScript将声明移至作用域 scope (全局域或者当前函数作用域) 顶部的行为。

这意味着你可以在声明一个函数或变量之前引用它，或者可以说：一个变量或函数可以在它被引用之后声明。

所以，上面的代码与下面这段代码是等价的：

var name = 'World!';
(function () {
    var name;
    if (typeof name === 'undefined') {
      name = 'Jack';
      console.log('Goodbye ' + name);
    } else {
      console.log('Hello ' + name);
    }
})();
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
参考资料：


MDN：变量提升
这篇博客解释的比较详细：js变量提升

6. 死循环陷阱
var END = Math.pow(2, 53);
var START = END - 100;
var count = 0;
for (var i = START; i <= END; i++) { 
    count++;
}
console.log(count);


// A. 0
// B. 100
// C. 101
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
答案是D。在JavaScript中，2^53 是最大的值，没有比这更大的值了。所以 2^53 + 1 == 2^53，所以这个循环无法终止。

7. 过滤器魔法
var ary = [0,1,2];
ary[10] = 10;
ary.filter(function(x) {
    return x === undefined;
});


// A. [undefined x 7]
// B. [0, 1, 2, 10]
// C. []
// D. [undefined]
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是C。看MDN官方文档的描述：

filter 为数组中的每个元素调用一次 callback 函数，并利用所有使得 callback 返回 true 或 等价于 true 的值 的元素创建一个新数组。callback 只会在已经赋值的索引上被调用，对于那些已经被删除或者从未被赋值的索引不会被调用。那些没有通过 callback 测试的元素会被跳过，不会被包含在新数组中。

参考资料：

MDN：Array.prototype.filter()
8. 警惕IEEE 754标准
var two = 0.2;
var one = 0.1;
var eight = 0.8;
var six = 0.6;
[two - one == one, eight - six == two]


// A. [true, false]
// B. [false, false]
// C. [true, false]
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是C。JavaScript中采用双精度浮点数格式，即IEEE 754标准。在该格式下，有些数字无法表示出来，比如：0.1 + 0.2 = 0.30000000000000004 ，这不是JavaScript的锅，所有采用该标准的语言都有这个问题，比如：Java、Python等。

参考资料：

Wiki：Double-precision floating-point format
9. 字符串陷阱
function showCase(value) {
    switch(value) {
    case 'A':
      console.log('Case A');
      break;
    case 'B':
      console.log('Case B');
      break;
    case undefined:
      console.log('undefined');
      break;
    default:
      console.log('Do not know!');
    }
}
showCase(new String('A'));


// A. Case A
// B. Case B
// C. Do not know!
// D. undefined
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
20.
21.
22.
答案是C。在 switch 内部使用严格相等 === 进行判断，并且 new String("A") 返回的是一个对象，而 String("A") 则是直接返回字符串 "A"。你也可以参考MDN中对原始字符串和String对象的区分：

Note that JavaScript distinguishes between String objects and primitive string values. (The same is true of Boolean and Numbers.)

String literals (denoted by double or single quotes) and strings returned from String calls in a non-constructor context (i.e., without using the new keyword) are primitive strings. JavaScript automatically converts primitives to String objects, so that it's possible to use String object methods for primitive strings. In contexts where a method is to be invoked on a primitive string or a property lookup occurs, JavaScript will automatically wrap the string primitive and call the method or perform the property lookup.

参考资料：

MDN：String
10. 再一次的字符串陷阱

function showCase(value) {
  switch(value) {
    case 'A':
      console.log('Case A');
      break;
    case 'B':
      console.log('Case B');
      break;
    case undefined:
      console.log('undefined');
      break;
    default:
      console.log('Do not know!');
  }
}
showCase(String('A'));


// A. Case A
// B. Case B
// C. Do not know!
// D. undefined
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
20.
21.
22.
答案显然是A。与上面唯一不同的是没有使用 new 关键字，所以直接返回字符串，实际上，typeof string("A") === "string" 的结果是 true。解释参见第9条的解释。

11. 并非都是奇偶
function isOdd(num) {
    return num % 2 == 1;
}


function isEven(num) {
  return num % 2 == 0;
}


function isSane(num) {
  return isEven(num) || isOdd(num);
}


var values = [7, 4, "13", -9, Infinity];
values.map(isSane);


// A. [true, true, true, true, true]
// B. [true, true, true, true, false]
// C. [true, true, true, false, false]
// D. [true, true, false, false, false]
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
20.
21.
22.
23.
答案是C。-9 % 2 = -1 以及 Infinity % 2 = NaN，求余运算符会保留符号，所以只有 isEven 的判断是可靠的。

12. parseInt小贼
parseInt(3, 8);
parseInt(3, 2);
parseInt(3, 0);


// A. 3, 3, 3
// B. 3, 3, NaN
// C. 3, NaN, NaN
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是D。实际结果是 3, NaN, 3，这个在第一个问题中解释的很清楚了。

13. 数组原型是数组
Array.isArray( Array.prototype )


// A. true
// B. false
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是A。一个鲜为人知的事实：其实 Array.prototype 也是一个数组。这点在MDN文档中提到过。

参考资料：

MDN：Array.isArray()
14. 一言难尽的强制转换
var a = [0];
if ([0]) {
    console.log(a == true);
} else {
    console.log("wut");
}


// A. true
// B. false
// C. "wut"
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
答案是B。这个是JavaScript中强制转换的经典案例，关于强制转换不是一两句话可以跟你说清楚的，我建议你系统性的学习一下，推荐你看看《你不知道的JavaScript-中卷》这本书，如果不舍得买书，github上有英文原版：You-Dont-Know-JS，深入理解之后你就是高手了。

好了，回到当前这个问题。当 [0] 需要被强制转成 Boolean 的时候会被认为是 true。所以进入第一个 if 语句，而 a == true 的转换规则在ES5规范的第11.9.3节中已经定义过，你可以自己详细探索下。

规范指出，== 相等中，如果有一个操作数是布尔类型，会先把他转成数字，所以比较变成了 [0] == 1；同时规范指出如果其他类型和数字比较，会尝试把这个类型转成数字再进行宽松比较，而对象（数组也是对象）会先调用它的 toString() 方法，此时 [0] 会变成 "0"，然后将字符串 "0" 转成数字 0，而 0 == 1 的结果显然是 false。

参考资料：


ES5规范：11.9.3
《你不知道的JavaScript-中卷》

15. 撒旦之子“==”
[]==[]


// A. true
// B. false
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是B。ES5规范11.9.3.1-f指出：如果比较的两个对象指向的是同一个对象，就返回 true，否则就返回 false，显然，这是两个不同的数组对象。

参考资料：


ES5规范：11.9.3.1
《你不知道的JavaScript-中卷》

16. 加号 VS 减号
'5' + 3;
'5' - 3;


// A. "53", 2
// B. 8, 2
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
答案是A。"5" + 2 = "52" 很好理解，+ 运算符中只要有一个是字符串，就会变成字符串拼接操作。你不知道的是，- 运算符要求两个操作数都是数字，如果不是，会强制转换成数字，所以结果就变成了 5 - 2 = 3。

参考资料：

《你不知道的JavaScript-中卷》，第四章：4.4.2 字符串和数字之间的隐式强制类型转换
17. 打死那个疯子
1 + - + + + - + 1


// A. 2
// B. 1
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是A。这个只能出现在示例代码中，如果你发现哪个疯子写了这个在生产代码中，打死他就行了。你只要知道 + 1 = 1和- 1 = -1，注意符号之间的空格。两个减号抵消，所以最终结果等效于 1 + 1 = 2。或者你也可以在符号之间插入 0 来理解，即 1 + 0 - 0 + 0 + 0 + 0 - 0 + 1，这样你就一目了然了吧！千万别写这样的代码，因为可能会被打死！

18. 淘气的map
var ary = Array(3);
ary[0] = 2;
ary.map(function(elem) {
    return "1";
});


// A. [2, 1, 1]
// B. ["1", "1", "1"]
// C. [2, "1", "1"]
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是D。实际上结果是 ["1", undefined x 2]，因为规范写得很清楚：

map 方法会给原数组中的每个元素都按顺序调用一次 callback 函数。callback 每次执行后的返回值组合起来形成一个新数组。callback 函数只会在有值的索引上被调用；那些从来没被赋过值或者使用 delete 删除的索引则不会被调用。

参考资料：

MDN: Array.prototype.map()
19. 统统算我的
function sidEffecting(ary) {
    ary[0] = ary[2];
}


function bar(a, b, c) {
  c = 10;
  sidEffecting(arguments);
  return a + b + c;
}


bar(1, 1, 1);


// A. 3
// B. 12
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
答案是D。实际上结果是 21。在JavaScript中，参数变量和 arguments 是双向绑定的。改变参数变量，arguments 中的值会立即改变；而改变 arguments 中的值，参数变量也会对应改变。

20. 损失精度的IEEE 754
var a = 111111111111111110000;
var b = 1111;
console.log(a + b);


// A. 111111111111111111111
// B. 111111111111111110000
// C. NaN
// D. Infinity
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是B。这是IEEE 754规范的黑锅，不是JavaScript的问题。表示这么大的数占用过多位数，会丢失精度，学过计算机组成原理的应该知道是怎么回事。

参考资料：

Wiki：Double-precision floating-point format
21. 反转世界
var x = [].reverse;
x();


// A. []
// B. undefined
// C. error
// D. window
1.
2.
3.
4.
5.
6.
7.
8.
答案是D。MDN规范关于 reverse 的描述：

reverse 方法颠倒数组中元素的位置，并返回该数组的引用。

而这里调用的时候没有制定数组，所以默认的 this 就是 window，所以最后结果返回的是 window。

参考资料：

MDN：Array.prototype.reverse()
22. 最小的正值
Number.MIN_VALUE > 0


// A. false
// B. true
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是B。看规范描述吧：

MIN_VALUE属性是 JavaScript 里最接近 0 的正值，而不是最小的负值。

MIN_VALUE的值约为 5e-324。小于 MIN_VALUE

("underflow values") 的值将会转换为 0。

因为 MIN_VALUE是 Number 的一个静态属性，因此应该直接使用：Number.MIN_VALUE，而不是作为一个创建的 Number实例的属性。

参考资料：

MDN：Number.MIN_VALUE
23. 谨记优先级
[1 < 2 < 3, 3 < 2 < 1]


// A. [true, true]
// B. [true, false]
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是A。<和>的优先级都是从左到右，所以 1 < 2 < 3 会先比较 1 < 2，这会得到 true，但是 < 要求比较的两边都是数字，所以会发生隐式强制转换，将 true 转换成 1，所以最后就变成了比较 1 < 3，结果显然为 true。同理可以分析后者。

参考资料：

MDN：运算符优先级
24. 坑爹中的战斗机
// the most classic wtf
2 == [[[2]]]


// A. true
// B. false
// C. undefined
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
答案是A。根据ES5规范，如果比较的两个值中有一个是数字类型，就会尝试将另外一个值强制转换成数字，再进行比较。而数组强制转换成数字的过程会先调用它的 toString方法转成字符串，然后再转成数字。所以 [2]会被转成 "2"，然后递归调用，最终 [[[2]]] 会被转成数字 2。

25. 小数点魔术
3.toString();
3..toString();
3...toString();


// A. "3", error, error
// B. "3", "3.0", error
// C. error, "3", error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是C。点运算符会被优先识别为数字常量的一部分，然后才是对象属性访问符。所以 3.toString() 实际上被JS引擎解析成 (3.)toString()，显然会出现语法错误。但是如果你这么写 (3).toString()，人为加上括号，这就是合法的。

26. 自动提升为全局变量
(function() {
    var x = y = 1;
})();
console.log(y);
console.log(x);


// A. 1, 1
// B. error, error
// C. 1, error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是C。很经典的例子，在函数中没有用 var 声明变量 y，所以 y 会被自动创建在全局变量 window下面，所以在函数外面也可以访问得到。而 x 由于被 var 声明过，所以在函数外部是无法访问的。

27. 正则表达式实例
var a = /123/;
var b = /123/;
a == b;
a === b;


// A. true, true
// B. true, false
// C. false, false
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
答案是C。每个字面的正则表达式都是一个单独的实例，即使它们的内容相同。

28. 数组也爱比大小
var a = [1, 2, 3];
var b = [1, 2, 3];
var c = [1, 2, 4];


a == b;
a === b;
a > c;
a < c;


// A. false, false, false, true
// B. false, false, false, false
// C. true, true, false, true
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
答案是A。数组也是对象，ES5规范指出如果两个对象进行相等比较，只有在它们指向同一个对象的情况下才会返回 true，其他情况都返回 false。而对象进行大小比较，会调用 toString 方法转成字符串进行比较，所以结果就变成了字符串 "1,2,3" 和 "1,2,4" 按照字典序进行比较了（你若不信，可以重现两个变量的 toString 方法，进行测试）。

29. 原型把戏
var a = {};
var b = Object.prototype;


[a.prototype === b, Object.getPrototypeOf(a) == b]


// A. [false, true]
// B. [true, true]
// C. [false, false]
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是A。对象是没有 prototype 属性的，所以 a.prototype 是 undefined，但我们可以通过 Object.getPrototypeOf 方法来获取一个对象的原型。

30. 构造函数的函数
function f() {}
var a = f.prototype;
var b = Object.getPrototypeOf(f);
a === b;


// A. true
// B. false
// C. null
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
答案是B。这个解释起来有点绕口，我们先来看另外一段代码：

function Person() {}
var p = new Person();


var a = p.__proto__;
var b = Object.getPrototypeOf(p);
var c = Person.prototype;
console.log(a === b, a === c, b === c);
// true, true, true


var d = Person.__proto__;
var e = Object.getPrototypeOf(Person);
var f = Function.prototype;
console.log(d === e, d === f, e === f);
// true, true, true
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
首先你要明白，任何函数都是 Function 的实例，而p是函数 Person 的实例，Object.getPrototypeOf 会获取构造当前对象的原型。所以 Object.getPrototypeOf(p) === Person.prototype，而 Object.getPrototypeOf(Person) === Function.prototype，所以答案就很明显了。

我解释的不是很好，如果读者有更好的解释，欢迎评论。

31. 禁止修改函数名
function foo() {}
var oldName = foo.name;
foo.name = "bar";
[oldName, foo.name];


// A. error
// B. ["", ""]
// C. ["foo", "foo"]
// D. ["foo", "bar"]
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
答案是C。函数名是禁止修改的，规范写的很清楚，所以这里的修改无效。

参考资料：

MDN：Function.name
32. 替换陷阱
"1 2 3".replace(/\d/g, parseInt);


// A. "1 2 3"
// B. "0 1 2"
// C. "NaN 2 3"
// D. "1 NaN 3"
1.
2.
3.
4.
5.
6.
7.
答案是D。如果 replace 方法第二个参数是一个函数，则会在匹配的时候多次调用，第一个参数是匹配的字符串，第二个参数是匹配字符串的下标。所以变成了调用 parseInt(1, 0)、parseInt(2, 2)和parseInt(3, 4)，结果你就懂了。

参考资料：

MDN：String.prototype.replace()
33. Function的名字
function f() {}
var parent = Object.getPrototypeOf(f);
console.log(f.name);
console.log(parent.name);
console.log(typeof eval（f.name));
console.log(typeof eval（parent.name));


// A. "f", "Empty", "function", "function"
// B. "f", undefined, "function", error
// C. "f", "Empty", "function", error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
答案是C。根据第30题的解释，我们知道代码中的 parent 实际上就是 Function.prototype，而它在控制台中输出为：

function () {
  [native code]
}
1.
2.
3.
它的 name 属性是 ""，所以你 eval（"")是得不到任何东西的。

34. 正则测试陷阱
var lowerCaseOnly = /^[a-z]+$/;
[lowerCaseOnly.test(null), lowerCaseOnly.test()]


// A. [true, false]
// B. error
// C. [true, true]
// D. [false, true]
1.
2.
3.
4.
5.
6.
7.
8.
答案是C。test 方法的参数如果不是字符串，会经过抽象 ToString操作强制转成字符串，因此实际上测试的是字符串 "null" 和 "undefined"。

35. 逗号定义数组
[,,,].join(", ")


// A. ", , , "
// B. "undefined, undefined, undefined, undefined"
// C. ", , "
// D. ""
1.
2.
3.
4.
5.
6.
7.
答案是C。JavaScript允许用逗号来定义数组，得到的数组是含有3个 undefined 值的数组。MDN关于 join 方法的描述：

所有的数组元素被转换成字符串，再用一个分隔符将这些字符串连接起来。如果元素是undefined 或者null， 则会转化成空字符串。

参考资料：

MDN：Array.prototype.join()
36. 保留字 class
var a = {class: "Animal", name: "Fido"};
console.log(a.class);


// A. "Animal"
// B. Object
// C. an error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
答案是D。实际上真正的答案取决于浏览器。class 是保留字，但是在Chrome、Firefox和Opera中可以作为属性名称，在IE中是禁止的。另一方面，其实所有浏览器基本接受大部分的关键字（如：int、private、throws等）作为变量名，而class是禁止的。

37. 无效日期
var a = new Date("epoch");


// A. Thu Jan 01 1970 01:00:00 GMT+0100(CET)
// B. current time
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
答案是D。实际结果是 Invalid Date，它实际上是一个Date对象，因为 a instance Date 的结果是 true，但是它是无效的Date。Date对象内部是用一个数字来存储时间的，在这个例子中，这个数字是 NaN。

38. 神鬼莫测的函数长度
var a = Function.length;
var b = new Function().length;
console.log(a === b);


// A. true
// B. false
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是B。实际上a的值是1，b的值是0。还是继续来看MDN文档关于 Function.length 的描述吧！

Function构造器的属性：

Function 构造器本身也是个Function。他的 length 属性值为 1 。该属性 Writable: false, Enumerable: false, Configurable: true。

Function原型对象的属性：

Function原型对象的 length 属性值为 0 。

所以，在本例中，a代表的是 Function 构造器的 length 属性，而b代表的是 Function 原型的 length 属性。

参考资料：

MDN：Function.length
39. Date的面具
var a = Date(0);
var b = new Date(0);
var c = new Date();
[a === b, b === c, a === c];


// A. [true, true, true]
// B. [false, false, false]
// C. [false, true, false]
// D. [true, false, false]
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
答案是B。先看MDN关于Date对象的注意点：

需要注意的是只能通过调用 Date 构造函数来实例化日期对象：以常规函数调用它（即不加 new 操作符）将会返回一个字符串，而不是一个日期对象。另外，不像其他JavaScript 类型，Date 对象没有字面量格式。

所以a是字符串，b和c是Date对象，并且b代表的是1970年那个初始化时间，而c代表的是当前时间。

参考资料：

MDN：Date
40. min与max共舞
var min = Math.min();
var max = Math.max();
console.log(min < max);


// A. true
// B. false
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是B。看MDN文档，对 Math.min的描述：

如果没有参数，结果为Infinity。

对 Math.max 的描述：

如果没有参数，结果为-Infinity。

参考资料：


MDN：Math.min
MDN：Math.max

41. 警惕全局匹配
function captureOne(re, str) {
    var match = re.exec(str);
    return match && match[1];
}


var numRe = /num=(\d+)/ig,
      wordRe = /word=(\w+)/i,
      a1 = captureOne(numRe, "num=1"),
      a2 = captureOne(wordRe, "word=1"),
      a3 = captureOne(numRe, "NUM=1"),
      a4 = captureOne(wordRe, "WORD=1");


[a1 === a2, a3 === a4]


// A. [true, true]
// B. [false, false]
// C. [true, false]
// D. [false, true]
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
20.
21.
答案是C。看MDN关于 exec 方法的描述：

当正则表达式使用 "g" 标志时，可以多次执行 exec 方法来查找同一个字符串中的成功匹配。当你这样做时，查找将从正则表达式的  lastIndex 属性指定的位置开始。

所以a3的值为 null。

参考资料：

MDN：RegExp.prototype.exec()
42. 最熟悉的陌生人
var a = new Date("2014-03-19");
var b = new Date(2014, 03, 19);
[a.getDay() == b.getDay(), a.getMonth() == b.getMonth()]


// A. [true, true]
// B. [true, false]
// C. [false, true]
// D. [false, false]
1.
2.
3.
4.
5.
6.
7.
8.
9.
答案是D。先看MDN关于Date的一个注意事项：

当Date作为构造函数调用并传入多个参数时，如果数值大于合理范围时（如月份为13或者分钟数为70），相邻的数值会被调整。比如 new Date(2013, 13, 1)等于new Date(2014, 1, 1)，它们都表示日期2014-02-01（注意月份是从0开始的）。其他数值也是类似，new Date(2013, 2, 1, 0, 70)等于new Date(2013, 2, 1, 1, 10)，都表示时间2013-03-01T01:10:00。

此外，getDay 返回指定日期对象的星期中的第几天（0～6），所以，你懂的。

参考资料：

MDN：Date
43. 匹配隐式转换
if("http://giftwrapped.com/picture.jpg".match(".gif")) {
    console.log("a gif file");
} else {
    console.log("not a gif file");
}


// A. "a gif file"
// B. "not a gif file"
// C. error
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
答案是A。看MDN对 match 方法的描述：

如果传入一个非正则表达式对象，则会隐式地使用 new RegExp(obj)

将其转换为正则表达式对象。

所以我们的字符串 ".gif" 会被转换成正则对象 /.gif/，会匹配到 "/gif"。

参考资料：

MDN：String.prototype.match()
44. 重复声明变量
function foo(a) {
    var a;
    return a;
}


function bar(a) {
  var a = "bye";
  return a;
}


[foo("hello"), bar("hello")]


// A. ["hello", "hello"]
// B. ["hello", "bye"]
// C. ["bye", "bye"]
// D. other
1.
2.
3.
4.
5.
6.
7.
8.
9.
10.
11.
12.
13.
14.
15.
16.
17.
18.
19.
答案是B。一个变量在同一作用域中已经声明过，会自动移除 var 声明，但是赋值操作依旧保留，结合前面提到的变量提升机制，你就明白了。
-----------------------------------
44道比较难的 JS 面试题
https://blog.51cto.com/u_15503053/5050894

1. parseInt with map



```javascript
["1", "2", "3"].map(parseInt)

// A. ["1", "2", "3"]
// B. [1, 2, 3]
// C. [0, 1, 2]
// D. other
```







2. 神奇的null

3. 愤怒的reduce

4. 该死的优先级

5. 神鬼莫测之变量提升

6. 死循环陷阱

7. 过滤器魔法

8. 警惕IEEE 754标准

9. 字符串陷阱

11. 并非都是奇偶

12. parseInt小贼

13. 数组原型是数组

14. 一言难尽的强制转换

15. 撒旦之子“==”

16. 加号 VS 减号

17. 打死那个疯子

18. 淘气的map

19. 统统算我的

20. 损失精度的IEEE 754

21. 反转世界

22. 最小的正值

23. 谨记优先级

24. 坑爹中的战斗机

25. 小数点魔术

26. 自动提升为全局变量

27. 正则表达式实例

28. 数组也爱比大小

29. 原型把戏

30. 构造函数的函数

31. 禁止修改函数名

32. 替换陷阱

33. Function的名字

34. 正则测试陷阱

35. 逗号定义数组

36. 保留字 class

37. 无效日期

38. 神鬼莫测的函数长度

39. Date的面具

40. min与max共舞

41. 警惕全局匹配

42. 最熟悉的陌生人

43. 匹配隐式转换

44. 重复声明变量