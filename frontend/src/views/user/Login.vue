<template>
  <div>
    <v-container class="fill-height" fluid>
      <v-row class="text-center" align="center" justify="center">
        <v-col 
          cols="12"
          sm="8"
          offset-sm="2"
          md="6"
          offset-md="3"
          lg="4"
          offset-lg="4"
        >
          <v-img
            src="@/assets/honeycombo.png"
            width="100%"
          />
        </v-col>
      </v-row>
      <h1 class="display-2 font-weight-bold mb-3">
        Login
      </h1>     
      <v-row class="text-center" align="center" justify="center">
        <v-col 
          cols="12"
          sm="8"
          offset-sm="2"
          md="6"
          offset-md="3"
          lg="4"
          offset-lg="4"
        >
          <v-spacer><br><br></v-spacer>
          <v-text-field
            v-model="id"
            v-bind:class="{error : error.id, complete:!error.id&&id.length!==0}"
            @keyup.enter="Login"
            prepend-icon="mdi-account"
            id="id"
            :rules="idRules"
            label="아이디를 입력하세요"
            required
            autofocus
          ></v-text-field>
          <v-text-field
            v-model="password"
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
            @keyup.enter="Login"
            prepend-icon="mdi-lock"
            id="password"
            :rules="passwordRules"
            label="비밀번호를 입력하세요."
            required
            type="password"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="text-right" align="end" justify="end">
        <v-col 
          cols="12"
          sm="8"
          offset-sm="2"
          md="6"
          offset-md="3"
          lg="4"
          offset-lg="4"
        >
          <div>
            <router-link to="/user/password_choice">비밀번호를 잊으셨나요?</router-link>
          </div>
        </v-col>
      </v-row>
      <v-row class="text-center" align="center" justify="right">
        <v-col
          cols="12"
          sm="8"
          offset-sm="2"
          md="6"
          offset-md="3"
          lg="4"
          offset-lg="4"
        >
          <v-btn
            blue
            :class="{disabled : !isSubmit}"
            :disabled="!isSubmit"
            @click="onLogin"
            width="100%"
            x-large
          >LOGIN</v-btn>

          <div>
            <p>페이스북 트위터 구글</p>
          </div>
        </v-col>
        <v-toolbar color="blue" dense flat min-width="100vw">
          계정이 없습니까?
        </v-toolbar>
        <router-link to="/user/signup">
          <v-footer
            absolute
            class="font-weight-medium"
            color="blue"
            dark
          >
            <v-col
              class="text-center"
              cols="12"
              sm="8"
              offset-sm="2"
              md="6"
              offset-md="3"
              lg="4"
              offset-lg="4"
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
import PV from "password-validator";
// import * as EmailValidator from "email-validator";
// import "../../components/css/user.scss";
// import UserApi from "../../api/UserApi";


export default {
  name: "Login", 
  components: {
  },
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password: function() {
      this.checkForm();
    },
    id: function() {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      // if (this.id.length >= 0)
      //   this.error.id = "아이디를 입력해주세요.";
      // else this.error.id = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    // onLogin() {
    //   if (this.isSubmit) {
    //     let { email, password } = this;
    //     let data = {
    //       email,
    //       password
    //     };

    //     //요청 후에는 버튼 비활성화
    //     this.isSubmit = false;

    //     UserApi.requestLogin(
    //       data,
    //       res => {
    //         //통신을 통해 전달받은 값 콘솔에 출력
    //         //console.log(res);

    //         //요청이 끝나면 버튼 활성화
    //         this.isSubmit = true;

    //         this.$router.push("/main");
    //       },
    //       error => {
    //         //요청이 끝나면 버튼 활성화
    //         this.isSubmit = true;
    //       }
    //     );
    //   }
    // }
  },
  data: () => {
    return {
      id: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      component: this
    };
  }
}
</script>

<style>
</style>