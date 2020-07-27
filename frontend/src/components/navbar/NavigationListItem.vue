<template>
  <router-link :to="data.link" class="text-decoration-none">
    <v-list-item>
      <v-list-item-icon>
        <v-icon>{{ data.icon }}</v-icon>
      </v-list-item-icon>
      <v-list-item-title>{{ data.title }}</v-list-item-title>
    </v-list-item>
  </router-link>
</template>

<script>
export default {
  name: "NavigationListItem",
  props: {
    data: Object,
  },
};
</script>

<style>
</style>