<template>
  <v-card class="mx-auto" flat max-width="350">
    <h1 class="text-left ml-3" style="color:#ea907a;">비밀번호 찾기</h1>
    <div class="text-right">
      <router-link
        :to="{ name: 'RetrieveID' }"
        class="text-decoration-none mr-2"
        style="color: #ea907a;"
        >아이디 찾기</router-link
      >
    </div>
    <br />
    <br />
    <v-text-field
      v-model="id"
      :messages="error.id"
      label="ID."
      outlined
      solo
      required
      autofocus
      @input="idChecked = false"
      color="#424242"
      class="mt-10 mb-7"
      autocomplete="off"
    >
      <v-icon
        slot="append"
        :color="idChecked ? '' : '#ea907a'"
        @click="idCheck3(id)"
        >mdi-check</v-icon
      >
    </v-text-field>
    <br />
    <br />
    <div>
      <v-btn color="grey" class="white--text" @click="$router.go(-1)"
        >뒤로가기</v-btn
      >
      <v-divider class="mr-5" vertical></v-divider>
      <v-btn
        :disabled="!id.length || !idChecked"
        color="#ea907a"
        @click="$emit('toEmailCheck', id), setUid(id)"
        class="white--text"
        >다음으로</v-btn
      >
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
    ...mapActions("accounts", ["idCheck2", "setUid"]),
    idCheck3(id) {
      this.idCheck2(id).then((res) => {
        // console.log("res", res);
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

<style></style>
