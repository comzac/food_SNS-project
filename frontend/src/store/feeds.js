import axios from "axios";

// import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: null,
    userDetailData: null,
    selectedFeed: null,
  },
  getters: {
    feed: (state) => state.selectedFeed,
  },
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = feeds;
    },
    SET_USERDETAILDATA(state, userDetailData) {
      state.userDetailData = userDetailData;
    },
    SET_SELECTEDFEED(state, feed) {
      state.selectedFeed = feed;
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
          console.log(res);
          commit("SET_USERDETAILDATA", res.data);
        })
        .catch((err) => console.log(err.response));
    },

    setUserDetailData({ rootGetters }, data) {
      const config = rootGetters["accounts/config"];
      config;
      const formData = new FormData();
      formData.append("unick", data.unick);
      formData.append("text", data.text);
      formData.append("img", data.img);
      axios
        .post(
          SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + SERVER.ROUTES.feeds.page,
          formData,
          config
        )
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
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

    getFeedDetail({ rootGetters, commit }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
        .then((res) => {
          console.log(res);
          commit("SET_SELECTEDFEED", res.data);
        })
        .catch((err) => console.log(err));
    },

    updateFeed({ rootGetters }, formData) {
      const config = rootGetters["accounts/config"];
      config.headers["Content-Type"] = "multipart/form-data";
      config.headers["Accept"] = "application/json";
      const id = formData.get("id");
      console.log(id);
      axios
        .put(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, formData, config)
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
