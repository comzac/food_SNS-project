<template>
  <div @click="moveToContestFeed" :class="{inRank: isInRank}">
    <v-col v-if="rank === 0" class="d-flex justify-center align-end">
      <v-icon large color="#FFD700" class="ml-n2">mdi-crown</v-icon>
      <h2 class="mr-n1">{{ rank+1 }}등</h2>
    </v-col>
    <v-col v-if="rank === 1" class="d-flex justify-center align-center my-1">
      <v-icon color="#C0C0C0">mdi-crown</v-icon>
      <h3>{{ rank+1}}등</h3>
    </v-col>
    <v-col v-if="rank === 2" class="d-flex justify-center align-center my-1">
      <v-icon color="#cd7f32">mdi-crown</v-icon>
      <h3>3등</h3>
    </v-col>
    <v-img v-if="!mediaRoute" :aspect-ratio="1" contain class="grey darken-3" />
    <v-img
      v-if="mediaRoute && mediaType != 'video/mp4'"
      :aspect-ratio="1"
      contain
      class="grey lighten-3 img-link"
      :class="{'mx-1': isInRank}"
      :src="mediaRoute"
    />
    <v-responsive
      v-if="mediaType == 'video/mp4'"
      class="align-center"
      :class="{'mx-1': isInRank}"
      aspect-ratio="1"
      style="background-color:#e0e0e0;"
    >
      <video
        :aspect-ratio="1"
        :src="mediaRoute"
        width="100%"
        height="100%"
        autoplay
        loop
        muted
        playsinline
      ></video>
    </v-responsive>

    <h5 class="text-center feed-title">{{ title }}</h5>
    <h5 class="text-center">
      <img src="@/assets/like/contest_like.png" class="like-icon mb-n1 mt-1" />
      {{ likeCount }}
    </h5>
  </div>
</template>

<script>
import SERVER from "@/api/api";

export default {
  props: {
    feed: Object,
    rank: Number,
  },
  computed: {
    fid() {
      return this.feed.id;
    },
    mediaType() {
      if (this.feed.files.length) {
        return this.feed.files[0].type;
      } else return false;
    },
    mediaName() {
      if (this.feed.files.length) {
        return this.feed.files[0].name;
      } else return false;
    },
    mediaRoute() {
      if (this.mediaName) {
        return SERVER.MEDIA_DIR + this.mediaName;
      } else return false;
    },
    likeCount() {
      return this.feed.likeCount;
    },
    isInRank() {
      if (this.rank >= 0 && this.rank < 3) return true;
      else return false;
    },
    title() {
      if (document.activeElement.clientWidth < 576) {
        if (this.feed.title.length > 7) {
          return this.feed.title.substring(0, 6) + "...";
        } else {
          return this.feed.title;
        }
      } else {
        return this.feed.title;
      }
    },
  },
  methods: {
    moveToContestFeed() {
      const ubirth = this.$store.state.accounts.userSimpleData.ubirth;
      const usex = this.$store.state.accounts.userSimpleData.usex;
      if (ubirth && usex) {
        this.$router.push({
          name: "ContestFeed",
          params: { fid: this.fid },
        });
      } else {
        this.$router.push({ name: "UserAdditionalDataEdit" });
      }
    },
  },
};
</script>

<style scoped>
.v-image {
  cursor: pointer;
}

.like-icon {
  width: 18px;
  height: 18px;
}

.inRank {
  border: 2px solid #ea907a;
  /* padding: 2px; */
}

h5.feed-title {
  cursor: pointer;
}
</style>
