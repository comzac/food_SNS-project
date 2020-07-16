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
        <v-text-field
          v-model="confirm"
          v-bind:class="{error : error.confirm, complete:!error.confirm&&confirm.length!==0}"
          id="confirm"
          :error-messages="error.confirm"
          label="Verification Code"
          reverse
          outlined
          solo
          required
        ></v-text-field>
        <v-spacer><br><br><br><br><br><br><br><br></v-spacer>

        <v-btn
          @click="move()"
          width="100%"
          x-large
        >-></v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import router from '@/router'

export default {
  name: "PasswordChoiceEmailVerification",
  created() {
    this.component = this;
  },
  methods: {
    move() {
      // axios 보내고
      // 인증 완료 되서 넘어 오면
      router.push({ name: 'PasswordChange'})
    },
  },
  data: () => {
    return {
      confirm: "",
      error: {
        confirm: false,
      },
      component: this
    };
  }
}
</script>

<style>

</style>