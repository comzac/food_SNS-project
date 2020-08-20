<template>
  <v-card class="mx-auto" flat max-width="350" outlined>
    <h2 class="text-left red--text text--lighten-2 ml-3">회원정보 수정</h2>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-card class="mx-auto" flat max-width="975" outlined>
            <v-list-item>
              <v-list-item-avatar
                size="80"
                :color="authUserImgRoute ? 'white' : 'grey'"
              >
                <v-icon x-large v-if="!authUserImgRoute" dark
                  >mdi-account</v-icon
                >
                <v-img v-if="authUserImgRoute" :src="authUserImgRoute" />
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="title">{{
                  authUserUnick
                }}</v-list-item-title>
                <v-list-item-subtitle>{{ authUserUid }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
            <v-list-item>
              <v-card-text class="text-center">{{
                authUserProfileText
              }}</v-card-text>
            </v-list-item>
          </v-card>
        </v-col>
        <v-col cols="12">
          <v-btn @click="toProfileEdit" large color="#5CC4CF" width="100%" dark
            >프로필 변경</v-btn
          >
        </v-col>
        <v-col cols="12">
          <v-btn
            @click="$emit('pwChange')"
            large
            color="#5CC4CF"
            width="100%"
            dark
            >비밀번호 변경</v-btn
          >
        </v-col>
        <v-col cols="12">
          <v-btn
            large
            color="red lighten-2"
            width="100%"
            dark
            @click="deleteUser"
            >회원 탈퇴</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "UserEdit",
  data() {
    return {};
  },
  computed: {
    ...mapGetters("accounts", [
      "authUserImgRoute",
      "authUserUid",
      "authUserUnick",
      "authUserProfileText",
    ]),
  },
  methods: {
    ...mapActions("accounts", ["deleteUser"]),
    toProfileEdit() {
      this.$router.push({
        name: "UserProfileEdit",
        params: { uid: this.authUserUid },
      });
    },
  },
};
</script>

<style scoped>
.theme--light.v-sheet--outlined {
  border: none;
}
</style>
