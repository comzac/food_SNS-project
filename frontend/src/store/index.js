import Vue from "vue";
import Vuex from "vuex";

import cookies from "vue-cookies";
import axios from "axios";

// import http from "@/util/http-common";
import SERVER from "@/api/api";
import router from "../router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authData: cookies.get('auth-id'),
    userInfo: {},
    isLogin: false,
    signupId: null,
    signupNick: null,
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
      if(uid === "") {
        alert('아이디를 입력하세요')
        commit('SET_IDCHECK', false)
        return false
      }
      return axios.get(SERVER.URL + SERVER.ROUTES.accounts.idCheck + uid)
      .then(res => {
        console.log(res.data)
        if(res.data === 'success') {
          alert("사용 가능한 아이디입니다.")
          commit('SET_IDCHECK', true)
          commit('SET_SIGNUPID', uid)
          return true
        }else {
          alert("이미 사용 중인 아이디입니다.")
          commit('SET_IDCHECK', false)
          return false
        }
      })
      .catch(err=>console.log(err.response))
    },

    nickCheck({ commit }, unick) {
      if(unick === "") {
        alert('별명을 입력하세요')
        commit('SET_IDCHECK', false)
        return false
      }      
      return axios.get(SERVER.URL + SERVER.ROUTES.accounts.nickCheck + unick)
      .then(res => {
        console.log(res.data)
        if(res.data === "success") {
          alert("사용 가능한 별명입니다.")
          commit('SET_NICKCHECK', true)
          commit('SET_SIGNUPNICK', unick)
          return true
        }else {
          alert("이미 사용 중인 별명입니다.")
          commit('SET_NICKCHECK', false)
          return false
        }

      })
      .catch(err=>console.log(err.response))
    },

    pwreset(context , userEmailData) {
      axios.post(SERVER.URL + SERVER.ROUTES.accounts.pwcheck, userEmailData)
        .then(res => {
          console.log(res)
          if(res.data === "success"){
            router.push({ name: "Home" })
          }else {
            alert("변경 실패")
          }
        })
        .catch(err => console.log(err.response))
    }

  },
  modules: {

  }
});
