import Vue from "vue";
import Vuex from "vuex";

import axios from "axios";

import SERVER from "../api/api";
import router from "../router"

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    uid: null,
    upw: null,
    unick: null,
    uemail: null,
    uregdate: null,
    ubirth: null,
    usex: null
  },
  mutations: {
    SET_USERDATA(state, userData) {
      state.uid = userData.uid
      state.upw = userData.upw
      state.unick = userData.unick
      state.uemail = userData.uemail
      state.uregdate = userData.uregdate
      state.ubirth = userData.ubirth
      state.usex = userData.usex
    }
  },
  actions: {
    postAuthData({ commit }, info) {
      axios.post(SERVER.URL + info.route, info.data)
        .then(res => {
          console.log(res)
          commit('SET_USERDATA', res.user)
          router.push('/')
        })
        .catch(console.log(err))
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        route: SERVER.ROUTES.accounts.login
      }
      dispatch('postAuthData', info)
    } 
  },
  modules: {

  }
});
