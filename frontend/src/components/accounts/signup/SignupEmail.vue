<template>
  <v-card class="mx-auto" flat max-width="350">
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
      :messages="error.email"
      label="E-mail."
      @input="emailChecked = false"
      outlined
      solo
      required
      autofocus
      color="#424242"
      autocomplete="off"
    >
      <v-icon
        slot="append"
        :color="emailChecked ? '' : '#ea907a'"
        @click="emailCheck2(email)"
        >mdi-check</v-icon
      >
    </v-text-field>
    <v-spacer>
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
    </v-spacer>
    <div>
      <v-btn color="grey" class="white--text" @click="$emit('pageDown')"
        >뒤로가기</v-btn
      >
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
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
  </v-card>
</template>

<script>
import swal from "sweetalert";
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
    },
  },
  methods: {
    ...mapActions("accounts", ["getConfirmCode", "emailCheck"]),
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다.";
      } else if (!this.emailChecked) {
        this.error.email = "오른쪽 체크를 눌러서 이메일 중복 확인해주세요.";
      }

      let isSubmit = true;

      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    emailVerification(email) {
      // email 보내기 + 받아서
      const signupEmailComponent = this;
      signupEmailComponent.overlay = !signupEmailComponent.overlay;
      this.getConfirmCode(email).then((code) => {
        // console.log("code : ", code);
        if (code.status === 200) {
          signupEmailComponent.overlay = !signupEmailComponent.overlay;
          swal("인증번호가 발송되었습니다.", { buttons: [null, "확인"] });
          this.$emit("toEmailVerification", {
            confirmCode: code,
            userEmail: email,
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
        // console.log(res);
        // console.log(this.emailChecked);
        if (res === true) {
          swal("사용 가능한 이메일입니다.", { buttons: [null, "확인"] });
          this.emailChecked = true;
        } else {
          swal({
            text: "이미 사용 중인 이메일입니다.",
            dangerMode: true,
            buttons: [null, "확인"],
          });
          this.emailChecked = false;
        }
        if (this.emailChecked) {
          this.error.email = "";
        } else {
          this.error.email = "오른쪽 체크를 눌러서 이메일 중복 확인해주세요.";
        }
        // console.log(this.emailChecked);
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

<style></style>
