<template>
  <v-card class="mx-auto" flat max-width="975" outlined>
    <h2 class="text-left red--text text--lighten-2 ml-3">회원정보 수정</h2>
    <br />
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-btn large color="#2699fb" width="100%" dark>비밀번호 변경</v-btn>
        </v-col>
        <v-col cols="12">
          <v-btn large color="#2699fb" width="100%" dark>이메일 변경</v-btn>
        </v-col>
        <v-col cols="12">
          <v-btn large color="#2699fb" width="100%" dark>차단 목록 관리</v-btn>
        </v-col>
        <v-col cols="6" class="mt-10">
          <v-btn large color="#2699fb" width="100%" dark>공개</v-btn>
        </v-col>
        <v-col cols="6" class="mt-10">
          <v-btn large width="100%" disabled>비공개</v-btn>
          <!-- 추후에 공개 계정인지 비공개 계정인지에 따라 조정되도록 변경 필요 -->
        </v-col>
        <v-col cols="12" class="mt-10">
          <v-btn large color="#D50000" width="100%" dark @click="deleteUser">회원 탈퇴</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions("accounts", ["deleteUser"]),
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>