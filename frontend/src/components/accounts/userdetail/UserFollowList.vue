<template>
  <div>
    <UserFollowListItem :user="user" v-for="user in userFollows" :key="user.uid" />
  </div>
</template>

<script>
import UserFollowListItem from "./UserFollowListItem";
export default {
  name: "UserFollowList",
  components: {
    UserFollowListItem,
  },
  props: {
    userFollows: Array,
  },
};
</script>

<style>
</style>