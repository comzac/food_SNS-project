<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card
            :class="`elevation-${hover ? 24 : 6}`"
            class="transition-swing mx-auto"
            max-width="614"
          >
            <!-- 작성자 -->
            <Writer
              :recommand="false"
              :user="selectedFeed.user"
              :item="false"
            />
            <Media
              :dbFiles="selectedFeed.feed.dbFiles"
              @likeUnlike="feedLU()"
            />
            <v-card-text>
              <!-- 본문 -->
              <Main
                v-if="selectedFeed"
                :feed="selectedFeed.feed"
                :hashtag="selectedFeed.hashtag"
                :flow="false"
                :like="selectedFeed.like"
                :likeCount="selectedFeed.likeCount"
                :item="false"
                @likeUnlike="feedLU()"
              />
              <!-- Comment module ?? -->
              <Comment
                :fid="selectedFeed.feed.id"
                :comments="selectedFeed.comment"
                :commentCount="selectedFeed.commentCount"
              />
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Comment from "@/components/feed/comment/Comment";
import { mapActions, mapState } from "vuex";

import Writer from "@/components/feed/item/Writer";
import Main from "@/components/feed/item/Main";
import Media from "@/components/feed/item/Media";

export default {
  name: "FeedView",
  components: {
    Comment,
    Writer,
    Main,
    Media,
  },
  computed: {
    ...mapState("feeds", ["selectedFeed"]),
    ymd2() {
      if (this.ymd < 60) {
        return `${this.ymd}초 전`;
      } else if (this.ymd < 3600) {
        return `${parseInt(this.ymd / 60)}분 전`;
      } else if (this.ymd < 86400) {
        return `${parseInt(this.ymd / 3600)}시간 전`;
      } else if (this.ymd < 86400 * 2) {
        return `어제`;
      } else if (this.ymd < 86400 * 7) {
        return `${parseInt(this.ymd / 86400)}일 전`;
      } else {
        return `${parseInt(this.ymd / 604800)}주 전`;
      }
    },
  },
  data() {
    return {
      ymd: "",
    };
  },
  methods: {
    ...mapActions("feeds", ["getFeedDetail"]),
    ...mapActions("feeds", ["feedLikeUnlike"]),
    feedLU() {
      // console.log("likeunlike");
      const likeData = {
        fid: this.$route.params.fid,
        like: this.selectedFeed.like,
      };
      // console.log(likeData);
      this.feedLikeUnlike(likeData).then(() => {
        if (this.selectedFeed.like) {
          this.selectedFeed.likeCount -= 1;
        } else {
          this.selectedFeed.likeCount += 1;
        }
        this.selectedFeed.like = !this.selectedFeed.like;
      });
    },
  },
  created() {
    this.getFeedDetail(this.$route.params.fid).then((data) => {
      // console.log(data);
      this.ymd =
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(data.feed.regdate).getTime() / 1000) +
        1;
    });
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style scoped></style>
