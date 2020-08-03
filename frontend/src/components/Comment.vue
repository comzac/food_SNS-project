<template>
  <div>
    <!-- 댓글 하나 -->
    <!-- <router-link
      :to="{ name: 'CommentView', params: { fid: fid } }"
      class="text-decoration-none"
      style="color: #ff6666;"
    >
      <p class="text-left ma-0">
        <strong>댓글 {{ comments.length }}개 모두 보기</strong>
      </p>
    </router-link>
    <v-row v-if="comments.length !== 0" class="ma-0 align-baseline">
    <p>-->
    <!-- unick 안넘어오는데? -->
    <!-- <strong>{{ comments[0].unick }}</strong>
      </p>
      <v-btn x-small icon disabled></v-btn>
      <p>{{ comments[0].content }}</p>
      <v-spacer></v-spacer>
      <v-btn x-small icon @click="likeComment()">
        <v-icon v-if="like" color="#ff6666">mdi-heart-outline</v-icon>
        <v-icon v-if="!like" color="#ff6666">mdi-heart</v-icon>
      </v-btn>
    </v-row>-->
    <router-link
      :to="{ name: 'CommentView', params: { fid: fid } }"
      class="text-decoration-none"
      style="color: #ff6666;"
    >
      <p class="text-left ma-0">
        <strong>댓글 {{ comments.length }}개 모두 보기</strong>
      </p>
    </router-link>
    <v-list-item class="ma-0 pa-0">
      <router-link
        :to="{ name: 'UserDetail', params: { uid: comment.uid } }"
        class="text-decoration-none"
      >
        <v-list-item-avatar color="#ff6666" width="56" height="56" class="ma-0 mr-1">
          <!-- comment 밑에 usernick 이랑 userprofile 같이 넘겨줘야 할듯?? -->
          <!-- <v-list-item-avatar
          class="ma-auto"
          :color="comment.user.uprofile.data ? 'white' : 'grey'"
        >
          <v-icon v-if="!comment.user.uprofile.data" dark>mdi-account</v-icon>
          <v-img
            v-if="comment.user.uprofile.dataa"
            :src="`data:${comment.user.uprofile.type};base64,${comment.user.uprofile.data}`"
            :alt="comment.user.uprofile.data"
          />
          </v-list-item-avatar>-->
          <img src="@/assets/profile_default.png" />
        </v-list-item-avatar>
      </router-link>
      <v-list-item-content class="text-left">
        <router-link
          :to="{ name: 'UserDetail', params: { uid: comment.uid } }"
          class="text-decoration-none"
        >
          <!-- <v-list-item-title class="black--text">{{ comment.user.unick }}</v-list-item-title> -->
        </router-link>
        <v-list-item-subtitle class="black--text">{{ comment.content }}</v-list-item-subtitle>
        <v-list-item-subtitle class="gray--text">
          {{ computeYMD(comment.regdate) }}
          {{ comment.editdate ? "(수정됨)" : "" }}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon color="#ff6666">mdi-heart</v-icon>
        <v-icon color="#ff6666">mdi-heart-outline</v-icon>
      </v-btn>
    </v-list-item>
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
  computed: {
    comment() {
      return this.comments[0];
    },
  },
  methods: {
    likeComment() {
      // 댓글 좋아요 함수
      this.like = !this.like;
    },
    computeYMD(regdate) {
      var ymd =
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(regdate).getTime() / 1000);
      var ymd2 = function (ymd) {
        if (ymd < 60) {
          return `${ymd}초 전`;
        } else if (ymd < 3600) {
          return `${parseInt(ymd / 60)}분 전`;
        } else if (ymd < 86400) {
          return `${parseInt(ymd / 3600)}시간 전`;
        } else if (ymd < 86400 * 2) {
          return `어제`;
        } else if (ymd < 86400 * 7) {
          return `${parseInt(ymd / 86400)}일 전`;
        } else {
          return `${parseInt(ymd / 604800)}주 전`;
        }
      };
      return ymd2(ymd);
    },
  },
  created() {
    // 댓글 개수, 최신 댓글 2개 가져오기?
  },
};
</script>

<style></style>
