<template>
  <v-card class="mx-auto mt-10" flat max-width="975" outlined>
    <v-list-item class="justify-center mb-5">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            outlined
            rounded
            v-bind="attrs"
            v-on="on"
            class="mr-5 contest-btn"
            color="grey darken-3"
          >
            <v-icon left>mdi-trophy</v-icon>
            {{ currentTheme }}
            <v-icon right>mdi-chevron-down</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, index) in contestList"
            :key="index"
            @click="getSelectedThemeFeeds(item.id), setContest(item)"
          >
            <v-list-item-title>{{ item.theme }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn
        v-if="contestList[0].theme == currentTheme"
        color="grey darken-3 participate-btn"
        rounded
        small
        dark
        @click="moveToContestFeedCreate"
        >참가</v-btn
      >
    </v-list-item>
  </v-card>
</template>

<script>
// import swal from "sweetalert";
import { mapState, mapActions } from "vuex";
import SERVER from "@/api/api";

export default {
  props: {
    contestList: Array,
  },
  data() {
    return {
      recent: null,
    };
  },
  computed: {
    ...mapState("contests", ["currentContest"]),
    ...mapState("contests", ["currentContest2"]),
    currentTheme() {
      if (this.currentContest2) {
        return this.currentContest2.theme;
      } else if (this.currentContest) {
        // if (this.currentContest) {
        return this.currentContest.theme;
      } else return false;
    },
  },
  methods: {
    ...mapActions("contests", [
      "getContestData",
      "setContest",
      "setContestFeed",
    ]),
    getSelectedThemeFeeds(cid) {
      const route = SERVER.ROUTES.contest.URL + cid;
      const data = {
        route: route,
        mode: "oneList",
      };
      this.getContestData(data);
    },
    moveToContestFeedCreate() {
      this.$router.push({ name: "ContestFeedCreate" });
    },
  },
  mounted() {
    if (this.currentContest2) {
      this.getSelectedThemeFeeds(this.currentContest2.id);
    }
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}

button.non-active.theme--light.v-btn.v-btn--disabled {
  color: rgba(0, 0, 0, 0.87) !important;
}

.v-list {
  max-height: 35vh;
  overflow-y: auto;
}

.v-menu__content > div:nth-child(1) > div.v-list-item {
  border-bottom: 1px solid #d3d3d3;
}

.v-menu__content > div:nth-child(1) > div.v-list-item:hover {
  cursor: pointer;
  background-color: #f6f6f6;
}

.v-btn:not(.v-btn--round).v-size--small.participate-btn {
  height: 34px;
}
</style>
