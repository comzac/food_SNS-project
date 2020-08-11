<template>
  <v-list-item>
    <v-spacer></v-spacer>
    <v-menu left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
      </template>

      <v-list class="text-center">
        <v-list-item v-if="feed.uid === authUserId" @click="moveToUpdateFeed">
          <v-list-item-title class="blue--text text-lighten-2"
            >게시글 수정</v-list-item-title
          >
        </v-list-item>
        <v-list-item v-if="feed.uid === authUserId">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 삭제</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 신고</v-list-item-title
          >
        </v-list-item>
        <v-list-item @click="() => {}">
          <v-list-item-title class="blue--text text-lighten-2"
            >취소</v-list-item-title
          >
        </v-list-item>
      </v-list>
    </v-menu>
  </v-list-item>
</template>

<script>
import { mapGetters } from "vuex";
import router from "@/router";

export default {
  name: "ContestFeedHead",
  props: {
    feed: Object,
  },
  computed: {
    ...mapGetters("accounts", ["authUserId"]),
  },
  methods: {
    moveToUpdateFeed() {
      router.push({ name: "ContestFeedCreate", params: { fid: this.feed.id } });
    },
  },
};
</script>

<style></style>
