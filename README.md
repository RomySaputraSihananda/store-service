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
  - http://127.0.0.1:4444/docs
- **Auth Controller** ( no need authentication )
  - **POST** API for Signin user.
    - http://127.0.0.1:4444/api/v1/auth/signin
  - **POST** API for Signup new user.
    - http://127.0.0.1:4444/api/v1/auth/signup
- **Product Controller** ( for **USER** authentication only )
  - **GET** API for get all products.
    - http://127.0.0.1:4444/api/v1/product
  - **GET** API for get product by id.
    - http://127.0.0.1:4444/api/v1/product/{id}
  - **POST** API for create new product.
    - http://127.0.0.1:4444/api/v1/product
  - **PUT** API for update product.
    - http://127.0.0.1:4444/api/v1/product/{id}
  - **GET** API for get product by name.
    - http://127.0.0.1:4444/api/v1/product/search
- **User Controller** ( for **USER** authentication only )
  - **GET** API for get info self.
    - http://127.0.0.1:4444/api/v1/user
  - **POST** API for Reset Password.
    - http://127.0.0.1:4444/api/v1/user
  - **PUT** API for update info self.
    - http://127.0.0.1:4444/api/v1/user
  - **GET** API for get Log self.
    - http://127.0.0.1:4444/api/v1/user/logs
- **Admin Controller** ( for **ADMIN** authentication only )
  - **GET** API for get all users.
    - http://127.0.0.1:4444/api/v1/admin
  - **GET** API for get student by id.
    - http://127.0.0.1:4444/api/v1/admin/{id}
  - **POST** API for create new user.
    - http://127.0.0.1:4444/api/v1/admin
  - **PUT** API for update user.
    - http://127.0.0.1:4444/api/v1/admin/{id}
  - **DELETE** API for delete user.
    - http://127.0.0.1:4444/api/v1/admin/{id}
  - **GET** API for get Log user.
    - http://127.0.0.1:4444/api/v1/admin/logs/{id}
  - **GET** API for get user by name.
    - http://127.0.0.1:4444/api/v1/admin/search

## Licence

All source code is licensed under the GNU General Public License v3. Please [see](https://www.gnu.org/licenses) the original document for more details.
