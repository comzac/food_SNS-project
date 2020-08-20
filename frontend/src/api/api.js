export default {
  BASE_URL: "http://i3a108.p.ssafy.io:8080/",
  MEDIA_DIR: "http://i3a108.p.ssafy.io/media/",
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
      page: "page/",
    },
    feeds: {
      URL: "feeds/",
      page: "pages/",
      pagination: "pagination/",
      followerPagination: "pages/follower/",
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
    contest: {
      URL: "contests/",
      list: "all/",
      feeds: "feeds/",
      likes: "likes/",
      files: "feeds/files",
    },
  },
};
