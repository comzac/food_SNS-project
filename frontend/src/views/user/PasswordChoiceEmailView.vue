<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <PasswordChoiceEmail v-if="page === 1" @toEmailVerification="setConfirmCode" />
        <PasswordChoiceEmailVerification
          v-if="page === 2"
          :confirmCode="confirmCode"
          @toPasswordChange="toPasswordChange"
          @pageDown="page-=1"
        />
        <PasswordChange v-if="page === 3" @changePassword="doPasswordReset" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
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
      page: 1,
      confirmCode: "",
      userEmailData: {
        uemail: "",
        upw: "",
      },
    };
  },
  methods: {
    ...mapActions("accounts", ["pwreset"]),
    setConfirmCode(userEmailData) {
      this.userEmailData.uemail = userEmailData.uemail;
      this.confirmCode = userEmailData.confirmCode;
      this.page += 1;
    },
    toPasswordChange() {
      this.page += 1;
    },
    doPasswordReset(password) {
      this.userEmailData.upw = password;
      this.pwreset(this.userEmailData);
    },
  },
};
</script>

<style>
</style>