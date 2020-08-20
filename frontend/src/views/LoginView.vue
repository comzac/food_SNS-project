<template>
  <v-container fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <v-card flat width="350" class="mx-auto">
          <h2 class="text-right mt-n10">색다르게</h2>
          <h2 class="text-right">맛있는 음식을</h2>
          <h2 class="text-right">먹고싶은</h2>
          <h2 class="text-right">자취생이라면</h2>
          <h1 class="text-left ml-1" style="color: #ea907a;">로그인</h1>
          <v-spacer>
            <br />
          </v-spacer>
          <v-text-field
            v-model="loginData.uid"
            prepend-icon="mdi-account"
            label="아이디를 입력하세요"
            required
            autofocus
            color="#ea907a"
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
            color="#ea907a"
            autocomplete="off"
          ></v-text-field>
          <div class="text-right">
            <router-link
              :to="{ name: 'RetrieveID' }"
              class="text-decoration-none mr-2"
              style="color: #ea907a;"
              >아이디 찾기</router-link
            >

            <router-link
              to="/user/password_choice_email"
              class="text-decoration-none"
              style="color: #ea907a;"
              >비밀번호 찾기</router-link
            >
          </div>
          <v-spacer>
            <br />
          </v-spacer>
          <v-btn
            class="white--text"
            color="#ea907a"
            @click="login(loginData)"
            :disabled="loginData.uid == 0 || loginData.upw.length < 8"
            width="100%"
            x-large
            >로그인</v-btn
          >
          <v-spacer>
            <br />
          </v-spacer>
          <v-layout style="color: #ea907a;" justify-space-around>
            <v-sheet
              @click="googleLogin"
              elevation="6"
              class="d-flex justify-space-around align-center px-5 py-1 social-icon"
            >
              <v-avatar class="mr-3 ml-n2">
                <v-img
                  max-width="30"
                  contain
                  src="@/assets/google-login.png"
                  alt="Google"
                />
              </v-avatar>
              <h4 class="grey--text text--darken-2 mb-1">
                구글 계정으로 로그인
              </h4>
            </v-sheet>
          </v-layout>
        </v-card>
      </v-col>
    </v-row>
    <v-overlay :value="overlay">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";
import SERVER from "@/api/api";

export default {
  name: "LoginView",
  components: {},
  data() {
    return {
      overlay: false,
      loginData: {
        uid: "",
        upw: "",
      },
      show1: false,
      // kakaoImgData:
      //   "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",
      // kakaoImgType: "image/png",
    };
  },
  methods: {
    ...mapActions("accounts", [
      "login",
      "setPage",
      "setEmail",
      "setCode",
      "setUid",
      "postAuthData",
    ]),
    login2(loginData) {
      this.overlay = true;
      this.login(loginData);
      this.overlay = false;
    },
    moveToSignup() {
      this.$router.push({ name: "Signup" });
    },
    googleLogin() {
      const option = {
        scope: "profile email",
        prompt: "select_account",
      };
      this.$gAuth
        .signIn(option)
        .then((googleUser) => {
          const tokens = {
            access_token: googleUser.getAuthResponse().access_token,
            id_token: googleUser.getAuthResponse().id_token,
          };
          const route =
            SERVER.ROUTES.accounts.URL + SERVER.ROUTES.accounts.google;
          const info = {
            route: route,
            data: tokens,
          };
          // console.log("googleUser info : ", tokens);
          // console.log(route);
          this.postAuthData(info);
        })
        .catch((err) => console.log(err));
    },
  },
  created() {
    this.setPage(1);
    this.setEmail("");
    this.setCode("");
    this.setUid("");
  },
};
</script>

<style scoped>
.social-icon {
  cursor: pointer;
}

.social-icon:hover {
  opacity: 0.4;
}

/* .google:hover {
  border: 1px solid #ea907a !important;
}

.naver:hover {
  border: 1px solid #089345 !important;
} */
</style>
