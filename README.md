# :honey_pot: Honey-Combo :bee:

 ![Github](https://img.shields.io/badge/vue-2.6.11-%234FC08D?style=plastic&logo=Vue.js)![Github](https://img.shields.io/badge/spring_boot-2.3.1-%236DB33F?style=plastic&logo=Spring)![Github](https://img.shields.io/badge/MySQL-8.0-%234479A1?style=plastic&logo=mysql)![Github](https://img.shields.io/badge/Redis-3.0-%23DC382D?style=plastic&logo=Redis)![Github](https://img.shields.io/badge/build-passing-brightgreen?style=plastic)



Honey-Combo` 는 1인 가구를 위한 음식 조합을 공유하기 위한 소셜 네트워크 서비스(Social Networking Service, SNS) 입니다. 사용자가 직접 요리한 사진을 피드로 게시해 여러 사용자들과 공유하며 소통할 수 있습니다.

1. Common Service
   - 꿀조합 피드 게시, 댓글, 좋아요 서비스를 제공합니다.
   - 유저들 간 팔로우 및 팔로잉 서비스를 제공합니다.
   - 유저의 성별과 나이대를 기반으로 피드 게시, 피드 좋아요, 피드 상세 조회, 검색 데이터를 분석해 피드를 추천합니다.
2. Contest Service
   - 관리자가 매주 하나의 주제로 콘테스트를 열어 유저들이 익명으로 참여합니다.
   - 좋아요를 가장 많이 받은 3개의 꿀조합을 게시한 유저에게 포인트를 부여합니다. 
   - 차트를 이용해 콘테스트 피드의 좋아요를 누른 유저의 성별과 나이대 데이터를 시각화합니다. 
3. Liquors Service
   - 관리자가 매주 새로운 주류 조합을 게시합니다.

[<img src="/uploads/678293e8c7eb2f43bc67389391d19739/crop.gif" width=200>](http://honeycombo.online)를 클릭해 사이트를 확인하세요 :slightly_smiling_face:

:earth_asia: 번역: [KR](https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108/blob/master/README.md) [US](https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108/tree/master/docs/README.md)



## 📌 목차

[:honey_pot: Honey-Combo :bee:](#-honey-combo-)  

   - [시작하기](#-시작하기)
     - [시작하기에 앞서](#시작하기에-앞서)
     - [설치하기](#설치하기)
     - [실행하기](#실행하기)
     - [배포하기](#배포하기)
     - [데모](#데모)
- [지원하는 브라우저](#-지원하는-브라우저)
- [사용된 도구](#-사용된-도구)
- [사용된 기술](#-사용된-기술)
- [저자](#-저자)
- [라이센스](#-라이센스)
- [참고](#-참고)



## :runner: 시작하기

아래 방법을 따르시면 프로젝트를 실행시킬 수 있습니다.

### 시작하기에 앞서

* [Windows 10](https://www.microsoft.com/en-us/software-download/windows10)
* [JDK 1.8](https://www.oracle.com/kr/java/technologies/javase/javase-jdk8-downloads.html)
* [Node.js 12.8.1](https://nodejs.org/ko/download/)
* [MySQL 8.0](https://www.mysql.com/downloads/)

### 설치하기

1. 깃헙의 레포지토리를 클론합니다.

   ```
   $ git clone https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108.git
   ```

2. yarn을 설치합니다.

   ```
   $ yarn install
   ```

### 실행하기

`Honey-combo` 서비스를 사용하기 위해서는 다음과 같은 방법으로 실행합니다:

1. 데이터베이스를 설정합니다.

   - [honey-combo.sql](https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108/tree/master/docs/honey-combo.sql)을 참고해서 데이터베이스를 생성합니다. ([여기](https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108/tree/master/docs/honeycombo_erd.png)를 눌러 erd를 확인하세요.)

   - `application.yml`에 데이터베이스 설정을 추가합니다.

     ```
     spring:
       datasource:
         driver-class-name: com.mysql.cj.jdbc.Driver
         url : jdbc:mysql://localhost:3306/honeycombo?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
         username : {데이터베이스 계정 아이디}
         password : {데이터베이스 계정 비밀번호}
     ```

2. 백엔드 서버를 실행합니다.

   - IDE에 import 후 실행합니다.

     : IntelliJ, STS와 같은 IDE를 사용하는 경우, `backend`를 import하여 실행합니다.

   - war파일을 생성 후 실행합니다.

     ```
     $ gradlew -DskipTests=true build
     ```

     ```
     $ java -jar [filename].war
     ```

3. 프론트엔드를 실행합니다.

   ```
   $ yarn serve
   ```

### 배포하기

해당 서비스는 `AWS EC2`를 이용하여 배포하였습니다. 사전에 [여기](https://victorydntmd.tistory.com/61)를 참고해서 `AWS EC2`계정을 생성하세요.

배포를 하기위해서는 다음과 같은 방법으로 실행합니다:

1. AWS EC2 인스턴스 생성
2. JDK 설치 (환경변수 설정)
3. DB 설치 (해당 프로젝트에서 MySQL 사용)
4. gradle wrapper을 위한 버전 설정 (6.0.0 이상)
5. gradle clean build 실행 (war 파일 생성)
6. yarn build (dist 폴더 생성)
7. Nginx (front-end, back-end 경로 설정)

### 데모

[여기](http://honeycombo.onle)를 클릭하세요.



## :globe_with_meridians: 지원하는 브라우저

| <img src='/uploads/36874cdc0e0e6d724b92241664c99b9f/chrome_logo.png' width=60> | <img src='/uploads/74b4e657301cecd856ac88327e369ad9/Mozilla_Firefox_logo_2013.png' width=60> | <img src='/uploads/f1be4a8b751857b900b8a70a36c54c3d/micro-edge.png' width=60> | <img src='/uploads/74b4e657301cecd856ac88327e369ad9/Mozilla_Firefox_logo_2013.png' width=60> |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                            latest                            |                            latest                            |                            latest                            |                            latest                            |



## :hammer_and_wrench: ​사용된 도구

* Vue.js 2.6.11
* vue/cli 4.4.6
* yarn 1.22.4
* Spring boot 2.3.1
* Gradle 6.4.1
* IDE: Visual Studio Code 1.48, Spring Tool Suite 3



## :desktop_computer: ​사용된 기술

<img src="/uploads/1998b9a000c7faf7cf1e64ce382c4baf/stack.png" width=850>

#### 프론트엔드

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

#### 백엔드

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

* Spring security와 JWT(JSON Web Token)를 이용해 사용자 인증 및 인가 과정을 거쳐 웹 어플리케이션의 보안을 구성하였습니다.
* 로그인한 사용자의 인증 토큰을 인-메모리 데이터 저장소인 Redis에 저장하여 로그인 및 로그아웃한 사용자를 관리하였습니다.
* 인증 프로토콜 중 하나인 OAuth2(Open Authorization) 를 통해 구글 소셜 로그인을 구현하였습니다.
* JPA(Java Persistence API) + Spring Data JPA 를 사용하여 객체들과 테이블, 레코드를 자동으로 관리하며 기본적인 쿼리를 자동으로 생성합니다.
* QueryDSL을 이용해 타입에 안전한 방식으로 동적 쿼리를 생성해 SQL문을 문자열로 작성하거나 XML파일에 작성하지 않고  코드로 작성합니다.
* 사용자의 성별과 연령대를 기반으로 피드 게시, 피드 좋아요, 피드 상세 조회, 검색 로그 데이터를 분석해 추천 서비스를 제공합니다.
* AWS EC2와 Nginx를 이용하여 서비스를 배포하였습니다.

#### 추후 적용

* 추가로 docker를 사용하여 배포할 예정입니다.



## :vhs: 시연 영상

* [시연 영상]()
* 로그인, 피드, 댓글, 좋아요, 팔로우, 알림 서비스
  * <img src="/uploads/1dcd54a17522cbd3fd09a4abc6862e56/로그인.gif" width=350>
  * <img src="/uploads/3cc8f6f82f85117aba2fec74745b0822/피드작성.gif" width=350>
  * <img src="/uploads/6e3a18b408e58cdbc6aaa5d236a8972e/댓글.gif" width=350>
  * <img src="/uploads/9a7a7d5256d1cc83b1868e938fdc524b/알림.gif" width=350>
* 콘테스트 서비스
  * <img src="/uploads/4da0d5f3562de88d3e2c87f7f50ab264/콘테스트.gif" width=350>
* 주류 서비스
  * <img src="/uploads/0379ea40cb5b2cc9ccf4808f02912b6b/주류.gif" width=350>



## 👤 저자

* 김세훈 - Sehoon Kim - kimsae123@naver.com
* 김순빈 - Soonbeen Kim - ksb940925@gmail.com
* 류승민 - Seungmin Ryu - dkqyqytt@gmail.com
* 이선수 - Sunsoo Lee - tjstn921030@gmail.com
* 서용준 - Yongjoon Seo - koreakkrea12@naver.com



## :page_with_curl: 라이센스

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



## :books: 참고

* https://gist.github.com/taeukme/e004e01963190615d308a16bcd6e6040

* https://github.com/naver/egjs-flicking