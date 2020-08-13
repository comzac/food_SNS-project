<template>
  <div @click="
      moveToContestFeed
    ">
    <v-img v-if="!mediaRoute" :aspect-ratio="1" contain class="grey darken-3" />
    <v-img
      v-if="mediaRoute && mediaType != 'video/mp4'"
      :aspect-ratio="1"
      contain
      class="grey lighten-3 img-link"
      :src="mediaRoute"
    />
    <v-responsive
      v-if="mediaType == 'video/mp4'"
      class="align-center"
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
    <h5 class="text-center">
      <img src="@/assets/like/contest_like.png" class="like-icon mb-n1 mt-1" />
      {{likeCount}}
    </h5>
  </div>
</template>

<script>
import SERVER from "@/api/api";

export default {
  props: {
    feed: Object,
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
</style>
