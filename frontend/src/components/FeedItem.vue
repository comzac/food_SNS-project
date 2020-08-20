<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card
            :class="[
              `elevation-${hover ? 24 : 6}`,
              { 'recommanded-feed': feed.recommand },
            ]"
            class="transition-swing ma-auto"
            max-width="614"
            outlined
          >
            <!-- 작성자 -->
            <Writer
              :recommand="feed.recommand"
              :user="feed.user"
              :item="true"
            />

            <!-- 미디어 -->
            <Media :dbFiles="feed.feed.dbFiles" @likeUnlike="feedLU()" />
            <v-card-text>
              <!-- 본문 -->
              <Main
                :feed="feed.feed"
                :hashtag="feed.hashtag"
                :flow="true"
                :like="feed.like"
                :likeCount="feed.likeCount"
                :item="true"
                @likeUnlike="feedLU()"
              />
              <!-- 댓글 -->
              <Comment
                :fid="feed.feed.id"
                :comments="feed.comment"
                :commentCount="feed.commentCount"
              />
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

import Comment from "@/components/feed/comment/Comment";
import Writer from "@/components/feed/item/Writer";
import Main from "@/components/feed/item/Main";
import Media from "@/components/feed/item/Media";

export default {
  name: "FeedItem",
  components: {
    Comment,
    Writer,
    Main,
    Media,
  },
  props: {
    feed: Object,
  },
  data() {
    return {};
  },
  methods: {
    ...mapActions("feeds", ["feedLikeUnlike"]),
    feedLU() {
      // console.log("likeunlike");
      const likeData = { fid: this.feed.feed.id, like: this.feed.like };
      // console.log(likeData);
      this.feedLikeUnlike(likeData).then(() => {
        if (this.feed.like) {
          this.feed.likeCount -= 1;
        } else {
          this.feed.likeCount += 1;
        }
        this.feed.like = !this.feed.like;
      });
    },
  },
};
</script>

<style scoped>
.recommanded-feed {
  background-color: antiquewhite;
}
</style>
