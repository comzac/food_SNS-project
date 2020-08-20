<template>
  <div>
    <v-btn
      fab
      color="#ea907a"
      dark
      bottom
      left
      fixed
      small
      class="mb-14"
      @click="$router.go(-1)"
    >
      <v-icon>mdi-arrow-left-bold</v-icon>
    </v-btn>
    <v-card class="mx-auto mb-18" flat max-width="975" outlined>
      <h2
        class="text-left red--text text--lighten-2 ml-3 follow-title"
        v-if="followerView"
      >
        팔로워 목록
      </h2>
      <h2
        class="text-left red--text text--lighten-2 ml-3 follow-title"
        v-if="followingView"
      >
        팔로잉 목록
      </h2>

      <UserFollowList v-if="userFollows" :userFollows="userFollows" />
    </v-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import UserFollowList from "@/components/accounts/userdetail/UserFollowList";

export default {
  name: "UserFollowView",
  components: {
    UserFollowList,
  },
  computed: {
    ...mapState("accounts", ["userFollows"]),
    followerView() {
      if (this.$route.name === "Follower") return true;
      else return false;
    },
    followingView() {
      if (this.$route.name === "Following") return true;
      else return false;
    },
  },
  methods: {
    ...mapActions("accounts", ["getUserFollowList"]),
    initUserFollowList() {
      let mode;
      if (this.followerView) {
        mode = "follower";
      } else {
        mode = "following";
      }
      const option = { uid: this.$route.params.uid, mode };
      this.getUserFollowList(option);
    },
  },
  created() {
    this.initUserFollowList();
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}

h2.follow-title {
  margin-top: 14px;
  margin-bottom: 14px;
}
</style>
