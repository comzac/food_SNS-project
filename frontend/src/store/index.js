import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import moduleAccounts from "./accounts";
import moduleFeeds from "./feeds";
import moduleComments from "./comments";
import moduleNotifications from "./notifications";
import moduleContests from "./contest";
import moduleFrame from "./frame";

Vue.use(Vuex);

const modules = {
  accounts: moduleAccounts,
  feeds: moduleFeeds,
  comments: moduleComments,
  notifications: moduleNotifications,
  contests: moduleContests,
  frame: moduleFrame,
};

export default new Vuex.Store({
  modules,
  plugins: [
    createPersistedState({
      paths: ["accounts", "contests"],
    }),
  ],
});
