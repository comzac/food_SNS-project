import cookies from "vue-cookies";
import axios from "axios";

import SERVER from "@/api/api";
import router from "@/router";

import swal from "sweetalert";

export default {
  namespaced: true,
  state: {
    authToken: cookies.get("auth-token"),
    page: cookies.get("page") ? cookies.get("page") : 1,
    confirmCode2: "",
    userSimpleData: {},
    signupData: {},
    uemail: "",
    uid: "",
    userFollows: null,
    foundUid: null,
    isAuthorized: false,
    // confirmCode: cookies.get("confirm-code"),
    // signupId: null,
    // signupNick: null,
    // idChecked: false,
    // nickChecked: false,
  },
  getters: {
    isLoggedIn: (state) => !!state.authToken,
    config: (state) => ({ headers: { "X-AUTH-TOKEN": state.authToken } }),
    // authUserImgType: (state) => {
    //   if (state.userSimpleData.uprofile) {
    //     if (state.userSimpleData.uprofile.type) {
    //       return state.userSimpleData.uprofile.type;
    //     } else return false;
    //   } else return false;
    // },
    // authUserImgName: (state) => {
    //   if (state.userSimpleData.uprofile) {
    //     if (state.userSimpleData.uprofile.name) {
    //       return state.userSimpleData.uprofile.name;
    //     } else return false;
    //   } else return false;
    // },
    authUserImgRoute: (state) => {
      if (state.userSimpleData.uprofile) {
        if (state.userSimpleData.uprofile.name) {
          return SERVER.MEDIA_DIR + state.userSimpleData.uprofile.name;
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
    authUserId: (state) => state.userSimpleData.id,
    authUserSex: (state) => state.userSimpleData.usex,
    authUserAge: (state) => {
      const date = state.userSimpleData.ubirth.split("-");
      const nowDate = new Date().getFullYear();
      return nowDate - date[0] + 1;
    },
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token;
      cookies.set("auth-token", token);
    },
    SET_UID(state, uid) {
      state.uid = uid;
    },
    SET_AUTHORIZE(state, value) {
      state.isAuthorized = value;
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
    setAuthorized({ commit }, value) {
      commit("SET_AUTHORIZE", value);
    },
    setUid({ commit }, uid) {
      commit("SET_UID", uid);
    },
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
      return axios
        .post(SERVER.BASE_URL + info.route, info.data, {
          headers: {
            "content-type": "application/json",
          },
        })
        .then((res) => {
          // console.log(res);
          commit("SET_TOKEN", res.headers.token);
          router.push({ name: "Home" });
        })
        .catch((err) => {
          swal({
            text: "로그인 정보를 확인해주세요.",
            dangerMode: true,
          });
          console.log(err);
        });
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        route: SERVER.ROUTES.accounts.URL + SERVER.ROUTES.accounts.login,
      };
      dispatch("postAuthData", info).then(() => {
        dispatch("getNotifyCount", null, { root: true });
      });
    },

    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        route: SERVER.ROUTES.accounts.URL + SERVER.ROUTES.accounts.signup,
      };
      dispatch("postAuthData", info);
    },

    logout({ commit, getters, dispatch }) {
      // console.log(getters.config);
      axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.logout,
          getters.config
        )
        // .then((res) => {
        //   console.log(res);
        // })
        .catch((err) => console.log(err.response));

      commit("SET_TOKEN", null);
      commit("SET_USERSIMPLEDATA", {});
      dispatch("clear", null, { root: true });
      cookies.remove("auth-token");
      router.replace({ name: "Login" });
    },

    idCheck({ commit }, uid) {
      if (uid === "") {
        swal("아이디를 입력하세요.");
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
            swal("사용 가능한 아이디입니다.");
            // commit("SET_IDCHECK", true);
            // commit("SET_SIGNUPID", uid);
            return true;
          } else {
            swal({
              text: "이미 사용 중인 아이디입니다.",
              dangerMode: true,
            });
            // commit("SET_IDCHECK", false);
            return false;
          }
        })
        .catch((err) => console.log(err.response));
    },
    idCheck2({ commit }, uid) {
      if (uid === "") {
        swal("아이디를 입력하세요");
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
          if (res.data === "success") {
            swal({
              text: "가입되지 않은 아이디입니다.",
              dangerMode: true,
            });
            return true;
          } else {
            swal("가입된 아이디입니다.");
            return false;
          }
        })
        .catch((err) => console.log(err.response));
    },

    nickCheck(context, unick) {
      if (unick === "") {
        swal("별명을 입력하세요");
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
            swal("사용 가능한 별명입니다.");
            // commit("SET_NICKCHECK", true);
            // commit("SET_SIGNUPNICK", unick);
            return true;
          } else {
            swal({
              text: "이미 사용 중인 별명입니다.",
              dangerMode: true,
            });
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
            swal("이메일을 확인해주세요.");
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
      // console.log(userEmailData);
      axios
        .put(
          SERVER.BASE_URL +
            SERVER.ROUTES.accounts.URL +
            SERVER.ROUTES.accounts.pwreset,
          userEmailData
        )
        .then((res) => {
          // console.log("res : ", res);
          if (res.data === "success") {
            swal("비밀번호가 변경되었습니다.");
            router.push({ name: "Login" });
          } else {
            swal({
              text: "변경 실패",
              icon: "error",
              dangerMode: true,
            });
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
            // console.log(res);
            commit("SET_USERSIMPLEDATA", res.data.data);
            const ubirth = state.userSimpleData.ubirth;
            const usex = state.userSimpleData.usex;
            if (!ubirth || !usex) {
              router.push({ name: "SocialLoginDataInput" });
            }
          })
          .catch((err) => console.log(err));
      }
    },
    deleteUser({ getters, commit, state }) {
      swal({
        title: "탈퇴하시겠습니까?",
        text: "이 작업은 취소 할 수 없습니다.",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      }).then((willDelete) => {
        if (willDelete) {
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
            })
            .catch((err) => console.log(err));
          swal("회원 탈퇴가 완료되었습니다.", {
            icon: "success",
          });
        }
      });
      // const doubleCheck = confirm(
      //   "정말 탈퇴하시겠습니까?\n이 작업은 취소 할 수 없습니다."
      // );

      // if (doubleCheck) {
      //   const uid = state.userSimpleData.uid;
      //   axios
      //     .delete(
      //       SERVER.BASE_URL + SERVER.ROUTES.accounts.URL + uid,
      //       getters.config
      //     )
      //     .then(() => {
      //       commit("SET_TOKEN", null);
      //       commit("SET_USERSIMPLEDATA", {});
      //       cookies.remove("auth-token");
      //       router.replace({ name: "Login" });
      //       alert("회원 탈퇴가 완료되었습니다.");
      //     })
      //     .catch((err) => console.log(err));
      // }
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
        // .then((res) => console.log(res))
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
    setAdditionalUserData({ state, commit, getters }, data) {
      // console.log("data : ", data);
      const userEditRoute =
        SERVER.BASE_URL + SERVER.ROUTES.accounts.URL + state.userSimpleData.uid;
      const simpleUserRoute =
        SERVER.BASE_URL +
        SERVER.ROUTES.accounts.URL +
        SERVER.ROUTES.accounts.simple;
      axios
        .put(userEditRoute, data, getters.config)
        .then(() => {
          // console.log(res);
          return axios.get(simpleUserRoute, getters.config);
        })
        .then((res) => {
          // console.log("res : ", res.data.data);
          commit("SET_USERSIMPLEDATA", res.data.data);
        })
        .catch((err) => console.log(err));
    },
  },
};
