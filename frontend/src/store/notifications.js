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
  },
  getters: {
    notifyItems(state) {
      return [
        {
          headers: "Non-Read",
        },
        { divider: true },
        state.nonReadNotification,
        {
          headers: "Read",
        },
        { divider: true },
        state.readNotification,
      ];
    },
  },
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
  },
  actions: {
    fetchNotifications({ commit, rootGetters }) {
      const config = rootGetters["accounts/config"];
      //axios 요청
      //   console.log("fetchNotify");
      //   console.log(config);
      axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.notifications.URL, config)
        .then((res) => {
          console.log(res);
          commit("SET_NOTIFICATIONCOUNT", res.data.data);
        })
        .catch((err) => console.log(err.response));
    },

    getNotifications({ rootGetters, commit }) {
      const config = rootGetters["accounts/config"];

      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.notifications.URL +
            SERVER.ROUTES.notifications.getAll,
          config
        )
        .then((res) => {
          console.log(res.data.data);
          commit("SET_NONREADNOTIFICATION", res.data.data.nonReadNotification);
          commit("SET_READNOTIFICATION", res.data.data.readNotification);
          return res.data.data;
        })
        .catch((err) => console.log(err.response));
    },
  },
};
