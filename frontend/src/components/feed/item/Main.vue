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
        <!-- <v-icon v-if="like">mdi-heart</v-icon>
        <v-icon v-if="!like">mdi-heart-outline</v-icon>-->
        <img v-if="!like" :src="imgRoute.unlike" alt />
        <img v-if="like" :src="imgRoute.like_small" alt s />
        <!-- <img v-if="like" :src="imgRoute.like_big" alt="" /> -->
      </v-btn>
      <span style="font-size: 15px">{{ likeCount }}</span>
      <v-spacer></v-spacer>
      <v-btn class="share-btn" color="#ea907a" icon x-small @click="share()">
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-row>
    <p class="text-left" :style="item ? 'cursor:pointer;' : 'cursor:default;'">
      <v-row class="space-around mx-0" @click="overflow2()">
        <strong>{{ feed.title }}</strong>
        <v-spacer></v-spacer>
        <small>{{ ymd2 }}</small>
      </v-row>
    </p>
    <p v-if="item" :class="overflow" @click="overflow2()">{{ feed.content }}</p>
    <p
      v-if="!item"
      style="white-space: pre-line; cursor: default;"
      class="text-left"
    >
      {{ feed.content }}
    </p>
    <div class="text-left mb-4" v-if="hashtag">
      <span
        class="mr-2"
        style="cursor: pointer;"
        v-for="tag in hashtag"
        :key="tag.id"
        @click="
          $router.push({
            name: 'SearchedView',
            params: { keyword: tag.content },
          })
        "
        ><v-chip outlined color="grey darken-3"> # {{ tag.content }} </v-chip>
      </span>
    </div>
  </div>
</template>

<script>
import swal from "sweetalert";

export default {
  name: "Main",
  props: {
    feed: Object,
    hashtag: Array,
    flow: Boolean,
    like: Boolean,
    likeCount: Number,
    item: Boolean,
  },
  data() {
    return {
      ymd:
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(this.feed.regdate).getTime() / 1000) +
        1,
      imgRoute: {
        like_small: require("@/assets/like/like_small.png"),
        like_big: require("@/assets/like/like_big.png"),
        unlike: require("@/assets/like/unlike.png"),
      },
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
    share() {
      var tempElement = document.createElement("textarea");
      document.body.appendChild(tempElement);
      tempElement.value = `honeycombo.online/feed/${this.feed.id}`;
      tempElement.select();
      document.execCommand("copy");
      document.body.removeChild(tempElement);
      swal("주소가 복사되었습니다.", { buttons: [null, "확인"] });
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
img {
  width: 18px;
  height: 18px;
}
p:hover {
  cursor: pointer;
}
</style>
