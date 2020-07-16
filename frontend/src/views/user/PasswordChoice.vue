<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center" no-gutters>
      <v-col
        cols="12"
        sm="8"
        md="6"
        lg="4"
      > 
        <h1 class="text-left blue--text">Retrieve Your Password</h1>
        <v-spacer><br><br><br><br><br><br><br><br><br></v-spacer>
        <router-link to="/user/password_choice_email" class="text-decoration-none">
          <v-text-field background-color="blue" filled solo rounded append-icon="mdi-email" value="이메일로 찾기" readonluy type="button"></v-text-field>
        </router-link>
        <v-spacer><br><br><br></v-spacer>
        <router-link to="/user/password_change" class="text-decoration-none">
          <v-text-field background-color="blue" filled solo rounded append-icon="mdi-cellphone" value="휴대폰으로 찾기" readonluy type="button"></v-text-field>
        </router-link>        
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "PasswordChoice",
}
</script>

<style>
</style>