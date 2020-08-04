<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ff6666;">이메일로 찾기</h1>
    <br />
    <br />
    <v-text-field
      v-model="id"
      :error-messages="error.id"
      label="ID."
      outlined
      solo
      required
      autofocus
      append-outer-icon="mdi-check"
      @input="idChecked=false"
      @click:append-outer="idCheck3(id)"
      color="#ff6666"
      class="mt-10 mb-7"
      autocomplete="off"
    ></v-text-field>
    <br />
    <br />
    <div>
      <v-btn color="#ff6666" class="white--text" @click="$router.go(-1)">뒤로가기</v-btn>
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        :disabled="!id.length || !idChecked"
        color="#ff6666"
        @click="$emit('toEmailCheck')"
        class="white--text"
      >다음으로</v-btn>
      <v-overlay :value="overlay">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
    </div>
  </v-card>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "IdCheck",
  data: () => {
    return {
      id: "",
      error: {
        id: "아이디를 입력해주세요.",
      },
      idChecked: false,
      overlay: false,
    };
  },
  methods: {
    ...mapActions("accounts", ["idCheck2"]),
    idCheck3(id) {
      this.idCheck2(id).then((res) => {
        console.log("res", res);
        if (res === false) {
          this.idChecked = true;
        } else {
          this.idChecked = false;
        }
      });
    },
  },
};
</script>

<style>
</style>