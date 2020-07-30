<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card
            :class="`elevation-${hover ? 24 : 6}`"
            class="transition-swing mx-auto"
            max-width="614"
          >
            <v-list-item>
              <router-link
                :to="{
                  name: 'UserDetail',
                  params: { uid: selectedFeed.user.uid },
                }"
                class="text-decoration-none"
              >
                <v-list-item-avatar color="#ff6666">
                  <img src="@/assets/profile_default.png" width="40" />
                </v-list-item-avatar>
              </router-link>
              <router-link
                :to="{
                  name: 'UserDetail',
                  params: { uid: selectedFeed.user.uid },
                }"
                class="text-decoration-none"
              >
                <v-list-item-content>
                  <v-list-item-title
                    class="text-left red--text text--lighten-2"
                    >{{ selectedFeed.user.unick }}</v-list-item-title
                  >
                  <v-list-item-subtitle
                    class="text-left red--text text--lighten-2"
                    >{{ selectedFeed.user.uid }}</v-list-item-subtitle
                  >
                </v-list-item-content>
              </router-link>
              <v-spacer></v-spacer>
              <v-menu left bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="#ff6666" icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>

                <v-list class="text-center">
                  <v-list-item @click="moveToUpdateFeed">
                    <v-list-item-title class="blue--text text-lighten-2"
                      >게시글 수정</v-list-item-title
                    >
                  </v-list-item>
                  <v-list-item @click="deleteFeedNow">
                    <v-list-item-title class="red--text text-lighten-2"
                      >게시글 삭제</v-list-item-title
                    >
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="red--text text-lighten-2"
                      >게시글 신고</v-list-item-title
                    >
                  </v-list-item>
                  <v-list-item @click="() => {}">
                    <v-list-item-title class="blue--text text-lighten-2"
                      >취소</v-list-item-title
                    >
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item>
            <v-img
              class="white--text"
              height="300px"
              src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
              <v-spacer>
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
                <br />
              </v-spacer>
              <span>Like_</span>
              <span>{{ selectedFeed.likeCount }}</span>
              <v-btn icon color="grey" v-if="!selectedFeed.like">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
              <v-btn icon color="#ff6666" v-else>
                <v-icon>mdi-heart</v-icon>
              </v-btn>
            </v-img>
            <v-card-text>
              <p class="text-left">
                <v-row class="space-around mx-0">
                  <strong>{{ selectedFeed.feed.title }}</strong>
                  <v-spacer></v-spacer>
                  <small>{{ ymd2 }}</small>
                </v-row>
              </p>
              <p class="text-left">{{ selectedFeed.feed.content }}</p>
              <div class="text-left">
                <p>#{{ feedhashtag.fid }}</p>
              </div>
              <!-- Comment module ?? -->
              <Comment :fid="selectedFeed.feed.id" />
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Comment from "@/components/Comment";
import { mapActions, mapState } from "vuex";

export default {
  name: "FeedView",
  components: {
    Comment,
  },
  computed: {
    ...mapState("feeds", ["selectedFeed"]),
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
  data() {
    return {
      feed: "",
      feedhashtag: "",
      feedlike: "",
      user: "",
      overflow: "text-left text-overflow",
      hashtag: {},

      ymd: "",
    };
  },
  methods: {
    ...mapActions("feeds", ["getFeedDetail", "deleteFeed"]),
    // fetchFeed() {
    //   this.feed = {
    //     id: 1,
    //     uid: 1, //?
    //     title: "엄마가 섬그늘에",
    //     content:
    //       "Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe harum quod exercitationem sapiente rerum deleniti, ipsa nesciunt voluptatum aspernatur similique labore optio commodi inventore expedita ad praesentium vel officia totam?",
    //     regdate: "2020-07-26T15:00:00.000+00:00",
    //     editdate: null,
    //   };
    //   this.feedhashtag = {
    //     fid: 1,
    //     hid: 1,
    //   };
    //   this.feedlike = {
    //     uid: 1,
    //     fid: 1,
    //   };
    //   this.user = {
    //     // 받아오는 것만 남긴다
    //     id: this.feed.uid,
    //     uid: "catcatcat",
    //     upw: "",
    //     unick: "YesYouCan",
    //     uemail: "",
    //     uregdate: "",
    //     ubirth: "",
    //     usex: "",
    //     roles: "",
    //   };
    //   this.ymd =
    //     parseInt(new Date().getTime() / 1000) -
    //     parseInt(new Date(this.selectedFeedfeed.regdate).getTime() / 1000);
    // },

    moveToUpdateFeed() {
      this.$router.push({
        name: "FeedCreateView",
        params: { fid: this.selectedFeed.feed.id },
      });
    },

    deleteFeedNow() {
      this.deleteFeed(this.$route.params.fid);
    },
  },
  created() {
    this.getFeedDetail(this.$route.params.fid).then((data) => {
      console.log(data);
      this.ymd =
        parseInt(new Date().getTime() / 1000) -
        parseInt(new Date(data.feed.regdate).getTime() / 1000);
    });
  },
};
</script>

<style scoped></style>
