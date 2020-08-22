<template>
  <div>
    <UserProfile v-if="userProfileData" :userDetailData="userProfileData" />
    <UserFeedList v-if="userProfileData" :userDetailData="userProfileData" />
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
    <v-btn
      color="#ea907a"
      elevation="24"
      fixed
      bottom
      left
      small
      fab
      @click="back()"
      class="mb-14"
    >
      <v-icon color="#ffffff">mdi-arrow-left-bold</v-icon>
    </v-btn>
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
    back() {
      this.$router.go(-1);
    },
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
