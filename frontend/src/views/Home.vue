<template>
  <div>
    <v-tabs
      v-model="tab"
      background-color="transparent"
      color="#ff6666"
      centered
    >
      <v-tab>
        Follower
      </v-tab>
      <v-tab>
        All
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item :value="0">
        <FollowFeed />
      </v-tab-item>

      <v-tab-item :value="1">
        <FeedAll />
      </v-tab-item>
    </v-tabs-items>

    <!-- <v-row align="center" justify="center" class="mx-0 pb-5">
      <v-btn-toggle v-model="showFeedAll">
        <v-btn class="toggle">All</v-btn>
        <v-btn class="toggle">Follower</v-btn>
      </v-btn-toggle>
    </v-row>
    <FeedAll v-if="!showFeedAll" />
    <FollowFeed v-if="showFeedAll" /> -->
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
      tab: 0,
      showFeedAll: true,
    };
  },
  mounted() {
    this.$emit("change-page", 0);
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
