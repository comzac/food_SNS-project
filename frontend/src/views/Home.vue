<template>
  <div class="home">
    <FeedItem :feed="feed" :feedhashtag="feedhashtag" :feedlike="feedlike" />
  </div>
</template>

<script>
// @ is an alias to /src
import FeedItem from "@/components/FeedItem.vue";

export default {
  name: "Home",
  components: {
    FeedItem
  },
  data() {
    return {
      feed: {
        id: 1,
        uid: 1, //?
        title: "엄마가 섬그늘에",
        content:
          "Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe harum quod exercitationem sapiente rerum deleniti, ipsa nesciunt voluptatum aspernatur similique labore optio commodi inventore expedita ad praesentium vel officia totam?",
        regdate: "2018-07-22",
        editdate: "2020-07-23"
      },
      feedhashtag: {
        fid: 1,
        hid: 1
      },
      feedlike: {
        uid: 1,
        fid: 1
      },
      page: 0,
      isEnd: false
    };
  },
  methods: {}
};
</script>
