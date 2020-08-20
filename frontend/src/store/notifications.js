import axios from "axios";

import SERVER from "@/api/api";
// import router from "@/router";

export default {
  namespaced: true,
  state: {
    nonReadNotification: null,
    readNotification: null,
    nonReadCount: 0,
    readCount: 0,
    poll: null,
  },
  getters: {},
  mutations: {
    SET_NONREADNOTIFICATION(state, notifications) {
      state.notifications = notifications;
    },
    SET_READNOTIFICATION(state, notifications) {
      state.notifications = notifications;
    },
    SET_NOTIFICATIONCOUNT(state, count) {
      state.nonReadCount = count;
    },
    SET_POLL(state, poll) {
      state.poll = poll;
    },
    REDUCE_NOTIFYCOUNT(state) {
      state.nonReadCount--;
    },
  },
  actions: {
    polling({ commit, rootGetters, dispatch }, title) {
      const isLoggedIn = rootGetters["accounts/isLoggedIn"];
      if (title === "Notification" && isLoggedIn) {
        const poll = setInterval(function() {
          dispatch("getNotifyCount", null, { root: true });
        }, 60000);
        commit("SET_POLL", poll);
      }
    },
    getNotifyCount: {
      root: true,
      handler({ commit, rootGetters }) {
        const config = rootGetters["accounts/config"];
        //axios 요청
        //   console.log("fetchNotify");
        //   console.log(config);
        axios
          .get(SERVER.BASE_URL + SERVER.ROUTES.notifications.URL, config)
          .then((res) => {
            // console.log(res);
            commit("SET_NOTIFICATIONCOUNT", res.data.data);
          })
          .catch((err) => console.log(err.response));
      },
    },
    clear: {
      root: true,
      handler({ state, commit }) {
        // console.log("clear");
        clearInterval(state.poll);
        commit("SET_POLL", null);
        commit("SET_NOTIFICATIONCOUNT", 0);
      },
    },

    getNotifications({ rootGetters, commit }) {
      const config = rootGetters["accounts/config"];
      // console.log(config);
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.notifications.URL +
            SERVER.ROUTES.notifications.getAll,
          config
        )
        .then((res) => {
          // console.log(res.data.data);
          commit("SET_NONREADNOTIFICATION", res.data.data.nonReadNotification);
          commit("SET_READNOTIFICATION", res.data.data.readNotification);
          return res.data.data;
        })
        .catch((err) => console.log(err.response));
    },

    readNotification({ rootGetters }, notifyId) {
      const config = rootGetters["accounts/config"];

      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.notifications.URL + notifyId,
          config
        )
        // .then((res) => {
        //   console.log(res);
        // })
        .catch((err) => console.log(err.response));
    },

    reduceNotifyCount({ commit }) {
      // console.log("reduce");
      commit("REDUCE_NOTIFYCOUNT");
    },
  },
};
