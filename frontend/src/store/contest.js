import axios from "axios";
import SERVER from "@/api/api";

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
  },
};
