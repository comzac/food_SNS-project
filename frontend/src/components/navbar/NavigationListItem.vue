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
import { mapActions } from "vuex";

export default {
  name: "NavigationListItem",
  props: {
    listItem: Object,
  },
  data() {
    return {
      notifications: null,
    };
  },
  methods: {
    ...mapActions("notifications", ["fecthNotifications"]),
    polling() {
      setInterval(function () {
        this.fetchNotifications();
      }, 2000);
    },
  },
  created() {
    this.polling();
  },
  beforeDestroy() {
    clearInterval(this.polling);
  },
};
</script>

<style>
</style>