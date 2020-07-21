<template>
  <v-container>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-text-field
          label="아이디"
          name="signup"
          prepend-icon="mdi-account"
          type="text"
          v-model="signupData.uid"
          color="#ff6666"
          append-outer-icon="mdi-check"
          autocapitalize='off'
          autocorrect='off'
          @click:append-outer="idcheck = !idcheck"
        ></v-text-field>

        <v-text-field
          label="별명"
          name="nickname"
          prepend-icon="mdi-account-box"
          type="text"
          v-model="signupData.unick"
          color="#ff6666"
          append-outer-icon="mdi-check"
          @click:append-outer="nickcheck = !nickcheck"
        ></v-text-field>

        <v-text-field
          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="show1 = !show1"
          :type="show1 ? 'text' : 'password'"
          label="비밀번호"
          name="password"
          prepend-icon="mdi-lock-outline"
          v-model="signupData.upw"
          color="#ff6666"
        ></v-text-field>

        <v-text-field
          :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="show2 = !show2"
          :type="show2 ? 'text' : 'password'"
          label="비밀번호 확인"
          name="password confirm"
          prepend-icon="mdi-lock-outline"
          v-model="signupData.upw2"
          color="#ff6666"
        ></v-text-field>

        <v-text-field
          label="생년월일"
          name="dob"
          prepend-icon="mdi-account"
          type="date"
          v-model="signupData.ubirth"
          color="#ff6666"
        ></v-text-field>

        <v-int-field
          label="성별"
          name="gender"
          prepend-icon="mdi-account"
          v-model="signupData.usex"
          color="#ff6666"
          readonly
        ></v-int-field>
        <v-radio-group v-model="signupData.usex" row>
          <v-radio color="#ff6666" label="male" value="1"></v-radio>
          <v-radio color="#ff6666" label="female" value="2"></v-radio>
        </v-radio-group>
        <!-- 영문, 숫자 혼용 확인 필요 -->
        <v-btn
          :disabled="!signupData.uid || !signupData.unick || !signupData.upw || !signupData.upw2 || !signupData.ubirth || !signupData.usex || signupData.upw2!=signupData.upw || !idcheck || !nickcheck || signupData.upw.length<8"
          @click="toEmailVerification()"
          color="#ff6666"
          class="white--text"
        >다음으로</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "BasicForm",
  data() {
    return {
      signupData: {
        uid: "",
        unick: "",
        upw: "",
        upw2: "",
        uemail: "",
        ubirth: "",
        usex: ""
      },
      signupData2: {
        uid: "아이디",
        unick: "별명",
        upw: "비밀번호",
        upw2: "비밀번호 확인",
        ubirth: "생년월일",
        usex: "성별"
      },
      show1: false,
      show2: false,
      idcheck: false,
      nickcheck: false
    };
  },
  methods: {
    ...mapActions(["idCheck", "nickCheck"]),
    toEmailVerification() {
      for (const [key, value] of Object.entries(this.signupData)) {
        if (key === "uemail") continue;
        if (value === "") {
          const key2 = this.signupData2[key];
          console.log(key2);
          alert(`${key2} 확인해주세요`);
          return;
        }
      }
      // 다른 방식 고려
      delete this.signupData.upw2;
      this.$emit("toEmailVerification", this.signupData);
    }
  }
};
</script>

<style>
</style>