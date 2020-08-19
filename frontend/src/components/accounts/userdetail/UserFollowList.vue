<template>
  <div>
    <UserFollowListItem :user="user" v-for="user in userFollows" :key="user.uid" />
    <h2 class="text-center" v-if="followerView && emptyList">&#x3c;{{ authUserUnick }}> 님이</h2>
    <h2 class="text-center" v-if="followerView && emptyList">팔로우 중인 회원이 없습니다.</h2>
    <h2 class="text-center" v-if="followingView && emptyList">&#x3c;{{ authUserUnick }}> 님을</h2>
    <h2 class="text-center" v-if="followingView && emptyList">팔로우 하는 회원이 없습니다.</h2>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import UserFollowListItem from "./UserFollowListItem";

export default {
  name: "UserFollowList",
  components: {
    UserFollowListItem,
  },
  props: {
    userFollows: Array,
  },
  computed: {
    ...mapGetters("accounts", ["authUserUnick"]),
    followerView() {
      if (this.$route.name === "Follower") return true;
      else return false;
    },
    followingView() {
      if (this.$route.name === "Following") return true;
      else return false;
    },
    emptyList() {
      if (this.userFollows.length === 0) return true;
      else return false;
    },
  },
};
</script>

<style></style>
