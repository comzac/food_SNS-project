<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
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
        <v-card flat class="ma-auto" max-width="350">
          <h1 class="text-left ml-3" style="color:#ff6666;">비밀번호 찾기</h1>
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
        </v-card>
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