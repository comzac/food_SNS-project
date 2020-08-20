import axios from "axios";

import router from "@/router";
import SERVER from "@/api/api";

import swal from "sweetalert";

export default {
  namespaced: true,
  state: {
    feeds: [],
    followFeeds: [],
    userProfileData: null,
    selectedFeed: null,
  },
  getters: {
    feed: (state) => state.selectedFeed,
  },
  mutations: {
    SET_FEEDS(state, feeds) {
      state.feeds = state.feeds.concat(feeds);
    },
    CLEAR_FEEDS(state) {
      state.feeds = [];
    },
    SET_FOLLOWFEEDS(state, feeds) {
      state.feeds = state.feeds.concat(feeds);
    },
    CLEAR_FOLLOWFEEDS(state) {
      state.feeds = [];
    },
    SET_USERPROFILEDATA(state, userProfileData) {
      state.userProfileData = userProfileData;
    },
    SET_SELECTEDFEED(state, feed) {
      state.selectedFeed = feed;
    },
  },
  actions: {
    fetchFeeds({ commit, rootGetters }, feedParams) {
      const config = rootGetters["accounts/config"];
      // console.log(config);
      config.params = feedParams;
      // console.log(config);
      return axios
        .get(
          SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + SERVER.ROUTES.feeds.page,
          config
        )
        .then((res) => {
          delete config.params;
          return res;
        })
        .then((res) => {
          // console.log(res);
          commit("SET_FEEDS", res.data.feedAll);
          return res.data.feedAll;
        })
        .catch((err) => console.log(err.response));
    },

    clearFeeds({ commit }) {
      commit("CLEAR_FEEDS");
    },

    fetchFollowFeeds({ commit, rootGetters }, feedParams) {
      const config = rootGetters["accounts/config"];
      // console.log(feedParams);
      config.params = feedParams;
      // console.log(config);
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.followerPagination,
          config
        )
        .then((res) => {
          delete config.params;
          return res;
        })
        .then((res) => {
          // console.log(res);
          commit("SET_FOLLOWFEEDS", res.data.feedAll);
          return res.data.feedAll;
        })
        .catch((err) => console.log(err.response));
    },

    clearFollowFeeds({ commit }) {
      commit("CLEAR_FOLLOWFEEDS");
    },

    getUserPageData({ commit, rootGetters }, uid) {
      const config = rootGetters["accounts/config"];
      axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.accounts.page +
            uid,
          config
        )
        .then((res) => {
          // console.log(res);
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
      formData.append("hasImage", data.hasImage);
      formData.append(
        "coordi",
        data.coordi
          .replace(/ /g, "")
          .replace(/\r/g, "")
          .replace(/\n/g, "")
      );
      formData.append("coordi", "");
      // console.log(data.hasImage);
      axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.accounts.page,
          formData,
          config
        )
        .then((res) => {
          commit("accounts/SET_USERSIMPLEDATA", res.data.data, {
            root: true,
          });
          swal("프로필이 수정되었습니다.", { buttons: [null, "확인"] });
          router.go(-1);
        })
        .catch((err) => console.log(err));
    },

    insertFeed({ rootGetters }, feedData) {
      // console.log("feedDataa", feedData);
      const config = rootGetters["accounts/config"];
      const form = new FormData();
      const mediaData = feedData.dbFiles;
      // console.log("dbFiles", feedData.dbFiles);
      delete feedData.dbFiles;
      // console.log(config);
      mediaData.forEach((file) => {
        form.append("files", file);
        form.append(
          "coordi",
          file.coordi
            ? file.coordi
                .replace(/ /g, "")
                .replace(/\r/g, "")
                .replace(/\n/g, "")
            : ""
        );
      });
      form.append("coordi", "");
      // let id;
      // console.log("form", feedData);
      axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, feedData, config)
        .then((res) => {
          // console.log(res.data.data);
          // id = res.data.data;
          form.append("fid", res.data.data);
          return axios.post(
            SERVER.BASE_URL +
              SERVER.ROUTES.files.URL +
              SERVER.ROUTES.files.upload,
            form,
            config
          );
        })
        .then(() => {
          // console.log("res:", res);
          router.push({ name: "Home" });
        })
        .catch((err) => console.log(err.response));
    },

    getFeedDetail({ rootGetters, commit }, id) {
      const config = rootGetters["accounts/config"];
      return axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
        .then((res) => {
          // console.log(res);
          commit("SET_SELECTEDFEED", res.data.feedAll[0]);
          return res.data.feedAll[0];
        })
        .catch((err) => console.log(err));
    },

    updateFeed({ rootGetters }, feedData) {
      const config = rootGetters["accounts/config"];
      const id = feedData.id;
      delete feedData.id;
      axios
        .put(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, feedData, config)
        .then(() => {
          // console.log(res);
          router.push({ name: "FeedView", params: { fid: id } });
        })
        .catch((err) => console.log(err));
    },

    deleteFeed({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];
      axios
        .delete(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, config)
        .then(() => {
          // console.log(res);
          router.push({ name: "Home" });
        })
        .catch((err) => console.log(err));
    },

    feedLikeUnlike({ rootGetters }, likeData) {
      const config = rootGetters["accounts/config"];
      // console.log("fid", likeData.fid, "like", likeData.like);
      if (likeData.like) {
        axios
          .delete(
            SERVER.BASE_URL + SERVER.ROUTES.likes.feed + likeData.fid,
            config
          )
          // .then((res) => console.log(res))
          .catch((err) => console.error(err));
      } else {
        axios
          .post(
            SERVER.BASE_URL + SERVER.ROUTES.likes.feed + likeData.fid,
            null,
            config
          )
          // .then((res) => console.log(res))
          .catch((err) => console.error(err));
      }
    },
    searchKeyword({ rootGetters }, keyword) {
      // console.log(keyword);
      keyword = keyword.replace(/ /g, "");
      const config = rootGetters["accounts/config"];
      // console.log(
      //   SERVER.BASE_URL +
      //     SERVER.ROUTES.feeds.URL +
      //     SERVER.ROUTES.feeds.search +
      //     "/temp/" +
      //     keyword
      // );
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.search +
            "/temp/" +
            keyword,
          config
        )
        .then((res) => {
          return res.data;
        })
        .catch((err) => console.error("error : ", err));
    },
    searchedKeyword({ rootGetters }, keyword) {
      const config = rootGetters["accounts/config"];
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.search +
            "/" +
            keyword,
          config
        )
        .then((res) => {
          return res.data;
        })
        .catch((err) => console.error("error : ", err));
    },
  },
};
