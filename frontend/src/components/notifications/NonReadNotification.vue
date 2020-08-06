<template>
  <div>
    <v-row style="background-color: #FFFADF">
      <!-- <v-subheader v-if="item.headers" :key="item.header">{{ item.headers }}</v-subheader> -->

      <v-list-item :key="item.title" ripple @click="confirmNotification">
        <v-avatar class="ml-3 mr-5" color="teal" size="40">
          <span class="white--text headline">SM</span>
        </v-avatar>
        <v-list-item-content>
          <v-list-item-title v-if="item.state !== 1" v-html="item.title"></v-list-item-title>

          <v-list-item-subtitle v-if="item.state === 1">{{item.followid}} 님이 팔로우하였습니다.</v-list-item-subtitle>
          <v-list-item-subtitle
            v-if="item.state === 3"
          >{{ item.commentid }}님이 '{{ item.title }}' 글에 댓글을 남겼습니다.</v-list-item-subtitle>
          <v-list-item-subtitle
            v-if="item.state === 2"
          >{{ item.likeid }}님이 '{{ item.title }}' 글을 좋아합니다.</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-row>
    <v-divider></v-divider>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "Notification",
  props: {
    item: Object,
  },
  methods: {
    ...mapActions("notifications", ["readNotification", "reduceNotifyCount"]),
    confirmNotification() {
      this.readNotification(this.item.id);
      this.reduceNotifyCount();
      if (this.item.state === 1) {
        this.$router.push({
          name: "UserDetail",
          params: { uid: this.item.followid },
        });
      } else {
        this.$router.push({ name: "FeedView", params: { fid: this.item.fid } });
      }
    },
  },
};
</script>

<style scoped>
</style>