<template>
  <div>
    <!-- 댓글 하나 -->
    <router-link
      :to="{ name: 'CommentView', params: { fid: fid } }"
      class="text-decoration-none"
      style="color: #ff6666;"
    >
      <p class="text-left ma-0">
        <strong>댓글 {{ comments.length }}개 모두 보기</strong>
      </p>
    </router-link>
    <v-row v-if="comments.length !== 0" class="ma-0 align-baseline">
      <p>
        <!-- unick 안넘어오는데? -->
        <strong>{{ comments[0].unick }}</strong>
      </p>
      <v-btn x-small icon disabled></v-btn>
      <p>{{ comments[0].content }}</p>
      <v-spacer></v-spacer>
      <v-btn x-small icon @click="likeComment()">
        <v-icon v-if="like" color="#ff6666">mdi-heart-outline</v-icon>
        <v-icon v-if="!like" color="#ff6666">mdi-heart</v-icon>
      </v-btn>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "Comment",
  components: {},
  props: {
    fid: Number,
    comments: Array,
  },
  data() {
    return {
      like: false,
    };
  },
  methods: {
    likeComment() {
      // 댓글 좋아요 함수
      this.like = !this.like;
    },
  },
  created() {
    // 댓글 개수, 최신 댓글 2개 가져오기?
  },
};
</script>

<style></style>
