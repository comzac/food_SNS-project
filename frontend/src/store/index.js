import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import moduleAccounts from "./accounts";
import moduleFeeds from "./feeds";

Vue.use(Vuex);

const modules = {
  accounts: moduleAccounts,
  feeds: moduleFeeds,
};

export default new Vuex.Store({
  modules,
  plugins: [
    createPersistedState({
      paths: ["accounts"],
    }),
  ],
});
