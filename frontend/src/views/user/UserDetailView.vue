<template>
  <div>
    <UserProfile v-if="userProfileData" :userDetailData="userProfileData" />
    <UserFeedList v-if="userProfileData" :userDetailData="userProfileData" />
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
    ...mapState("feeds", ["userProfileData"]),
  },
  methods: {
    ...mapActions("feeds", ["getUserPageData"]),
    ...mapMutations("feeds", ["SET_USERPROFILEDATA"]),
  },
  created() {
    this.getUserPageData(this.$route.params.uid);
    setTimeout(() => {
      this.overlay = false;
    }, 800);
  },
  beforeRouteUpdate(to, from, next) {
    this.SET_USERPROFILEDATA(null);
    this.overlay = true;
    this.getUserPageData(to.params.uid);
    setTimeout(() => {
      this.overlay = false;
    }, 800);
    next();
  },
  beforeDestroy() {
    this.SET_USERPROFILEDATA(null);
    this.overlay = true;
  },
  mounted() {
    this.$emit("change-page", 7);
  },
};
</script>

<style></style>
