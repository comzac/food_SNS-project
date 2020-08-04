<template>
  <div>
    <div class="home" v-for="(feed, i) in feed_data" :key="i">
      <FeedItem :feed="feed" style="max-width: 614;" />
    </div>
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
// @ is an alias to /src
import FeedItem from "@/components/FeedItem";
import { mapActions, mapState } from "vuex";

export default {
  name: "Home",
  components: {
    FeedItem,
  },
  computed: {
    ...mapState("feeds", ["feeds"]),
  },
  data() {
    return {
      // feed 를 page 를 증가시키면서 하나씩 받아와야 할 듯
      page: 1,
      fid: 100000,
      feed_data: [],
    };
  },
  methods: {
    ...mapActions("feeds", ["fetchFeeds", "clearFeeds"]),
    setFeeds(feeds) {
      this.feed_data = this.feed_data.concat(feeds);
    },
    infiniteScroll() {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 30
      ) {
        // axios 로 받아와야 하는 부분
        // const feed_data2 = JSON.parse(
        //   JSON.stringify(this.feed_data.slice(-1)[0])
        // );
        // feed_data2.feed.id += 1;
        // this.feed_data.push(feed_data2);
        // this.page += 1;
        this.fetchFeeds().then((newFeeds) => {
          console.log("infinite scroll get");
          newFeeds.forEach((feed) => {
            if (feed.feed.id < this.fid) {
              this.fid = feed.feed.id;
            }
          });
          this.setFeeds(newFeeds);
        });
      }
    },
    top() {
      scrollTo(0, 0);
    },
  },
  created() {
    window.addEventListener("scroll", this.infiniteScroll);
    this.fetchFeeds().then((newFeeds) => {
      // console.log(newFeeds);
      newFeeds.forEach((feed) => {
        if (feed.feed.id < this.fid) {
          this.fid = feed.feed.id;
        }
      });
      this.setFeeds(newFeeds);
    });
  },
  destroyed() {
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>
