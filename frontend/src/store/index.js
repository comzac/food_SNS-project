import Vue from "vue";
import Vuex from "vuex";

import cookies from "vue-cookies";
import axios from "axios";

// import http from "@/util/http-common";
import SERVER from "../api/api";
import router from "../router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authData: cookies.get('auth-id'),
    userInfo: {},
    isLogin: false,
    signupId: null,
    signupNick: null,
    canSignup: false,
    idChecked: false,
    nickChecked: false,
  },
  getters: {
    isLogin: state => !!state.authData,
  },
  mutations: {
    // mutateIsLogin(state, isLogin){
    //   state.isLogin = isLogin
    //   console.log(7);
    // },
    // mutateUserInfo(state, userInfo){
    //   state.userInfo = userInfo
    // },

    SET_COOKIE(state, id) {
      state.authData = id;
      cookies.set('auth-id', id);
    },
    SET_USERDATA(state, userData) {
      state.userData = userData
    },
    SET_IDCHECK(state, check) {
      state.idChecked = check
    },
    SET_SIGNUPID(state, id) {
      state.signupId = id
    },
    SET_NICKCHECK(state, check) {
      state.nickChecked = check
    },
    SET_SIGNUPNICK(state, nick) {
      state.signupNick = nick
    }
  },
  actions: {
    postAuthData({ commit }, info) {
      console.log(info.data)
      console.log(SERVER.URL + info.route)
      axios.post(SERVER.URL + info.route, info.data, {
        headers: {
          "Content-type": "application/json"
        }
      })
        .then(res => {
          console.log("response")
          console.log(res.data)
          commit('SET_USERDATA', res.data)
          commit('SET_COOKIE', res.uid)
          router.push('/')
        })
        .catch(err => console.log(err.response))
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        route: SERVER.ROUTES.accounts.login
      }
      dispatch('postAuthData', info)
    },

    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        route: SERVER.ROUTES.accounts.signup
      }
      dispatch('postAuthData', info)
    },

    idCheck({commit}, uid) {
      axios.get(SERVER.URL + SERVER.ROUTES.accounts.idCheck + uid)
        .then(res => {
          console.log(res.data)
          if(res.data === "success") {
            alert("사용 가능한 아이디입니다.")
            commit('SET_IDCHECK', true)
            commit('SET_SIGNUPID', uid)
          }else {
            alert("이미 사용 중인 아이디입니다.")
          }

        })
        .catch(err=>console.log(err.response))
    },

    nickCheck({ commit }, unick) {
      axios.get(SERVER.URL + SERVER.ROUTES.accounts.nickCheck + unick)
      .then(res => {
        console.log(res.data)
        if(res.data === "success") {
          alert("사용 가능한 닉네임입니다.")
          commit('SET_NICKCHECK', true)
          commit('SET_SIGNUPNICK', unick)
        }else {
          alert("이미 사용 중인 닉네임입니다.")
        }

      })
      .catch(err=>console.log(err.response))
    }
    // login(context, {uid, upw}) {
    //   http
    //     .post("/login", {
    //       uid: uid,
    //       upw: upw
    //     })
    //     .then(({ data }) => {
    //       console.log(data);
    //       context.commit('mutateIsLogin', true);
    //       context.commit('mutateUserInfo', data);
    //     })
    //     .catch((error) => {
    //       if (error.response.status == '404') {
    //         alert("아이디 또는 비밀번호가 올바르지 않습니다.");
    //       } else {
    //          alert('로그인 처리시 에러가 발생했습니다.');
    //       }
    //       console.log(error.response);
    //     });
    // },
    // login({ commit }, loginData ) {
    //   const data = [loginData.uid, loginData.upw]
    //   console.log(data)
    //   axios.post(SERVER.URL + SERVER.ROUTES.accounts.login, loginData)
    //     .then(res => {
    //       console.log("response")
    //       console.log(res.data)
    //       commit('SET_USERDATA', res.data)
    //       commit('SET_COOKIE', res.uid)
    //       router.push('/')
    //     })
    //     .catch(err => console.log(err.response))
    // },
  },
  modules: {

  }
});
