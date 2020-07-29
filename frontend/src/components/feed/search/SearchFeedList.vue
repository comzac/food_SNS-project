<template>
  <v-card class="mx-auto" flat max-width="975">
    <v-container>
      <v-row>
        <v-col cols="4" v-for="item in items" :key="item">
          <SearchFeedItem />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import SearchFeedItem from "@/components/feed/search/SearchFeedItem";
export default {
  components: {
    SearchFeedItem,
  },
  data() {
    return {
      last_page: 24,
      items: [
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
      ],
    };
  },
  methods: {
    infiniteScroll() {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 30
      ) {
        // axios 로 받아와야 하는 부분
        for (var i = 0; i < 9; i++) {
          this.last_page += 1;
          this.items.push(this.last_page);
        }
      }
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
</style>