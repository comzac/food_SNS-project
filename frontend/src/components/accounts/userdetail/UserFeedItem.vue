<template>
  <div
    @click="
      $router.push({
        name: 'FeedView',
        params: { fid: fid, uid: $route.params.uid },
      })
    "
    style="cursor: pointer;"
  >
    <!-- <v-img v-if="!mediaRoute" :aspect-ratio="1" contain class="grey darken-4" /> -->
    <v-img
      v-if="mediaType != 'video/mp4'"
      :aspect-ratio="1"
      contain
      class="grey lighten-2 img-link"
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
      if (this.feed.dbFiles.length) {
        return this.feed.dbFiles[0].type;
      } else return false;
    },
    mediaName() {
      if (this.feed.dbFiles.length) {
        return this.feed.dbFiles[0].name;
      } else return false;
    },
    mediaRoute() {
      if (this.mediaName) {
        return SERVER.MEDIA_DIR + this.mediaName;
      } else return false;
    },
  },
};
</script>

<style scoped>
.img-link {
  cursor: pointer;
}
</style>
