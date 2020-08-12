<template>
  <div>
    <v-row align="center" justify="center" class="pb-5">
      <v-btn-toggle v-model="showFeedAll">
        <v-btn class="toggle">
          All
        </v-btn>
        <v-btn class="toggle">
          Follower
        </v-btn>
      </v-btn-toggle>
    </v-row>
    <FeedAll v-if="!showFeedAll" />
    <FollowFeed v-if="showFeedAll" />
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
    };
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
