<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ff6666;">이메일로 찾기</h1>
    <br />
    <br />
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
      class="mt-10 mb-7"
    ></v-text-field>
    <br />
    <br />
    <div>
      <v-btn
        color="#ff6666"
        class="white--text"
        @click="$router.push({ name: 'PasswordChoice' })"
      >뒤로가기</v-btn>
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        :disabled="!emailChecked"
        @click="emailVerification(email)"
        color="#ff6666"
        class="white--text"
      >다음으로</v-btn>
      <v-overlay :value="overlay">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
    </div>
  </v-card>
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
      const signupEmailComponent = this;
      signupEmailComponent.overlay = !signupEmailComponent.overlay;
      this.getConfirmCode(email).then((code) => {
        if (code.status === 200) {
          signupEmailComponent.overlay = !signupEmailComponent.overlay;
          alert("인증번호가 발송되었습니다.");
          this.$emit("toEmailVerification", {
            confirmCode: code,
            uemail: email,
          });
        } else {
          signupEmailComponent.overlay = !signupEmailComponent.overlay;
          alert("인증번호 발송에 실패하였습니다.");
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
      overlay: false,
    };
  },
};
</script>

<style>
</style>