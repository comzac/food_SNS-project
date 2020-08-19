<template>
  <router-link :to="listItem.link" class="text-decoration-none">
    <v-list-item :value="value" :disabled="listItem.link.name == '' ? true : false">
      <v-list-item-icon>
        <v-icon>{{ listItem.icon }}</v-icon>
      </v-list-item-icon>
      <v-list-item-title>{{ listItem.title }}</v-list-item-title>
      <div v-if="listItem.title === '알림'">
        <span class="mr-1">{{ nonReadCount }}</span>
      </div>
    </v-list-item>
  </router-link>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: "NavigationListItem",
  props: {
    listItem: Object,
    value: Number,
  },
  computed: {
    ...mapState("notifications", ["nonReadCount"]),
    ...mapGetters("accounts", ["isLoggedIn"]),
  },
  methods: {
    ...mapActions("notifications", ["polling"]),
  },
  created() {
    if (this.listItem.title === "Navigation") {
      this.$store.dispatch("getNotifyCount", null, { root: true });
      this.polling(this.listItem.title);
    }
  },
};
</script>

<style></style>
