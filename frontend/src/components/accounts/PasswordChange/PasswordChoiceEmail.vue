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
          autofocus
          append-outer-icon="mdi-check"
          @click:append-outer="emailCheck2(email)"
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
        <div>
          <v-btn color="#ff6666" class="white--text" @click="$emit('pageDown')">뒤로가기</v-btn>
          <v-divider class="mr-5" vertical></v-divider>
          <v-btn
            :disabled="!emailChecked"
            @click="emailVerification(email)"
            color="#ff6666"
            class="white--text"
          >다음으로</v-btn>
        </div>
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
import { mapActions } from "vuex";
import * as EmailValidator from "email-validator";

export default {
  name: "PasswordChoiceEmailView",
  created() {
    this.component = this;
  },
  watch: {
    email: function () {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions("accounts", ["getConfirmCode", "emailCheck"]),
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다.";
      } else if (!this.emailChecked) {
        this.error.email = "오른쪽 체크를 눌러서 가입 여부를 확인해주세요";
      }

      let isSubmit = true;

      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    emailVerification(email) {
      // console.log(SERVER.URL)
      alert("잠시 기다려주세요.");
      this.getConfirmCode(email).then((code) => {
        if (code !== "") {
          this.$emit("toEmailVerification", {
            confirmCode: code,
            uemail: email,
          });
        }
      });
    },
    emailCheck2(email) {
      this.emailCheck(email).then((res) => {
        if (res === true) {
          alert("가입된 이메일이 아닙니다.");
          this.emailChecked = false;
        } else {
          alert("가입된 이메일입니다.");
          this.emailChecked = true;
        }
        if (this.emailChecked) {
          this.error.email = "";
        } else {
          this.error.email = "오른쪽 체크를 눌러서 가입 여부를 확인해주세요";
        }
      });
    },
  },
  data: () => {
    return {
      email: "",
      error: {
        email: false,
      },
      isSubmit: false,
      component: this,
      emailChecked: false,
    };
  },
};
</script>

<style>
</style>