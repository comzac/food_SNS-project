<template>
  <div>
    <UserProfile v-if="userDetailData" :userDetailData="userDetailData" />
    <UserFeedList v-if="userDetailData" :userDetailData="userDetailData" />
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import UserProfile from "@/components/accounts/userdetail/UserProfile";
import UserFeedList from "@/components/accounts/userdetail/UserFeedList";

export default {
  components: {
    UserProfile,
    UserFeedList,
  },
  computed: {
    ...mapState("feeds", ["userDetailData"]),
  },
  methods: {
    ...mapActions("feeds", ["getUserDetailData"]),
    ...mapMutations("feeds", ["SET_USERDETAILDATA"]),
  },
  created() {
    this.getUserDetailData(this.$route.params.uid);
  },
  beforeRouteUpdate(to, from, next) {
    this.SET_USERDETAILDATA(null);
    this.getUserDetailData(to.params.uid);
    next();
  },
  beforeDestroy() {
    this.SET_USERDETAILDATA(null);
  },
};
</script>

<style>
</style>