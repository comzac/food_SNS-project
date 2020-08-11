import axios from "axios";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    selectedContestFeed: null,
    contestFeeds: null,
    contestList: null,
    currentContest: null,
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
    SET_CURRENTCONTEST(state, data) {
      state.currentContest = data;
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
            console.log(res.data.data);
            commit("SET_CONTESTFEEDS", res.data.data.contestFeedAll);
            const contestData = {
              theme: res.data.data.theme,
              cid: res.data.data.cid,
            };
            commit("SET_CURRENTCONTEST", contestData);
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
  },
};
