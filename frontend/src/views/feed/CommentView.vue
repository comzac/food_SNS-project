<template>
  <div>
    <v-container>
      <v-row class="text-center" align="center" justify="center">
        <v-col cols="12">
          <v-card flat class="text-left mx-auto" max-width="614">
            <p>
              <strong>{{ selectedFeed.feed.title }}</strong>
            </p>
            <p>{{ selectedFeed.feed.content }}</p>
          </v-card>
          <v-spacer>
            <br />
          </v-spacer>
          <v-card flat class="mx-auto" max-width="614">
            <v-row class="ma-0">
              <v-list-item-avatar class="ma-auto" :color="authUserImgData ? 'white' : 'grey'">
                <v-icon v-if="!authUserImgData" dark>mdi-account</v-icon>
                <v-img
                  v-if="authUserImgData"
                  :src="`data:${authUserImgType};base64,${authUserImgData}`"
                  :alt="authUserImgName"
                />
              </v-list-item-avatar>
              <v-text-field
                rounded
                outlined
                label="댓글달기"
                type="text"
                v-model="commentData.content"
                color="#ff6666"
                append-icon="mdi-send"
                @click:append="createComment(commentData)"
                @keyup.enter="createComment(commentData)"
                hide-details
              ></v-text-field>
            </v-row>
            <div v-for="comment in comments.comments" :key="comment.id">
              <v-list-item class="ma-0 pa-0">
                <router-link
                  :to="{ name: 'UserDetail', params: { uid: comment.uid } }"
                  class="text-decoration-none"
                >
                  <!-- <v-list-item-avatar color="#ff6666" width="56" height="56" class="ma-0 mr-1"> -->
                  <!-- comment 밑에 usernick 이랑 userprofile 같이 넘겨줘야 할듯?? -->
                  <v-list-item-avatar class="ma-auto" :color="authUserImgData ? 'white' : 'grey'">
                    <v-icon v-if="!authUserImgData" dark>mdi-account</v-icon>
                    <v-img
                      v-if="authUserImgData"
                      :src="`data:${authUserImgType};base64,${authUserImgData}`"
                      :alt="authUserImgName"
                    />
                  </v-list-item-avatar>
                  <!-- <img src="@/assets/profile_default.png" /> -->
                  <!-- </v-list-item-avatar> -->
                </router-link>
                <v-list-item-content class="text-left">
                  <router-link
                    :to="{ name: 'UserDetail', params: { uid: comment.uid } }"
                    class="text-decoration-none"
                  >
                    <v-list-item-title class="black--text">{{ comment.unick }}</v-list-item-title>
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
                <v-menu left bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
                      <v-icon>mdi-dots-horizontal</v-icon>
                    </v-btn>
                  </template>

                  <v-list class="text-center">
                    <!-- user 비교 필요 -->
                    <v-list-item @click="showEdit(comment.id)">
                      <v-list-item-title class="blue--text text-lighten-2">댓글 수정</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="() => {}">
                      <v-list-item-title class="red--text text-lighten-2">댓글 삭제</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="() => {}">
                      <v-list-item-title class="red--text text-lighten-2">댓글 신고</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="() => {}">
                      <v-list-item-title class="blue--text text-lighten-2">취소</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </v-list-item>
              <EditComment
                class="update"
                :id="comment.id"
                style="display: none;"
                :comment="comment"
                @editComment="editComment()"
              />
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-hover v-slot:default="{ hover }" open-delay="200">
      <v-btn
        :color="hover ? '#ef5656' : '#ff6666'"
        :elevation="hover ? 24 : 2"
        fixed
        small
        bottom
        right
        fab
        @click="top()"
      >
        <v-icon color="#ffffff">mdi-arrow-up-bold</v-icon>
      </v-btn>
    </v-hover>
  </div>
</template>

<script>
import EditComment from "@/components/feed/comment/EditComment";
import { mapActions, mapState, mapGetters } from "vuex";

export default {
  name: "CommentView",
  components: { EditComment },
  data() {
    return {
      fid: this.$route.params.fid,
      commentData: {
        title: "",
        content: "",
      },
      comments: "",
    };
  },
  computed: {
    ...mapState("feeds", ["selectedFeed"]),
    // ...mapState("comments", ["comments"]),
    ...mapGetters("accounts", [
      "authUserImgData",
      "authUserImgType",
      "authUserImgName",
    ]),
  },
  methods: {
    ...mapActions("feeds", ["getFeedDetail"]),
    ...mapActions("comments", ["fetchComments", "insertComment"]),
    createComment(commentData) {
      commentData.fid = this.fid;
      this.insertComment(commentData)
        .then(this.fetchComments(this.fid))
        .then(() => {
          this.comments = this.$store.state.comments;
        })
        .catch((err) => console.log(err.response));
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
    showEdit(comment_id) {
      const update = document.getElementsByClassName("update");
      update.forEach((item) => (item.style = "display: none;"));
      document.getElementById(`${comment_id}`).style = "display: show;";
    },
    editComment() {
      const update = document.getElementsByClassName("update");
      update.forEach((item) => (item.style = "display: none;"));

      // this.fetchComment(this.fid);
    },
  },
  created() {
    this.getFeedDetail(this.fid);
    this.fetchComments(this.fid).then(() => {
      this.comments = this.$store.state.comments;
    });
  },
};
</script>

<style scoped></style>
