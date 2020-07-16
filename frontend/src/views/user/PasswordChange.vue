<template>
  <v-container class="fill-height" fluid>
    <h1>Change Your Password</h1>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        offset-sm="2"
        md="6"
        offset-md="3"
        lg="4"
        offset-lg="4"
      > 
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>
        <v-text-field
          v-model="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          :error-messages="error.password"
          label="Password."
          reverse
          outlined
          solo
          required
          type="password"
        ></v-text-field>
        <v-spacer><br><br><br><br></v-spacer>
                <v-text-field
          v-model="passwordConfirm"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
          id="passwordConfirm"
          :error-messages="error.passwordConfirm"
          label="Password Confirmation"
          reverse
          outlined
          solo
          required
          type="password"
        ></v-text-field>       
        <v-spacer><br><br><br><br></v-spacer>
        <v-btn
          :class="{disabled : !isSubmit}"
          :disabled="!isSubmit"
          width="100%"
          x-large
        >비밀번호 변경</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PV from "password-validator";

export default {
  name: "PasswordChange",
  created() {
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
    passwordConfirm: function() {
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

      if (
        this.passwordConfirm.length >= 0 &&
        !this.passwordSchema.validate(this.passwordConfirm) &&
        this.passwordConfirm != this.password
      )
        this.error.passwordConfirm = "영문,숫자 포함 8 자리이상이며 위와 같아야 합니다";
      else this.error.passwordConfirm = false;
      
      let isSubmit = true;
      
      if (this.password != this.passwordConfirm) {
        isSubmit = false;
      }

      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
  },
  data: () => {
    return {
      password: "",
      passwordConfirm: "",
      passwordSchema: new PV(),
      error: {
        password: false,
        passowrdConfirm: false
      },
      isSubmit: false,
      component: this
    };
  }
}
</script>

<style>

</style>