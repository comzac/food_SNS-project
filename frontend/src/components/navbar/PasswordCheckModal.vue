<template>
  <v-dialog v-model="dialog" max-width="600px">
    <template v-slot:activator="{ on, attrs }">
      <v-list-item v-bind="attrs" v-on="on">
        <v-list-item-icon>
          <v-icon>{{ listItem.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-title>{{ listItem.title }}</v-list-item-title>
      </v-list-item>
    </template>
    <v-card>
      <v-card-title>
        <span class="headline mt-4 ml-2">비밀번호 확인</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="password"
                label="비밀번호"
                type="password"
                color="#ff6666"
                hint="계속하려면 비밀번호를 다시 입력하세요"
                persistent-hint
                required
                autofocus
                @keypress.enter="passwordCheck"
                autocomplete="off"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="grey"
          class="white--text"
          width="40%"
          @click="(dialog = false), $emit('clear-item')"
          >취소</v-btn
        >
        <v-spacer></v-spacer>
        <v-btn
          :disabled="tooShortPassword"
          @click="passwordCheck"
          color="#ff6666"
          width="40%"
          class="white--text"
          >진행</v-btn
        >
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import swal from "sweetalert";

import { mapActions } from "vuex";
export default {
  name: "PasswordCheckModal",
  data() {
    return {
      dialog: false,
      password: "",
    };
  },
  props: {
    listItem: Object,
  },
  methods: {
    ...mapActions("accounts", ["pwcheck", "setAuthorized"]),
    passwordCheck() {
      if (this.password.length >= 8) {
        this.pwcheck(this.password).then((checkResult) => {
          if (checkResult) {
            this.setAuthorized(true);
            this.$router.push({ name: "UserEdit" });
          } else {
            swal({
              title: "비밀번호 불일치",
              text: "비밀번호를 다시 한번 확인해주세요.",
              icon: "error",
              dangerMode: true,
              buttons: [null, "확인"],
            });
          }
          this.password = "";
        });
      }
    },
    clearPassword() {
      if (!this.dialog) this.password = "";
    },
  },
  computed: {
    tooShortPassword() {
      if (this.password.length < 8) return true;
      else return false;
    },
  },
  updated() {
    this.clearPassword();
  },
};
</script>

<style></style>
