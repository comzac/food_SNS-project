<template>
  <v-bottom-navigation app background-color="#ff6666">
    <router-link :to="{ name: 'Home' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-home</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: '' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-television-play</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: '' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-music-note</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: 'Liquor' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-glass-cocktail</v-icon>
      </v-btn>
    </router-link>

    <router-link :to="{ name: '' }" class="text-decoration-none">
      <v-btn height="56">
        <v-icon color="#ffffff">mdi-image</v-icon>
      </v-btn>
    </router-link>
  </v-bottom-navigation>
</template>

<script>
export default {
  name: "Bottom",
};
</script>

<style></style>
