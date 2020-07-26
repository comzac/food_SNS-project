import cookies from "vue-cookies";
import axios from "axios";

import SERVER from "@/api/api";
import router from "@/router";

export default {
  namespaced: true,
  state: {
    authToken: cookies.get("auth-token"),
    userData: {},
    // signupId: null,
    // signupNick: null,
    // idChecked: false,
    // nickChecked: false,
  },
  getters: {
    isLoggedIn: (state) => !!state.authToken,
    config: (state) => ({ headers: { "X-AUTH-TOKEN": state.authToken } }),
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token;
      cookies.set("auth-token", token);
    },
    SET_USERDATA(state, userData) {
      state.userData = userData;
    },
    // SET_IDCHECK(state, check) {
    //   state.idChecked = check;
    // },
    // SET_SIGNUPID(state, id) {
    //   state.signupId = id;
    // },
    // SET_NICKCHECK(state, check) {
    //   state.nickChecked = check;
    // },
    // SET_SIGNUPNICK(state, nick) {
    //   state.signupNick = nick;
    // },
  },
  actions: {
    postAuthData({ commit }, info) {
      // console.log(info.data);
      // console.log(SERVER.URL + info.route);
      // console.log(SERVER.ROUTES.accounts.URL);
      axios
        .post(SERVER.BASE_URL + info.route, info.data, {
          headers: {
            "content-type": "application/json",
          },
        })
        .then((res) => {
          console.log(res);
          const data = res.data.data;
          commit("SET_TOKEN", data);
          delete data.token;
          commit("SET_USERDATA", data);
          router.push({ name: "Home" });
        })
        .catch((err) => {
          alert("로그인 정보를 확인해주세요.");
          console.log(err);
        });
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        route: SERVER.ROUTES.accounts.URL + SERVER.ROUTES.accounts.login,
      };
      dispatch("postAuthData", info);
    },

    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        route: SERVER.ROUTES.accounts.URL + SERVER.ROUTES.accounts.signup,
      };
      dispatch("postAuthData", info);
    },

    logout({ commit }) {
      commit("SET_TOKEN", null);
      commit("SET_USERDATA", {});
      cookies.remove("auth-token");
      router.replace({ name: "Login" });
    },

    idCheck({ commit }, uid) {
      if (uid === "") {
        alert("아이디를 입력하세요");
        commit("SET_IDCHECK", false);
        return false;
      }
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.idCheck +
            uid
        )
        .then((res) => {
          // console.log(res.data);
          if (res.data === "success") {
            alert("사용 가능한 아이디입니다.");
            // commit("SET_IDCHECK", true);
            // commit("SET_SIGNUPID", uid);
            return true;
          } else {
            alert("이미 사용 중인 아이디입니다.");
            // commit("SET_IDCHECK", false);
            return false;
          }
        })
        .catch((err) => console.log(err.response));
    },

    nickCheck(context, unick) {
      if (unick === "") {
        alert("별명을 입력하세요");
        // commit("SET_IDCHECK", false);
        return false;
      }
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.nickCheck +
            unick
        )
        .then((res) => {
          // console.log(res.data);
          if (res.data === "success") {
            alert("사용 가능한 별명입니다.");
            // commit("SET_NICKCHECK", true);
            // commit("SET_SIGNUPNICK", unick);
            return true;
          } else {
            alert("이미 사용 중인 별명입니다.");
            // commit("SET_NICKCHECK", false);
            return false;
          }
        })
        .catch((err) => console.log(err.response));
    },

    emailCheck(context, email) {
      return axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.emailCheck,
          {
            userEmail: email,
          }
        )
        .then((res) => {
          if (res.data === "success") {
            return true;
          } else {
            return false;
          }
        })
        .catch((err) => console.log(err));
    },

    getConfirmCode(context, email) {
      return axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.getConfirmCode,
          {
            userEmail: email,
          }
        )
        .then((res) => {
          console.log(res);
          return res;
        })
        .then((confirmCode) => {
          if (confirmCode === "fail") {
            alert("이메일을 확인해주세요.");
            return "";
          } else {
            return confirmCode;
          }
        })
        .catch((err) => console.log(err.response));
    },

    pwcheck({ state, rootGetters }, password) {
      const config = rootGetters["accounts/config"];
      const requestData = {
        uid: state.userData.uid,
        upw: password,
      };
      return axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwcheck,
          requestData,
          config
        )
        .then((res) => {
          if (res.data === "success") {
            return true;
          } else {
            return false;
          }
        })
        .catch((err) => console.log(err));
    },

    pwreset(context, userEmailData) {
      axios
        .put(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwreset,
          userEmailData
        )
        .then((res) => {
          // console.log(res);
          if (res.data === "success") {
            router.push({ name: "Home" });
          } else {
            alert("변경 실패");
          }
        })
        .catch((err) => console.log(err.response));
    },
  },
};
