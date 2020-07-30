<template>
  <div>
    <div class="home" v-for="(feed,i) in feeds" :key="i">
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
      feed_data: [
        {
          feed: {
            id: 1,
            uid: 1, //?
            title: "엄마가 섬그늘에",
            content:
              "Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe harum quod exercitationem sapiente rerum deleniti, ipsa nesciunt voluptatum aspernatur similique labore optio commodi inventore expedita ad praesentium vel officia totam?",
            regdate: "2020-07-26T15:00:00.000+00:00",
            editdate: null,
            dbFiles: [],
          },
          hashtag: [
            { id: 1, content: "치킨" },
            { id: 2, content: "치킨무" },
          ],
          feedlike: {
            uid: 1,
            fid: 1,
          },
        },
      ],
    };
  },
  methods: {
    ...mapActions("feeds", ["fetchFeeds"]),
    infiniteScroll() {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 30
      ) {
        // axios 로 받아와야 하는 부분
        const feed_data2 = JSON.parse(
          JSON.stringify(this.feed_data.slice(-1)[0])
        );
        feed_data2.feed.id += 1;
        this.feed_data.push(feed_data2);
        this.page += 1;
      }
    },
    top() {
      scrollTo(0, 0);
    },
  },
  mounted() {
    this.infiniteScroll();
    this.infiniteScroll();
    this.infiniteScroll();
  },
  created() {
    window.addEventListener("scroll", this.infiniteScroll);
    this.fetchFeeds();
  },
  destroyed() {
    window.removeEventListener("scroll", this.infiniteScroll);
  },
};
</script>
