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
    fetchFeeds({ commit, rootGetters }) {
      const config = rootGetters["accounts/config"];
      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + SERVER.ROUTES.feeds.home,
          null,
          config
        )
        .then((res) => {
          console.log(res);
          commit("SET_FEEDS", res.data);
        })
        .catch((err) => console.log(err));
    },

    insertFeed({ rootGetters }, feedData) {
      const config = rootGetters["accounts/config"];
      axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, feedData, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },

    getFeedDetail({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, null, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },

    updateFeed({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .put(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, null, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },

    deleteFeed({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .delete(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, null, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },
  },
};
