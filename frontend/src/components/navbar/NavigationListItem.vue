<template>
  <router-link :to="listItem.link" class="text-decoration-none">
    <v-list-item>
      <v-list-item-icon>
        <v-icon>{{ listItem.icon }}</v-icon>
      </v-list-item-icon>
      <v-list-item-title>{{ listItem.title }}</v-list-item-title>
    </v-list-item>
  </router-link>
</template>

<script>
export default {
  name: "NavigationListItem",
  props: {
    listItem: Object,
  },
};
</script>

<style>
</style>