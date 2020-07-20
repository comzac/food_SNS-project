<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col 
        cols="12"
        sm="8"
        md="6"
        lg="4"
      >
        <h1 class="text-left blue--text">Login</h1>
        <v-spacer><br><br><br><br><br><br></v-spacer>
        <v-text-field
          v-model="loginData.uid"
          prepend-icon="mdi-account"
          label="아이디를 입력하세요"
          required
          autofocus
        ></v-text-field>
          <!-- @keyup.enter="login(loginData)" -->
        <v-text-field
          v-model="loginData.upw"
          prepend-icon="mdi-lock"
          label="비밀번호를 입력하세요."
          required
          type="password"
        ></v-text-field>
        <div class="text-right">
          <router-link to="/user/password_choice" class="text-decoration-none blue--text">비밀번호를 잊으셨나요?</router-link>
        </div>
        <v-spacer><br><br><br></v-spacer>
        <v-btn
          class="white--text"
          color="blue"
          @click="login(loginData)"
          :disabled="loginData.uid==0||loginData.upw.length<8"
          width="100%"
          :disabled="loginData.uid==0||loginData.upw.length<8"
          x-large
        >LOGIN</v-btn>
        <v-spacer><br><br></v-spacer>
        <p class="blue--text">SNS 로그인</p>
        <v-layout class="blue--text" justify-space-around>
          <i class="fa fa-naver" aria-hidden="true"></i>
          <i class="fa fa-facebook" aria-hidden="true"></i>
          <i class="fa fa-google" aria-hidden="true"></i>
          <i class="fa fa-twitter" aria-hidden="true"></i>
          <i class="fa fa-kakao" aria-hidden="true"></i>
        </v-layout>
        <v-spacer><br><br></v-spacer>
        <p class="blue--text">게정이 없습니까?</p>
      </v-col>
      
      <router-link to="/signup">
        <v-footer
          absolute
          class="font-weight-medium"
          color="blue"
          dark
        >
          <v-col
            class="text-center"
            cols="12"
          >
          <strong>SIGN UP</strong>
          </v-col>
        </v-footer>
      </router-link>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'


export default {
  name: "LoginView", 
  components: {
  },
  methods: {
    ...mapActions(['login']),
    moveToSignup() {
        this.$router.push({ name: 'Signup' })
    },
  },
  data() {
    return {
      loginData: {
        uid:"",
        upw:"",
      },
    };
  }
}
</script>

<style>
</style>