<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-list two-line>
          <div
            style="text-align:center; margin-top:65px"
            v-if="
              nonReadNotification.length === 0 && readNotification.length === 0
            "
          >
            <img
              class="d-flex d-sm-none mx-auto"
              style="height: 300px; opacity:0.3"
              :src="beeImg"
            />
            <img
              class="d-none d-sm-flex mx-auto"
              style="height: 100px; opacity:0.3"
              :src="beeImg"
            />
            <template>
              <p style="opacity:0.5">알림이 없는데용 ^_^</p>
            </template>
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
  </v-container>
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
      beeImg: require("@/assets/bees/bee.png"),
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
    // console.log(this.$store);v
    this.getNotifications().then((res) => {
      // console.log(res);
      this.nonReadNotification = res.nonReadNotification;
      this.readNotification = res.readNotification;
    });
  },
  mounted() {
    this.$emit("change-page", 3);
    window.scrollTo(0, 0);
  },
};
</script>

<style></style>
