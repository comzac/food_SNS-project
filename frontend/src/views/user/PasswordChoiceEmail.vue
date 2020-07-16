<template>
  <v-container class="fill-height" fluid>
    <h1>Retrieve Your Password</h1>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        md="6"
        lg="4"
      > 
        <v-spacer><br><br><br><br></v-spacer>
        <v-text-field
          v-model="email"
          :error-messages="error.email"
          label="E-mail."
          outlined
          solo
          required
        ></v-text-field>
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>

        <v-btn
          color="blue"
          :class="{disabled : !isSubmit}"
          :disabled="!isSubmit"
          width="100%"
          x-large
          @click="emailVerification()"
        >-></v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import * as EmailValidator from "email-validator";
import router from "@/router"

export default {
  name: "PasswordChange",
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
    emailVerification() {
      router.push({name: "PasswordChange"})
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