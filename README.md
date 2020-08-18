# :honey_pot: Honey-Combo :honeybee:

![GitHub](https://img.shields.io/github/license/mashape/apistatus?style=plastic) 

1인 가구를 위한 음식 꿀조합 공유 SNS



## Content

[TOC]



## Intro

20대 및 30대 자취생을 위한 음식 꿀조합 공유 SNS





## Project documentation

* https://lab.ssafy.com/s03-webmobile2-sub3/s03p13a108
* http://honeycombo.online:8080/swagger-ui.html





## Project introduction

"Honey-combo"는 1인 가구의 식사 해결을 위해 유저들 간의 음식 조합을 공유하기 위한 SNS입니다. 

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



### :floppy_disk:Demos

Check our [Demos](http://honeycombo.online)



### :floppy_disk:Environment setup

Make sure you have installed all of the following prerequisites on your development machine:

#### Common development tools & environment

* OS: Windows 10
* Java 8
* AWS EC2

#### Front-end development tools & environment

* IDE: Visual Studio Code

#### Back-end development tools & environment

* IDE: Spring Tool Suite 3
* Gradle 6



### :floppy_disk:Stack

![stack](/uploads/1998b9a000c7faf7cf1e64ce382c4baf/stack.png)



### :floppy_disk:Technical selection

#### Front-end technology

| Technology  | Description                     | Official website               |
| ----------- | ------------------------------- | ------------------------------ |
| Vue         | Front-end framework             | https://vuejs.org/             |
| Vue-router  | Routing library                 | https://router.vuejs.org/      |
| Vuex        | Global State Management library | https://vuex.vuejs.org/        |
| Axios       | HTTP communication library      | https://github.com/axios/axios |
| Vuetify     | Vue UI library                  | https://vuetifyjs.com/         |
| vue-chartjs | Wrapper for Chart.js in vue     | https://vue-chartjs.org/       |

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



### :floppy_disk:ERD

![ERD](/uploads/4a3553df95063dedcb2f70de36914421/ERD.png)



### :floppy_disk:Build steps

#### Front-end 

##### Project setup

```
$ yarn install
```

##### Compiles and hot-reloads for development

```
$ yarn serve
```

##### Compiles and minifies for production

```
$ yarn build
```

##### Lints and fixes files

```
$ yarn lint
```



#### Back-end

##### build.gradle

```groovy
plugins {
   id 'org.springframework.boot' version '2.3.1.RELEASE'
   id 'io.spring.dependency-management' version '1.0.9.RELEASE'
   id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
   id 'java'
   id 'war'
}

group = 'com.ssafy'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
   mavenCentral()
}

dependencies {
   implementation 'org.springframework.boot:spring-boot-starter-web'
   implementation 'org.springframework.boot:spring-boot-starter-web-services'
   //compile('org.springframework.session:spring-session')
   implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3'
   developmentOnly 'org.springframework.boot:spring-boot-devtools'
   runtimeOnly 'mysql:mysql-connector-java'
   providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
   testImplementation('org.springframework.boot:spring-boot-starter-test') {
      exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
   }

   // for jsp
   implementation 'javax.servlet:jstl'
   implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
   
   // for aop
   compile 'org.springframework.boot:spring-boot-starter-aop'
      
   //Swagger2
   compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'
   compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'
   
   // mail
   implementation 'org.springframework.boot:spring-boot-starter-mail'
   
   // jpa
   implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
   
   // query dsl 
   compile("com.querydsl:querydsl-apt") 
   compile("com.querydsl:querydsl-jpa")
   
   // spring security
   implementation 'org.springframework.boot:spring-boot-starter-security'
   testImplementation('org.springframework.boot:spring-boot-starter-test') {
       exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
   }
   testImplementation 'org.springframework.security:spring-security-test'
   
   // jwt
   implementation 'io.jsonwebtoken:jjwt:0.9.1'
   
   // lombok
   compileOnly 'org.projectlombok:lombok'
   annotationProcessor 'org.projectlombok:lombok'
   
   // Redis
   implementation 'org.springframework.boot:spring-boot-starter-data-redis'
   implementation 'it.ozimov:embedded-redis:0.7.2'
   // https://mvnrepository.com/artifact/com.github.kstyrc/embedded-redis
   testCompile group: 'com.github.kstyrc', name: 'embedded-redis', version: '0.6'
   // https://mvnrepository.com/artifact/redis.clients/jedis
   compile group: 'redis.clients', name: 'jedis', version: '3.3.0'
   // social
   
	// 1. oauth
	implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
	// 2. thymeleaf
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	// 3. h2database
	runtimeOnly 'com.h2database:h2'
	// security, jpa, web, lombok 등 중복 // 


	// 구글 소셜
	// https://mvnrepository.com/artifact/com.google.auth/google-auth-library-oauth2-http
	compile group: 'com.google.auth', name: 'google-auth-library-oauth2-http', version: '0.21.1'
	// https://mvnrepository.com/artifact/com.google.auth/google-auth-library-credentials
	compile group: 'com.google.auth', name: 'google-auth-library-credentials', version: '0.21.1'
	// https://mvnrepository.com/artifact/com.google.api-client/google-api-client
	compile group: 'com.google.api-client', name: 'google-api-client', version: '1.30.10'
	// https://mvnrepository.com/artifact/com.google.api-client/google-api-client-jackson2
	compile group: 'com.google.api-client', name: 'google-api-client-jackson2', version: '1.27.0'
	// https://mvnrepository.com/artifact/com.google.http-client/google-http-client-jackson2
	compile group: 'com.google.http-client', name: 'google-http-client-jackson2', version: '1.35.0'

}

// querydsl 적용 
apply plugin: "com.ewerk.gradle.plugins.querydsl" // Plugin 적용 
def querydslSrcDir = 'src/main/generated'

querydsl {
	 library = "com.querydsl:querydsl-apt" 
	 jpa = true 
	 querydslSourcesDir = querydslSrcDir 
}

compileQuerydsl{
	 options.annotationProcessorPath = configurations.querydsl 
} 

configurations {
	querydsl.extendsFrom compileClasspath 
} 
  
sourceSets {
   main {
	    java{ 
	    	srcDirs = ['src/main/java', querydslSrcDir] 
	    	} 
    	} 
}

tasks.withType(JavaCompile) { 
	options.annotationProcessorGeneratedSourcesDirectory = file(querydslSrcDir) 
}

test {
   useJUnitPlatform()
}
```

##### approperties.yml

 ```
custom: 
  oauth2: 
    kakao: 
      client-id: -
      client-secret: -
      scope: profile, account_email, gender, age_range, birthday
    naver: 
      client-id: - 
      client-secret: -
server:
  port: 8080
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url : jdbc:mysql://localhost:3306/dbtest?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
    username : -
    password : -
  mail:
    host : smtp.gmail.com
    port : 587
    username : -
    password : -
    properties:
      mail:
        smtp:
          auth : true
          timeout : 5000
          starttls:
            enable : true    
  jpa:
    properties:
      hibernate: 
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
    hibernate:
      ddl-auto: update
  servlet:
    multipart:
      enabled: true
      file-size-threshold: 2KB
      max-file-size: 200MB
      max-request-size: 215MB
  redis:
    host: localhost
    port: 6379
    database: 0
  h2:
    console:
      enabled: true
      path: /console
  thymeleaf: 
    cache: false 
  security: 
    oauth2: 
      client: 
        registration: 
          google: 
            client-id: -
            client-secret: -
            scope : email, profile
  file:
    location: - #파일이 저장될 폴더 위치
  
logging:
  level:
    root: info
  file:
    name: ./log/local #파일이 저장될 폴더 위치
  recommand:
    root: ./log/ #파일이 저장될 폴더 위치

 ```

##### Project build

```
$ gradle build
```

##### Project run

```
// 해당 파일이 있는 위치
$ java -jar demo-0.0.1-SNAPSHOT.war
```





## License

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





## Contact​ :mailbox_with_mail:

* 김세훈 | Sehoon Kim | kimsae123@naver.com
* 김순빈 | Soonbeen Kim | ksb940925@gmail.com
* 류승민 | Seungmin Ryu | dkqyqytt@gmail.com
* 이선수 | Sunsoo Lee | tjstn921030@gmail.com
* 서용준 | Yongjoon Seo | koreakkrea12@naver.com

