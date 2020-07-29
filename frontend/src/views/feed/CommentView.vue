<template>
  <div>
    <v-container>
      <v-row class="text-center" align="center" justify="center">
        <v-col cols="12" sm="8" md="6" lg="4">
          <v-card flat class="text-left">
            <p>
              <strong>{{ feed.title }}</strong>
            </p>
            <p>{{ feed.content }}</p>
          </v-card>
          <v-spacer>
            <br />
          </v-spacer>
          <v-row class="ma-0">
            <v-avatar width="56" height="56">
              <!-- <v-icon>mdi-account</v-icon> -->
              <img src="@/assets/profile_default.png" />
            </v-avatar>
            <v-text-field
              rounded
              outlined
              label="댓글달기"
              type="text"
              v-model="commentData"
              color="#ff6666"
              append-icon="mdi-send"
              @click:append="createComment(commentData)"
              @keyup.enter="createComment(commentData)"
            ></v-text-field>
          </v-row>
          <div v-for="comment in comments" :key="comment.id">
            <v-list-item class="ma-0 pa-0">
              <router-link
                :to="{ name: 'UserDetail', params: { uid: comment.uid} }"
                class="text-decoration-none"
              >
                <v-list-item-avatar color="#ff6666" width="56" height="56" class="ma-0 mr-1">
                  <img src="@/assets/profile_default.png" />
                </v-list-item-avatar>
              </router-link>
              <v-list-item-content class="text-left">
                <router-link
                  :to="{ name: 'UserDetail', params: { uid: comment.uid} }"
                  class="text-decoration-none"
                >
                  <v-list-item-title class="black--text">{{ comment.unick }}</v-list-item-title>
                </router-link>
                <v-list-item-subtitle class="black--text">{{ comment.content }}</v-list-item-subtitle>
                <v-list-item-subtitle
                  class="gray--text"
                >{{ computeYMD(comment.regdate) }} {{ comment.editdate?'(수정됨)':'' }}</v-list-item-subtitle>
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
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import EditComment from "@/components/feed/comment/EditComment";

export default {
  name: "CommentView",
  components: { EditComment },
  data() {
    return {
      fid: this.$route.params.fid,
      feed: {
        title: "",
        content: "",
      },
      commentData: "",
      comments: [],
      page: 1,
    };
  },
  methods: {
    createComment(commentData) {
      this.page += 1;
      var comment2 = {
        id: this.page,
        content: commentData,
        unick: "댓글 작성자",
        uid: "fish8686",
        regdate: String(new Date()),
        editdate: "",
      };
      this.comments.unshift(comment2);
      this.commentData = "";
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
    // feed content 불러오기
    fetchContent(fid) {
      console.log(fid);
      this.feed.title = "엄마가 섬그늘에";
      this.feed.content =
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe harum quod exercitationem sapiente rerum deleniti, ipsa nesciunt voluptatum aspernatur similique labore optio commodi inventore expedita ad praesentium vel officia totam?";
    },
    // fid 활용해서 댓글 불러오기
    fetchComment(fid) {
      console.log(fid);
      var comment2 = {
        id: 1,
        content: "첫번째 댓글 테스트",
        unick: "TEST Kim",
        uid: "fish8863",
        regdate: "2020-07-26T15:00:00.000+00:00",
        editdate: "",
      };
      this.comments.unshift(comment2);
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
    this.fetchContent(this.fid);
    this.fetchComment(this.fid);
  },
};
</script>

<style>
</style>