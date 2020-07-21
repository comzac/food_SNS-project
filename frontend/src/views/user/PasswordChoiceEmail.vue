<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col cols="12" sm="8" md="6" lg="4">
        <h1 class="text-left">Retrieve Your Password</h1>
        <v-spacer>
          <br />
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
          v-model="email"
          :error-messages="error.email"
          label="E-mail."
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
          <br />
        </v-spacer>

        <v-btn
          color="#ff6666"
          class="white--text"
          :disabled="!isSubmit"
          width="100%"
          x-large
          @click="emailVerification()"
        >
          <i class="fa fa-arrow-right" aria-hidden="true"></i>
        </v-btn>
        <v-spacer>
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
import * as EmailValidator from "email-validator";

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
      this.$router.push({ name: "PasswordChoiceEmailVerification" });
    }
  },
  data: () => {
    return {
      email: "",
      error: {
        email: false
      },
      isSubmit: false,
      component: this
    };
  }
};
</script>

<style>
</style>