<template>
  <v-col cols="12" sm="8" md="6" lg="4">
    <h2 class="text-left red--text text--lighten-2 ml-3">프로필 수정</h2>
    <!-- 프로필 사진 -->
    <!-- 프로필 문구 -->
    <v-text-field
      label="별명"
      name="nickname"
      prepend-icon="mdi-account-box"
      type="text"
      v-model="unick"
      color="#ff6666"
      append-outer-icon="mdi-check"
      @click:append-outer="nickCheck2(unick)"
      :error-messages="nickcheck?'':'오른쪽의 체크를 눌러 중복확인해주세요'"
      @input="nickcheck = false"
    ></v-text-field>
  </v-col>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      unick: "",
      uprofileText: "",
      uprofileImg: null,
      nickcheck: false,
    };
  },
  methods: {
    ...mapActions("accounts", ["nickCheck"]),
    nickCheck2(unick) {
      // if분기로 받아온 unick과 입력한 값이 다를 때만 nickCheck 하도록
      this.nickCheck(unick).then((res) =>
        res ? (this.nickcheck = true) : (this.nickcheck = false)
      );
    },
  },
};
</script>

<style>
</style>