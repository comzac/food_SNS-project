import axios from "axios";

import router from "@/router";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    feeds: [],
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
    SET_USERPROFILEDATA(state, userProfileData) {
      state.userProfileData = userProfileData;
    },
    SET_SELECTEDFEED(state, feed) {
      state.selectedFeed = feed;
    },
  },
  actions: {
    fetchFeeds({ commit, rootGetters }, fid) {
      const config = rootGetters["accounts/config"];
      console.log(config);
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.pagination +
            fid,
          config
        )
        .then((res) => {
          console.log(res.data.feedAll);
          commit("SET_FEEDS", res.data.feedAll);
          return res.data.feedAll;
        })
        .catch((err) => console.log(err.response));
    },

    clearFeeds({ commit }) {
      commit("CLEAR_FEEDS");
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
      formData.append("hasImage", data.hasImage);
      console.log(data.hasImage);
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
      // let id;
      console.log(feedData);
      axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL, feedData, config)
        .then((res) => {
          console.log(res.data.data);
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
        .then((res) => {
          console.log("res:", res);
          router.push({ name: "Home" });
        })
        .catch((err) => console.log(err.response));
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

    updateFeed({ rootGetters }, feedData) {
      const config = rootGetters["accounts/config"];
      const id = feedData.id;
      delete feedData.id;
      axios
        .put(SERVER.BASE_URL + SERVER.ROUTES.feeds.URL + id, feedData, config)
        .then((res) => {
          console.log(res);
          router.push({ name: "FeedView", params: { fid: id } });
        })
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

    feedLikeUnlike({ rootGetters }, likeData) {
      const config = rootGetters["accounts/config"];
      console.log("fid", likeData.fid, "like", likeData.like);
      if (likeData.like) {
        axios
          .delete(
            SERVER.BASE_URL + SERVER.ROUTES.likes.feed + likeData.fid,
            config
          )
          .then((res) => console.log(res))
          .catch((err) => console.error(err));
      } else {
        axios
          .post(
            SERVER.BASE_URL + SERVER.ROUTES.likes.feed + likeData.fid,
            null,
            config
          )
          .then((res) => console.log(res))
          .catch((err) => console.error(err));
      }
    },
    searchKeyword({ rootGetters }, keyword) {
      console.log(keyword);
      var state;
      if (/^#/.test(keyword)) {
        state = "HASHTAG";
        keyword = keyword.replace(/^#/g, "").replace(/ /g, "");
      } else {
        keyword = keyword.replace(/ /g, "");
        state = "USERID";
      }
      const config = rootGetters["accounts/config"];
      console.log(
        SERVER.BASE_URL +
          SERVER.ROUTES.feeds.URL +
          SERVER.ROUTES.feeds.search +
          "/temp/" +
          keyword +
          "/" +
          state
      );
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.feeds.URL +
            SERVER.ROUTES.feeds.search +
            "/temp/" +
            keyword +
            "/" +
            state,
          config
        )
        .then((res) => {
          console.log("keyword : ", res);
          return res;
        })
        .catch((err) => console.error("error : ", err));
    },
    searchedKeyword({ rootGetters }, keyword) {
      const config = rootGetters["accounts/config"];
      console.log(
        SERVER.BASE_URL +
          SERVER.ROUTES.feeds.URL +
          SERVER.ROUTES.feeds.search +
          "/" +
          keyword
      );
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
          console.log("keyword : ", res);
          return res;
        })
        .catch((err) => console.error("error : ", err));
    },
  },
};
