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
            <!-- 작성자 -->
            <Writer :user="selectedFeed.user" :item="false" />

            <!-- 이미지 여러개 보이게 하는거 + data 제대로 안들어감 -->
            <!-- 비디오, 사진 미디어로 한번에 처리 ?? -->
            <!-- <div
              v-for="(file, i) in selectedFeed.feed.dbFiles"
              :key="i"
              id="files"
            >
              <v-responsive
                v-if="file.type === 'video/mp4'"
                v-show="i == i2"
                aspect-ratio="1"
                class="align-center"
              >
                <video
                  :id="i"
                  :src="
                    `data:${selectedFeed.feed.dbFiles[0].type};base64,${selectedFeed.feed.dbFiles[0].data}`
                  "
                  controls
                  type="video/mp4"
                  width="100%"
                  class="my-auto"
                ></video>
              </v-responsive>
              <v-img
                v-if="file.type !== 'video/mp4'"
                v-show="i == i2"
                :id="i"
                :src="
                  `data:${selectedFeed.feed.dbFiles[0].type};base64,${selectedFeed.feed.dbFiles[0].data}`
                "
                width="100%"
                aspect-ratio="1"
              ></v-img>
            </div>-->
            <!-- 슬라이더 -->
            <!-- <v-slider
              prepend-icon="mdi-chevron-double-left"
              @click:prepend="i2 > 1 ? (i2 -= 1) : (i2 = 0)"
              append-icon="mdi-chevron-double-right"
              @click:append="
                i2 < selectedFeed.feed.dbFiles.length - 1
                  ? (i2 += 1)
                  : (i2 = selectedFeed.feed.dbFiles.length - 1)
              "
              v-if="selectedFeed.feed.dbFiles.length > 0"
              v-model="i2"
              :max="selectedFeed.feed.dbFiles.length - 1"
              step="1"
              thumb-color="#ff6666"
              thumb-labels="always"
              thumb-size="40"
              id="slider"
            >
              <template v-slot:thumb-label>{{ i2 + 1 }}</template>
            </v-slider>
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
            </v-btn>-->
            <v-img
              class="white--text"
              height="300px"
              :src="
                `data:${selectedFeed.feed.dbFiles[0].type};base64,${selectedFeed.feed.dbFiles[0].data}`
              "
              :alt="selectedFeed.feed.dbFiles[0].name"
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
            <!-- 미디어 -->
            <Media :dbFiles="selectedFeed.dbFiles" />
            <v-card-text>
              <!-- 본문 -->
              <Main :feed="selectedFeed.feed" :hashtag="selectedFeed.hashtag" :flow="false" />
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

import Writer from "@/components/feed/item/Writer";
import Main from "@/components/feed/item/Main";
import Media from "@/components/feed/item/Media";

export default {
  name: "FeedView",
  components: {
    Comment,
    Writer,
    Main,
    Media,
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
      feedlike: "",
      user: "",
      overflow: "text-left text-overflow",
      hashtag: [],

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
