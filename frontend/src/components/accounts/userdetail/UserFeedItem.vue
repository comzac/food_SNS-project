<template>
  <!-- <v-img src="https://picsum.photos/350/165?random" :aspect-ratio="1" contain class="grey darken-4"></v-img> -->
  <v-img @click="moveToFeed" :aspect-ratio="1" contain class="grey darken-4"></v-img>
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

<style>
</style>