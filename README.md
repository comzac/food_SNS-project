# Sub PJT 3 (20.08.03-20.08.21)

## 9조 둘! 셋! (그래도 좋은 날이 더 많기를)



### Git

1. Commit 규칙 정하기
   * https://djkeh.github.io/articles/How-to-write-a-git-commit-message-kor/
   * https://hyeran-story.tistory.com/99
   * **Modify show following list | S03P12A108-46**
2. Git Flow 바탕 branch naming
   * https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html
   * develop branch를 만들고, 이를 front와 back branch로 나눈다.
   * 기본적으로 front, back branch에서 각각 기능별로 개발하고, 이 branch들로 merge request를 보낸다.
   * **feature/알아서**
   * 안쓰는 기능이면 팀장한테 얘기



### DB

1. ERD

   * USER (사용자)
     * **id** (AI)(pk)
     * uid (unique)
     * upw
     * unick (unique)
     * uemail
     * uregdate
     * ubirth
     * usex
     * roles
       * ROLE_ADMIN
       * ROLE_USER
   * BLACKLIST (블랙리스트)
     * **user.id** (fk)(pk)
     * expiredate 만료일
   * FEED (피드)
     * **id** (AI)(pk)
     * user.id 
     * title
     * content
     * <u>media</u>
     * regdate
     * editdate
   * COMMENT (댓글)
     * **id**
     * user.id
     * feed.id
     * content
     * regdate
     * editdate
   * RELATIONSHIP (팔로우/차단관리)
     * **user.id**
     * **user.id**
     * state
       * 0: follow
       * 1: block
   * FEEDLIKE (피드 좋아요)
     * **user.id**
     * **feed.id**
   * COMMENTLIKE (코멘트 좋아요)
     * **user.id**
     * **comment.id**
   * HASHTAG (#)
     * **id** (AI)
     * content (unique)
   * FEED-HASHTAG
     * **feed.id**
     * **hashtag.id**



### API

- 스프레드 시트를 활용하여, 기능별로 구현하기 전에 요청 및 응답 형태를 정한다.
- 백엔드 코딩과 동시에 swagger를 통해 문서화한다.



