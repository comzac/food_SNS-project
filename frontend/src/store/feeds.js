import axios from "axios";

import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: null,
    userProfileData: null,
    selectedFeed: null,
  },
  getters: {
    feed: (state) => state.selectedFeed,
  },
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = feeds;
    },
    SET_USERPROFILEDATA(state, userProfileData) {
      state.userProfileData = userProfileData;
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
          commit("SET_FEEDS", res.data.feedAll);
        })
        .catch((err) => console.log(err));
    },

    getUserPageData({ commit, rootGetters }, uid) {
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
          commit("SET_USERPROFILEDATA", res.data);
        })
        .catch((err) => console.log(err.response));
    },

    setUserProfileData({ commit, rootGetters }, data) {
      const config = rootGetters["accounts/config"];
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
        .then((res) => {
          commit("accounts/SET_USERSIMPLEDATA", res.data.data, {
            root: true,
          });
          alert("프로필이 수정되었습니다.");
          router.go(-1);
        })
        .catch((err) => console.log(err));
    },

    insertFeed({ rootGetters }, feedData) {
      console.log(feedData);
      const config = rootGetters["accounts/config"];
      const form = new FormData();
      const mediaData = feedData.dbFiles;
      console.log(feedData.dbFiles);
      delete feedData.dbFiles;
      console.log(config);
      mediaData.forEach((file) => {
        form.append("files", file);
      });
      console.log(feedData);
      axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, feedData, config)
        .then((res) => {
          console.log(res.data.data);
          form.append("fid", res.data.data);
          return axios.post(
            SERVER.BASE_URL +
              SERVER.ROUTES.files.URL +
              SERVER.ROUTES.files.upload,
            form,
            config
          );
        })
        .then((res) => console.log("res:", res))
        .catch((err) => console.log(err.response));

      // axios
      //   .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, feedData, config)
      //   .then((res) => {
      //     console.log(res.data.data);
      //     form.append("fid", res.data.data);
      //     // config.headers["Content-Type"] = "multipart/form-data";
      //     console.log(config);
      //     axios
      //       .post(
      //         SERVER.BASE_URL +
      //           SERVER.ROUTES.files.URL +
      //           SERVER.ROUTES.files.upload,
      //         form,
      //         config
      //       )
      //       .then((res) => console.log("res:", res))
      //       .catch((err) => console.log(err.response));
      //   })
      //   .catch((err) => console.log(err.response));
    },

    getFeedDetail({ rootGetters, commit }, id) {
      const config = rootGetters["accounts/config"];
      return axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
        .then((res) => {
          console.log(res);
          commit("SET_SELECTEDFEED", res.data.feedAll[0]);
          return res.data.feedAll[0];
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
        .then((res) => {
          console.log(res);
          router.push({ name: "Home" });
        })
        .catch((err) => console.log(err));
    },
  },
};
