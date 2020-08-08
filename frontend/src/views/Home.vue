<template>
  <div>
    <div class="home" v-for="(feed, i) in feed_data" :key="i">
      <FeedItem :feed="feed" style="max-width: 614;" />
    </div>
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
        document.body.offsetHeight - 10
      ) {
        // axios 로 받아와야 하는 부분
        // const feed_data2 = JSON.parse(
        //   JSON.stringify(this.feed_data.slice(-1)[0])
        // );
        // feed_data2.feed.id += 1;
        // this.feed_data.push(feed_data2);
        // this.page += 1;
        this.fetchFeeds(this.fid).then((newFeeds) => {
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
  },
  created() {
    console.log("created");
    this.clearFeeds();
    this.fetchFeeds(this.fid)
      .then((newFeeds) => {
        // console.log(newFeeds);
        newFeeds.forEach((feed) => {
          if (feed.feed.id < this.fid) {
            this.fid = feed.feed.id;
          }
        });
        this.setFeeds(newFeeds);
      })
      .then(() => {
        window.addEventListener("scroll", this.infiniteScroll);
      })
      .catch((err) => console.log(err.response));
  },
  mounted() {
    this.$emit("change-page", 0);
  },
  destroyed() {
    console.log("destroyed");
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>

<style scoped></style>
