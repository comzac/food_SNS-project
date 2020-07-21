<template>
    <div>
        <PasswordChoiceEmailView v-if='page === 1' @toEmailVerification='setConfirmCode'/>
        <PasswordChoiceEmailVerification v-if='page === 2' :confirmCode='confirmCode'/>
    </div>
</template>

<script>
import PasswordChoiceEmailView from "@/components/accounts/PasswordChange/PasswordChoiceEmailView.vue"
import PasswordChoiceEmailVerification from "@/components/accounts/PasswordChange/PasswordChoiceEmailVerification.vue"

export default {
    name: "PasswordChoiceEmail",
    components: {
        PasswordChoiceEmailView,
        PasswordChoiceEmailVerification
    },
    data() {
        return {
            page: 1,
            confirmCode: "",
        }
    },
    methods: {
        setConfirmCode( confirmCode ) {
            console.log("111")
            this.confirmCode = confirmCode
            this.page += 1
        },
    }
}
</script>

<style>

</style>