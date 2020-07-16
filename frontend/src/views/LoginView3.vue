<template>
<div class="login">
      <h1 class="text-center">로그인</h1>
      <div class="login-form">
        <div class="form-group">
            <input class="form-control" @keypress.enter="login(loginData)" v-model="loginData.uid" id="uid" type="text" placeholder="아이디">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="login(loginData)" v-model="loginData.upw" id="upw" type="password" placeholder="비밀번호">
        </div>
        <div class="d-flex justify-content-between">
            <button class="btn btn-primary" @click="login(loginData)">로그인</button>
            <button class="btn btn-success" @click="moveToSignup">회원가입</button>
        </div>
      </div>
  </div>

</template>

<script>
import { mapActions } from 'vuex'
// import store from "@/store/index"

export default {
    name: 'LoginView',
    data() {
        return {
            loginData: {
                uid: null,
                upw: null,
            }
        }
    },
    methods: {
        ...mapActions(['login']),
        moveToSignup() {
            this.$router.push({ name: 'Signup' })
        }
    },
}
</script>

<style>

</style>