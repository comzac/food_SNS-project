// import axios from "axios";

// import SERVER from "@/api/api";
// import router from "@/router";

export default {
  state: {
    notifications: null,
  },
  getters: {},
  mutations: {
    SET_NOTIFICATION(state, notifications) {
      state.notifications = notifications;
    },
  },
  actions: {
    fetchNotifications({ rootGetters }) {
      const config = rootGetters["accounts/config"];
      //axios 요청
      console.log(config);
    },
  },
};
