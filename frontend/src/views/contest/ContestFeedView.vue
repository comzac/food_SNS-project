<template>
  <div>
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
              <!-- <Writer :user="selectedContestFeed.user" :item="false" /> -->
              <Head
                v-if="selectedContestFeed"
                :feed="selectedContestFeed.contestFeed"
              />
              <Media
                :dbFiles="selectedContestFeed.contestFeed.files"
                @likeUnlike="feedLU()"
              />
              <v-card-text>
                <!-- 본문 -->
                <Main
                  v-if="selectedContestFeed"
                  :feed="selectedContestFeed.contestFeed"
                  :flow="false"
                  :like="selectedContestFeed.islike"
                  :likeCount="selectedContestFeed.contestFeed.likeCount"
                  @likeUnlike="feedLU()"
                />
                <!-- Comment module ?? -->
              </v-card-text>
              <div v-if="selectedContestFeed.contestFeed.likeCount !== 0">
                <!-- <Gender v-if="sexData" :sexData="sexData" /> -->
                <SexChart v-if="sexData" :sexData="sexData" />
                <AgeChart v-if="ageData" :ageData="ageData" />
              </div>
              <div v-else>
                좋아요 데이터가 없습니다
                <v-spacer>
                  <br />
                </v-spacer>
              </div>
            </v-card>
          </v-hover>
        </v-col>
      </v-row>
    </v-container>
    <v-btn
      color="#ea907a"
      elevation="24"
      fixed
      bottom
      left
      small
      fab
      @click="back()"
      class="mb-14"
    >
      <v-icon color="#ffffff">mdi-arrow-left-bold</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";

import Head from "@/components/contest/ContestFeedHead";
import Main from "@/components/feed/item/Main";
import Media from "@/components/feed/item/Media";
import AgeChart from "@/components/charts/AgeChart";
import SexChart from "@/components/charts/SexChart";
// import Gender from "@/components/Gender";

export default {
  name: "FeedView",
  components: {
    Head,
    Main,
    Media,
    AgeChart,
    SexChart,
    // Gender,
  },
  computed: {
    ...mapState("contests", ["selectedContestFeed"]),
    ...mapGetters("accounts", ["authUserSex", "authUserAge"]),
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
      ymd: "",
      ageData: null,
      sexData: null,
    };
  },
  watch() {
    this.getContestFeedDetail(this.$route.params.fid).then((data) => {
      // console.log("watch");
      // console.log(data.statistics);
      this.ageData = data.statistics.uage;
      this.sexData = data.statistics.usex;
    });
  },
  methods: {
    back() {
      this.$router.push({ name: "ContestList" });
    },
    ...mapActions("contests", [
      "getContestFeedDetail",
      "contestFeedLikeUnlike",
    ]),
    feedLU() {
      // console.log("likeunlike");
      const likeData = {
        fid: this.$route.params.fid,
        like: this.selectedContestFeed.islike,
      };
      // console.log(likeData);
      this.contestFeedLikeUnlike(likeData).then(() => {
        if (this.selectedContestFeed.islike) {
          this.selectedContestFeed.contestFeed.likeCount -= 1;
          if (this.authUserSex === 1) {
            this.sexData.male -= 1;
          } else {
            this.sexData.female -= 1;
          }
          if (this.authUserAge >= 50) {
            this.ageData.age50 -= 1;
          } else if (this.authUserAge >= 40) {
            this.ageData.age40 -= 1;
          } else if (this.authUserAge >= 30) {
            this.ageData.age30 -= 1;
          } else if (this.authUserAge >= 20) {
            this.ageData.age20 -= 1;
          } else {
            this.ageData.age10 -= 1;
          }
        } else {
          this.selectedContestFeed.contestFeed.likeCount += 1;
          if (this.authUserSex === 1) {
            this.sexData.male += 1;
          } else {
            this.sexData.female += 1;
          }
          if (this.authUserAge >= 50) {
            this.ageData.age50 += 1;
          } else if (this.authUserAge >= 40) {
            this.ageData.age40 += 1;
          } else if (this.authUserAge >= 30) {
            this.ageData.age30 += 1;
          } else if (this.authUserAge >= 20) {
            this.ageData.age20 += 1;
          } else {
            this.ageData.age10 += 1;
          }
        }
        this.selectedContestFeed.islike = !this.selectedContestFeed.islike;
      });
    },
    initContestFeedDetail() {
      this.getContestFeedDetail(this.$route.params.fid).then((data) => {
        // console.log("data");
        // console.log(data.statistics);
        this.ageData = data.statistics.uage;
        this.sexData = data.statistics.usex;
        this.ymd =
          parseInt(new Date().getTime() / 1000) -
          parseInt(new Date(data.feed.regdate).getTime() / 1000);
      });
    },
  },
  created() {
    this.initContestFeedDetail();
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style scoped></style>
