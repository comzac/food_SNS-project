<template>
  <v-row class="text-center" align="center" justify="center">
    <v-col cols="12">
      <h2 class="text-left red--text text--lighten-2 ml-3 mb-8">
        추가정보 입력
      </h2>
      <v-card class="mx-auto" flat max-width="350">
        <v-text-field
          label="생년월일"
          name="dob"
          prepend-icon="mdi-cake-variant"
          v-model="additionalData.ubirth"
          :error-messages="
            additionalData.ubirth ? '' : '생년월일을 입력해주세요'
          "
          onkeydown="return false"
          type="date"
          color="#ea907a"
          autocomplete="off"
          max="2100-12-31"
          min="1900-01-01"
        ></v-text-field>
        <v-radio-group v-model="additionalData.usex" row>
          <v-btn icon color="#ea907a">
            <v-icon>mdi-gender-male-female</v-icon>
          </v-btn>
          <v-spacer></v-spacer>
          <v-radio color="#ea907a" label="남성" value="1"></v-radio>
          <v-btn icon :color="additionalData.usex == 1 ? '#ea907a' : ''">
            <v-icon>mdi-gender-male</v-icon>
          </v-btn>
          <v-radio color="#ea907a" label="여성" value="2"></v-radio>
          <v-btn icon :color="additionalData.usex == 2 ? '#ea907a' : ''">
            <v-icon>mdi-gender-female</v-icon>
          </v-btn>
          <v-spacer></v-spacer>
        </v-radio-group>
        <div class="mt-5">
          <v-btn
            color="grey"
            v-if="$route.name === 'SocialLoginDataInput'"
            class="white--text"
            @click="$emit('pageDown')"
            >뒤로가기</v-btn
          >
          <v-btn
            color="grey"
            v-if="$route.name !== 'SocialLoginDataInput'"
            class="white--text"
            @click="$router.go(-1)"
            >뒤로가기</v-btn
          >
          <v-divider class="mr-5" vertical></v-divider>
          <v-btn
            v-if="$route.name === 'SocialLoginDataInput'"
            :disabled="!additionalData.ubirth || !additionalData.usex"
            color="#ea907a"
            class="white--text"
            @click="conductAndReturn"
            >가입완료</v-btn
          >
          <v-btn
            v-if="$route.name !== 'SocialLoginDataInput'"
            :disabled="!additionalData.ubirth || !additionalData.usex"
            color="#ea907a"
            class="white--text"
            @click="conductAndReturn"
            >다음으로</v-btn
          >
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapActions } from "vuex";
import swal from "sweetalert";

export default {
  data() {
    return {
      additionalData: {
        ubirth: null,
        usex: null,
      },
    };
  },
  methods: {
    ...mapActions("accounts", ["setAdditionalUserData"]),
    conductAndReturn() {
      this.setAdditionalUserData(this.additionalData);
      if (this.$route.name === "SocialLoginDataInput") {
        swal("가입되었습니다!", "허니콤보에 오신 것을 환영합니다.", "success");
        this.$router.push({ name: "Home" });
      } else {
        this.$router.go(-1);
      }
    },
  },
};
</script>

<style></style>
