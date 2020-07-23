<template>
  <v-card max-width="344" class="mx-auto" outlined>
    <v-container>
      <v-row>
        <v-col cols="4" v-for="item in items" :key="item">
          <UserFeedItem />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import UserFeedItem from "./UserFeedItem";
export default {
  data() {
    return {
      items: [
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
      ],
    };
  },
  components: {
    UserFeedItem,
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>