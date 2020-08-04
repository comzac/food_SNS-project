<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <UserEdit v-if="isAuthorized && page=='1'" @pwChange="page='11', setPage(11)" />
        <PasswordChange v-if="isAuthorized && page == '11'" @changePassword="doPasswordReset" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";

import { mapActions } from "vuex";

import UserEdit from "@/components/accounts/useredit/UserEdit";
import PasswordChange from "@/components/accounts/passwordchange/PasswordChange";

export default {
  name: "UserEditView",
  components: {
    UserEdit,
    PasswordChange,
  },
  data() {
    return {
      isAuthorized: this.$store.state.accounts.isAuthorized,
      page: cookies.get("page") ? cookies.get("page") : 1,
    };
  },
  methods: {
    ...mapActions("accounts", ["setAuthorized", "setPage", "pwreset"]),
    doPasswordReset(password) {
      var changeData = {};
      changeData.upw = password;
      changeData.uid = this.$store.state.accounts.userSimpleData.uid;
      this.pwreset(changeData);
      this.setPage(1);
    },
  },
  created() {
    this.setAuthorized(false);
    this.setPage(1);
  },
};
</script>

<style>
</style>