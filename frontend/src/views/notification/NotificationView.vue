
<template>
  <v-row justify="center">
    <v-col cols="12" sm="8" md="6">
      <v-list two-line>
        <div
          style="text-align:center;"
          v-if="nonReadNotification.length === 0 || readNotification.length === 0"
        >
          <template>알림이 없습니다.</template>
        </div>
        <div>
          <template v-for="item in nonReadNotification">
            <NonReadNotification :key="item.id" :item="item" />
          </template>
          <template v-for="item in readNotification">
            <Notification :key="item.id" :item="item" />
          </template>
        </div>
      </v-list>
    </v-col>
  </v-row>
</template>

<script>
import Notification from "@/components/notifications/Notification";
import NonReadNotification from "@/components/notifications/NonReadNotification";

import { mapActions } from "vuex";

export default {
  name: "NotificationView",
  components: {
    Notification,
    NonReadNotification,
  },
  data() {
    return {
      nonReadNotification: [],
      readNotification: [],
      // items: [
      //   {
      //     headers: "Non-Read",
      //   },
      //   { divider: true },
      //   // this.nonReadNotification,
      //   {
      //     headers: "Read",
      //   },
      //   { divider: true },
      //   // this.readNotification,
      // ],
      items: [],
    };
  },
  methods: {
    ...mapActions("notifications", ["getNotifications"]),
  },
  created() {
    // console.log(this.$store);
    this.getNotifications().then((res) => {
      // console.log(res);
      this.nonReadNotification = res.nonReadNotification;
      this.readNotification = res.readNotification;
    });
  },
};
</script>

<style>
</style>