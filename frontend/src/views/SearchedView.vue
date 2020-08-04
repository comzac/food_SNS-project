<template>
  <div>
    <v-card max-width="614" class="mx-auto" flat>
      <v-container>
        <v-row class="mx-0 align-center">
          <h1 class="red--text text--lighten-2"># {{ keyword }}</h1>
          <v-spacer></v-spacer>
          <h3>게시물 {{ searched_items.length }}</h3>
          <v-spacer></v-spacer>
        </v-row>
      </v-container>
    </v-card>
    <SearchFeedList v-if="searched_items" :feeds="searched_items" />
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import { mapActions } from "vuex";

import SearchFeedList from "@/components/feed/search/SearchFeedList";
// @ is an alias to /src

export default {
  name: "Home",
  components: {
    SearchFeedList,
  },
  data() {
    return {
      keyword: this.$route.params.keyword,
      searched_items: [],
      overlay: true,
    };
  },
  methods: {
    ...mapActions("feeds", ["searchedKeyword"]),
    searchfeed(keyword) {
      const res = this.searchedKeyword(keyword);
      console.log(res);
      // SearchFeedList 에 데이터 props 로 넘겨야 하나?
      // 일단 데이터 + 데이터 갯수 넘겨줘야 함
    },
  },
  created() {
    this.searchfeed(this.keyword);
    setTimeout(() => {
      this.overlay = false;
    }, 800);
  },
  beforeRouteUpdate(to, from, next) {
    this.SET_USERPROFILEDATA(null);
    this.overlay = true;
    this.getUserPageData(to.params.uid);
    setTimeout(() => {
      this.overlay = false;
    }, 800);
    next();
  },
  beforeDestroy() {
    this.SET_USERPROFILEDATA(null);
    this.overlay = true;
  },
};
</script>
