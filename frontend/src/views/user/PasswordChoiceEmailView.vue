<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <IdCheck v-if="page == '1'" @toEmailCheck="toPageTwo" />
        <PasswordChoiceEmail
          v-if="page == '2'"
          @toEmailVerification="setConfirmCode"
          @pageDown="(page = '1'), setPage(1)"
        />
        <PasswordChoiceEmailVerification
          v-if="page == '3'"
          @moveToConductPage="moveToConductPage"
          @pageDown="(page = '2'), setPage(2)"
        />
        <PasswordChange
          v-if="page == '4'"
          @changePassword="doPasswordReset"
          @pageDown="(page = '3'), setPage(3)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";

import { mapActions } from "vuex";

import IdCheck from "@/components/accounts/passwordchange/IdCheck";
import PasswordChoiceEmail from "@/components/accounts/passwordchange/PasswordChoiceEmail";
import PasswordChoiceEmailVerification from "@/components/accounts/passwordchange/PasswordChoiceEmailVerification";
import PasswordChange from "@/components/accounts/passwordchange/PasswordChange";

export default {
  name: "PasswordChoiceEmailView",
  components: {
    IdCheck,
    PasswordChoiceEmail,
    PasswordChoiceEmailVerification,
    PasswordChange,
  },
  data() {
    return {
      page: cookies.get("page") ? cookies.get("page") : 1,
      confirmCode: "",
      userEmailData: {
        uid: this.$store.state.accounts.uid,
        uemail: this.$store.state.accounts.uemail,
        upw: "",
      },
    };
  },
  methods: {
    toPageTwo(id) {
      this.setId(id);
      this.page = "2";
      this.setPage(2);
    },
    ...mapActions("accounts", ["pwreset", "setPage", "setEmail", "setCode"]),
    setConfirmCode(userEmailData) {
      this.userEmailData.uemail = userEmailData.uemail;
      this.setEmail(userEmailData.uemail);
      this.confirmCode = userEmailData.confirmCode;
      this.page = "3";
      this.setPage(3);
    },
    moveToConductPage() {
      this.page = "4";
      this.setPage(4);
    },
    doPasswordReset(password) {
      // console.log(this.userEmailData);
      this.userEmailData.upw = password;
      this.pwreset(this.userEmailData);
      this.setPage(1);
      this.setEmail("");
      this.setCode("");
    },
    setId(id) {
      this.userEmailData.uid = id;
    },
  },
};
</script>

<style></style>
