<template>
  <div>
    <router-link
      :to="{ name: 'CommentView', params: { fid: fid } }"
      class="text-decoration-none grey--text text--darken-3"
    >
      <p v-if="commentCount" class="text-left ma-0 comment-link">
        <strong>댓글 {{ commentCount }}개 모두 보기</strong>
      </p>
      <p v-else class="text-left ma-0">
        <strong>댓글 작성하기</strong>
      </p>
    </router-link>
    <v-list-item v-if="comments.length !== 0" class="ma-0 pa-0">
      <router-link
        :to="{ name: 'UserDetail', params: { uid: comment.user.uid } }"
        class="text-decoration-none"
      >
        <!-- comment 밑에 usernick 이랑 userprofile 같이 넘겨줘야 할듯?? -->
        <v-list-item-avatar
          class="mr-5"
          :color="commentHasProfileImg ? 'white' : 'grey'"
        >
          <v-icon v-if="!commentHasProfileImg" dark>mdi-account</v-icon>
          <v-img v-if="commentHasProfileImg" :src="commentProfileImgRoute" />
        </v-list-item-avatar>
      </router-link>
      <v-list-item-content class="text-left">
        <router-link
          :to="{ name: 'UserDetail', params: { uid: comment.user.uid } }"
          class="text-decoration-none"
        >
          <v-list-item-title class="black--text">{{
            comment.user.unick
          }}</v-list-item-title>
        </router-link>
        <v-list-item-subtitle class="black--text">{{
          comment.content
        }}</v-list-item-subtitle>
        <v-list-item-subtitle class="gray--text">
          {{ computeYMD(comment.regdate) }}
          {{ comment.editdate ? "(수정됨)" : "" }}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-spacer></v-spacer>
    </v-list-item>
  </div>
</template>

<script>
import SERVER from "@/api/api";

export default {
  name: "Comment",
  components: {},
  data() {
    return {
      media_dir: SERVER.MEDIA_DIR,
    };
  },
  props: {
    fid: Number,
    comments: Array,
    commentCount: Number,
  },
  computed: {
    comment() {
      return this.comments[0];
    },
    commentHasProfileImg() {
      if (this.comment.user.uprofile) {
        if (this.comment.user.uprofile.name) return true;
        else return false;
      } else return false;
    },
    commentProfileImgRoute() {
      if (this.commentHasProfileImg) {
        return this.media_dir + this.comment.user.uprofile.name;
      } else return "";
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
        parseInt(new Date(regdate).getTime() / 1000) +
        1;
      var ymd2 = function(ymd) {
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

<style scoped>
.comment-link {
  font-size: 15px;
}
</style>
