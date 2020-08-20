<template>
  <v-card class="mx-auto" flat max-width="975" outlined>
    <v-container>
      <v-row>
        <v-col cols="4" v-for="feed in feeds" :key="feed.id">
          <SearchFeedItem :feed="feed.feed" />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import SearchFeedItem from "@/components/feed/search/SearchFeedItem";

export default {
  name: "SearchFeedList",
  components: {
    SearchFeedItem,
  },
  props: {
    feeds: Array,
  },
  methods: {
    infiniteScroll() {
      // console.log("asdf");
    },
  },
  created() {
    window.addEventListener("scroll", this.infiniteScroll);
  },
  destroyed() {
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>
