<template>
  <div>
    <UserProfile v-if="userDetailData" :userDetailData="userDetailData" />
    <UserFeedList v-if="userDetailData" :userDetailData="userDetailData" />
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import UserProfile from "@/components/accounts/userdetail/UserProfile";
import UserFeedList from "@/components/accounts/userdetail/UserFeedList";

export default {
  data() {
    return {
      overlay: true,
    };
  },
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
    setTimeout(() => {
      this.overlay = false;
    }, 800);
  },
  beforeRouteUpdate(to, from, next) {
    this.SET_USERDETAILDATA(null);
    this.overlay = true;
    this.getUserDetailData(to.params.uid);
    setTimeout(() => {
      this.overlay = false;
    }, 800);
    next();
  },
  beforeDestroy() {
    this.SET_USERDETAILDATA(null);
    this.overlay = true;
  },
};
</script>

<style>
</style>