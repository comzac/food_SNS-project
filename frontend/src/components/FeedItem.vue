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
            <Writer :user="user" :item="true" />

            <!-- 지울 것 ?? -->
            <v-img
              class="white--text text-right"
              height="300px"
              src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
              <v-spacer>
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
              </v-spacer>
              <span>Like_</span>
              <span>2387</span>
              <v-btn icon color="grey">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <v-btn icon color="#ff6666">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
            </v-img>

            <!-- 미디어 -->

            <v-card-text>
              <!-- 본문 -->
              <Main :feed="feed" :hashtag="hashtag" :flow="true" />
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

export default {
  name: "FeedItem",
  components: {
    Comment,
    Writer,
    Main,
  },
  props: {
    feed: Object,
    hashtag: Array,
    feedlike: Object,
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
