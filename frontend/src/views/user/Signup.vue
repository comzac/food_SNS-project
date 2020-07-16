
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <v-container class="fill-height" fluid>
    <h1>Create an Account</h1>
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
        <v-text-field
          v-model="id"
          v-bind:class="{error : error.id, complete:!error.id&&id.length!==0}"
          id="id"
          :rules="idRules"
          label="Id"
          required
          reverse 
          solo
          outlined
          autofocus
        ></v-text-field>
        
        <v-text-field
          v-model="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          :rules="passwordRules"
          label="Password"
          required
          type="password"
          reverse 
          solo
          outlined
        ></v-text-field>

        <v-text-field
          v-model="passwordConfirm"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
          id="passwordConfirm"
          :rules="passwordRules"
          label="Password Confirmation"
          required
          type="password"
          reverse 
          solo
          outlined
        ></v-text-field>

        <v-text-field
          v-model="nickName"
          v-bind:class="{error : error.nickName, complete:!error.nickName&&nickName.length!==0}"
          id="nickName"
          :rules="nickNameRules"
          label="Nickname"
          required
          reverse 
          solo
          outlined
        ></v-text-field>

        <v-text-field
          v-model="dateOfBirth"
          v-bind:class="{error : error.dateOfBirth, complete:!error.dateOfBirth&&dateOfBirth.length!==0}"
          id="dateOfBirth"
          :rules="dateOfBirthRules"
          label="Date of Birth"
          required
          reverse 
          solo
          outlined
          maxlength="6"
          oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
          hint="900000"
        ></v-text-field>

        <v-text-field
          v-model="gender"
          v-bind:class="{error : error.gender, complete:!error.gender&&gender.length!==0}"
          id="gender"
          :rules="genderRules"
          label="Gender"
          required
          reverse 
          solo
          outlined
          readonly
        ></v-text-field>

        <v-radio-group v-model="gender" row>
          <v-radio
            label="male"
            value="male"
          ></v-radio>
          <v-radio
            label="female"
            value="female"
          ></v-radio>
        </v-radio-group>

        <v-btn
          color="blue"
          block
          height="56px"
        >
          ->
        </v-btn>
        
      </v-col>
    </v-row>
  
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      id: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      dateOfBirth: "",
      gender: "",
      isTerm: false,
      isLoading: false,
      error: {
        id: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        dateOfBirth: false,
        gender: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  }
};
</script>


