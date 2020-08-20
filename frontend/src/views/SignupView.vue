<template>
  <v-container class="signup" fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <BasicForm
          v-if="page == 1"
          @toEmailVerification="setSignupData"
          :signupData2="signupData"
        />
        <SignupEmail
          v-if="page == 2"
          @toEmailVerification="emailVerification"
          @pageDown="(page = '1'), setPage(1)"
        />
        <SignupEmailVerification
          v-if="page == 3"
          @finishSignup="doSignup"
          @pageDown="(page = '2'), setPage(2)"
        />
        <!-- 3번 째꺼  -->
        <!-- :confirmCode="confirmCode" -->
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import swal from "sweetalert";

import cookies from "vue-cookies";

import BasicForm from "@/components/accounts/signup/BasicForm";
import SignupEmail from "@/components/accounts/signup/SignupEmail";
import SignupEmailVerification from "@/components/accounts/signup/SignupEmailVerification";
import { mapActions } from "vuex";

export default {
  name: "SignupView",
  components: {
    BasicForm,
    SignupEmail,
    SignupEmailVerification,
  },
  data() {
    return {
      page: cookies.get("page") ? cookies.get("page") : 1,
      signupData: {},
      // confirmCode: "",
    };
  },
  methods: {
    ...mapActions("accounts", ["signup"]),
    ...mapActions("accounts", ["setPage"]),
    ...mapActions("accounts", ["setSignupData2"]),
    setSignupData(signupData) {
      this.setSignupData2(signupData);
      this.signupData = this.$store.state.accounts.signupData;
      // console.log(this.signupData);
      this.page = "2";
      this.setPage(this.page);
    },
    emailVerification(userEmailData) {
      // this.confirmCode = userEmailData.confirmCode;
      this.signupData = this.$store.state.accounts.signupData;
      this.signupData.uemail = userEmailData.userEmail;
      // console.log(this.signupData);
      this.setSignupData2(this.signupData);
      this.page = "3";
      this.setPage(this.page);
    },
    doSignup() {
      // console.log(this.signupData);
      this.signup(this.signupData);
      swal(
        "가입되었습니다!",
        `${this.signupData.unick} 님\n허니콤보에 오신 것을 환영합니다.`,
        "success",
        { buttons: [null, "확인"] }
      );
      this.setSignupData2({});
      this.setPage(1);
      this.$router.push({ name: "Home" });
    },
  },
  created() {
    this.signupData = this.$store.state.accounts.signupData;
    // console.log(this.signupData);
  },
};
</script>

<style></style>
