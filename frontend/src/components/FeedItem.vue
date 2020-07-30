<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card
            :class="`elevation-${hover ? 24 : 6}`"
            class="transition-swing ma-auto"
            max-width="614"
            outlined
          >
            <!-- 작성자 -->
            <Writer :user="feed.user" :item="true" />
            <!-- 미디어 -->
            <Media :dbFiles="feed.feed.dbFiles" />
            <v-card-text>
              <!-- 본문 -->
              <Main :feed="feed.feed" :hashtag="feed.hashtag" :flow="true" />
              <!-- 댓글 -->
              <Comment :fid="feed.id" />
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Comment from "@/components/Comment";
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
    return {
      user: {
        // 받아오는 것만 남긴다
        id: this.feed.uid,
        uid: "fish87",
        upw: "",
        unick: "Whoever Kim",
        uemail: "",
        uregdate: "",
        ubirth: "",
        usex: "",
        roles: "",
      },
    };
  },
};
</script>

<style scoped></style>
