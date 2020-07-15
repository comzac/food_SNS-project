<template>
  <div class="signup">
      <h1 class='text-center'>회원 가입</h1>
      <div class="signup-form">
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.uid" id="uid" type="text" placeholder="아이디">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.upw" id="upw" type="password" placeholder="비밀번호">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.unick" id="unick" type="text" placeholder="닉네임">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.uemail" id="uemail" type="text" placeholder="이메일">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.ubirth" id="ubirth" type="date" placeholder="생년월일">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.usex" id="usex" type="number" placeholder="성별">
        </div>
        <button class="btn btn-primary" @click="signup(signupData)">회원 가입</button>
      </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'SignupView',
    data() {
        return {
            signupData: {
                uid: null,
                upw: null,
                unick: null,
                uemail: null,
                ubirth: null,
                usex: null,
            }
        }
    },
    methods: {
        ...mapActions(['signup'])
    },
}
</script>

<style>

</style>