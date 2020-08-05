<template>
  <router-link :to="listItem.link" class="text-decoration-none">
    <v-list-item>
      <v-list-item-icon>
        <v-icon>{{ listItem.icon }}</v-icon>
      </v-list-item-icon>
      <v-list-item-title>{{ listItem.title }}</v-list-item-title>
      <div v-if="listItem.title === 'Notification'">
        <v-list-item>{{ nonReadCount }}</v-list-item>
      </div>
    </v-list-item>
  </router-link>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NavigationListItem",
  props: {
    listItem: Object,
  },
  computed: {
    ...mapState("notifications", ["nonReadCount"]),
  },
  created() {
    const store = this.$store;
    if (this.listItem.title === "Notification") {
      setInterval(function () {
        store.dispatch("notifications/fetchNotifications");
      }, 30000);
    }
  },
};
</script>

<style>
</style>