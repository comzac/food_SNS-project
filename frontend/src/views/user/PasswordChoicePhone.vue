<template>
  <v-container class="fill-height" fluid>
    <h1>Retrieve Your Password</h1>
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
        <router-link to="/user/password_change">
          <v-text-field dark solo rounded append-icon="mdi-email" value="이메일로 찾기" readonluy type="button"></v-text-field>
        </router-link>
        <v-spacer><br><br><br><br></v-spacer>
        <router-link to="/user/password_change">
          <v-text-field dark solo rounded append-icon="mdi-cellphone" value="휴대폰으로 찾기" readonluy type="button"></v-text-field>
        </router-link>        
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