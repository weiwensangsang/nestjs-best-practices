# NPM and modules



### NPM

At first, use this command to install node.

```
brew install node
```

The full name of npm is node package manager. Wikipedia's introduction to Node.js indicates that npm is the package manager that comes with Node.js. When downloading and installing node, npm will be installed additionally. npm is a command-line tool for downloading and installing Node.js programs from the NPM Registry while resolving dependencies. npm increases the speed of development because it can take care of the installation and management of third-party Node.js programs.



Npm is essentially `node ./bin/npm-cli.js`, so we can also use `node ./bin/npm-cli.js` instead of npm.

node ./bin/npm-cli.js install moment is equivalent to npm install moment .



In the development code stage, we often use npm:

- When initializing the project, we will use the `npm init -y` command to generate the `package.json` file
- Then `npm install <packageName>` to install the module
- After the development code is completed, you need to configure the scripts field start in package.json, and then execute the code through `npm run start

#### npm init 

`npm init` will execute [lib/init.js](https://github.com/npm/cli/blob/latest/lib/init.js), and the core logic is to call initJson().

```
await new Promise((res, rej) => {
    initJson(dir, initFile, npm.config, (er, data) => {
      npm.log.resume()
      npm.log.enableProgress()
      npm.log.silly('package data', data)
      if (er && er.message === 'canceled') {
        npm.log.warn('init', 'canceled')
        return res()
      }
      if (er)
        rej(er)
      else {
        npm.log.info('init', 'written successfully')
        res(data)
      }
    })
  })
复制代码
```

initJson essentially calls the init method in init-package-json. The main thing to do is to write to the package.json file



#### npm install

Here is how install walks.

1. load the existing node_modules tree from disk
2. clone the tree
3. fetch the package.json and assorted metadata and add it to the clone
4. walk the clone and add any missing dependencies, dependencies will be added as close to the top as is possible,  without breaking any other modules

5. compare the original tree with the cloned tree and make a list of actions to take to convert one to the other
6. execute all of the actions, deepest first

(kinds of actions are install, update, remove and move)



------



### modules



Node.js provides a module system that allows developers to organize their code into reusable pieces of functionality. A module is essentially a file that contains code and can be loaded into other files using the `require()` function.

Node.js has a core set of built-in modules that can be used without any additional installation or configuration. Some examples of core modules include `fs` for working with the file system, `http` for creating HTTP servers, and `crypto` for cryptographic functions.

In addition to core modules, Node.js also allows developers to create and use external modules. These modules can be installed using the Node Package Manager (NPM) and can be used in a project by requiring them in the code.

The `.exports` property is used in Node.js modules to define the interface that is exposed to other modules when they require the module. Whatever is assigned to `.exports` becomes the public interface of the module.

To import external libraries in Node.js, you can use the `require()` function followed by the name of the library. For example, to import the `express` library, you would use `const express = require('express')`. This will load the `express` library and make it available for use in your code.



### Useful modules

  - How would you use a URL module in Node.js? 

    -  The URL module in Node.js allows you to parse and format URL strings. You can load the URL module by using the following code:

    ```javascript
    const url = require('url');
    ```

    You can parse a URL string into a URL object by using the `url.parse()` method, and format a URL object into a URL string by using the `url.format()` method.

  - What is the use of the connect module in Node.js? 

    -  The connect module is a middleware framework for Node.js that simplifies the development of web applications. It provides many built-in middleware functions, such as routing, error handling, body parsing, and more. You can load the module by using `require('connect')`.

  - What is the use of the crypto module in Node.js? 

    -  The crypto module in Node.js provides cryptographic functions, including symmetric and asymmetric encryption, hashing functions, digital signatures, and more. You can load the module by using `require('crypto')`.

-  Explain the concept of Punycode in Node.js. 

   -  Punycode is an encoding scheme used to represent Unicode strings within the ASCII character set, primarily for handling internationalized domain names. Node.js provides the Punycode module, which can be used to convert Unicode strings to Punycode strings and vice versa using the `punycode.encode()` and `punycode.decode()` methods.