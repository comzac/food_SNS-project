<template>
  <div class="signup">
      <h1 class='text-center'>회원 가입</h1>
      <div class="signup-form">
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.uid" id="uid" type="text" placeholder="아이디">
            <button @click="idCheck(signupData.uid)">중복체크</button>
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.upw" id="upw" type="password" placeholder="비밀번호">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.unick" id="unick" type="text" placeholder="닉네임">
            <button @click="nickCheck(signupData.unick)">중복체크</button>
        </div>
        <div class="form-group"> 
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.ubirth" id="ubirth" type="date" placeholder="생년월일">
        </div>
        <div class="form-group">
            <input class="form-control" @keypress.enter="signup(signupData)" v-model="signupData.usex" id="usex" type="number" placeholder="성별">
        </div>
        <button class="btn btn-primary" @click="toEmailVerification()">다음으로</button>
      </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'BasicForm',
    data() {
        return {
            signupData: {
                uid: null,
                upw: null,
                unick: null,
                uemail: null,
                ubirth: null,
                usex: null,
            },
        }
    },
    watch() {

    },
    methods: {
        ...mapActions(['idCheck', 'nickCheck', 'signupCheck']),
        toEmailVerification() {
            for(const [key, value] of Object.entries(this.signupData)) {
                if(key === 'uemail') continue
                if(value === null) {
                    alert(`${key}를 입력하세요`)
                    return
                }
            }
            if(this.signupCheck({ 'uid': this.signupData.uid, 'unick': this.signupData.unick})) {
                this.$emit('toEmailVerification', this.signupData)
            }
        }
    }

}
</script>

<style>

</style>