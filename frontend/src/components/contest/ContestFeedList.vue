<template>
  <v-card class="mx-auto" flat max-width="975" outlined>
    <v-container class="mt-n3">
      <!-- <v-row>
        <v-col cols="4" class="d-flex justify-center align-end mb-n5">
          <v-icon large color="#FFD700">mdi-crown</v-icon>
          <h2 class="mt-2">1등</h2>
        </v-col>
        <v-col cols="4" class="d-flex justify-center align-center mb-n7">
          <v-icon color="#C0C0C0" class="mb-n2">mdi-crown</v-icon>
          <h3 class="mt-2">2등</h3>
        </v-col>
        <v-col cols="4" class="d-flex justify-center align-center mb-n7">
          <v-icon color="#cd7f32" class="mb-n2">mdi-crown</v-icon>
          <h3 class="mt-2">3등</h3>
        </v-col>
      </v-row>-->
      <v-row>
        <v-col cols="4" v-for="(feed, i) in contestFeeds" :key="feed.id">
          <ContestFeedListItem :rank="i" :feed="feed.contestFeed" />
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
