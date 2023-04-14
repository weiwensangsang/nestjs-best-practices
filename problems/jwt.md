# JWT Login



Implementing authentication using JWT in NestJS requires the completion of the following steps:

install dependencies
First, you need to install two dependencies @nestjs/jwt and @nestjs/passport:



```
npm install @nestjs/jwt @nestjs/passport passport passport-jwt
```



### Create the JWT module

In NestJS, JWT is implemented through a separate module. Create a jwt.module.ts file with the following content:

```javascript
import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';

@Module({
   imports: [
     JwtModule. register({
       secret: 'secretKey',
       signOptions: { expiresIn: '60s' },
     }),
   ],
   exports: [JwtModule],
})
export class JwtAuthModule {}
```

In this example, we used the JwtModule.register method to create the JWT module. In it, we set an encryption key secretKey and an expiration time of 60s.

### Create Passport module

Passport is a very popular authentication middleware for Node.js. It gives us a way to validate the JWT. Create a passport.module.ts file with the following content:

```javascript
import { Module } from '@nestjs/common';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';
import { JwtStrategy } from './jwt.strategy';

@Module({
   imports: [
     PassportModule. register({ defaultStrategy: 'jwt' }),
     JwtModule. register({
       secret: 'secretKey',
       signOptions: { expiresIn: '60s' },
     }),
   ],
   providers: [JwtStrategy],
   exports: [PassportModule, JwtStrategy],
})
export class PassportAuthModule {}
```

In this example, we used the PassportModule.register method to create the Passport module and specified the default policy as jwt. We also include the JWT module in our module so we can use the JwtService to generate and verify JWTs.

### Create a JWT policy

Create a jwt.strategy.ts file with the following content:

```javascript
import { Injectable } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import { ExtractJwt, Strategy } from 'passport-jwt';
import { AuthService } from './auth.service';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
   constructor(private authService: AuthService) {
     super({
       jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
       ignoreExpiration: false,
       secretOrKey: 'secretKey',
     });
   }

   async validate(payload: any) {
     return { userId: payload.sub, username: payload.username };
   }
}
```


In this example, we create a JwtStrategy class that extends the PassportStrategy class. We also inject an AuthService which will be used in the validate method.

We set the jwtFromRequest in the constructor, which says to extract the JWT from the Authorization header. We also set the encryption key secretOrKey. In the validate method, we validate the JWT and return the user information in the payload.

### Create an Auth Service

auth.service.ts file with the following content:

```javascript
import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { User } from './user.entity';

@Injectable()
export class AuthService {
   constructor(private jwtService: JwtService) {}

   async validateUser(username: string, password: string): Promise<User> {
     // Implement user authentication logic here
     // If the username and password are verified, return a User object
   }

   async login(user: User) {
     const payload = { username: user.username, sub: user.userId };
     return {
       access_token: this.jwtService.sign(payload),
     };
   }
}
```

In this example, we create an AuthService class that contains two methods: validateUser and login. In the validateUser method, we can implement our own user authentication logic. In this example, we simply return a User object. In the login method, we use the JwtService to generate the JWT and return an object containing the JWT.

### Create an Auth Controller

Create an auth.controller.ts file with the following content:

```javascript
import { Controller, Request, Post, UseGuards } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';
import { AuthService } from './auth.service';

@Controller()
export class AuthController {
   constructor(private authService: AuthService) {}

   @UseGuards(AuthGuard('local'))
   @Post('login')
   async login(@Request() req) {
     return this.authService.login(req.user);
   }
}
```

In this example, we create an AuthController class that contains a login method that uses the @UseGuards(AuthGuard('local')) decorator to specify the use of the local authentication strategy, which is the JwtStrategy we defined in PassportAuthModule. In the login method, we return an object containing the JWT.

### Register Auth Module

Finally, register the JwtAuthModule, PassportAuthModule and AuthController in your main module:

```javascript
import { Module } from '@nestjs/common';
import { JwtAuthModule } from './jwt-auth/jwt-auth.module';
import { PassportAuthModule } from './passport-auth/passport-auth.module';
import { AuthController } from './auth.controller';

@Module({
   imports: [JwtAuthModule, PassportAuthModule],
   controllers: [AuthController],
})
export class AppModule {}
```

Now, you can use JWT for authentication. When the user calls the POST /login interface, if the username and password are verified, the server will return a JWT, and the user can use the JWT for authentication in subsequent requests.