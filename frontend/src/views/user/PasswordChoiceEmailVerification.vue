<template>
  <v-container class="fill-height" fluid>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        md="6"
        lg="4"
      > 
        <h1 class="text-left blue--text">Retrieve Your Password</h1>
        <v-spacer><br><br><br><br><br><br><br><br><br></v-spacer>
        <v-text-field
          v-model="confirm"
          :error-messages="error.confirm"
          label="Verification Code"
          outlined
          solo
          required
        ></v-text-field>
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>

        <v-btn
          color="blue"
          :disabled="!isSubmit"
          @click="verify()"
          width="100%"
          x-large
        >-></v-btn>
        <v-spacer><br><br><br><br></v-spacer>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from '@/router'

export default {
  name: "PasswordChoiceEmailVerification",
  created() {
    this.component = this;
  },
  watch: {
    confirm: function() {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.confirm.length != 6)
        this.error.confirm = "확인 코드는 6자리 입니다.";
      else this.error.confirm = "";  

      let isSubmit = true;

      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    verify() {
      // axios 보내고
      // 인증 완료 되서 넘어 오면
      router.push({ name: 'PasswordChange'})
    },
  },
  data: () => {
    return {
      confirm: "",
      error: {
        confirm: "",
      },
      isSubmit: false,
      component: this,
    };
  }
}
</script>

<style>

</style>