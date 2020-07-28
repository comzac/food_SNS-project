<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-btn
          fab
          color="#ff6666"
          dark
          top
          left
          absolute
          class="mt-11"
          @click="$router.push({ name: 'Login' })"
        >
          <v-icon>mdi-arrow-left-bold</v-icon>
        </v-btn>
        <h1 class="text-left red--text text--lighten-2 ml-3">비밀번호 찾기</h1>
        <br />

        <router-link to="/user/password_choice_email" class="text-decoration-none">
          <v-btn class="white--text my-10" color="#ff6666" rounded width="100%" x-large>
            <v-spacer></v-spacer>이메일로 찾기
            <v-spacer></v-spacer>
            <i class="fa fa-envelope" aria-hidden="true"></i>
          </v-btn>
        </router-link>

        <router-link to="/user/password_change" class="text-decoration-none">
          <v-btn class="white--text my-10" color="#ff6666" rounded width="100%" x-large>
            <v-spacer></v-spacer>휴대폰으로 찾기
            <v-spacer></v-spacer>
            <i class="fa fa-mobile" aria-hidden="true"></i>
          </v-btn>
        </router-link>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "PasswordChoiceView",
};
</script>

<style>
</style>