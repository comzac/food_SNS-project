<template>
  <v-list-item>
    <router-link
      v-if="!recommand"
      :to="{
        name: 'UserDetail',
        params: { uid: user.uid },
      }"
      class="text-decoration-none"
    >
      <v-list-item-avatar :color="imgRoute ? 'white' : 'grey'">
        <v-img v-if="imgRoute" :src="imgRoute" width="40" />
        <v-icon v-if="!imgRoute" dark>mdi-account</v-icon>
      </v-list-item-avatar>
    </router-link>
    <router-link
      v-if="!recommand"
      :to="{
        name: 'UserDetail',
        params: { uid: user.uid },
      }"
      class="text-decoration-none"
    >
      <v-list-item-content>
        <v-list-item-title class="text-left black--text">
          {{ user.unick }}
        </v-list-item-title>
        <v-list-item-subtitle class="text-left black--text">
          {{ user.uid }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </router-link>
    <v-list-item-content v-if="recommand">추천 피드</v-list-item-content>
    <v-spacer></v-spacer>
    <v-menu v-if="!item" left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn color="#ea907a" icon v-bind="attrs" v-on="on">
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </template>

      <v-list class="text-center">
        <v-list-item
          v-if="user.unick === authUserUnick"
          @click="moveToUpdateFeed"
        >
          <v-list-item-title class="blue--text text-lighten-2"
            >게시글 수정</v-list-item-title
          >
        </v-list-item>
        <v-list-item v-if="user.unick === authUserUnick" @click="deleteFeedNow">
          <v-list-item-title class="red--text text-lighten-2"
            >게시글 삭제</v-list-item-title
          >
        </v-list-item>
        <v-list-item v-if="user.unick !== authUserUnick" @click="reportFeed">
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
import { mapActions, mapGetters } from "vuex";
import SERVER from "@/api/api";

export default {
  name: "Writer",
  props: {
    user: Object,
    item: Boolean,
    recommand: Boolean,
  },
  computed: {
    ...mapGetters("accounts", ["authUserUnick"]),
    imgName() {
      if (this.user.uprofile) {
        return this.user.uprofile.name;
      } else return false;
    },
    imgRoute() {
      if (this.imgName) {
        return SERVER.MEDIA_DIR + this.imgName;
      } else return false;
    },
  },
  methods: {
    ...mapActions("feeds", ["deleteFeed"]),
    moveToUpdateFeed() {
      this.$router.push({
        name: "FeedCreateView",
        params: { fid: this.$route.params.fid },
      });
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
          this.deleteFeed(this.$route.params.fid);
        }
      });
    },

    reportFeed() {
      swal("신고가 접수되었습니다.", { buttons: [null, "확인"] });
    },
  },
};
</script>

<style></style>
