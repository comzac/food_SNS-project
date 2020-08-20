import axios from "axios";
import SERVER from "@/api/api";
import router from "@/router";

export default {
  namespaced: true,
  state: {
    selectedContestFeed: null,
    contestFeeds: null,
    contestList: null,
    currentContest: null,
    currentContest2: null,
  },
  getters: {
    currentContestId: (state) => state.currentContest.cid,
  },
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
    SET_CURRENTCONTEST2(state, item) {
      state.currentContest2 = item;
    },
  },
  actions: {
    setContest({ commit }, item) {
      commit("SET_CURRENTCONTEST2", item);
    },
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
          // console.log(res);
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
            // console.log(res.data.data);
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
      // console.log("fid", likeData.fid, "like", likeData.like);
      if (likeData.like) {
        axios
          .delete(
            SERVER.BASE_URL +
              SERVER.ROUTES.contest.URL +
              SERVER.ROUTES.contest.likes +
              likeData.fid,
            config
          )
          // .then((res) => console.log(res))
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
          // .then((res) => console.log(res))
          .catch((err) => console.error(err));
      }
    },
    insertContestFeed({ rootGetters, getters }, feedData) {
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
      // console.log("feedData");
      // console.log(feedData);
      const newFeedData = feedData.feed;
      // console.log("newFeedData");
      // console.log(newFeedData);
      newFeedData.cid = getters.currentContestId;
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
          // console.log(res.data.data);
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
        .then(() => {
          // console.log("res:", res);
          router.push({ name: "ContestList" });
        })
        .catch((err) => console.log(err.response));
    },

    updateContestFeed({ rootGetters, getters }, feedData) {
      const config = rootGetters["accounts/config"];
      const id = feedData.id;
      delete feedData.id;
      const newFeedData = feedData.feed;
      // console.log("newFeedData");
      // console.log(newFeedData);
      newFeedData.cid = getters.currentContestId;
      // console.log("lastFeedData");
      // console.log(newFeedData);
      // console.log(feedData);
      axios
        .put(
          SERVER.BASE_URL +
            SERVER.ROUTES.contest.URL +
            SERVER.ROUTES.contest.feeds +
            id,
          newFeedData,
          config
        )
        .then(() => {
          // console.log(res);
          router.push({ name: "ContestFeed", params: { fid: id } });
        })
        .catch((err) => {
          console.log(err.response);
        });
    },

    deleteContestFeed({ rootGetters }, id) {
      const config = rootGetters["accounts/config"];

      axios
        .delete(
          SERVER.BASE_URL +
            SERVER.ROUTES.contest.URL +
            SERVER.ROUTES.contest.feeds +
            id,
          config
        )
        .then(() => {
          // console.log(res);
          router.push({ name: "ContestList" });
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
  },
};
