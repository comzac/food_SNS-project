<template>
  <div>
    <v-row class="mx-0 mb-2">
      <v-btn
        class="mr-1 like-btn"
        id="like-btn"
        color="#ff6666"
        icon
        x-small
        @click="$emit('likeUnlike'), animate()"
      >
        <i>
          <v-icon v-if="!like">mdi-heart-outline</v-icon>
        </i>
        <i>
          <v-icon v-if="like">mdi-heart</v-icon>
          <!-- <img v-if="!like" :src="imgRoute.unlike" alt />
        <img v-if="like" :src="imgRoute.like_small" alt s />-->
          <!-- <img v-if="like" :src="imgRoute.like_big" alt="" /> -->
        </i>
      </v-btn>
      <span style="font-size: 15px; margin-top: -2px;">{{ likeCount }}</span>
      <v-spacer></v-spacer>
      <v-btn
        v-if="$route.name != 'ContestFeed'"
        class="share-btn"
        color="#ea907a"
        icon
        x-small
        @click="share1()"
      >
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
      <v-btn
        v-else
        class="share-btn"
        color="#ea907a"
        icon
        x-small
        @click="share2()"
      >
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
    </v-row>
    <p class="text-left" :style="item ? 'cursor:pointer;' : 'cursor:default;'">
      <v-row class="space-around mx-0" @click="overflow2()">
        <strong class="grey--text text--darken-3 feed-title">{{
          feed.title
        }}</strong>
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
      >
        <v-chip outlined small color="grey darken-3" class="mb-2"
          ># {{ tag.content }}</v-chip
        >
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
    animate() {
      const a = document.getElementById("like-btn");
      a.classList.remove("like-btn");
      a.classList.add("like-btn");
    },
    overflow2() {
      if (this.flow) {
        this.$router.push({ name: "FeedView", params: { fid: this.feed.id } });
      }
    },
    share1() {
      var tempElement = document.createElement("textarea");
      document.body.appendChild(tempElement);
      tempElement.value = `honeycombo.online/feed/${this.feed.id}`;
      tempElement.select();
      document.execCommand("copy");
      document.body.removeChild(tempElement);
      swal("주소가 복사되었습니다.", { buttons: [null, "확인"] });
    },
    share2() {
      var tempElement = document.createElement("textarea");
      document.body.appendChild(tempElement);
      tempElement.value = `honeycombo.online/contest/${this.feed.id}`;
      tempElement.select();
      document.execCommand("copy");
      document.body.removeChild(tempElement);
      swal("주소가 복사되었습니다.", { buttons: [null, "확인"] });
    },
  },
  updated() {
    this.animate();
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

strong.grey--text.feed-title {
  font-size: 18px;
}

.like-btn i {
  animation: scaleBounce 0.3s alternate;
  animation-iteration-count: 2;
}

@keyframes scaleBounce {
  from {
    transform: scale(0.7);
  }
  to {
    transform: scale(1.5);
  }
}
</style>
