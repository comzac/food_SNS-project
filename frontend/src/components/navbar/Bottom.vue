<template>
  <v-bottom-navigation app background-color="#ea907a">
    <router-link :to="{ name: 'Home' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-home</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: 'SearchingView' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-magnify</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: 'ContestList' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-trophy</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: 'Liquor' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-glass-cocktail</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: 'NotificationView' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-bell</v-icon>
        <v-badge v-if="nonReadCount" color="#f4f7c5" dot class="ml-3"></v-badge>
      </v-btn>
    </router-link>
  </v-bottom-navigation>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Bottom",
  computed: {
    ...mapState("notifications", ["nonReadCount"]),
  },
};
</script>

<style></style>
