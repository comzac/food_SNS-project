<template>
  <v-container class="signup" fill-height>
    <BasicForm v-if="page === 1" @toEmailVerification="setSignupData" />
    <SignupEmail v-if="page === 2" @toEmailVerification="emailVerification" @pageDown="page-=1" />
    <SignupEmailVerification
      v-if="page === 3"
      :confirmCode="confirmCode"
      @finishSignup="doSignup"
      @pageDown="page-=1"
    />
    <v-btn @click="page-=1" v-if="page!==1">뒤로가기</v-btn>
  </v-container>
</template>

<script>
import BasicForm from "@/components/accounts/signup/BasicForm.vue";
import SignupEmail from "@/components/accounts/signup/SignupEmail.vue";
import SignupEmailVerification from "@/components/accounts/signup/SignupEmailVerification.vue";
import { mapActions } from "vuex";

export default {
  name: "SignupView",
  components: {
    BasicForm,
    SignupEmail,
    SignupEmailVerification
  },
  data() {
    return {
      page: 1,
      signupData: {
        uid: null,
        upw: null,
        unick: null,
        uemail: null,
        ubirth: null,
        usex: null
      },
      confirmCode: ""
    };
  },
  methods: {
    ...mapActions("accounts", ["signup"]),
    setSignupData(signupData) {
      this.signupData = signupData;
      this.page += 1;
    },
    emailVerification(userEmailData) {
      this.confirmCode = userEmailData.confirmCode;
      this.signupData.uemail = userEmailData.userEmail;
      this.page += 1;
    },
    doSignup() {
      this.signup(this.signupData);
      alert("회원가입이 완료되었습니다.");
      this.$router.push({ name: "Home" });
    }
  }
};
</script>

<style>
</style>