<img src='https://user-images.githubusercontent.com/19357410/91040270-e3acae00-e648-11ea-96f8-d2022d22c741.gif'>

# <center> :honey_pot: Honey-combo :bee: </center> 

![Github](https://img.shields.io/badge/vue-2.6.11-%234FC08D?style=plastic&logo=Vue.js)![Github](https://img.shields.io/badge/spring_boot-2.3.1-%236DB33F?style=plastic&logo=Spring)![Github](https://img.shields.io/badge/MySQL-8.0-%234479A1?style=plastic&logo=mysql)![Github](https://img.shields.io/badge/Redis-3.0-%23DC382D?style=plastic&logo=Redis)![Github](https://img.shields.io/badge/build-passing-brightgreen?style=plastic)



:earth_asia: Translation: [KR](https://github.com/YongjoonSeo/1st_pjt_301/blob/master/README.md) [US](https://github.com/YongjoonSeo/1st_pjt_301/blob/master/docs/README.md)



`Honey-combo` is a social networking service (SNS) to share food combinations for single households. Users can share and communicate with others by posting pictures of your own cooking as a feed.

1. Common Service
   - We provide `honey-combo` feed posting, comments, and likes services.
   - We provide following and follower services among users.
   - We recommend feeds by analyzing feed posting, feed likes, feed detailed view, and search data based on the user's gender and age.
2. Contest Service
   - The administrator holds a contest on one topic every week, and users participate anonymously.
   - Points are awarded to the 3 users who get the most 'like'.
   - We use charts to visualize the gender and age group of users who liked the contest feed.
3. Liquors Service
   - The admin posts a new liquor combination feed every week.

You can see our homepage [here](http://honeycombo.online)​ :slightly_smiling_face:





## 📌 Table of Contents

[:honey_pot: Honey-Combo :bee:](#-honey-combo-)  

   - [Getting Started](#-getting-started)
        - [Prerequisites](#prerequisites)
        - [Installing Honey-combo](#installing-honey-combo)
        - [Using Honey-combo](#using-Honey-combo)
        - [Deploying Honey-combo](#deploying Honey-combo)
- [Browser Support](#-browser-support)
- [Technical selection](#-technical selection)
- [Demos](#-demos)
- [Authors](#-authors)
- [license](#-license)
- [Acknowledgements](#-acknowledgements)



## :runner: Getting Started

You can start `honey-combo` following these steps:

### Prerequisites

Before you begin, ensure you have met the following requirements:

* You have a [Windows 10](https://www.microsoft.com/en-us/software-download/windows10) machine
* You have installed the version of [JDK 1.8](https://www.oracle.com/kr/java/technologies/javase/javase-jdk8-downloads.html), [MySQL 8](https://www.mysql.com/downloads/), [Node.js 12.8.1](https://nodejs.org/ko/download/)

### Installing Honey-combo

To install `honey-combo`, follow these steps:

1. Clone the repository

   ```
   $ git clone https://github.com/YongjoonSeo/1st_pjt_301.git
   ```

2. Install yarn packages

   ```
   $ yarn install
   ```

### Using Honey-combo

To use `honey-combo`, follow these steps:

1. Create Database

   - Create database by referring to [honey-combo.sql](https://github.com/YongjoonSeo/1st_pjt_301/blob/master/docs/honey-combo.sql) ([here](https://github.com/YongjoonSeo/1st_pjt_301/blob/master/docs/honeycombo_erd.png) is ERD)

   - Add database settings to the `application.yml`

     ```
     spring:
       datasource:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url : jdbc:mysql://localhost:3306/honeycombo?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
         username : {database user id}
         password : {database user password}
     ```

2. Run back-end

   - IDE import
     : When you use IDE, import `backend` and just run as Spring Boot App

   - Run war file

     ```
     $ gradlew -DskipTests=true build
     ```

     ```
     $ java -jar [filename].war
     ```

3. Run front-end 

   ```
   $ yarn serve
   ```

### Deploying Honey-combo

Before you start make sure you have `AWS EC2`. You can find out how to do that [here](https://victorydntmd.tistory.com/61).

To deploy `Honey-combo`, follow these steps:

1. Create an AWS EC2 Instance
2. JDK installation (path setting)
3. DB Installation (MySQL)
4. Version setting for gradle wrapper (6.0.0 or above)
5. Gradle clean build (create war file)
6. Yarn build (create dist folder)
7. Nginx (frontend and backend path setting)



## :globe_with_meridians: Browser Support

| <img src='https://user-images.githubusercontent.com/19357410/91040268-e27b8100-e648-11ea-9dfa-21123112fd23.png' width=60> | <img src='https://user-images.githubusercontent.com/19357410/91040279-e7403500-e648-11ea-8b38-07049ca300af.png' width=60> | <img src='https://user-images.githubusercontent.com/19357410/91040276-e6a79e80-e648-11ea-8c97-ddc1d35d761c.png' width=60> | <img src='https://user-images.githubusercontent.com/19357410/91040282-e7403500-e648-11ea-9f42-d8abd35e9b50.png' width=60> |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| latest                                                       | latest                                                       | latest                                                       | latest                                                       |



## :hammer_and_wrench: Built With

* Vue.js 2.6.11
* vue/cli 4.4.6
* yarn 1.22.4
* Spring boot 2.3.1
* Gradle 6.4.1
* IDE: Visual Studio Code 1.48, Spring Tool Suite 3



## :desktop_computer: Technical selection

<img src="https://user-images.githubusercontent.com/19357410/91040283-e7d8cb80-e648-11ea-8724-e20afcbefcc4.png" width=850>

#### Front-end technology

| Technology          | Description                                                  | Official website                                  |
| ------------------- | ------------------------------------------------------------ | ------------------------------------------------- |
| Vue                 | Front-end framework                                          | https://vuejs.org/                                |
| Vue-router          | Routing library                                              | https://router.vuejs.org/                         |
| Vuex                | Global State Management library                              | https://vuex.vuejs.org/                           |
| vuex-persistedstate | Persist and rehydrate your Vuex state between page reloads   | https://www.npmjs.com/package/vuex-persistedstate |
| Axios               | HTTP communication library                                   | https://github.com/axios/axios                    |
| Vuetify             | Vue UI library                                               | https://vuetifyjs.com/                            |
| vue-cookies         | A simple Vue.js plugin for handling browser cookies          | https://www.npmjs.com/package/vue-cookies         |
| vue-chartjs         | Wrapper for Chart.js in vue                                  | https://vue-chartjs.org/                          |
| vue-cropperjs       | A Vue wrapper component for [cropperjs](https://github.com/fengyuanchen/cropperjs) | https://www.npmjs.com/package/vue-cropperjs       |
| sweetalert          | A beautiful replacement for messages                         | https://sweetalert.js.org/guides/                 |
| vue-google-oauth2   | Handling Google sign-in and sign-out for Vue.js applications | https://www.npmjs.com/package/vue-google-oauth2   |

#### Back-end technology

| Technology      | Dscription                       | Official Website                           |
| --------------- | -------------------------------- | ------------------------------------------ |
| Spring Boot     | Container + MVC framework        | https://spring.io/projects/spring-boot     |
| Spring Security | Authentication and authorization | https://spring.io/projects/spring-security |
| Redis           | Distributed cache                | https://redis.io/                          |
| JWT             | JWT login support                | https://jwt.io/                            |
| MySQL           | RDBMS                            | https://www.mysql.com/                     |
| JPA             | ORM framework                    | https://spring.io/projects/spring-data-jpa |
| QueryDsl        | Java persistence query language  | http://www.querydsl.com/                   |
| Lombok          | Simplified object packaging tool | https://projectlombok.org/                 |
| OAuth           | Authentication and authorization | https://oauth.net/                         |
| Swagger-UI      | Document production tool         | https://github.com/swagger-api/swagger-ui  |

* Web application security is configured through user authentication and authorization using Spring security and JWT (JSON Web Token).
* The authentication tokens of logged in and logged out users are managed by storing in Redis, an in-memory data storage.
* We implemented Google social login through OAuth2 (Open Authorization), one of the authentication protocols.
* We use JPA (Java Persistence API) and Spring Data JPA for automatically managing Objects, tables, and records and generating basic queries.
* We use QueryDSL to create dynamic queries as code in a type-safe way rather than writing SQL satements as strings in XML file.
* We provide recommendation service by analyzing feed posting, feed likes, feed detailed view, and search log data based on the user's gender and age group.
* We deployed the service using AWS EC2 and Nginx.

#### Roadmap

* We are going to deploy using docker.



## :vhs: Demos

* You can see [here](http://honeycombo.online)
* [Video](https://github.com/YongjoonSeo/1st_pjt_301/blob/master/docs/HONEYCOMBO_UCC_LOW.mp4)
* Common Service
  * <img src="https://user-images.githubusercontent.com/19357410/91041095-62eeb180-e64a-11ea-9614-ec12760ad28b.gif" width=350>
  * <img src="https://user-images.githubusercontent.com/19357410/91041119-6bdf8300-e64a-11ea-9f3c-b98d2d5e032f.gif" width=350>
  * <img src="https://user-images.githubusercontent.com/19357410/91041081-5e29fd80-e64a-11ea-8f44-7f77596022ce.gif" width=350>
  * <img src="https://user-images.githubusercontent.com/19357410/91041105-65510b80-e64a-11ea-8ac7-5bede39d7493.gif" width=350>
* Contest Service
  * <img src="https://user-images.githubusercontent.com/19357410/91041112-68e49280-e64a-11ea-9c86-d76ac9142956.gif" width=350>
* Liquors Service
  * <img src="https://user-images.githubusercontent.com/19357410/91041106-66823880-e64a-11ea-8b99-3c3f60538de4.gif" width=350>



## 👤 Authors

* Sehoon Kim - @ttppggnnss - kimsae123@naver.com
* Soonbeen Kim - @soo-ni - ksb940925@gmail.com
* Seungmin Ryu - @dkqyqyt - dkqyqytt@gmail.com
* Sunsoo Lee - @LEESUNSOO - tjstn921030@gmail.com
* Yongjoon Seo - @YongjoonSeo - koreakkrea12@naver.com



## :page_with_curl: License

This project uses the following license: [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Copyright (c) 2015 Juns Alen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



## :books: Acknowledgements

* https://github.com/scottydocs/README-template.md

* https://github.com/naver/egjs-flicking
