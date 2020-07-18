<template>
  <v-container fill-height fluid>
    <!-- <h1>Email Verification</h1> -->
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        md="6"
        lg="4"
      > 
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>
        <v-text-field
          v-model="email"
          :error-messages="error.email"
          label="E-mail."
          outlined
          solo
          required
          autofocus=""
        ></v-text-field>
        <v-spacer><br><br><br><br><br><br><br></v-spacer>

        <v-btn
          color="blue"
          :class="{disabled : !isSubmit}"
          :disabled="!isSubmit"
          width="100%"
          x-large
          @click="emailVerification(email)"
        >-></v-btn>
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import * as EmailValidator from "email-validator"
import SERVER from "@/api/api"
import axios from "axios"

export default {
  name: "Signupemail",
  created() {
    this.component = this;
  },  
  watch: {
    email: function() {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = "";  

      let isSubmit = true;

      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    emailVerification(email) {
      // email 보내기 + 받아서
      console.log(SERVER.URL)
      console.log(email)
      axios.post('http://localhost:8080/echeck', {"userEmail":email})
        .then(res => {
          console.log(res)
          return res
        })
        .then(confirmCode => {
          if(confirmCode === "fail") {
            alert('이메일을 확인해주세요.')
          }else{
            this.$emit('toEmailVerificationNumber', { "confirmCode": confirmCode, "userEmail": email })
          }
        })
        .catch(err => console.log(err.response))
    },
  },
  data: () => {
    return {
      email: "",
      error: {
        email: false,
      },
      isSubmit: false,
      component: this
    };
  }
}
</script>

<style>

</style>