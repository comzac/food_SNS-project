<template>
  <v-container fill-height fluid>
    <v-row class="text-center" align="center" justify="center">
      <v-col cols="12">
        <UserEdit
          v-if="isAuthorized && page == '1'"
          @pwChange="(page = '2'), setPage(2)"
        />
        <PasswordChange
          v-if="isAuthorized && page == '2'"
          @changePassword="doPasswordReset"
          @pageDown="(page = '1'), setPage(1)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cookies from "vue-cookies";
import swal from "sweetalert";
import router from "vue-router";

import { mapActions } from "vuex";
import store from "@/store";

import UserEdit from "@/components/accounts/useredit/UserEdit";
import PasswordChange from "@/components/accounts/passwordchange/PasswordChange";

export default {
  name: "UserEditView",
  components: {
    UserEdit,
    PasswordChange,
  },
  data() {
    return {
      isAuthorized: this.$store.state.accounts.isAuthorized,
      page: cookies.get("page") ? cookies.get("page") : 1,
    };
  },
  methods: {
    ...mapActions("accounts", [
      "setAuthorized",
      "setPage",
      "pwreset",
      "pwcheck",
    ]),
    doPasswordReset(password) {
      var changeData = {};
      changeData.upw = password;
      changeData.uid = this.$store.state.accounts.userSimpleData.uid;
      this.pwreset(changeData);
      this.setPage(1);
    },
  },
  created() {
    this.setAuthorized(false);
    this.setPage(1);
  },
  beforeDestroy() {
    this.setAuthorized(false);
    this.setPage(1);
  },
  mounted() {
    this.$emit("change-page", 1);
  },
  beforeRouteEnter(to, from, next) {
    if (from.name !== "UserProfileEdit") {
      swal({
        title: "비밀번호 입력",
        content: {
          element: "input",
          attributes: {
            placeholder: "계속하려면 비밀번호를 다시 입력해주세요.",
            type: "password",
          },
        },
        icon: "warning",
        dangerMode: true,
        buttons: ["취소", "확인"],
      }).then((value) => {
        // console.log(value);
        if (value) {
          store.dispatch("accounts/pwcheck", value).then((checkResult) => {
            if (checkResult) {
              store.dispatch("accounts/setAuthorized", true);
              next();
            } else {
              swal({
                title: "비밀번호 불일치",
                text: "비밀번호를 다시 한번 확인해주세요.",
                icon: "error",
                dangerMode: true,
                buttons: [null, "확인"],
              }).then(() => router.go(-1));
            }
          });
        } else {
          router.go(-1);
        }
      });
    } else {
      next();
    }
  },
};
</script>

<style></style>
