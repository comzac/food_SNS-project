<template>
  <!-- <v-img src="https://picsum.photos/350/165?random" :aspect-ratio="1" contain class="grey darken-4"></v-img> -->
  <v-img src :aspect-ratio="1" contain class="grey darken-4"></v-img>
</template>

<script>
export default {
  props: {
    feed: Object,
  },
};
</script>

<style>
</style>