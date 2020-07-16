<template>
  <div class="signup">
    <BasicForm v-if='page === 1' @toEmailVerification='setSignupData' />
  </div>
</template>

<script>
import BasicForm from '@/components/accounts/signup/BasicForm.vue'

export default {
    name: 'SignupView',
    components: {
        BasicForm
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
        }
    },
    methods: {
        setSignupData(signupData) {
            this.signupData = signupData
            this.page += 1
        }
    },
}
</script>

<style>

</style>