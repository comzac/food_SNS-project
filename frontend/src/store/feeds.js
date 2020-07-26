import axios from "axios";

// import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: [],
    userFeeds: [],
  },
  getters: {},
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = feeds;
    },
    SET_USERFEEDS(state, userFeeds) {
      state.userFeeds = userFeeds
    }
  },
  actions: {
    fetchFeeds({ commit, rootGetters }) {
      const config = rootGetters["accounts/config"];
      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + SERVER.ROUTES.feeds.page,
          null,
          config
        )
        .then((res) => {
          console.log(res);
          commit("SET_FEEDS", res.data);
        })
        .catch((err) => console.log(err));
    },

    getUserFeed({ commit, rootGetters }, userId) {
      const config = rootGetters["accounts/cofnig"];
      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.feed.URL + SERVER.ROUTES.feeds.page + userId,
          null,
          config
        )
        .then(res => {
          console.log(res)
          commit("SET_USERFEEDS", res.data)
        })
        .catch(err => console.log(err.response))
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
