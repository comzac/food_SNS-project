<template>
  <v-card class="mx-auto" flat max-width="975" outlined>
    <v-container>
      <v-row>
        <v-col cols="4" v-for="feed in contestFeeds" :key="feed.id">
          <ContestFeedListItem :feed="feed.contestFeed" />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapState } from "vuex";
import ContestFeedListItem from "./ContestFeedListItem";
export default {
  components: {
    ContestFeedListItem,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState("contests", ["contestFeeds"]),
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>
