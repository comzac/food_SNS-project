<template>
  <div>
    <PasswordChoiceEmailView v-if="page === 1" @toEmailVerification="setConfirmCode" />
    <PasswordChoiceEmailVerification
      v-if="page === 2"
      :confirmCode="confirmCode"
      @toPasswordChange="toPasswordChange"
    />
    <PasswordChange v-if="page === 3" @changePassword="doPasswordReset" />
  </div>
</template>

<script>
import { mapActions } from "vuex";

import PasswordChoiceEmailView from "@/components/accounts/PasswordChange/PasswordChoiceEmailView.vue";
import PasswordChoiceEmailVerification from "@/components/accounts/PasswordChange/PasswordChoiceEmailVerification.vue";
import PasswordChange from "@/components/accounts/PasswordChange/PasswordChange.vue";

export default {
  name: "PasswordChoiceEmail",
  components: {
    PasswordChoiceEmailView,
    PasswordChoiceEmailVerification,
    PasswordChange
  },
  data() {
    return {
      page: 1,
      confirmCode: "",
      userEmailData: {
        uemail: "",
        upw: ""
      }
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
    }
  }
};
</script>

<style>
</style>