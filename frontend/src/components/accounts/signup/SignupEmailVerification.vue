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
      v-model="confirm"
      :messages="error.confirm"
      label="Verification Code"
      outlined
      solo
      required
      color="#424242"
      autocomplete="off"
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
    <div>
      <v-btn color="grey" class="white--text" @click="$emit('pageDown')"
        >뒤로가기</v-btn
      >
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        color="#ea907a"
        class="white--text"
        :disabled="!isSubmit"
        @click="verify()"
        >회원가입 완료</v-btn
      >
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

import { mapState } from "vuex";

export default {
  name: "SignupEmailVerification",
  created() {
    this.component = this;
  },
  watch: {
    confirm: function() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (this.confirm.length != 6)
        this.error.confirm = "확인 코드는 6자리 입니다.";
      else this.error.confirm = "";

      let isSubmit = true;

      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    verify() {
      // axios 보내고
      // 인증 완료 되서 넘어 오면

      const num = Number(this.confirm);
      // num === this.confirmCode.data ||
      if (num === this.confirmCode2) {
        this.$emit("finishSignup");
      } else {
        swal({
          text: "인증번호를 확인해주세요",
          dangerMode: true,
          buttons: [null, "확인"],
        });
      }
    },
  },
  data: () => {
    return {
      confirm: "",
      error: {
        confirm: "",
      },
      isSubmit: false,
      component: this,
    };
  },
  computed: {
    ...mapState("accounts", ["confirmCode"]),
    ...mapState("accounts", ["confirmCode2"]),
  },
  props: {
    // confirmCode: Object,
  },
};
</script>

<style></style>
