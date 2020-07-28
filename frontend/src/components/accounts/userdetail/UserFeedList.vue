<template>
  <v-card max-width="344" class="mx-auto" outlined>
    <v-container>
      <v-row>
        <v-col cols="4" v-for="feed in feeds" :key="feed.id">
          <UserFeedItem />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import UserFeedItem from "./UserFeedItem";
export default {
  components: {
    UserFeedItem,
  },
  data() {
    return {
      feeds: this.userDetailData.feed,
    };
  },
  props: {
    userDetailData: Object,
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>