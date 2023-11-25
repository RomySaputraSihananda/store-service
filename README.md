[![Twitter: romy](https://img.shields.io/twitter/follow/RomySihananda)](https://twitter.com/RomySihananda)

# store-service

![](https://raw.githubusercontent.com/RomySaputraSihananda/RomySaputraSihananda/main/images/F7MvWqwXIAA2oNy.jpeg)

Web Services Rest API with Sping Boot v3.1.3 with JWT (JSON Web Token)</br>for authentication features, using Elasticsearch as Storage Database and Swagger UI for</br>Endpoint API documentation.

# Dependency

- org.springframework.boot:spring-boot-starter-web:**3.1.5**
- org.springframework.boot:spring-boot-starter-security:**3.1.3**
- org.springdoc:springdoc-openapi-starter-webmvc-ui:**2.2.0**
- org.projectlombok:lombok:**1.18.30**
- co.elastic.clients:elasticsearch-java:**7.17.8**
- com.fasterxml.jackson.core:jackson-databind:**2.15.2**
- io.jsonwebtoken:jjwt-api:**0.11.5**
- io.jsonwebtoken:jjwt-impl:**0.11.5**
- io.jsonwebtoken:jjwt-jackson:**0.11.5**

# EndPoints

The following is a list of available Request URIs:

- **Swagger Documentation** ( no need authentication )

  - | URL                                 | Method | Information           |
    | :---------------------------------- | :----: | :-------------------- |
    | [/docs](http://localhost:4444/docs) |  GET   | Swagger Documentation |

- **Auth Controller** ( no need authentication )

  - | URL                                                             | Method | Information             |
    | :-------------------------------------------------------------- | :----: | :---------------------- |
    | [/api/v1/auth/signin](http://localhost:4444/api/v1/auth/signin) |  POST  | API for Signin user     |
    | [/api/v1/auth/signup](http://localhost:4444/api/v1/auth/signup) |  POST  | API for Signup new user |

- **Product Controller** ( for **USER** authentication only )

  - | URL                                                             | Method | Information                  |
    | :-------------------------------------------------------------- | :----: | :--------------------------- |
    | [/api/v1/product](http:localhost:4444/api/v1/product)           |  GET   | API for get all products     |
    | [/api/v1/product/{id}](http:localhost:4444/api/v1/product/{id}) |  GET   | API for get product by id    |
    | [/api/v1/product](http:localhost:4444/api/v1/product)           |  POST  | API for create new product   |
    | [/api/v1/product/{id}](http:localhost:4444/api/v1/product/{id}) |  PUT   | API for update product       |
    | [/api/v1/product/search](http:localhost:4444/api/v1/product)    |  GET   | API for get product by field |

- **User Controller** ( for **USER** authentication only )

  - | URL                                                       | Method | Information              |
    | :-------------------------------------------------------- | :----: | :----------------------- |
    | [/api/v1/user](http:localhost:4444/api/v1/user)           |  GET   | API for get self info    |
    | [/api/v1/user](http:localhost:4444/api/v1/user)           |  POST  | API for Reset Password   |
    | [/api/v1/user](http:localhost:4444/api/v1/user)           |  PUT   | API for update self info |
    | [/api/v1/user/logs](http:localhost:4444/api/v1/user/logs) |  GET   | API for get self logs    |

- **Admin Controller** ( for **ADMIN** authentication only )
  - | URL                                                                   | Method | Information                  |
    | :-------------------------------------------------------------------- | :----: | :--------------------------- |
    | [/api/v1/admin](http:localhost:4444/api/v1/admin)                     |  GET   | API for get all users        |
    | [/api/v1/admin/{id}](http:localhost:4444/api/v1/admin/{id})           |  GET   | API for get student by id    |
    | [/api/v1/admin](http:localhost:4444/api/v1/admin)                     |  POST  | API for create new user      |
    | [/api/v1/admin/{id}](http:localhost:4444/api/v1/admin/{id})           |  PUT   | API for update user          |
    | [/api/v1/admin/{id}](http:localhost:4444/api/v1/admin/{id})           | DELETE | API for delete user          |
    | [/api/v1/admin/logs/{id}](http:localhost:4444/api/v1/admin/logs/{id}) |  GET   | API for get user log         |
    | [/api/v1/admin/search](http:localhost:4444/api/v1/admin/search)       |  GET   | API for get user by username |

## Licence

All source code is licensed under the GNU General Public License v3. Please [see](https://www.gnu.org/licenses) the original document for more details.
