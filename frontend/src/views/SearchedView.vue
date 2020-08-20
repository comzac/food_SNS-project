<template>
  <div>
    <v-card max-width="614" class="mx-auto" flat>
      <v-container>
        <v-row class="mx-0 align-center">
          <h1 style="color: #ea907a;"># {{ keyword }}</h1>
          <h1
            v-if="keyword2 !== ''"
            class="ml-4"
            style="color: #ea907a; white-space: pre-line;  word-break:break-all;"
          >
            # {{ keyword2 }}
          </h1>
        </v-row>
        <v-spacer></v-spacer>
        <v-text-field
          v-if="searched_items.length"
          v-model="keyword2"
          @input="changeItem()"
          append-icon="mdi-magnify"
          label="추가 검색어를 입력하세요"
          autofocus
          color="#ff6666"
          autocapitalize="off"
          autocorrect="off"
          autocomplete="off"
          class="mb-0 pb-0"
        ></v-text-field>

        <h2 v-if="!searched_items.length" class="mx-0" style="color: #424242;">
          관련된 게시글이 없습니다.
        </h2>
        <h2
          v-if="keyword2 !== '' && !searched_items2.length"
          class="mx-0"
          style="color: #424242;"
        >
          관련된 게시글이 없습니다.
        </h2>
      </v-container>
    </v-card>
    <SearchFeedList
      v-if="searched_items && keyword2 == ''"
      :feeds="searched_items"
    />
    <SearchFeedList v-if="keyword2 !== ''" :feeds="searched_items2" />

    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
    <v-btn
      color="#ea907a"
      elevation="24"
      fixed
      bottom
      left
      fab
      @click="back()"
      class="mb-14"
    >
      <v-icon color="#ffffff">mdi-arrow-left-bold</v-icon>
    </v-btn>
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
      keyword2: "",
      searched_items: [],
      searched_items2: [],
      overlay: true,
    };
  },
  methods: {
    ...mapActions("feeds", ["searchedKeyword"]),
    searchfeed(keyword) {
      this.searchedKeyword(keyword).then((res) => {
        this.searched_items = res.feedAll;
      });
      // SearchFeedList 에 데이터 props 로 넘겨야 하나?
      // 일단 데이터 + 데이터 갯수 넘겨줘야 함
    },
    back() {
      this.$router.go(-1);
    },
    changeItem() {
      this.keyword2 = this.keyword2.toUpperCase();
      // console.log(this.keyword2);
      this.searched_items2 = this.searched_items.filter((i) =>
        i.hashtag.some((j) => j.content.includes(this.keyword2))
      );
    },
  },
  created() {
    this.searchfeed(this.keyword);
    setTimeout(() => {
      this.overlay = false;
      // console.log(this.searched_items);
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
