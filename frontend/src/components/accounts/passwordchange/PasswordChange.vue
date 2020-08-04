<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ff6666;">비밀번호 변경</h1>
    <br />
    <br />
    <v-text-field
      v-model="password"
      :error-messages="error.password"
      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="show1 = !show1"
      :type="show1 ? 'text' : 'password'"
      label="Password"
      outlined
      solo
      required
      color="#ff6666"
      class="mt-10 mb-7"
      autocomplete="off"
    ></v-text-field>

    <v-text-field
      v-model="passwordConfirm"
      :error-messages="error.passwordConfirm"
      :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="show2 = !show2"
      :type="show2 ? 'text' : 'password'"
      label="Password Confirmation"
      outlined
      solo
      required
      color="#ff6666"
      class="mt-10 mb-7"
      autocomplete="off"
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
  </v-card>
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
      show1: false,
      show2: false,
    };
  },
};
</script>

<style>
</style>