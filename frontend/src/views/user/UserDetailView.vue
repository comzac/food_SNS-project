<template>
  <div>
    <UserProfile />
    <UserFeedList />
  </div>
</template>

<script>
import UserProfile from "@/components/accounts/userdetail/UserProfile";
import UserFeedList from "@/components/accounts/userdetail/UserFeedList";

export default {
  components: {
    UserProfile,
    UserFeedList,
  },
};
</script>

<style>
</style>