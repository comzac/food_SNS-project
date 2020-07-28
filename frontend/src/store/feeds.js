import axios from "axios";

// import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: null,
    userDetailData: null,
  },
  getters: {},
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = feeds;
    },
    SET_USERDETAILDATA(state, userDetailData) {
      state.userDetailData = userDetailData;
    },
  },
  actions: {
    fetchFeeds({ commit, rootGetters }) {
      const config = rootGetters["accounts/config"];
      axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + SERVER.ROUTES.feeds.page,
          config
        )
        .then((res) => {
          console.log(res);
          commit("SET_FEEDS", res.data);
        })
        .catch((err) => console.log(err));
    },

    getUserDetailData({ commit, rootGetters }, uid) {
      const config = rootGetters["accounts/config"];
      axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.page +
            uid,
          config
        )
        .then((res) => {
          commit("SET_USERDETAILDATA", res.data);
        })
        .catch((err) => console.log(err.response));
    },

    insertFeed({ rootGetters }, formData) {
      console.log(formData);
      const config = rootGetters["accounts/config"];
      config.headers["Content-Type"] = "multipart/form-data";
      config.headers["Accept"] = "application/json";
      console.log(formData.getAll("hashtag"));
      axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, formData, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },

    getFeedDetail({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
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
        .delete(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    },
  },
};
