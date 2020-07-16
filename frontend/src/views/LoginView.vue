<template>
  <div>
    <v-container class="fill-height" fluid>
      <v-row class="text-center" align="center" justify="center">
        <v-col 
          cols="12"
          sm="8"
          md="6"
          lg="4"
        >
          <v-img
            src="@/assets/honeycombo.png"
            width="100%"
          />
        </v-col>
      </v-row>
      <v-row class="text-center" align="center" justify="center">
        <v-col 
          cols="12"
          sm="8"
          md="6"
          lg="4"
        >
          <h1 class="text-left display-2 font-weight-bold mb-3">
            Login
          </h1>     
          <v-text-field
            v-model="loginData.uid"
            prepend-icon="mdi-account"
            label="아이디를 입력하세요"
            required
            autofocus
          ></v-text-field>
          <v-text-field
            v-model="loginData.upw"
            @keyup.enter="login(loginData)"
            prepend-icon="mdi-lock"
            label="비밀번호를 입력하세요."
            required
            type="password"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="text-right" align="end" justify="center">
        <v-col 
          cols="12"
          sm="8"
          md="6"
          lg="4"
        >
          <router-link to="/user/password_choice" class="text-decoration-none blue--text">비밀번호를 잊으셨나요?</router-link>
        </v-col>
      </v-row>
      <v-row class="text-center" align="end" justify="center">
        <v-col 
          cols="12"
          sm="8"
          md="6"
          lg="4"
        >
          <v-btn
            class="white--text"
            color="blue"
            @click="login(loginData)"
            width="100%"
            x-large
          >LOGIN</v-btn>
          <v-spacer><br></v-spacer>
          <p>페이스북   트위터   구글</p>
          <p>게정이 없습니까?</p>
          <v-spacer><br></v-spacer>
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
  </div>
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