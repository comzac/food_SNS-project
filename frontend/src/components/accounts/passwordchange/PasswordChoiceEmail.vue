<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ea907a;" v-if="isRetrieveIDPage">
      아이디 찾기
    </h1>
    <h1 class="text-left ml-3" style="color:#ea907a;" v-if="!isRetrieveIDPage">
      비밀번호 찾기
    </h1>
    <br />
    <br />
    <v-text-field
      v-model="email"
      :messages="error.email"
      @input="emailChecked = false"
      label="E-mail."
      outlined
      solo
      required
      autofocus
      color="#424242"
      class="mt-10 mb-7"
      autocomplete="off"
    >
      <v-icon
        slot="append"
        :color="emailChecked ? '' : '#ea907a'"
        @click="emailCheck2(email)"
        >mdi-check</v-icon
      >
    </v-text-field>
    <br />
    <br />
    <div>
      <v-btn
        color="grey"
        class="white--text"
        @click="$emit('pageDown'), $emit('go-back')"
        >뒤로가기</v-btn
      >
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        v-if="isRetrieveIDPage"
        :disabled="!emailChecked"
        @click="emailVerification(email)"
        color="#ea907a"
        class="white--text"
        >인증번호 받기</v-btn
      >
      <v-btn
        v-if="!isRetrieveIDPage"
        :disabled="!emailChecked"
        @click="emailVerification(email)"
        color="#ea907a"
        class="white--text"
        >인증번호 받기</v-btn
      >

      <v-overlay :value="overlay">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
    </div>
  </v-card>
</template>

<script>
import swal from "sweetalert";

import { mapActions } from "vuex";
import * as EmailValidator from "email-validator";

export default {
  name: "PasswordChoiceEmailView",
  data: () => {
    return {
      email: "",
      error: {
        email: "",
      },
      isSubmit: false,
      component: this,
      emailChecked: false,
      overlay: false,
    };
  },
  computed: {
    isRetrieveIDPage() {
      if (this.$route.name === "RetrieveID") return true;
      else return false;
    },
  },
  watch: {
    email: function() {
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
        // console.log(code.data);
        if (code.status === 200) {
          signupEmailComponent.overlay = !signupEmailComponent.overlay;
          swal("인증번호가 발송되었습니다.", { buttons: [null, "확인"] });
          this.$emit("toEmailVerification", {
            confirmCode: code.data,
            uemail: email,
          });
        } else {
          signupEmailComponent.overlay = !signupEmailComponent.overlay;
          swal({
            text: "인증번호 발송에 실패하였습니다.",
            icon: "error",
            dangerMode: true,
            buttons: [null, "확인"],
          });
        }
      });
    },
    emailCheck2(email) {
      this.emailCheck(email).then((res) => {
        if (res === true) {
          swal({
            text: "가입된 이메일이 아닙니다.",
            icon: "error",
            dangerMode: true,
            buttons: [null, "확인"],
          });
          this.emailChecked = false;
        } else {
          swal("가입된 이메일입니다.", { buttons: [null, "확인"] });
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
  created() {
    this.component = this;
  },
};
</script>

<style></style>
