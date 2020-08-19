<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ea907a;">인증번호 입력</h1>
    <br />
    <br />
    <v-text-field
      v-model="confirm"
      :messages="error.confirm"
      label="Verification Code"
      outlined
      solo
      required
      color="#424242"
      class="mt-10 mb-7"
      autocomplete="off"
      @keypress.enter="verify"
    ></v-text-field>
    <br />
    <br />

    <div>
      <v-btn color="grey" class="white--text" @click="pageDown">뒤로가기</v-btn>
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        :disabled="!isSubmit"
        @click="verify"
        color="#ea907a"
        class="white--text"
        >다음으로</v-btn
      >
    </div>
  </v-card>
</template>

<script>
import swal from "sweetalert";

import { mapState } from "vuex";

export default {
  name: "PasswordChoiceEmailVerification",
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
      // console.log(num)
      if (num === this.confirmCode2) {
        this.$emit("moveToConductPage");
      } else {
        swal({
          text: "인증번호를 확인해주세요.",
          icon: "warning",
          dangerMode: true,
          buttons: [null, "확인"],
        });
      }
    },
    pageDown() {
      this.$emit("pageDown");
    },
    setDocumentTitle() {
      document.title = "인증번호 입력";
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
    ...mapState("accounts", ["confirmCode2"]),
  },
  props: {
    // confirmCode: String,
  },
  mounted() {
    this.setDocumentTitle();
  },
};
</script>

<style></style>
