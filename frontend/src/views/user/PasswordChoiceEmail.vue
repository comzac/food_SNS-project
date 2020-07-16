<template>
  <v-container class="fill-height" fluid>
    <h1>Retrieve Your Password</h1>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        offset-sm="2"
        md="6"
        offset-md="3"
        lg="4"
        offset-lg="4"
      > 
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>
        <v-text-field
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          id="email"
          :error-messages="error.email"
          label="E-mail."
          reverse
          outlined
          solo
          required
        ></v-text-field>
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>

        <v-btn
          :class="{disabled : !isSubmit}"
          :disabled="!isSubmit"
          width="100%"
          x-large
          v-if="!isSubmit"
        >-></v-btn>
        <router-link to="/user/password_choice_email_verification" v-if="isSubmit">
          <v-text-field :disabled="!isSubmit" dark solo value="->" readonluy type="button"></v-text-field>
        </router-link>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import * as EmailValidator from "email-validator";

export default {
  name: "PasswordChoiceEmail",
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
      else this.error.email = false;  

      let isSubmit = true;

      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
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