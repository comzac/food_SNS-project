import axios from "axios";
import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    selectedContestFeed: null,
  },
  getters: {},
  mutations: {
    SET_SELECTEDCONTESTFEED(state, feed) {
      state.selectedContestFeed = feed;
    },
  },
  actions: {
    getContestFeedDetail({ rootGetters, commit }, fid) {
      const config = rootGetters["accounts/config"];
      return axios
        .get(
          SERVER.BASE_URL +
            SERVER.ROUTES.contests.URL +
            SERVER.ROUTES.contests.feeds +
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
  },
};
