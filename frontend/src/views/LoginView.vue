<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat width="350" class="mx-auto">
          <h1 class="text-left" style="color: #ff6666;">Login</h1>
          <v-spacer>
            <br />
          </v-spacer>
          <v-text-field
            v-model="loginData.uid"
            prepend-icon="mdi-account"
            label="아이디를 입력하세요"
            required
            autofocus
            color="#ff6666"
            autocapitalize="off"
            autocorrect="off"
            autocomplete="off"
          ></v-text-field>
          <v-text-field
            append-outer-icon
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="show1 = !show1"
            :type="show1 ? 'text' : 'password'"
            v-model="loginData.upw"
            @keyup.enter="login(loginData)"
            prepend-icon="mdi-lock"
            label="비밀번호를 입력하세요."
            required
            color="#ff6666"
            autocomplete="off"
          ></v-text-field>
          <div class="text-right">
            <router-link
              :to="{name: 'RetrieveID'}"
              class="text-decoration-none mr-2"
              style="color: #ff6666;"
            >아이디 찾기</router-link>

            <router-link
              to="/user/password_choice"
              class="text-decoration-none"
              style="color: #ff6666;"
            >비밀번호 찾기</router-link>
          </div>
          <v-spacer>
            <br />
          </v-spacer>
          <v-btn
            class="white--text"
            color="#ff6666"
            @click="login(loginData)"
            :disabled="loginData.uid==0||loginData.upw.length<8"
            width="100%"
            x-large
          >LOGIN</v-btn>
          <v-spacer>
            <br />
          </v-spacer>
          <p style="color: #ff6666;">SNS 로그인</p>
          <v-layout style="color: #ff6666;" justify-space-around>
            <v-spacer></v-spacer>
            <i class="fa fa-facebook" aria-hidden="true"></i>
            <v-spacer></v-spacer>
            <i class="fa fa-google" aria-hidden="true"></i>
            <v-spacer></v-spacer>
            <i class="fa fa-twitter" aria-hidden="true"></i>
            <v-spacer></v-spacer>
          </v-layout>
          <v-spacer>
            <br />
            <br />
          </v-spacer>
          <p style="color: #ff6666;">게정이 없습니까?</p>
          <v-spacer>
            <br />
          </v-spacer>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "LoginView",
  components: {},
  methods: {
    ...mapActions("accounts", ["login", "setPage", "setEmail", "setCode"]),
    moveToSignup() {
      this.$router.push({ name: "Signup" });
    },
  },
  data() {
    return {
      loginData: {
        uid: "",
        upw: "",
      },
      show1: false,
    };
  },
  created() {
    this.setPage(1);
    this.setEmail("");
    this.setCode("");
  },
};
</script>

<style>
</style>