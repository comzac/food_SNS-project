<template>
  <div>
    <h1>
      회원님의 아이디는
      <br />uid
      <!-- uid -->
      <br />입니다
    </h1>

    <v-btn color="#ff6666" class="white--text" @click="$router.push({name:'Login'})">로그인하기</v-btn>
    <v-divider class="mr-5" vertical></v-divider>
    <v-btn
      @click="$router.push({name: 'PasswordChoice'})"
      color="#ff6666"
      class="white--text"
    >비밀번호찾기</v-btn>
  </div>
</template>

<script>
export default {};
// foundUid 초기화(null) 하기필요
</script>

<style>
</style>