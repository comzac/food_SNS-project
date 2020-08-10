export default {
  BASE_URL: "http://localhost:8080/",
  // BASE_URL: "http://172.30.1.16:8080/",
  // BASE_URL: "http://i3a108.p.ssafy.io:8080/",
  MEDIA_DIR: "http://localhost/media/",
  // MEDIA_DIR: "http://i3a108.p.ssafy.io/media/",
  ROUTES: {
    accounts: {
      URL: "users/",
      login: "login",
      signup: "join",
      idCheck: "uid-dup/",
      nickCheck: "unick-dup/",
      emailCheck: "e-dup/",
      getConfirmCode: "e-valid",
      pwcheck: "pw-dup",
      pwreset: "pw-reset",
      getUserId: "uid-find",
      logout: "logout",
      simple: "simple",
      google: "google",
    },
    feeds: {
      URL: "feeds/",
      page: "page/",
      pagination: "pagination/",
      search: "search",
    },
    comments: {
      URL: "comments/",
    },
    files: {
      URL: "files/",
      upload: "upload/multipleFiles",
    },
    likes: {
      feed: "likes/feed/",
      comment: "likes/comment/",
    },
    relations: {
      URL: "relations/",
    },
    notifications: {
      URL: "notifications/",
      getAll: "all/",
    },
  },
};
