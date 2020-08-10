export default {
  namespaced: true,
  state: {
    selection: 0,
  },
  getters: {
    selection(state) {
      return state.selection;
    },
  },
  mutations: {
    SET_SELECTION(state, item) {
      state.selection = item;
    },
  },
  actions: {},
};
