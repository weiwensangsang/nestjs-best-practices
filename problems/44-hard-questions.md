

# 44 very hard questions(fixme)



### parseInt with map

  ```javascript
   ["1", "2", "3"].map(parseInt)
  
  // A. ["1", "2", "3"]
  // B. [1, 2, 3]
  // C. [0, 1, 2]
  // D. other
  ```

  

The result of executing ["1", "2", "3"].map(parseInt) is [1, NaN, NaN].

This is because the map() method will sequentially pass each element in the array as the first parameter to the callback function. The first parameter of the callback function receives the value of the current element, and the second parameter receives the is the index of the current element. The parseInt() method receives two parameters, the first parameter is the value to be parsed, and the second parameter is the base number. If you do not specify the base number, parseInt() will judge the base base according to the value passed in, for example, "10" is decimal, and "0x10" is hexadecimal.

Therefore, when ["1", "2", "3"].map(parseInt) is executed, the first element is "1", which is parsed as decimal 1 when passed into parseInt(), so the return value is 1; the second element is "2", which is parsed as binary when passed to parseInt(), and the number 2 does not exist in binary, so the return value is NaN; the third element is "3", which is passed to parseInt () is parsed as ternary, and the number 3 does not exist in ternary, so the return value is NaN.



### Weird null

```javascript
[typeof null, null instanceof Object]


// A. ["object", false]
// B. [null, false]
// C. ["object", true]
// D. other
```

The result of typeof null is "object", it's an ECMAScript bug, it should be "null"

The instanceof operator is used to test whether an object has a prototype property on its prototype chain constructor. The null value is not created with the Object prototype, so null instanceof Object returns false.





### reduce

```javascript
 [ [3,2,1].reduce(Math.pow), [].reduce(Math.pow) ]


// A. an error
// B. [9, 0]
// C. [9, NaN]
// D. [9, undefined]
```

305:1 Uncaught TypeError: Reduce of empty array with no initial value
    at Array.reduce (<anonymous>)
    at <anonymous>:1:33





### priority

```javascript
var val = 'smtg';
console.log('Value is ' + (val === 'smtg') ? 'Something' : 'Nothing');
  


// A. Value is Something
// B. Value is Nothing
// C. NaN
// D. other
```

D



### var and function

```javascript
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
```



The code is equals to the follows.

```javascript
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
```




### infinite loop

```javascript
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
```

In JavaScript中，2^53 is the max value，so 2^53 + 1 == 2^53。



### filter

```javascript
   var ary = [0,1,2];
   ary[10] = 10;
   ary.filter(function(x) {
    return x === undefined;
   });

// A. [undefined x 7]
// B. [0, 1, 2, 10]
// C. []
// D. [undefined]
```



C

filter calls the callback function once for each element in the array and creates a new array with all elements for which callback returns true or a value equivalent to true. The callback will only be called on the assigned index, and will not be called on those indexes that have been deleted or have never been assigned. Elements that fail the callback test are skipped and not included in the new array.



### Be wary of the IEEE 754 standard

```javascript
var two = 0.2;
var one = 0.1;
var eight = 0.8;
var six = 0.6;
[two - one == one, eight - six == two]

// A. [true, false]
// B. [false, false]
// C. [true, false]
// D. other
```

The answer is C. JavaScript uses double-precision floating-point format, that is, the IEEE 754 standard. In this format, some numbers cannot be represented, for example: 0.1 + 0.2 = 0.30000000000000004 0. All languages that adopt this standard have this problem, such as: Java, Python, etc.





### String Traps

```javascript
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
```



The answer is C. Strict equality === is used inside the switch to judge, and new String("A") returns an object, while String("A") directly returns the string "A". You can also refer to the distinction between raw strings and String objects in MDN:

Note that JavaScript distinguishes between String objects and primitive string values. (The same is true of Boolean and Numbers.)

String literals (denoted by double or single quotes) and strings returned from String calls in a non-constructor context (i.e., without using the new keyword) are primitive strings. JavaScript automatically converts primitives to String objects, so that it's possible to use String object methods for primitive strings. In contexts where a method is to be invoked on a primitive string or a property lookup occurs, JavaScript will automatically wrap the string primitive and call the method or perform the property lookup.





### Another string trap

```javascript
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
```

The answer is obviously A. The only difference from the above is that the new keyword is not used, so the string is returned directly. In fact, the result of typeof string("A") === "string" is true.



### Not all parity

```javascript
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
values. map(isSane);

// A. [true, true, true, true, true]
// B. [true, true, true, true, false]
// C. [true, true, true, false, false]
// D. [true, true, false, false, false]
```



The answer is C. -9 % 2 = -1 and Infinity % 2 = NaN, the remainder operator preserves sign, so only isEven is reliable.





### The array prototype is an array

```
Array.isArray( Array.prototype )

// A.true
// B. false
// C. error
// D. other
```



The answer is A. A little-known fact: Array.prototype is actually an array.



### Unexplainable coercion

```javascript
var a = [0];
if ([0]) {
console. log(a == true);
} else {
console.log("wut");
}

// A.true
// B. false
// C. "wut"
// D. other
```



The answer is B.

Alright, back to the current question. Will be considered true when [0] needs to be coerced to Boolean. So enter the first if statement, and the conversion rule of a == true has been defined in Section 11.9.3 of the ES5 specification, you can explore it in detail yourself.

The specification points out that in == equality, if one of the operands is a Boolean type, it will be converted to a number first, so the comparison becomes [0] == 1; at the same time, the specification points out that if other types are compared with numbers, it will try to convert This type is converted into a number and then loosely compared, and the object (an array is also an object) will first call its toString() method, at this time [0] will become "0", and then the string "0" will be converted into a number 0 , and the result of 0 == 1 is obviously false.



### Son of Satan "=="

```javascript
[]==[]

// A.true
// B. false
// C. error
// D. other
```



The answer is B. ES5 specification 11.9.3.1-f points out: If the two compared objects point to the same object, return true, otherwise return false, obviously, these are two different array objects.





### plus sign vs minus sign

```javascript
'5' + 3;
'5' - 3;

// A. "53", 2
// B. 8, 2
// C. error
// D. other
```



The answer is A. "5" + 2 = "52" is easy to understand, as long as one of the + operators is a string, it will become a string concatenation operation. What you don't know is that the - operator requires both operands to be numbers, and if they aren't, they are coerced to numbers, so the result becomes 5 - 2 = 3.





### Kill that madman

```javascript
1 + - + + + - + 1

//A.2
//B.1
// C. error
// D. other


```

The answer is A. This can only appear in the example code, if you find some crazy person who wrote this in the production code, kill him. All you need to know is that + 1 = 1 and - 1 = -1, and pay attention to the spaces between the symbols. The two minus signs cancel out, so the final result is equivalent to 1 + 1 = 2. Or you can also insert 0 between the symbols to understand, that is, 1 + 0 - 0 + 0 + 0 + 0 - 0 + 1, so you can understand it at a glance! Never write such code, because you may be killed!





### Count me all

```javascript
function sidEffecting(ary) {
ary[0] = ary[2];
}


function bar(a, b, c) {
   c = 10;
   sidEffecting(arguments);
   return a + b + c;
}


bar(1, 1, 1);

//A.3
//B.12
// C. error
// D. other
```



The answer is D. Actually the result is 21. In JavaScript, parameter variables and arguments are two-way bound. If you change the parameter variable, the value in arguments will change immediately; if you change the value in arguments, the parameter variable will also change accordingly.



### IEEE 754 loss of precision

```javascript
var a = 111111111111111110000;
var b = 1111;
console.log(a + b);

// A. 111111111111111111111
// B. 111111111111111110000
// C.NaN
// D. Infinity
```



The answer is B. This is a scapegoat of the IEEE 754 specification, not a JavaScript issue. It means that such a large number occupies too many digits and will lose precision. Those who have studied the principles of computer composition should know what is going on.



### Invert the world

```javascript
var x = [].reverse;
x();

// A.[]
// B. undefined
// C. error
//D.window


```

The answer is D. The description of reverse in the MDN specification:

The reverse method reverses the position of the elements in the array and returns a reference to the array.

However, there is no array when calling here, so the default this is window, so the final result is window.

References:

MDN: Array.prototype.reverse()¶



### Smallest positive value

```javascript
Number.MIN_VALUE > 0

// A. false
// B.true
// C. error
// D. other
```



The answer is B. Look at the specification description:

The MIN_VALUE property is the closest positive value to 0 in JavaScript, not the smallest negative value.

The value of MIN_VALUE is about 5e-324. less than MIN_VALUE

("underflow values") will be converted to 0.

Because MIN_VALUE is a static property of Number, it should be used directly: Number.MIN_VALUE rather than as a property of a created Number instance.

References:

MDN: Number.MIN_VALUE



### Remember priority

```javascript
[1 < 2 < 3, 3 < 2 < 1]

// A. [true, true]
// B. [true, false]
// C. error
// D. other
```



The answer is A. The priority of < and > is from left to right, so 1 < 2 < 3 will compare 1 < 2 first, which will get true, but < requires both sides of the comparison to be numbers, so an implicit coercion will occur, which will true is converted to 1, so in the end it becomes a comparison 1 < 3, which is obviously true. The latter can be analyzed in the same way.





### The fighter in the cheating

```javascript
// the most classic wtf
2 == [[[2]]]

// A.true
// B. false
// C. undefined
// D. other
```



The answer is A. According to the ES5 specification, if one of the two values being compared is a number type, an attempt will be made to cast the other value to a number before comparison. The process of coercing an array into a number will first call its toString method to convert it into a string, and then convert it into a number. So [2] will be converted to "2", and then recursively called, and finally [[[2]]] will be converted to the number 2.



### Decimal point magic

```javascript
3.toString();
3..toString();
3...toString();

// A. "3", error, error
// B. "3", "3.0", error
// C. error, "3", error
// D. other
```



The answer is C. The dot operator is recognized as part of a numeric constant before object property accessors. So 3.toString() is actually parsed into (3.)toString() by the JS engine, obviously there will be a syntax error. But if you write (3).toString() like this, artificially adding parentheses, this is legal.



### Automatic promotion to global variables

```javascript
(function() {
var x = y = 1;
})();
console. log(y);
console. log(x);

// A. 1, 1
// B. error, error
// C. 1, error
// D. other
```



The answer is C. A classic example is that the variable y is not declared with var in the function, so y will be automatically created under the global variable window, so it can be accessed outside the function. Since x is declared by var, it cannot be accessed outside the function.



### Regular expression example

```javascript
var a = /123/;
var b = /123/;
a == b;
a === b;

// A. true, true
// B. true, false
// C. false, false
// D. other
```



The answer is C. Each literal regular expression is a separate instance, even if their content is the same.



### Arrays also love ratio size

```javascript
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


```

The answer is A. Arrays are also objects. The ES5 specification states that if two objects are compared for equality, true will only be returned if they point to the same object, and false will be returned in other cases. For object size comparison, the toString method will be called to convert it into a string for comparison, so the result becomes the string "1,2,3" and "1,2,4" are compared in lexicographical order (if you don't believe it , you can reproduce the toString method of two variables for testing).



### Prototype Tricks

```javascript
var a = {};
var b = Object.prototype;


[a.prototype === b, Object.getPrototypeOf(a) == b]

// A. [false, true]
// B. [true, true]
// C. [false, false]
// D. other
```



The answer is A. Objects do not have a prototype property, so a.prototype is undefined, but we can get the prototype of an object through the Object.getPrototypeOf method.



### Constructor function

```javascript
function f() {}
var a = f.prototype;
var b = Object. getPrototypeOf(f);
a === b;

// A.true
// B. false
// C. null
// D. other
```



The answer is B. This explanation is a bit convoluted, let's look at another piece of code first:

```javascript
function Person() {}
var p = new Person();


var a = p.__proto__;
var b = Object. getPrototypeOf(p);
var c = Person.prototype;
console.log(a === b, a === c, b === c);
// true, true, true

var d = Person.__proto__;
var e = Object. getPrototypeOf(Person);
var f = Function.prototype;
console.log(d === e, d === f, e === f);
// true, true, true
```



First of all, you need to understand that any function is an instance of Function, and p is an instance of the function Person, Object.getPrototypeOf will get the prototype of the current object. So Object.getPrototypeOf(p) === Person.prototype, and Object.getPrototypeOf(Person) === Function.prototype, so the answer is obvious.

My explanation is not very good, if readers have a better explanation, welcome to comment.



### Prohibit modifying the function name

```javascript
function foo() {}
var oldName = foo.name;
foo.name = "bar";
[oldName, foo.name];

// A. error
//B.["", ""]
// C. ["foo", "foo"]
// D. ["foo", "bar"]
```



The answer is C. The function name is forbidden to be modified, and the specification is very clear, so the modification here is invalid.

References:

MDN: Function.name



### Replace Traps

```javascript
"1 2 3".replace(/\d/g, parseInt);

// A. "1 2 3"
// B. "0 1 2"
// C. "NaN 2 3"
// D. "1 NaN 3"
```



The answer is D. If the second parameter of the replace method is a function, it will be called multiple times during matching. The first parameter is the matched string, and the second parameter is the subscript of the matched string. So it becomes calling parseInt(1, 0), parseInt(2, 2) and parseInt(3, 4) and you get the idea.

References:

MDN: String.prototype.replace()



### Function name

```javascript
function f() {}
var parent = Object. getPrototypeOf(f);
console.log(f.name);
console.log(parent.name);
console.log(typeof eval(f.name));
console.log(typeof eval(parent.name));

// A. "f", "Empty", "function", "function"
// B. "f", undefined, "function", error
// C. "f", "Empty", "function", error
// D. other
```



The answer is C. According to the explanation of question 30, we know that the parent in the code is actually Function.prototype, and its output in the console is:

```javascript
function () {
   [native code]
}
```

Its name attribute is "", so you don't get anything with eval("").



### Regex test pitfalls

```javascript
var lowerCaseOnly = /^[a-z]+$/;
[lowerCaseOnly.test(null), lowerCaseOnly.test()]

// A. [true, false]
// B. error
// C. [true, true]
// D. [false, true]
```



The answer is C. If the parameter of the test method is not a string, it will be converted into a string through the abstract ToString operation, so the string "null" and "undefined" are actually tested.



### Commas define arrays

```javascript
[,,,]. join(", ")

// A. ", , , "
// B. "undefined, undefined, undefined, undefined"
// C. ", , "
// D. ""
```



The answer is C. JavaScript allows arrays to be defined with commas, and the resulting array is an array of 3 undefined values. MDN's description of the join method:

All array elements are converted to strings, and these strings are concatenated with a delimiter. If the element is undefined or null, it will be converted to an empty string.

References:

MDN: Array.prototype.join()¶



### Reserved words class

```javascript
var a = {class: "Animal", name: "Fido"};
console. log(a. class);

// A. "Animal"
// B. Object
// C. an error
// D. other
```



The answer is D. Actually the real answer depends on the browser. class is a reserved word, but it can be used as an attribute name in Chrome, Firefox and Opera, and it is forbidden in IE. On the other hand, in fact, all browsers basically accept most keywords (such as: int, private, throws, etc.) as variable names, while class is prohibited.



### invalid date

```javascript
var a = new Date("epoch");

// A. Thu Jan 01 1970 01:00:00 GMT+0100(CET)
// B. current time
// C. error
// D. other
```



The answer is D. The actual result is Invalid Date, which is actually a Date object because a instance Date evaluates to true, but it's an invalid Date. Date object internally uses a number to store time, in this example, this number is NaN.



### Unpredictable function length

```javascript
var a = Function. length;
var b = new Function(). length;
console.log(a === b);

// A.true
// B. false
// C. error
// D. other
```



The answer is B. In fact, the value of a is 1, and the value of b is 0. Let's continue to look at the description of Function.length in the MDN document!

Properties of the Function constructor:

The Function constructor is itself a Function. Its length property has a value of 1 . The property Writable: false, Enumerable: false, Configurable: true.

Properties of the Function prototype object:

The value of the length property of the Function prototype object is 0 .

So, in this example, a represents the length property of the Function constructor, and b represents the length property of the Function prototype.

References:

MDN: Function. length



### Date's mask

```javascript
var a = Date(0);
var b = new Date(0);
var c = new Date();
[a === b, b === c, a === c];

// A. [true, true, true]
// B. [false, false, false]
// C. [false, true, false]
// D. [true, false, false]
```



The answer is B. First look at MDN's attention to the Date object:

Note that Date objects can only be instantiated by calling the Date constructor: calling it as a regular function (i.e. without the new operator) will return a string, not a Date object. Also, unlike other JavaScript types, Date objects do not have literal formatting.

So a is a string, b and c are Date objects, and b represents the initialization time in 1970, and c represents the current time.

References:

MDN: Date



#### min dances with max

```javascript
var min = Math.min();
var max = Math. max();
console.log(min < max);

// A.true
// B. false
// C. error
// D. other
```



The answer is B. Look at the MDN documentation, the description of Math.min:

With no arguments, the result is Infinity.

Description of Math.max:

With no arguments, the result is -Infinity.

References:

MDN: Math.min
MDN: Math.max





#### Be wary of global matching

```javascript
function captureOne(re, str) {
var match = re. exec(str);
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
```



The answer is C. See MDN's description of the exec method:

When the regular expression uses the "g" flag, the exec method can be executed multiple times to find a successful match in the same string. When you do this, the lookup will start at the position specified by the regex's lastIndex property.

So the value of a3 is null.

References:

MDN: RegExp.prototype.exec()¶



#### The most familiar stranger

```javascript
var a = new Date("2014-03-19");
var b = new Date(2014, 03, 19);
[a. getDay() == b. getDay(), a. getMonth() == b. getMonth()]

// A. [true, true]
// B. [true, false]
// C. [false, true]
// D. [false, false]
```

. First look at a note from MDN about Date:

When Date is called as a constructor and multiple parameters are passed in, if the value is greater than a reasonable range (such as 13 for the month or 70 for the minute), adjacent values will be adjusted. For example, new Date(2013, 13, 1) is equal to new Date(2014, 1, 1), they both represent the date 2014-02-01 (note that the month starts from 0). Other values are similar, new Date(2013, 2, 1, 0, 70) is equal to new Date(2013, 2, 1, 1, 10), both represent the time 2013-03-01T01:10:00.

Also, getDay returns the day of the week (0-6) of the specified date object, so, you get the idea.

References:

MDN: Date



### Match implicit conversions

```javascript
if("http://giftwrapped.com/picture.jpg".match(".gif")) {
console.log("a gif file");
} else {
console.log("not a gif file");
}

// A. "a gif file"
// B. "not a gif file"
// C. error
// D. other
```



The answer is A. See MDN's description of the match method:

If a non-regex object is passed in, new RegExp(obj) will be used implicitly

Convert it to a regular expression object.

So our string ".gif" will be converted to a regular object /.gif/, which will match "/gif".

References:

MDN: String.prototype.match()¶



### Duplicate variable declaration

```javascript
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
// C.["bye", "bye"]
// D. other
```



The answer is B. If a variable has been declared in the same scope, the var declaration will be automatically removed, but the assignment operation will still be retained. Combined with the variable promotion mechanism mentioned above, you will understand.