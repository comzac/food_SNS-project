import axios from "axios";

// import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: [],
  },
  getters: {},
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = feeds;
    },
  },
  actions: {
    insertFeed({ rootGetters }, feedData) {
      const config = rootGetters["accounts/config"];
      axios
        .post(SERVER.BASE_URL + SERVER.feeds.URL, feedData, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },

    getFeedDetail({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .get(SERVER.BASE_URL + SERVER.feeds.URL + id, null, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },
  },
};
