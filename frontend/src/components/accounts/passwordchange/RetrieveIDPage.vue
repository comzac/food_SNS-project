<template>
  <div>
    <h2>회원님의 아이디는</h2>
    <h1>{{ uid }}</h1>
    <h2>입니다</h2>
    <br />
    <br />
    <br />
    <br />
    <br />
    <v-btn
      color="#ea907a"
      class="white--text"
      @click="$router.push({ name: 'Login' })"
      >로그인하기</v-btn
    >
    <v-divider class="mr-5" vertical></v-divider>
    <v-btn
      @click="$router.push({ name: 'PasswordChoiceEmail' })"
      color="#ea907a"
      class="white--text"
      >비밀번호찾기</v-btn
    >
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  props: {
    uid: String,
  },
  methods: {
    ...mapMutations("accounts", ["SET_FOUNDUID"]),
  },
  beforeDestroy() {
    this.SET_FOUNDUID(null);
  },
};
</script>

<style></style>
