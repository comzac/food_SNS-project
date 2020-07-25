export default {
  BASE_URL: "http://localhost:8080/",
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
    },
    feeds: {
      URL: "feeds/",
      page: "page/",
      search: "search",
    },
  },
};
