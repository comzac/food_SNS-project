import Vue from "vue";
import Vuex from "vuex";

import moduleAccounts from "./accounts";
import moduleFeeds from "./feeds";

Vue.use(Vuex);

const modules = {
  accounts: moduleAccounts,
  feeds: moduleFeeds,
};

export default new Vuex.Store({
  modules,
});
