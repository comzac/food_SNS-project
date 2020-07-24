<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col cols="12" sm="8" md="6" lg="4">
        <h1 class="text-left">Retrieve Your Password</h1>
        <v-spacer>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
        </v-spacer>
        <router-link to="/user/password_choice_email" class="text-decoration-none">
          <v-btn class="white--text" color="#ff6666" rounded width="100%" x-large>
            <v-spacer></v-spacer>이메일로 찾기
            <v-spacer></v-spacer>
            <i class="fa fa-envelope" aria-hidden="true"></i>
          </v-btn>
        </router-link>
        <v-spacer>
          <br />
          <br />
          <br />
        </v-spacer>
        <router-link to="/user/password_change" class="text-decoration-none">
          <v-btn class="white--text" color="#ff6666" rounded width="100%" x-large>
            <v-spacer></v-spacer>휴대폰으로 찾기
            <v-spacer></v-spacer>
            <i class="fa fa-mobile" aria-hidden="true"></i>
          </v-btn>
        </router-link>
        <v-spacer>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
        </v-spacer>
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