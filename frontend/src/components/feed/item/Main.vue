<template>
  <div>
    <v-row class="mx-0 mb-2">
      <v-btn
        class="mr-1"
        color="#ff6666"
        icon
        x-small
        @click="$emit('likeUnlike')"
      >
        <v-icon v-if="like">mdi-heart</v-icon>
        <v-icon v-if="!like">mdi-heart-outline</v-icon>
      </v-btn>
      <span style="font-size: 15px">{{ likeCount }}</span>
      <v-spacer></v-spacer>
      <v-btn color="#ff6666" icon x-small>
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-row>
    <p class="text-left">
      <v-row class="space-around mx-0">
        <strong>{{ feed.title }}</strong>
        <v-spacer></v-spacer>
        <small>{{ ymd2 }}</small>
      </v-row>
    </p>
    <p :class="overflow" @click="overflow2()">{{ feed.content }}</p>
    <div class="text-left" v-if="hashtag">
      <span
        v-for="tag in hashtag"
        :key="tag.id"
        @click="
          $router.push({
            name: 'SearchedView',
            params: { keyword: tag.content },
          })
        "
        ># {{ tag.content }}</span
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "Main",
  props: {
    feed: Object,
    hashtag: Array,
    flow: Boolean,
    like: Boolean,
    likeCount: Number,
  },
  data() {
    return {
      ymd:
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(this.feed.regdate).getTime() / 1000),
    };
  },
  computed: {
    overflow() {
      return this.flow ? "text-left text-overflow" : "text-left";
    },
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
  methods: {
    overflow2() {
      if (this.flow) {
        this.$router.push({ name: "FeedView", params: { fid: this.feed.id } });
      }
    },
  },
};
</script>

<style scoped>
p.text-overflow {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 222px;
}
</style>
