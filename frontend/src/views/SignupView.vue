<template>
  <div class="signup">
    <BasicForm v-if='page === 1' @toEmailVerification='setSignupData' />
    <SignupEmail v-if='page === 2' @toEmailVerification='emailVerification'/>
    <SignupEmailVerification v-if='page === 3' />
  </div>
</template>

<script>
import BasicForm from '@/components/accounts/signup/BasicForm.vue'
import SignupEmail from '@/components/accounts/signup/SignupEmail.vue'
import SignupEmailVerification from '@/components/accounts/signup/SignupEmailVerification.vue'

export default {
    name: 'SignupView',
    components: {
        BasicForm,
        SignupEmail,
        SignupEmailVerification,
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
        setSignupData(signupData) {
            this.signupData = signupData
            this.page += 1
        },
        emailVerification(confirmCode) {
            this.confirmCode = confirmCode
            this.page += 1
        }
    },
}
</script>

<style>

</style>