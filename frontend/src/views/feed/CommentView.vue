<template>
  <div class="background">
    <v-text-field
      style="position: fixed; bottom: 0; width: 100%; z-index: 2;; justify: center;"
      solo
      flat
      outlined
      label="댓글달기"
      type="text"
      v-model="commentData.content"
      color="#424242"
      append-icon="mdi-send"
      @click:append="createComment(commentData)"
      @keyup.enter="createComment(commentData)"
      hide-details
      autocomplete="off"
      class="oldStyle"
    >
      <v-avatar
        slot="prepend-inner"
        class="ma-auto mr-2"
        :color="authUserImgRoute ? 'white' : 'grey'"
      >
        <v-icon max-width="40" max-height="40" v-if="!authUserImgRoute" dark
          >mdi-account</v-icon
        >
        <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
      </v-avatar>
    </v-text-field>
    <v-container class="mb-12">
      <v-row class="text-center" align="center" justify="center">
        <v-col cols="12">
          <v-card flat class="mx-auto myCard" max-width="614">
            <!-- <v-row class="ma-0 align-end"> -->
            <!-- <v-col cols="2">
                <v-list-item-avatar
                  class="ma-auto mr-2"
                  :color="authUserImgRoute ? 'white' : 'grey'"
                >
                  <v-icon v-if="!authUserImgRoute" dark>mdi-account</v-icon>
                  <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
                </v-list-item-avatar>
              </v-col> -->
            <!-- <v-col cols="12"> -->
            <!-- <v-text-field
              solo
              outlined
              label="댓글달기"
              type="text"
              v-model="commentData.content"
              color="#424242"
              append-icon="mdi-send"
              @click:append="createComment(commentData)"
              @keyup.enter="createComment(commentData)"
              hide-details
              autocomplete="off"
              class="oldStyle"
            >
              <v-avatar
                slot="prepend-inner"
                class="ma-auto mr-2"
                :color="authUserImgRoute ? 'white' : 'grey'"
              >
                <v-icon
                  max-width="40"
                  max-height="40"
                  v-if="!authUserImgRoute"
                  dark
                  >mdi-account</v-icon
                >
                <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
              </v-avatar>
            </v-text-field> -->
            <!-- </v-col> -->
            <!-- </v-row> -->
            <div
              v-for="(comment, idx) in comments.comments"
              :key="comment.comment.id"
            >
              <v-list-item class="ma-0 pa-0 align-start" :id="'a' + idx">
                <router-link
                  :to="{
                    name: 'UserDetail',
                    params: { uid: comment.comment.user.uid },
                  }"
                  class="text-decoration-none"
                >
                  <v-list-item-avatar
                    class="mr-2"
                    :color="commentHasProfileImg(comment) ? 'white' : 'grey'"
                  >
                    <v-icon v-if="!commentHasProfileImg(comment)" dark
                      >mdi-account</v-icon
                    >
                    <v-img
                      v-if="commentHasProfileImg(comment)"
                      :src="commentProfileImgRoute(comment)"
                    />
                  </v-list-item-avatar>
                </router-link>
                <v-list-item-content class="text-left">
                  <!-- <router-link
                    :to="{ name: 'UserDetail', params: { uid: comment.comment.user.uid } }"
                    class="text-decoration-none"
                  >-->
                  <v-list-item-title class="mb-1 black--text">
                    {{ comment.comment.user.unick }}
                    <span style="font-size: 0.7rem; color: grey;">{{
                      computeYMD(comment.comment.regdate)
                    }}</span>
                  </v-list-item-title>
                  <!-- </router-link> -->
                  <v-list-item-subtitle
                    class="black--text mb-1"
                    style="white-space:normal"
                  >
                    {{ comment.comment.content }}
                    <span style="font-size: 0.7rem; color: grey;">{{
                      comment.comment.editdate ? "(수정됨)" : ""
                    }}</span>
                  </v-list-item-subtitle>
                  <v-list-item-subtitle
                    class="gray--text"
                    style="font-size: 0.7rem"
                  >
                    <!-- {{ computeYMD(comment.comment.regdate) }} -->
                    <!-- {{ comment.comment.editdate ? "(수정됨)" : "" }} -->
                    좋아요 {{ comment.likeCount }}개
                  </v-list-item-subtitle>
                </v-list-item-content>
                <v-menu
                  v-if="authUserUnick === comment.comment.user.unick"
                  bottom
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="#ea907a" icon v-bind="attrs" v-on="on">
                      <v-icon>mdi-dots-horizontal</v-icon>
                    </v-btn>
                  </template>

                  <v-list class="text-center">
                    <!-- user 비교 필요 -->
                    <v-list-item
                      v-if="authUserUnick === comment.comment.user.unick"
                      @click="showEdit(idx)"
                    >
                      <v-list-item-title class="blue--text text-lighten-2"
                        >댓글 수정</v-list-item-title
                      >
                    </v-list-item>
                    <v-list-item
                      v-if="authUserUnick === comment.comment.user.unick"
                      @click="deleteCommentAndFetch(comment.comment.id)"
                    >
                      <v-list-item-title class="red--text text-lighten-2"
                        >댓글 삭제</v-list-item-title
                      >
                    </v-list-item>
                    <v-list-item
                      v-if="authUserUnick !== comment.comment.user.unick"
                      @click="() => {}"
                    >
                      <v-list-item-title class="red--text text-lighten-2"
                        >댓글 신고</v-list-item-title
                      >
                    </v-list-item>
                    <v-list-item @click="() => {}">
                      <v-list-item-title class="blue--text text-lighten-2"
                        >취소</v-list-item-title
                      >
                    </v-list-item>
                  </v-list>
                </v-menu>
                <v-btn
                  v-if="authUserUnick !== comment.comment.user.unick"
                  class="mr-1 mt-2"
                  :id="idx"
                  color="#ff6666"
                  icon
                  small
                  @click="
                    commentLike({
                      comment: comment,
                      idx: idx,
                    }),
                      animate(idx)
                  "
                >
                  <i>
                    <v-icon v-if="comment.islike">mdi-heart</v-icon>
                  </i>
                  <i>
                    <v-icon v-if="!comment.islike">mdi-heart-outline</v-icon>
                  </i>
                  <!-- <img class="like-btn" v-if="!comment.islike" :src="imgRoute.unlike" alt />
                  <img class="like-btn" v-if="comment.islike" :src="imgRoute.like_small" alt />-->
                  <!-- <img v-if="like" :src="imgRoute.like_big" alt="" /> -->
                </v-btn>
                <!-- <span>{{comment.likeCount }}</span> -->
              </v-list-item>
              <EditComment
                class="update"
                :id="'b' + idx"
                :idx="idx"
                :cid="comment.comment.id"
                style="display: none;"
                :comment="comment.comment"
                @edit-comment="editComment"
                @close-edit-comment="closeEditComment"
              />
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-btn
      color="#ea907a"
      elevation="24"
      fixed
      bottom
      left
      fab
      small
      @click="back()"
      class="mb-14"
    >
      <v-icon color="#ffffff">mdi-arrow-left-bold</v-icon>
    </v-btn>
  </div>
</template>

<script>
import swal from "sweetalert";
import EditComment from "@/components/feed/comment/EditComment";
import { mapActions, mapState, mapGetters } from "vuex";
import SERVER from "@/api/api";

export default {
  name: "CommentView",
  components: { EditComment },
  data() {
    return {
      edit: false,
      fid: this.$route.params.fid,
      commentData: {
        title: "",
        content: "",
      },
      comments: "",
      media_dir: SERVER.MEDIA_DIR,
      imgRoute: {
        like_small: require("@/assets/like/like_small.png"),
        like_big: require("@/assets/like/like_big.png"),
        unlike: require("@/assets/like/unlike.png"),
      },
    };
  },
  computed: {
    ...mapState("feeds", ["selectedFeed"]),
    // ...mapState("comments", ["comments"]),
    ...mapGetters("accounts", ["authUserImgRoute", "authUserUnick"]),
    commentHasProfileImg() {
      return (target) => {
        if (target.comment.user.uprofile) {
          if (target.comment.user.uprofile.name) return true;
          else return false;
        } else return false;
      };
    },
    commentProfileImgRoute() {
      return (target) => {
        if (this.commentHasProfileImg(target)) {
          return this.media_dir + target.comment.user.uprofile.name;
        } else return "";
      };
    },
  },
  methods: {
    animate(idx) {
      const a = document.getElementById(idx);
      a.classList.remove("like-btn");
      a.classList.add("like-btn");
    },
    ...mapActions("feeds", ["getFeedDetail"]),
    ...mapActions("comments", [
      "fetchComments",
      "insertComment",
      "deleteComment",
      "commentLikeUnlike",
    ]),
    createComment(commentData) {
      if (commentData.content !== "") {
        commentData.fid = this.fid;
        this.insertComment(commentData)
          .then(() => {
            this.commentData.content = "";
            this.fetchComments(this.fid);
          })
          .then(() => {
            this.comments = this.$store.state.comments;
          })
          .catch((err) => console.log(err.response));
      } else {
        swal("댓글을 입력하세요.", { buttons: [null, "확인"] });
      }
    },
    deleteCommentAndFetch(id) {
      swal({
        title: "삭제하시겠습니까?",
        text: "이 작업은 취소 할 수 없습니다.",
        icon: "warning",
        dangerMode: true,
        buttons: ["취소", "확인"],
      }).then((doDelete) => {
        if (doDelete) {
          this.deleteComment(id)
            .then(() => {
              this.fetchComments(this.fid);
            })
            .then(() => {
              this.comments = this.$store.state.comments;
            })
            .catch((err) => console.log(err.response));
        }
      });
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
    showEdit(idx) {
      // const update = document.getElementsByClassName("update");
      // update.forEach((item) => (item.style = "display: none;"));
      document.getElementById(`a${idx}`).style = "display: none;";
      document.getElementById(`b${idx}`).style = "display: show;";
    },

    editComment(idx) {
      // const update = document.getElementsByClassName("update");
      document.getElementById(`b${idx}`).style = "display: none;";

      // update.forEach((item) => (item.style = "display: none;"));
      this.fetchComments(this.fid).then(() => {
        this.comments = this.$store.state.comments;
      });
      // this.comments = this.$store.state.comments;
      // this.fetchComment(this.fid);
      document.getElementById(`a${idx}`).style = "display: show;";
    },

    closeEditComment(idx) {
      document.getElementById(`b${idx}`).style = "display: none;";
      document.getElementById(`a${idx}`).style = "display: show;";
    },

    commentLike(commentData) {
      // console.log(commentData.comment);
      const idx = commentData.idx;
      const commentLikeData = {
        isLike: commentData.comment.islike,
        cid: commentData.comment.comment.id,
      };
      // console.log(commentLikeData);
      // this.commentLikeUnlike(commentLikeData);
      // console.log(this.comments.comments[idx]);
      if (commentLikeData.isLike) {
        this.comments.comments[idx].likeCount -= 1;
      } else {
        this.comments.comments[idx].likeCount += 1;
      }
      // console.log("countcheck");
      this.comments.comments[idx].islike = !this.comments.comments[idx].islike;
    },

    back() {
      this.$router.push({
        name: "FeedView",
        params: { fid: this.selectedFeed.feed.id },
      });
      // this.$router.go(-1);
    },
  },
  created() {
    window.addEventListener("scroll", this.scrollY);
    this.getFeedDetail(this.fid);
    this.fetchComments(this.fid).then(() => {
      this.comments = this.$store.state.comments;
    });
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  destroyed() {
    window.removeEventListener("scroll", this.scrollY);
  },
};
</script>

<style scoped>
.oldStyle.v-text-field.v-input--dense
  .v-input__prepend-inner
  .v-input__icon
  > .v-icon {
  margin-top: 0 !important;
  padding: 0 !important;
}
/* div.background {
  background-color: #f3f1eb;
}
.myCard {
  background-color: #f3f1eb;
} */
div .v-list-item__subtitle .black--text {
  word-wrap: break-word;
}
img.like-btn {
  width: 18px;
  height: 18px;
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
