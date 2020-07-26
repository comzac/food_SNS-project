<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <UserEdit />
    </v-row>
  </v-container>
</template>

<script>
import UserEdit from "@/components/accounts/useredit/UserEdit";
export default {
  components: {
    UserEdit,
  },
};
</script>

<style>
</style>