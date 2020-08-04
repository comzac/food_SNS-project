<template>
  <div @click="$router.push({ name: 'FeedView', params: {fid: fid, uid: $route.params.uid}})">
    <v-img v-if="!imgData" :aspect-ratio="1" contain class="grey darken-4" />
    <v-img
      v-if="imgType!='video/mp4'"
      :aspect-ratio="1"
      contain
      class="grey lighten-2 img-link"
      :src="`data:${imgType};base64,${imgData}`"
      :alt="imgName"
    />
    <video
      v-if="imgType=='video/mp4'"
      :aspect-ratio="1"
      :src="`data:${imgType};base64,${imgData}`"
      :alt="imgName"
      width="100%"
      height="100%"
      autoplay
      loop
      muted
    ></video>
  </div>
</template>

<script>
export default {
  props: {
    feed: Object,
  },
  computed: {
    fid() {
      return this.feed.id;
    },
    imgData() {
      if (this.feed.dbFiles) {
        return this.feed.dbFiles[0].data;
      } else return false;
    },
    imgType() {
      if (this.feed.dbFiles) {
        return this.feed.dbFiles[0].type;
      } else return false;
    },
    imgName() {
      if (this.feed.dbFiles) {
        return this.feed.dbFiles[0].name;
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