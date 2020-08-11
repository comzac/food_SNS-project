<template>
  <v-container class="signup" fill-height>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <h2 class="text-left red--text text--lighten-2 ml-3 mb-8">추가정보 입력</h2>
        <v-card class="mx-auto" flat max-width="350">
          <v-text-field
            label="생년월일"
            name="dob"
            prepend-icon="mdi-cake-variant"
            v-model="additionalData.ubirth"
            :error-messages="additionalData.ubirth ? '' : '생년월일을 입력해주세요'"
            type="date"
            color="#ff6666"
            autocomplete="off"
          ></v-text-field>
          <v-radio-group v-model="additionalData.usex" row>
            <v-btn icon color="#ff6666">
              <v-icon>mdi-gender-male-female</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-radio color="#ff6666" label="남성" value="1"></v-radio>
            <v-btn icon :color="additionalData.usex == 1 ? '#ff6666' : ''">
              <v-icon>mdi-gender-male</v-icon>
            </v-btn>
            <v-radio color="#ff6666" label="여성" value="2"></v-radio>
            <v-btn icon :color="additionalData.usex == 2 ? '#ff6666' : ''">
              <v-icon>mdi-gender-female</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
          </v-radio-group>
          <div class="mt-5">
            <v-btn color="#ff6666" class="white--text" @click="$router.go(-1)">뒤로가기</v-btn>
            <v-divider class="mr-5" vertical></v-divider>
            <v-btn
              :disabled="!additionalData.ubirth || !additionalData.usex"
              color="#ff6666"
              class="white--text"
              @click="conductAndReturn"
            >다음으로</v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      additionalData: {
        ubirth: null,
        usex: null,
      },
    };
  },
  methods: {
    ...mapActions("accounts", ["setAdditionalUserData"]),
    conductAndReturn() {
      this.setAdditionalUserData(this.additionalData);
      this.$router.go(-1);
    },
  },
};
</script>

<style>
</style>