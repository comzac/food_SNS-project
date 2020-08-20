<template>
  <v-list-item>
    <p style="margin: auto; font-size: 20px">
      <strong>{{ feed.title }}</strong>
    </p>
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
        <v-list-item v-if="feed.uid === authUserId" @click="deleteFeedNow">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 삭제</v-list-item-title
          >
        </v-list-item>
        <v-list-item v-if="feed.uid !== authUserId" @click="() => {}">
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
import swal from "sweetalert";
import { mapGetters, mapActions } from "vuex";
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
    ...mapActions("contests", ["deleteContestFeed"]),
    moveToUpdateFeed() {
      router.push({ name: "ContestFeedCreate", params: { fid: this.feed.id } });
    },
    deleteFeedNow() {
      swal({
        title: "삭제하시겠습니까?",
        text: "이 작업은 취소 할 수 없습니다.",
        icon: "warning",
        dangerMode: true,
        buttons: ["취소", "확인"],
      }).then((doDelete) => {
        if (doDelete) {
          this.deleteContestFeed(this.$route.params.fid);
        }
      });
    },
  },
};
</script>

<style></style>
