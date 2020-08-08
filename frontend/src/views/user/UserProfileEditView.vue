<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <UserProfileEdit />
    </v-row>
  </v-container>
</template>

<script>
import UserProfileEdit from "@/components/accounts/useredit/UserProfileEdit";
export default {
  components: {
    UserProfileEdit,
  },
};
</script>

<style></style>
