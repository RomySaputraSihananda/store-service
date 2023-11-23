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

- **Swagger Documentation**
  - http://127.0.0.1:4444/docs
- **Auth Controller**
  - **POST** API for Signin user.
    - http://127.0.0.1:4444/api/v1/auth/sognin
  - **POST** API for Signup new user.
    - http://127.0.0.1:4444/api/v1/auth/sognup
- **Product Controller**
  - **GET** API for get all surat.
    - http://127.0.0.1:4444/api/v1/product
- **User Controller**
  - **GET** API for get all surat.
    - http://127.0.0.1:4444/api/v1/product

## Licence

All source code is licensed under the GNU General Public License v3. Please [see](https://www.gnu.org/licenses) the original document for more details.
