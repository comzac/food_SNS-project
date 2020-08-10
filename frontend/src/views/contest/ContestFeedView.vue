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
            <!-- <Writer :user="selectedContestFeed.user" :item="false" /> -->
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
</template>

<script>
import { mapActions, mapState } from "vuex";

// import Writer from "@/components/feed/item/Writer";
import Main from "@/components/feed/item/Main";
import Media from "@/components/feed/item/Media";
import AgeChart from "@/components/charts/AgeChart";
import SexChart from "@/components/charts/SexChart";

export default {
  name: "FeedView",
  components: {
    // Writer,
    Main,
    Media,
    AgeChart,
    SexChart,
  },
  computed: {
    ...mapState("contests", ["selectedContestFeed"]),
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
      // console.log("data");
      // console.log(data.statistics);
      this.ageData = data.statistics.uage;
      this.sexData = data.statistics.usex;
    });
  },
  methods: {
    ...mapActions("contests", [
      "getContestFeedDetail",
      "contestFeedLikeUnlike",
    ]),
    feedLU() {
      console.log("likeunlike");
      const likeData = {
        fid: this.$route.params.fid,
        like: this.selectedContestFeed.islike,
      };
      console.log(likeData);
      this.contestFeedLikeUnlike(likeData).then(() => {
        if (this.selectedContestFeed.islike) {
          this.selectedContestFeed.contestFeed.likeCount -= 1;
        } else {
          this.selectedContestFeed.contestFeed.likeCount += 1;
        }
        this.selectedContestFeed.islike = !this.selectedContestFeed.islike;
      });
    },
  },
  created() {
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
};
</script>

<style scoped></style>
