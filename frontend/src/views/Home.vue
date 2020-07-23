<template>
  <div class="home">
    <FeedItem />
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// @ is an alias to /src
import FeedItem from "@/components/FeedItem.vue";
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "Home",
  components: {
    FeedItem,
    HelloWorld
  }
};
</script>
