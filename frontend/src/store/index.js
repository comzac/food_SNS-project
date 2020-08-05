import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import moduleAccounts from "./accounts";
import moduleFeeds from "./feeds";
import moduleComments from "./comments";
import moduleNotifications from "./notifications";

Vue.use(Vuex);

const modules = {
  accounts: moduleAccounts,
  feeds: moduleFeeds,
  comments: moduleComments,
  notifications: moduleNotifications,
};

export default new Vuex.Store({
  modules,
  plugins: [
    createPersistedState({
      paths: ["accounts"],
    }),
  ],
});
