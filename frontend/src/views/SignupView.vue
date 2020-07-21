<template>
  <div class='signup'>
    <BasicForm v-if='page === 1' @toEmailVerification='setSignupData' />
    <SignupEmail v-if='page === 2' @toEmailVerification='emailVerification' />
    <SignupEmailVerification v-if='page === 3' />
    <v-btn @click='page-=1' v-if='page!==1'>뒤로가기</v-btn>
  </div>
</template>

<script>
import BasicForm from '@/components/accounts/signup/BasicForm.vue'
import SignupEmail from '@/components/accounts/signup/SignupEmail.vue'
import SignupEmailVerification from '@/components/accounts/signup/SignupEmailVerification.vue'
import { mapActions } from 'vuex'

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
        unick: null,
        upw: null,
        uemail: null,
        ubirth: null,
        usex: null
      },
      confirmCode: ""
    };
  },
  methods: {
    setSignupData(signupData) {
      this.signupData = signupData;
      this.page += 1;
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
                usex: null,
            },
            confirmCode: "",
        }
    },
    methods: {
        ...mapActions(['signup']),
        setSignupData(signupData) {
            this.signupData = signupData
            this.page += 1
        },
        emailVerification( userEmailData ) {
            this.confirmCode = userEmailData.confirmCode
            this.signupData.uemail = userEmailData.userEmail
            this.page += 1
        },
        doSignup() {
            this.signup(this.signupData)
        }
    },
}
</script>

<style>
</style>