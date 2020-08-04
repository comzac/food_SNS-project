<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <PasswordChoiceEmail v-if="page == '1'" @toEmailVerification="setConfirmCode" />
        <PasswordChoiceEmailVerification
          v-if="page == '2'"
          @toPasswordChange="toPasswordChange"
          @pageDown="page='1', setPage(1)"
        />
        <PasswordChange v-if="page == '3'" @changePassword="doPasswordReset" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";

import { mapActions } from "vuex";

import PasswordChoiceEmail from "@/components/accounts/passwordchange/PasswordChoiceEmail";
import PasswordChoiceEmailVerification from "@/components/accounts/passwordchange/PasswordChoiceEmailVerification";
import PasswordChange from "@/components/accounts/passwordchange/PasswordChange";

export default {
  name: "PasswordChoiceEmailView",
  components: {
    PasswordChoiceEmail,
    PasswordChoiceEmailVerification,
    PasswordChange,
  },
  data() {
    return {
      page: cookies.get("page") ? cookies.get("page") : 1,
      confirmCode: "",
      userEmailData: {
        uemail: this.$store.state.accounts.uemail,
        upw: "",
      },
    };
  },
  methods: {
    ...mapActions("accounts", ["pwreset", "setPage", "setEmail", "setCode"]),
    setConfirmCode(userEmailData) {
      this.userEmailData.uemail = userEmailData.uemail;
      this.setEmail(userEmailData.uemail);
      this.confirmCode = userEmailData.confirmCode;
      this.page = "2";
      this.setPage(2);
    },
    toPasswordChange() {
      this.page = "3";
      this.setPage(3);
    },
    doPasswordReset(password) {
      this.userEmailData.upw = password;
      this.pwreset(this.userEmailData);
      this.setPage(1);
      this.setEmail("");
      this.setCode("");
    },
  },
};
</script>

<style>
</style>