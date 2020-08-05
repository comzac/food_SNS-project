<template>
  <div :style="bgColor">
    <v-row>
      <!-- <v-subheader v-if="item.headers" :key="item.header">{{ item.headers }}</v-subheader> -->

      <v-list-item :key="item.title" ripple @click="confirmNotification">
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
    <v-divider :key="index"></v-divider>
  </div>
</template>

<script>
export default {
  name: "Notification",
  props: {
    item: Object,
    color: String,
  },
  computed: {
    bgColor() {
      return `background-color:#${this.color}`;
    },
  },
  methods: {
    confirmNotification() {
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
  craeted() {
    console.log(this.item);
  },
};
</script>

<style scoped>
</style>