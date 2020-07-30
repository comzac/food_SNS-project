<template>
  <div>
    <v-card max-width="614" class="mx-auto" flat>
      <v-container>
        <v-row class="mx-0 align-center">
          <h1 class="red--text text--lighten-2"># {{ keyword }}</h1>
          <v-spacer></v-spacer>
          <h3>게시물 4,010,650</h3>
          <v-spacer></v-spacer>
        </v-row>
      </v-container>
    </v-card>
    <SearchFeedList />
    <v-hover v-slot:default="{ hover }" open-delay="200">
      <v-btn
        :color="hover ? '#ef5656' : '#ff6666'"
        :elevation="hover ? 24 : 2"
        fixed
        small
        bottom
        right
        fab
        @click="top()"
      >
        <v-icon color="#ffffff">mdi-arrow-up-bold</v-icon>
      </v-btn>
    </v-hover>
  </div>
</template>

<script>
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
      timer: null,
    };
  },
  methods: {
    searchfeed(keyword) {
      console.log(keyword);
      // SearchFeedList 에 데이터 props 로 넘겨야 하나?
      // 일단 데이터 + 데이터 갯수 넘겨줘야 함
    },

    top() {
      scrollTo(0, 0);
    },
  },
  mounted() {
    this.searchfeed(this.keyword);
  },
};
</script>
