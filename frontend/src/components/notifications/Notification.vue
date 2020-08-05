<template>
  <div>
    <v-subheader v-if="item.header" :key="item.header">{{ item.header }}</v-subheader>

    <v-divider v-else-if="item.divider" :key="index"></v-divider>

    <v-list-item v-else :key="item.title" ripple @click="confirmNotification">
      <v-list-item-content>
        <v-list-item-title v-if="item.state !== 1" v-html="item.title"></v-list-item-title>

        <v-list-item-subtitle v-if="item.state === 1">{{item.followid}} 님이 팔로우하였습니다.</v-list-item-subtitle>
        <v-list-item-subtitle
          v-if="item.state === 2"
        >{{ item.commentid }}님이 '{{ item.title }}' 글에 댓글을 남겼습니다.</v-list-item-subtitle>
        <v-list-item-subtitle
          v-if="item.state === 3"
        >{{ item.likeid }}님이 '{{ item.title }}' 글을 좋아합니다.</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </div>
</template>

<script>
export default {
  name: "Notification",
  props: {
    item: Object,
  },
  methods: {
    confirmNotification() {
      if (this.item.state === 1) {
        this.$router.push({
          name: "UserDetail",
          params: { uid: this.item.followid },
        });
      } else {
        this.$router.push({ name: "feed", params: { fid: this.item.fid } });
      }
    },
  },
};
</script>

<style>
</style>