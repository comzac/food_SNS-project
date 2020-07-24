<template>
  <div>
    <PasswordChoiceEmailComponent v-if="page === 1" @toEmailVerification="setConfirmCode" />
    <PasswordChoiceEmailVerification
      v-if="page === 2"
      :confirmCode="confirmCode"
      @toPasswordChange="toPasswordChange"
      @pageDown="page-=1"
    />
    <PasswordChange v-if="page === 3" @changePassword="doPasswordReset" />
  </div>
</template>

<script>
import { mapActions } from "vuex";

import PasswordChoiceEmailComponent from "@/components/accounts/passwordchange/PasswordChoiceEmail.vue";
import PasswordChoiceEmailVerification from "@/components/accounts/passwordchange/PasswordChoiceEmailVerification.vue";
import PasswordChange from "@/components/accounts/passwordchange/PasswordChange.vue";

export default {
  name: "PasswordChoiceEmail",
  components: {
    PasswordChoiceEmailComponent,
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