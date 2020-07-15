import Vue from "vue";
import Vuex from "vuex";

import cookies from "vue-cookies";
import axios from "axios";

import SERVER from "../api/api";
import router from "../router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authData: cookies.get('auth-id'),
    userData: {},
  },
  getters: {
    isLogin: state => !!state.authData,
  },
  mutations: {
    SET_COOKIE(state, id) {
      state.authData = id;
      cookies.set('auth-id', id);
    },
    SET_USERDATA(state, userData) {
      state.userData = userData
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
          console.log(res)
          commit('SET_USERDATA', res)
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
    }

  },
  modules: {

  }
});
