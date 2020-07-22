<template>
  <v-container fill-height fluid>
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
          v-model="email"
          :error-messages="error.email"
          label="E-mail."
          outlined
          solo
          required
          autofocus
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
          width="100%"
          x-large
          @click="emailVerification(email)"
        >-></v-btn>
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
import { mapActions } from "vuex";
import * as EmailValidator from "email-validator";

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
    ...mapActions("accounts", ["getConfirmCode"]),
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
      this.getConfirmCode(email).then(code => {
        if (code !== "") {
          this.$emit("toEmailVerification", {
            confirmCode: code,
            userEmail: email
          });
        }
      });
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