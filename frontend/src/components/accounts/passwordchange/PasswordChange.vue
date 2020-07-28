<template>
  <v-col cols="12" sm="8" md="6" lg="4">
    <h1 class="text-left red--text text--lighten-2 ml-3">비밀번호 변경</h1>
    <br />
    <br />
    <v-text-field
      v-model="password"
      :error-messages="error.password"
      label="Password"
      outlined
      solo
      required
      color="#ff6666"
      type="password"
      class="mt-10 mb-7"
    ></v-text-field>

    <v-text-field
      v-model="passwordConfirm"
      :error-messages="error.passwordConfirm"
      label="Password Confirmation"
      outlined
      solo
      required
      color="#ff6666"
      type="password"
      class="mt-10 mb-7"
    ></v-text-field>
    <br />
    <br />
    <v-btn
      color="#ff6666"
      class="white--text"
      :disabled="!isSubmit || password!=passwordConfirm"
      width="100%"
      x-large
      @click="changePassword"
    >비밀번호 변경</v-btn>
  </v-col>
</template>

<script>
import PV from "password-validator";

export default {
  name: "PasswordChange",
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password() {
      this.checkForm();
    },
    passwordConfirm() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (
        this.password.length > 7 &&
        this.passwordSchema.validate(this.password)
      )
        this.error.password = "";
      else this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";

      if (
        this.passwordConfirm.length > 7 &&
        this.passwordSchema.validate(this.passwordConfirm) &&
        this.passwordConfirm == this.password
      )
        this.error.passwordConfirm = "";
      else
        this.error.passwordConfirm =
          "영문,숫자 포함 8 자리이상이며 위와 같아야 합니다";

      let isSubmit = true;

      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    changePassword() {
      this.$emit("changePassword", this.password);
    },
  },
  data: () => {
    return {
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      error: {
        password: "",
        passowrdConfirm: "",
      },
      isSubmit: false,
      component: this,
    };
  },
};
</script>

<style>
</style>