
<template>
  <v-row justify="center">
    <v-col cols="12" sm="8" md="6">
      <v-list two-line>
        <div
          style="text-align:center;"
          v-if="nonReadNotification === null || readNotification === null"
        >
          <template>알림이 없습니다.</template>
        </div>
        <div v-else>
          <template v-for="(item, index) in notifyItems">
            <Notification :key="index" :item="item" />
          </template>
        </div>
      </v-list>
    </v-col>
  </v-row>
</template>

<script>
import Notification from "@/components/notifications/Notification";

import { mapGetters, mapActions, mapState } from "vuex";

export default {
  name: "NotificationView",
  components: {
    Notification,
  },
  computed: {
    ...mapGetters("notifications", ["notifyItems"]),
    ...mapState("notifications", ["nonReadNotification", "readNotification"]),
  },
  methods: {
    ...mapActions("notifications", ["getNotifications"]),
  },
  created() {
    console.log(this.$store);
    this.getNotifications();
  },
};
</script>

<style>
</style>