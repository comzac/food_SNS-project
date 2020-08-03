import axios from "axios";

import SERVER from "@/api/api";

export default {
  namespaced: true,
  state: {
    comments: null,
  },
  getters: {},
  mutations: {
    SET_COMMENTLIST(state, comments) {
      state.comments = comments;
    },
  },
  actions: {
    fetchComments({ rootGetters, commit }, fid) {
      const config = rootGetters["accounts/config"];

      axios
        .get(SERVER.BASE_URL + SERVER.ROUTES.comments.URL + fid, config)
        .then((res) => {
          console.log(res);
          commit("SET_COMMENTLIST", res.data.data);
        })
        .catch((err) => console.log(err.response));
    },

    insertComment({ rootGetters }, commentData) {
      const config = rootGetters["accounts/config"];

      return axios
        .post(SERVER.BASE_URL + SERVER.ROUTES.comments.URL, commentData, config)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => console.log(err.response));
    },
  },
};
