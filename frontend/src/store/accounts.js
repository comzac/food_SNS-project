import cookies from "vue-cookies";
import axios from "axios";

import SERVER from "@/api/api";
import router from "@/router";

export default {
  namespaced: true,
  state: {
    authToken: cookies.get("auth-token"),
    page: cookies.get("page") ? cookies.get("page") : 1,
    confirmCode2: "",
    userSimpleData: {},
    signupData: {},
    uemail: "",
    userFollows: null,
    foundUid: null,
    // confirmCode: cookies.get("confirm-code"),
    // signupId: null,
    // signupNick: null,
    // idChecked: false,
    // nickChecked: false,
  },
  getters: {
    isLoggedIn: (state) => !!state.authToken,
    config: (state) => ({ headers: { "X-AUTH-TOKEN": state.authToken } }),
    authUserImgData: (state) => {
      if (state.userSimpleData.uprofile) {
        if (state.userSimpleData.uprofile.data) {
          return state.userSimpleData.uprofile.data;
        } else return false;
      } else return false;
    },
    authUserImgType: (state) => {
      if (state.userSimpleData.uprofile) {
        if (state.userSimpleData.uprofile.type) {
          return state.userSimpleData.uprofile.type;
        } else return false;
      } else return false;
    },
    authUserImgName: (state) => {
      if (state.userSimpleData.uprofile) {
        if (state.userSimpleData.uprofile.name) {
          return state.userSimpleData.uprofile.name;
        } else return false;
      } else return false;
    },
    authUserProfileText: (state) => {
      if (state.userSimpleData.uprofile) {
        if (state.userSimpleData.uprofile.text) {
          return state.userSimpleData.uprofile.text;
        } else return "";
      } else return "";
    },
    authUserUid: (state) => state.userSimpleData.uid,
    authUserUnick: (state) => state.userSimpleData.unick,
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token;
      cookies.set("auth-token", token);
    },
    SET_USERSIMPLEDATA(state, userSimpleData) {
      state.userSimpleData = userSimpleData;
    },
    SET_UEMAIL(state, email) {
      state.uemail = email;
    },
    SET_PAGE(state, page) {
      state.page = page;
      cookies.set("page", page);
    },
    SET_CODE(state, code) {
      state.confirmCode2 = code;
    },
    SET_SIGNUPDATA(state, signupData) {
      state.signupData = signupData;
    },
    SET_USERFOLLOWS(state, followData) {
      state.userFollows = followData;
    },
    SET_FOUNDUID(state, uid) {
      state.foundUid = uid;
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
    setEmail({ commit }, email) {
      commit("SET_UEMAIL", email);
    },
    setPage({ commit }, page) {
      commit("SET_PAGE", page);
    },
    setCode({ commit }, code) {
      commit("SET_CODE", code);
    },
    // state signupData 변경
    setSignupData2({ commit }, signupData) {
      commit("SET_SIGNUPDATA", signupData);
    },
    postAuthData({ commit }, info) {
      axios
        .post(SERVER.BASE_URL + info.route, info.data, {
          headers: {
            "content-type": "application/json",
          },
        })
        .then((res) => {
          console.log(res);
          commit("SET_TOKEN", res.headers.token);
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

    logout({ commit, getters }) {
      console.log(getters.config);
      axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.logout,
          getters.config
        )
        .then((res) => {
          console.log(res);
        })
        .catch((err) => console.log(err.response));

      commit("SET_TOKEN", null);
      commit("SET_USERSIMPLEDATA", {});
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

    getConfirmCode({ commit }, email) {
      return axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.getConfirmCode,
          {
            userEmail: email,
          }
        )
        .then((confirmCode) => {
          if (confirmCode === "fail") {
            alert("이메일을 확인해주세요.");
            return "";
          } else {
            commit("SET_CODE", confirmCode.data);
            return confirmCode;
          }
        })
        .catch((err) => {
          return err.response;
        });
    },

    pwcheck({ getters }, password) {
      return axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwcheck,
          { upw: password },
          getters.config
        )
        .then((res) => {
          if (res.data === "success") {
            return true;
          } else {
            return false;
          }
        })
        .catch((err) => console.log(err.response));
    },

    pwreset(context, userEmailData) {
      console.log(userEmailData);
      axios
        .put(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwreset,
          userEmailData
        )
        .then((res) => {
          console.log("res : ", res);
          if (res.data === "success") {
            alert("비밀번호가 변경되었습니다.");
            router.push({ name: "Home" });
          } else {
            alert("변경 실패");
          }
        })
        .catch((err) => console.log(err.response));
    },

    getUserId({ state }) {
      const data = new FormData();
      data.append("uemail", state.uemail);
      return axios.post(
        SERVER.BASE_URL +
          SERVER.ROUTES.accounts.URL +
          SERVER.ROUTES.accounts.getUserId,
        { uemail: state.uemail },
        null
      );
    },

    getUserSimpleData({ state, commit, getters }) {
      if (
        Object.keys(state.userSimpleData).length === 0 &&
        getters.isLoggedIn
      ) {
        axios
          .get(
            SERVER.BASE_URL +
              SERVER.ROUTES.accounts.URL +
              SERVER.ROUTES.accounts.simple,
            getters.config
          )
          .then((res) => {
            commit("SET_USERSIMPLEDATA", res.data.data);
          })
          .catch((err) => console.log(err));
      }
    },
    deleteUser({ getters, commit, state }) {
      const doubleCheck = confirm(
        "정말 탈퇴하시겠습니까?\n이 작업은 취소 할 수 없습니다."
      );

      if (doubleCheck) {
        const uid = state.userSimpleData.uid;
        axios
          .delete(
            SERVER.BASE_URL + SERVER.ROUTES.accounts.URL + uid,
            getters.config
          )
          .then(() => {
            commit("SET_TOKEN", null);
            commit("SET_USERSIMPLEDATA", {});
            cookies.remove("auth-token");
            router.replace({ name: "Login" });
            alert("회원 탈퇴가 완료되었습니다.");
          })
          .catch((err) => console.log(err));
      }
    },
    sendFollow({ getters }, uid) {
      const data = new FormData();
      data.append("uid", uid);
      axios
        .post(
          SERVER.BASE_URL + SERVER.ROUTES.relations.URL,
          data,
          getters.config
        )
        .then((res) => console.log(res))
        .catch((err) => console.log(err.response));
    },
    getUserFollowList({ commit, getters }, option) {
      const urlSuffix = `${option.mode}/${option.uid}`;
      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.relations.URL + urlSuffix,
          getters.config
        )
        .then((res) => {
          commit("SET_USERFOLLOWS", res.data.data);
        })
        .catch((err) => console.log(err.response));
    },
  },
};
