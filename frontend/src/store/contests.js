import axios from "axios";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    contestFeeds: null,
    contestList: null,
  },
  getters: {},
  mutations: {
    SET_CONTESTFEEDS(state, feeds) {
      state.contestFeeds = feeds;
    },
    SET_CONTESTLIST(state, list) {
      state.contestList = list;
    },
  },
  actions: {
    getContestData({ commit, rootGetters }, data) {
      axios
        .get(SERVER.BASE_URL + data.route, rootGetters["accounts/config"])
        .then((res) => {
          if (data.mode === "oneList") {
            commit("SET_CONTESTFEEDS", res.data.data);
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
