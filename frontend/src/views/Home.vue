<template>
  <div>
    <v-row align="center" justify="center">
      <div @click="changeState">
        <v-btn v-if="showFeedAll" text color="#ff6666">All</v-btn>
        <v-btn v-else text>All</v-btn>
      </div>
      <div @click="changeState">
        <v-btn v-if="showFeedFollow" text color="#ff6666">Followers</v-btn>
        <v-btn v-else text>Followers</v-btn>
      </div>
    </v-row>
    <FeedAll v-if="showFeedAll" />
    <FollowFeed v-else />
  </div>
</template>

<script>
import FeedAll from "@/components/homepage/FeedAll";
import FollowFeed from "@/components/homepage/FollowFeed";

export default {
  components: {
    FeedAll,
    FollowFeed,
  },
  data() {
    return {
      showFeedAll: true,
      showFeedFollow: false,
    };
  },
  methods: {
    changeState() {
      this.showFeedAll = !this.showFeedAll;
      this.showFeedFollow = !this.showFeedFollow;
    },
  },
};
</script>

<style scoped>
.toggle .v-btn .v-btn--contained .theme--light .v-size--default {
  border-style: none;
}
.theme--light.v-btn:not(.v-btn--flat):not(.v-btn--text):not(.v-btn--outlined) {
  background-color: #ffffff;
}
.v-btn-toggle > .v-btn.v-btn {
  border-style: none;
}
</style>
