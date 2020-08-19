<template>
  <div>
    <div class="home" v-for="(feed, i) in feed_data" :key="i">
      <FeedItem :feed="feed" style="max-width: 614;" />
    </div>
    <v-container v-if="!follow" fill-height justify-center>
      <h2 class="text-center">
        팔로우 중인 회원이 없습니다.
      </h2>
    </v-container>
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
    ...mapState("feeds", ["followFeeds"]),
  },
  data() {
    return {
      // feed 를 page 를 증가시키면서 하나씩 받아와야 할 듯
      follow: true,
      page: 1,
      feedParams: {
        lastFid: 100000,
        lastFidRecommand: 0,
      },
      feed_data: [],
    };
  },
  methods: {
    ...mapActions("feeds", ["fetchFollowFeeds", "clearFollowFeeds"]),

    setFeeds(feeds) {
      const ids = [];
      this.feed_data.forEach((feed) => {
        if (!ids.includes(feed.feed.id)) {
          ids.push(feed.feed.id);
        }
      });
      feeds.forEach((feed) => {
        if (!ids.includes(feed.feed.id)) {
          this.feed_data.push(feed);
        }
      });
      // this.feed_data = this.feed_data.concat(feeds);
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
        this.fetchFollowFeeds(this.feedParams).then((newFeeds) => {
          // console.log("infinite scroll get");
          // this.feedParams.lastFidRecommand = 0;
          newFeeds.forEach((feed) => {
            if (feed.recommand) {
              this.feedParams.lastFidRecommand = feed.feed.id;
            } else {
              if (feed.feed.id < this.feedParams.lastFid) {
                this.feedParams.lastFid = feed.feed.id;
              }
            }
          });
          this.setFeeds(newFeeds);
        });
      }
    },
  },
  created() {
    // console.log("created");
    this.clearFollowFeeds();
    this.fetchFollowFeeds(this.feedParams)
      .then((newFeeds) => {
        // console.log("newFeeds", newFeeds);
        if (newFeeds.length) {
          this.follow = true;
          // console.log("not change");
        } else {
          this.follow = false;
          // console.log("change");
        }
        // console.log("infinite scroll get");
        newFeeds.forEach((feed) => {
          if (feed.recommand) {
            this.feedParams.lastFidRecommand = feed.feed.id;
          } else {
            if (feed.feed.id < this.feedParams.lastFid) {
              this.feedParams.lastFid = feed.feed.id;
            }
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
    // console.log("destroyed");
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>

<style scoped></style>
