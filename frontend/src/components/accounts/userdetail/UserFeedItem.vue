<template>
  <div>
    <v-img v-if="!imgData" :aspect-ratio="1" contain class="grey darken-4" />
    <v-img
      v-if="imgData"
      :aspect-ratio="1"
      contain
      class="grey lighten-2 img-link"
      :src="`data:${imgType};base64,${imgData}`"
      :alt="imgName"
      @click="moveToFeed"
    />
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
  methods: {
    moveToFeed() {
      this.$router.push({
        name: "FeedView",
        params: { fid: this.fid, uid: this.$route.params.uid },
      });
    },
  },
};
</script>

<style scoped>
.img-link {
  cursor: pointer;
}
</style>