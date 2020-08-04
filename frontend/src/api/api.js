export default {
  BASE_URL: "http://localhost:8080/",
  // BASE_URL: "http://172.30.1.33:8080/",
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
      logout: "logout",
      simple: "simple",
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
  },
};
