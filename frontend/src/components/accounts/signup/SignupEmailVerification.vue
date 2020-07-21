<template>
  <v-container class="fill-height" fluid>
    <!-- <h1>Email Verification</h1> -->
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-spacer>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
        </v-spacer>
        <v-text-field
          v-model="confirm"
          :error-messages="error.confirm"
          label="Verification Code"
          outlined
          solo
          required
          color="#ff6666"
        ></v-text-field>
        <v-spacer>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
        </v-spacer>

        <v-btn
          color="#ff6666"
          class="white--text"
          :disabled="!isSubmit"
          @click="verify()"
          width="100%"
          x-large
        >회원가입 완료</v-btn>
        <v-spacer>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
        </v-spacer>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "SignupEmailVerification",
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
      // console.log(typeof this.confirm)
      // console.log(typeof this.code.data)
      // console.log('click')
      // axios 보내고
      // 인증 완료 되서 넘어 오면
      const num = Number(this.confirm)
      // console.log(num)
      // console.log(this.code.data)
      if(num === this.code.data) {
        this.$emit('finishCheck')
        this.$router.push({ name: "Home" })
      }else {
        alert('인증번호를 확인해주세요.')
      }
    },
  },
  data: () => {
    return {
      confirm: "",
      error: {
        confirm: ""
      },
      isSubmit: false,
      component: this
    };
  },
  props: {
    code: String,
  }
};
</script>

<style>
</style>