import axios from "axios";
import SERVER from "@/api/api";
import router from "@/router";

export default {
  namespaced: true,
  state: {
    selectedContestFeed: null,
    contestFeeds: null,
    contestList: null,
    currentTheme: null,
  },
  getters: {},
  mutations: {
    SET_SELECTEDCONTESTFEED(state, feed) {
      state.selectedContestFeed = feed;
    },
    SET_CONTESTFEEDS(state, feeds) {
      state.contestFeeds = feeds;
    },
    SET_CONTESTLIST(state, list) {
      state.contestList = list;
    },
    SET_CURRENTTHEME(state, theme) {
      state.currentTheme = theme;
    },
  },
  actions: {
    getContestFeedDetail({ rootGetters, commit }, fid) {
      const config = rootGetters["accounts/config"];
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.contest.URL +
            SERVER.ROUTES.contest.feeds +
            fid,
          config
        )
        .then((res) => {
          console.log(res);
          commit("SET_SELECTEDCONTESTFEED", res.data.data);
          return res.data.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getContestData({ commit, rootGetters }, data) {
      axios
        .get(SERVER.BASE_URL + data.route, rootGetters["accounts/config"])
        .then((res) => {
          if (data.mode === "oneList") {
            console.log(res.data.data.contestFeedAll);
            commit("SET_CONTESTFEEDS", res.data.data.contestFeedAll);
            commit("SET_CURRENTTHEME", res.data.data.theme);
          } else if (data.mode === "lists") {
            commit("SET_CONTESTLIST", res.data.data);
          }
        })
        .catch((err) => console.log(err));
    },
    getRecentContestFeeds({ dispatch }) {
      const route = SERVER.ROUTES.contest.URL;
      const data = {
        route: route,
        mode: "oneList",
      };
      dispatch("getContestData", data);
    },
    getContestList({ dispatch }) {
      const route = SERVER.ROUTES.contest.URL + SERVER.ROUTES.contest.list;
      const data = {
        route: route,
        mode: "lists",
      };
      dispatch("getContestData", data);
    },
    contestFeedLikeUnlike({ rootGetters }, likeData) {
      const config = rootGetters["accounts/config"];
      console.log("fid", likeData.fid, "like", likeData.like);
      if (likeData.like) {
        axios
          .delete(
            SERVER.BASE_URL +
              SERVER.ROUTES.contest.URL +
              SERVER.ROUTES.contest.likes +
              likeData.fid,
            config
          )
          .then((res) => console.log(res))
          .catch((err) => console.error(err));
      } else {
        axios
          .post(
            SERVER.BASE_URL +
              SERVER.ROUTES.contest.URL +
              SERVER.ROUTES.contest.likes +
              likeData.fid,
            null,
            config
          )
          .then((res) => console.log(res))
          .catch((err) => console.error(err));
      }
    },
    insertContestFeed({ rootGetters }, feedData) {
      // console.log("insertContestFeed");
      // console.log(feedData);
      const config = rootGetters["accounts/config"];
      const form = new FormData();
      const mediaData = feedData.dbFiles;
      // console.log(feedData.dbFiles);
      delete feedData.dbFiles;
      // console.log(config);
      mediaData.forEach((file) => {
        form.append("files", file);
      });
      // let id;
      // console.log("feedData");
      // console.log(feedData);
      const newFeedData = feedData.feed;
      // console.log("newFeedData");
      // console.log(newFeedData);
      newFeedData.cid = 1;
      // console.log("lastFeedData");
      // console.log(newFeedData);
      axios
        .post(
          SERVER.BASE_URL +
            SERVER.ROUTES.contest.URL +
            SERVER.ROUTES.contest.feeds,
          newFeedData,
          config
        )
        .then((res) => {
          console.log(res.data.data);
          // id = res.data.data;
          form.append("fid", res.data.data);
          return axios.post(
            SERVER.BASE_URL +
              SERVER.ROUTES.contest.URL +
              SERVER.ROUTES.contest.files,
            form,
            config
          );
        })
        .then((res) => {
          console.log("res:", res);
          router.push({ name: "ContestList" });
        })
        .catch((err) => console.log(err.response));
    },
  },
};
