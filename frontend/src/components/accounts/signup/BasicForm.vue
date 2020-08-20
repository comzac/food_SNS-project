<template>
  <v-card class="mx-auto" flat max-width="350">
    <v-text-field
      label="아이디"
      name="signup"
      prepend-icon="mdi-account"
      type="text"
      v-model="signupData.uid"
      color="#424242"
      :messages="idcheck ? '' : '오른쪽의 체크를 눌러 중복확인해주세요'"
      @input="idcheck = false"
      autofocus
      autocapitalize="off"
      autocorrect="off"
      autocomplete="off"
    >
      <v-icon
        slot="append"
        :color="idcheck ? '' : '#ea907a'"
        @click="idCheck2(signupData.uid)"
        >mdi-check</v-icon
      >
    </v-text-field>

    <v-text-field
      label="별명"
      name="nickname"
      prepend-icon="mdi-account-box"
      type="text"
      v-model="signupData.unick"
      color="#424242"
      :messages="nickcheck ? '' : '오른쪽의 체크를 눌러 중복확인해주세요'"
      @input="nickcheck = false"
      autocomplete="off"
    >
      <v-icon
        slot="append"
        :color="nickcheck ? '' : '#ea907a'"
        @click="nickCheck2(signupData.unick)"
        >mdi-check</v-icon
      >
    </v-text-field>

    <v-text-field
      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="show1 = !show1"
      :type="show1 ? 'text' : 'password'"
      label="비밀번호"
      name="password"
      prepend-icon="mdi-lock-outline"
      v-model="signupData.upw"
      color="#424242"
      :messages="
        pwdCheck(signupData.upw)
          ? ''
          : '비밀번호는 영문과 숫자를 섞어서 8자 이상 되어야 합니다'
      "
      autocomplete="off"
    ></v-text-field>

    <v-text-field
      :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="show2 = !show2"
      :type="show2 ? 'text' : 'password'"
      label="비밀번호 확인"
      name="password confirm"
      prepend-icon="mdi-lock-outline"
      v-model="signupData.upw2"
      color="#424242"
      :messages="
        pwdCheck2(signupData.upw, signupData.upw2)
          ? ''
          : '비밀번호와 동일하게 입력해주세요'
      "
      autocomplete="off"
    ></v-text-field>

    <v-text-field
      label="생년월일"
      name="dob"
      prepend-icon="mdi-cake-variant"
      type="date"
      v-model="signupData.ubirth"
      color="#424242"
      onkeydown="return false"
      :messages="signupData.ubirth ? '' : '생년월일을 입력해주세요'"
      autocomplete="off"
      max="2100-12-31"
      min="1900-01-01"
    ></v-text-field>

    <v-radio-group v-model="signupData.usex" row>
      <v-btn icon color="#757575">
        <v-icon>mdi-gender-male-female</v-icon>
      </v-btn>
      <!-- <v-text-field
            prepend-icon="mdi-gender-male-female"
            flat
            dense
            readonly
            color="#ea907a"
            :error-messages="signupData.usex?'':'성별을 선택해주세요'"
            :value="signupData.usex==1?'남성':signupData.usex==2?'여성':''"
      ></v-text-field>-->
      <v-spacer></v-spacer>
      <v-radio color="#ea907a" label="남성" value="1"></v-radio>
      <v-btn icon :color="signupData.usex == 1 ? '#ea907a' : ''">
        <v-icon>mdi-gender-male</v-icon>
      </v-btn>
      <v-radio color="#ea907a" label="여성" value="2"></v-radio>
      <v-btn icon :color="signupData.usex == 2 ? '#ea907a' : ''">
        <v-icon>mdi-gender-female</v-icon>
      </v-btn>
      <v-spacer></v-spacer>
    </v-radio-group>
    <!-- 영문, 숫자 혼용 확인 필요 -->
    <div>
      <v-btn
        color="grey"
        class="white--text"
        @click="$router.push({ name: 'Login' }), setSignupData2({})"
        >뒤로가기</v-btn
      >
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        :disabled="
          !signupData.uid ||
            !signupData.unick ||
            !signupData.upw ||
            !signupData.upw2 ||
            !signupData.ubirth ||
            !signupData.usex ||
            !idcheck ||
            !nickcheck ||
            !pwdCheck(signupData.upw) ||
            !pwdCheck2(signupData.upw, signupData.upw2)
        "
        @click="toEmailVerification()"
        color="#ea907a"
        class="white--text"
        >다음으로</v-btn
      >
    </div>
  </v-card>
</template>

<script>
import swal from "sweetalert";

import { mapActions } from "vuex";

export default {
  name: "BasicForm",
  props: {
    signupData2: Object,
  },
  mounted() {
    if (this.signupData2 != {}) {
      this.signupData = this.signupData2;
    }
    // console.log(this.$refs.id);
  },
  data() {
    return {
      signupData: {
        uid: "",
        unick: "",
        upw: "",
        upw2: "",
        uemail: "",
        ubirth: "",
        usex: "",
      },
      show1: false,
      show2: false,
      idcheck: false,
      nickcheck: false,
    };
  },
  methods: {
    ...mapActions("accounts", ["idCheck", "nickCheck", "setSignupData2"]),
    toEmailVerification() {
      for (const [key, value] of Object.entries(this.signupData)) {
        if (key === "uemail") continue;
        if (value === "") {
          const key2 = this.signupData2[key];
          // console.log(key2);
          swal({
            text: `${key2} 확인해주세요.`,
            dangerMode: true,
            buttons: [null, "확인"],
          });
          return;
        }
      }
      // 다른 방식 고려
      delete this.signupData.upw2;
      this.$emit("toEmailVerification", this.signupData);
    },
    pwdCheck(upw) {
      let pattern1 = /[0-9]/;
      let pattern2 = /[A-Za-z]/;
      //특수문자 확인
      // let pattern3 = /[~!@#$%^&*()_+|<>?:{}]/;
      if (pattern1.test(upw) == false) {
        return false;
      }
      if (pattern2.test(upw) == false) {
        return false;
      }
      // if(pattern3.test(pwd) == false){
      //     return false;
      // }
      if (upw.length < 8) return false;
      return true;
    },
    pwdCheck2(upw, upw2) {
      if (!this.pwdCheck(upw2)) return false;
      if (upw !== upw2) return false;
      return true;
    },
    idCheck2(uid) {
      this.idCheck(uid).then((res) =>
        res ? (this.idcheck = true) : (this.idcheck = false)
      );
    },
    nickCheck2(unick) {
      this.nickCheck(unick).then((res) =>
        res ? (this.nickcheck = true) : (this.nickcheck = false)
      );
    },
  },
};
</script>

<style scoped></style>
